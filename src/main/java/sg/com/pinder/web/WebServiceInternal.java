package sg.com.pinder.web;


import com.mongodb.MongoClient;

import java.net.UnknownHostException;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import sg.com.pinder.database.MongoDatabase;
import sg.com.pinder.database.SolrMongoDatabase;
import sg.com.pinder.pojo.EventRecord;
import sg.com.pinder.pojo.Images;
import sg.com.pinder.pojo.UserData;
import sg.com.pinder.pojo.UserProfile;

public class WebServiceInternal extends WebService {

	/**
	 * @return the eventDB
	 */
	public static SolrMongoDatabase<EventRecord> getEventDB() {
		return eventDB;
	}
	
	/**
	 * @return the profileDB
	 */
	public static SolrMongoDatabase<UserProfile> getProfileDB() {
		return profileDB;
	}

	/**
	 * @return the userDB
	 */
	public static MongoDatabase<UserData> getUserDB() {
		return userDB;
	}
	
	public static MongoDatabase<Images> getImageDB() {
		return imageDB;
	}

} 