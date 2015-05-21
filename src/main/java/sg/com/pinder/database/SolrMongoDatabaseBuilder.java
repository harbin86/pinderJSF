package sg.com.pinder.database;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;

import sg.com.pinder.exception.NoDatabaseException;

/**
 * Factory Class to generate SolrMongoDatabase Objects
 * @author Han Liang Wee Eric(A0065517)
 * @version
 */
public class SolrMongoDatabaseBuilder {
	
	private static SolrMongoDatabaseBuilder inst;
	private static SolrServer solr;
	
	private SolrMongoDatabaseBuilder() {}

	private SolrMongoDatabaseBuilder(String configFilePath, MongoDatabaseBuilder mdb) throws ConfigurationException {
		XMLConfiguration config =  new XMLConfiguration("solr.xml");
		String ipAddr = config.getString("ipaddress");
		String port = config.getString("port");
		String collection = config.getString("collection");
		solr = new HttpSolrServer("http://"+ipAddr+":"+port+"/solr/"+collection);
	}
	

	/**
	 * Gets an instance of this SolrMongoDatabaseBuilder, there can only be one instance in the world, with the 
	 * default name of the configuration file solr.xml
	 * @param mdb the MongoDatabaseBuilder to use to init this object
	 * @return the initialized builder
	 * @throws ConfigurationException when there is a problem with the Configuration
	 */
	public static SolrMongoDatabaseBuilder getInstance(MongoDatabaseBuilder mdb) throws ConfigurationException {
		return getInstance("solr.xml", mdb);
	}

	/**
	 * Gets an instance of the SolrMongoDatabaseBuilder, with the specified configuration file name
	 * @param configFilePath
	 * @param mdb the MongoDatabaseBuilder to use to init this object
	 * @return the initialized builder
	 * @throws ConfigurationException when there is a problem with the Configuration
	 */
	public static SolrMongoDatabaseBuilder getInstance(String configFilePath, MongoDatabaseBuilder mdb) throws ConfigurationException
	{
		if (inst == null) {
			inst = new SolrMongoDatabaseBuilder(configFilePath, mdb);
		}
		return inst;
	}
	
	/**
	 * Builds a solrMongoDatabase object
	 * @param objectClass object class in which will be stored using the database
	 * @return the configured solrMongoDatabase object
	 * @throws NoDatabaseException
	 * @throws ClassNotFoundException
	 */
	public <T> SolrMongoDatabase<T> build(Class<T> objectClass) throws NoDatabaseException, ClassNotFoundException {
		if (MongoDatabaseBuilder.serverNameList.size()==0) {
			throw new NoDatabaseException("Database not initialized.");
		} else {
			if(MongoDatabaseBuilder.morphia.isMapped(objectClass))
				return new SolrMongoDatabase<T>(MongoDatabaseBuilder.ds, objectClass, solr);
			else 
				throw new ClassNotFoundException(objectClass.toString()+" class is not initialized");
		}
	}

}