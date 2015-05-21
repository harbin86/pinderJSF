
package sg.com.pinder.database;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.log4j.Logger;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import sg.com.pinder.exception.MongoDBException;
import sg.com.pinder.exception.NoDatabaseException;
import sg.com.pinder.util.Security;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

/**
 * Builds a MongoDatabase object
 * 
 * @author Han Liang Wee, Eric
 * @version 07/12/2013
 */
public class MongoDatabaseBuilder {

	static Logger logger = Logger.getLogger(MongoDatabaseBuilder.class);
	private static MongoDatabaseBuilder inst;
	
	private static String dbName = null;
	
	static Morphia morphia;
	static Datastore ds;

	private static List<MongoClient> switchingDriver;
	static List<String> serverNameList;
	private static MongoClientOptions options;
	private static XMLConfiguration config;

	private static int choice = 0;

	private MongoDatabaseBuilder() {
	}

	private MongoDatabaseBuilder(String configFilePath) throws MongoDBException ,UnknownHostException, InstantiationException{
		
		try {
			
			// LOAD MONGODB config options
			config = new XMLConfiguration(configFilePath);
			
			dbName = config.getString("name");
			logger.info("Database for "+ dbName+" initializing...");

			// Initialize array
			switchingDriver = new ArrayList<MongoClient>();
			serverNameList = new ArrayList<String>();
			
			// get all credentials
			List<MongoCredential> credentialList = new ArrayList<MongoCredential>(); 
			List<Object> databaseList = config.getList("credentials.credential.database");
			List<Object> usernameList = config.getList("credentials.credential.username");
			List<Object> passwordList = config.getList("credentials.credential.password");
			
			String eaDatabase, eaUser, eaPassword; 
			
			if(databaseList.size()==usernameList.size()&&usernameList.size()==passwordList.size()) {
				for(int x=0; x<databaseList.size(); x++) {
					eaDatabase = (String) databaseList.get(x);
					eaUser = (String) usernameList.get(x);
					eaPassword = Security.decrypt((String) passwordList.get(x));
					
					logger.info("Credential["+eaUser+"@"+eaDatabase+"]");
					
					credentialList.add(MongoCredential.createMongoCRCredential(eaUser, eaDatabase, eaPassword.toCharArray()));
				}
			} else {
				throw new InstantiationException("Each credential must consist of database, username and password.");
			}
			
			// check the server configurations
			Iterator<String> iteString = config.getKeys("mode");
			while (iteString.hasNext()) {
				String currentKey = iteString.next();
				String serverName = currentKey.split("\\.")[1];

				if (config.getBoolean(currentKey)) {
					if (config.getString(serverName + "[@replicaset]") != null && config.getString(serverName + "[@replicaset]").equals("true")) {
						logger.info("Initialize MongoDB(replica-set)-" + serverName);
						switchingDriver.add(readAndBuildReplica(credentialList, serverName));
						serverNameList.add(serverName);
					} else {
						logger.info("Initialize MongoDB(single)-" + serverName);
						switchingDriver.add(readAndBuildOne(credentialList, serverName));
						serverNameList.add(serverName);
					}
				}
			}

			//Morphia setup
			morphia = new Morphia();
			
			morphia.mapPackage(config.getString("package"));
			ds = morphia.createDatastore(switchingDriver.get(choice), dbName);
			//Experimental - Eric Han
/*			ds.setDefaultWriteConcern(WriteConcern.NONE);*/
			ds.ensureIndexes();
			
		} catch (ConfigurationException cex) {
			logger.error(cex.getMessage(), cex);
			throw new MongoDBException(cex.getMessage(), cex);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new MongoDBException(e.getMessage(), e);
		}
	}

	/**
	 * Factory method to get instance of configuration to work with it, with default database.xml location
	 * @return itself
	 * @throws MongoDBException when there is no mongodatabase
	 * @throws UnknownHostException when there is a unknown host and cannot connect
	 * @throws InstantiationException when the object has some conflicts
	 */
	public static MongoDatabaseBuilder getInstance() throws MongoDBException, UnknownHostException, InstantiationException {
		return getInstance("database.xml");
	}

	/**
	 * Factory method to get instance of configuration to work with it
	 * @param configFilePath the config path of the database.xml
	 * @return itself
	 * @throws MongoDBException when there is no mongodatabase
	 * @throws UnknownHostException when there is a unknown host and cannot connect
	 * @throws InstantiationException when the object has some conflicts
	 */
	public static MongoDatabaseBuilder getInstance(String configFilePath) throws MongoDBException, UnknownHostException, InstantiationException
	{
		if (inst == null) {
			inst = new MongoDatabaseBuilder(configFilePath);
		}

		return inst;
	}
	
	/**
	 * Factory method to get instance of configuration to work with it, with default database.xml location with config options
	 * @param options MongoClientOptions
	 * @return itself
	 * @throws MongoDBException when there is no mongodatabase
	 * @throws UnknownHostException when there is a unknown host and cannot connect
	 * @throws InstantiationException when the object has some conflicts
	 */
	public static MongoDatabaseBuilder getInstance(MongoClientOptions options) throws MongoDBException, UnknownHostException, InstantiationException {
		MongoDatabaseBuilder.options=options;
		return getInstance();
	}

	/**
	 * Factory method to get instance of configuration to work with it with config options
	 * @param options MongoClientOptions
	 * @param configFilePath the config path of the database.xml
	 * @return itself
	 * @throws MongoDBException when there is no mongodatabase
	 * @throws UnknownHostException when there is a unknown host and cannot connect
	 * @throws InstantiationException when the object has some conflicts
	 */
	public static MongoDatabaseBuilder getInstance(MongoClientOptions options, String configFilePath) throws MongoDBException, UnknownHostException, InstantiationException
	{
		MongoDatabaseBuilder.options=options;
		return getInstance(configFilePath);
	}
	
	private static MongoClient buildDriver(List<MongoCredential> credentials, String address, int port, MongoClientOptions options)  throws UnknownHostException, InstantiationException
	{
		List<String> addressList = new ArrayList<String>();
		addressList.add(address);

		List<Integer> portList = new ArrayList<Integer>();
		portList.add(port);

		return buildDriver(credentials, addressList, portList, options);
	}

	private static MongoClient buildDriver(List<MongoCredential> credentials, List<String> address, List<Integer> port, MongoClientOptions options) throws UnknownHostException, InstantiationException
	{
		if (address.size() == port.size()) {
			MongoClient mongoDriver = null;
			List<ServerAddress> serverAddress = new ArrayList<ServerAddress>();

			Iterator<String> addressIterator = address.iterator();
			Iterator<Integer> portIterator = port.iterator();

			while (addressIterator.hasNext()) {

				ServerAddress thisServer = null;
				
				thisServer = new ServerAddress(addressIterator.next(), portIterator.next());
				logger.info("Connect to: " + thisServer);
				
				serverAddress.add(thisServer);
			}

			if (options == null)
			{
				logger.info("No MongoDB options.");
				mongoDriver = new MongoClient(serverAddress, credentials);
			} else {
				logger.info("MongoDB Options Loaded.");
				mongoDriver = new MongoClient(serverAddress, credentials, options);
			}
			
			return mongoDriver;
		} else {
			logger.fatal("Error in address and port config.");
			throw new InstantiationException("Error in address and port");
		}
	}

	private static MongoClient readAndBuildOne(List<MongoCredential> credentials, String serverName) throws UnknownHostException, InstantiationException {
		String localName = config.getString(serverName + ".server");
		String localAddress = config.getString(serverName + ".ipaddress");
		int localPort = config.getInt(serverName + ".port");
		logger.info("Load Config:" + localName + "@" + localAddress + ":" + localPort);
		return buildDriver(credentials, localAddress, localPort, options);
	}

	private static MongoClient readAndBuildReplica(List<MongoCredential> credentials, String serverName) throws UnknownHostException, InstantiationException {
		String serverPrimary = serverName + ".primary";
		String serverSeconary = serverName + ".secondaries.secondary";

		List<String> name = new ArrayList<String>();
		List<String> address = new ArrayList<String>();
		List<Integer> port = new ArrayList<Integer>();

		name.add(config.getString(serverPrimary + ".server"));
		address.add(config.getString(serverPrimary + ".ipaddress"));
		port.add(config.getInt(serverPrimary + ".port"));

		List<Object> secName = config.getList(serverSeconary + ".server");
		List<Object> secAddress = config.getList(serverSeconary + ".ipaddress");
		List<Object> secPort = config.getList(serverSeconary + ".port");

		if (secName.size() == secAddress.size() && secAddress.size() == secPort.size()) {
			for (int x = 0; x < secName.size(); x++) {
				name.add((String) secName.get(x));
				address.add((String) secAddress.get(x));
				port.add(Integer.parseInt((String) secPort.get(x)));
			}
		} else {
			throw new NoSuchElementException("'" + serverName + "' config is incomplete.");
		}

		return buildDriver(credentials, address, port, options);
	}

	/**
	 * Gets a specific server to connect
	 * @param serverName The server name as stated in the config file
	 * @return the Mongodatabase itself
	 * @throws NoDatabaseException when there is no database initialized
	 */
	public MongoDatabaseBuilder getServer(String serverName) throws NoDatabaseException {
		int choice = serverNameList.indexOf(serverName);
		if(choice == -1)
		{
			throw new NoDatabaseException("Database not initialized.");
		} else {
			MongoDatabaseBuilder.choice = choice;
			return this;
		}
	}

	/**
	 * Builds a mongodatabase object to be used
	 * @param dbName database name
	 * @param objectClass object class in which the mongodatabase object will be storing to database
	 * @return the mongodatabase object
	 * @throws NoDatabaseException when there is no database initialized
	 * @throws ClassNotFoundException 
	 */
	public <T> MongoDatabase<T> build(Class<T> objectClass) throws NoDatabaseException, ClassNotFoundException {
		if (serverNameList.size()==0) {
			throw new NoDatabaseException("Database not initialized.");
		} else {
			if(morphia.isMapped(objectClass))
				return new MongoDatabase<T>(ds, objectClass);
			else 
				throw new ClassNotFoundException(objectClass.toString()+" class is not initialized");
		}
	}

	/**
	 * Gets the Mongo Driver that is initialized.
	 * @return the mongoDriver
	 * @throws NoDatabaseException when there is no database initialized
	 */
	public MongoClient getMongoDriver() throws NoDatabaseException
	{
		if (serverNameList.size()==0) {
			throw new NoDatabaseException("Database not initialized.");
		} else {
			return switchingDriver.get(choice);
		}
	}

}
