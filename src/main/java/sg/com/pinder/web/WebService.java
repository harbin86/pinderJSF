/**
 * 
 */
package sg.com.pinder.web;

import java.net.UnknownHostException;
import java.util.Date;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrServer;

import sg.com.pinder.database.MongoDatabase;
import sg.com.pinder.database.MongoDatabaseBuilder;
import sg.com.pinder.database.SolrMongoDatabase;
import sg.com.pinder.database.SolrMongoDatabaseBuilder;
import sg.com.pinder.exception.MongoDBException;
import sg.com.pinder.exception.NoDatabaseException;
import sg.com.pinder.pojo.EventRecord;
import sg.com.pinder.pojo.Images;
import sg.com.pinder.pojo.UserData;
import sg.com.pinder.pojo.UserProfile;

import com.mongodb.MongoClient;

/**
 * @author Han Liang Wee, Eric
 *
 */
public class WebService {

	private static Logger logger = Logger.getLogger(WebService.class);
	
	protected static MongoClient mongo=null;
	private static SolrServer solr=null;

	protected static SolrMongoDatabase<EventRecord> eventDB;
	protected static SolrMongoDatabase<UserProfile> profileDB;
	protected static MongoDatabase<UserData> userDB;
	protected static MongoDatabase<Images> imageDB;

	private static Date startTime;
	
	static {
		
		startTime=new Date();
		logger.info("Connection services started at "+ startTime);
		
		//authenticate mongodb and connect to mongodb
		try {
			MongoDatabaseBuilder mdb = MongoDatabaseBuilder.getInstance(null, "database.xml");
			SolrMongoDatabaseBuilder smdb = SolrMongoDatabaseBuilder.getInstance(mdb);
			
			imageDB = mdb.build(Images.class);
			userDB = mdb.build(UserData.class);
			profileDB = smdb.build(UserProfile.class);
			eventDB = smdb.build(EventRecord.class);
			
		} catch (NoDatabaseException | UnknownHostException | InstantiationException | MongoDBException |
				ConfigurationException | ClassNotFoundException e) {
			logger.fatal(e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	/**
	 * Creates a object that provides webservices
	 */
	protected WebService() {}

}
