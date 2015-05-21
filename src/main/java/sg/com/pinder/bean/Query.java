/**
 * A class to do queries to the server, this is a bean, of course.
 */
package sg.com.pinder.bean;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.faces.event.ValueChangeEvent;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.log4j.Logger;
import org.bson.types.ObjectId;

import sg.com.pinder.database.MongoDatabase;
import sg.com.pinder.database.SolrMongoDatabase;
import sg.com.pinder.pojo.EventRecord;
import sg.com.pinder.pojo.UserData;
import sg.com.pinder.pojo.UserProfile;
import sg.com.pinder.response.SolrResponse;
import sg.com.pinder.web.WebService;

/**
 * Query class to handle all kinds of query
 * @author Han Liang Wee Eric(A0065517)
 * @version
 */
public class Query extends WebService  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(Query.class);

	// for event page
	private String eventTextId;
	private EventRecord eventRecord;

	// map query options
	private String distance;
	private String fromLocation;
	private String fromLocationCoord;
	private String dateRange;
	private String category;
	
	//for profiles
	private UserSession userSession;
	private String email;
	private UserProfile userProfile;
	private UserData user;
	
	private List<EventRecord> queryResultEventRecord;
	private List<UserProfile> queryResultUserRecord;
	
	private String search;

	private static Map<String,Object> tagsValue;
	static{
		//TODO
		//use config file to load
		tagsValue = new LinkedHashMap<String,Object>();
		tagsValue.put("All Categories", "NA"); //label, value
		tagsValue.put("Night Life", "nightlife"); //label, value
		for(int x=0; x<20; x++) {
			tagsValue.put("Category"+x, "category"+x); //label, value
		}
	}
	public Map<String,Object> getTagsValue() {
		return tagsValue;
	}
	
//	public void retrieveStatusInfo(ValueChangeEvent e) {
//		
//		String newValue = e.getNewValue().toString();
//		
//		switch(e.getComponent().getId()) {
//		case "date":
//			logger.debug("Date");
//			logger.debug(newValue);
//			break;
//		case "tags":
//			logger.debug("Tags");
//			logger.debug(newValue);
//			break;
//		}
//
//	}
	
	public Query() {
		this.distance="2KM";
		this.dateRange="0 days - 2 days";
		this.category="NA";
		this.fromLocation="";
		this.fromLocationCoord="";
	}
	
    /**
     * Retrieves the correct record in the database to be listed
     */
    public void queryDatabaseEventTextId() {
    	SolrMongoDatabase<EventRecord> solrMongoDB = Query.eventDB;
    	this.eventRecord = solrMongoDB.searchOneDB(this.eventTextId, "eventTextId");
    }
    
    public void queryWithMapOptions() {
		SolrQuery params = new SolrQuery();
	//	params.setQuery(this.eventTextId);
		params.setQuery("eventTextId:"+this.eventTextId);
	//	params.setFields("eventTextId");
		logger.debug("Querying Solr:"+params.toString());
	
		//TODO
		// Method is executed twice?!, WHY??!
		SolrResponse<EventRecord> response = Query.eventDB.searchSolrByQuery(params);
		List<EventRecord> queryList = response.getDocuments();
		if(queryList!=null&&queryList.size()==1)
			setEventRecord(queryList.get(0));
		else {
			if(queryList.size()!=1)
				logger.error("Cannot have more than one event with the same eventTextId.");
			else 
				logger.error("Why the query object null?");
		}
    }
    
    public void queryMap() {

    	String distanceVal = this.distance.replaceAll("KM", "");
    	String[] dateMinMax = this.dateRange.replaceAll(" days", "").split(" - ");
    	
    	logger.debug(dateMinMax[0]);
    	logger.debug(dateMinMax[1]);

//		this.distanceRange="1KM - 5KM";
//		this.dateRange="0 days - 2 days";
//		this.category="NA";
    	
    	SolrMongoDatabase<EventRecord> solrMongoDB = Query.eventDB;
		SolrQuery params = new SolrQuery();
		params.setQuery("*:*");
		params.setRows(10000);
		
		// EXAMPLES 
		//&q=*:*&fq=(state:"FL" AND city:"Jacksonville") OR _query_:"{!geofilt}"&sfield=store&pt=45.15,-93.85&d=50&sort=geodist()+asc
		//&q=*:*&fq={!bbox sfield=store}&pt=45.15,-93.85&d=5

		//http://lucene.472066.n3.nabble.com/Date-filter-query-td3764349.html
		//[NOW/DAY-30DAY+TO+NOW/DAY-1DAY-1SECOND] better performance??
		
		if(!this.fromLocationCoord.equals("")){
			if(!this.category.equals("NA")) 
				params.setFilterQueries("{!bbox}", "startDate:[NOW+"+dateMinMax[0]+"DAY/DAY TO NOW+"+dateMinMax[1]+"DAY/DAY]", "category:"+this.category);
			else 
				params.setFilterQueries("{!bbox}", "startDate:[NOW+"+dateMinMax[0]+"DAY/DAY TO NOW+"+dateMinMax[1]+"DAY/DAY]");
			params.set("pt", this.fromLocationCoord);
			params.set("d", distanceVal);
			params.set("sfield", "eventLocationLatLng");
			params.setSort("geodist()", ORDER.asc);
		} else {
			if(!this.category.equals("NA")) 
				params.setFilterQueries("startDate:[NOW+"+dateMinMax[0]+"DAY/DAY TO NOW+"+dateMinMax[1]+"DAY/DAY]", "category:"+this.category);
			else 
				params.setFilterQueries("startDate:[NOW+"+dateMinMax[0]+"DAY/DAY TO NOW+"+dateMinMax[1]+"DAY/DAY]");
			params.setSort("startDate", ORDER.asc);
		}
				
		logger.debug("Querying Solr:"+params.toString());
		SolrResponse<EventRecord> response = solrMongoDB.searchSolrByQuery(params);
		this.setQueryResultEventRecord(response.getDocuments());
    }
    
    public void queryAll() {
    	
    	SolrMongoDatabase<EventRecord> solrMongoDB = Query.eventDB;
		SolrQuery params = new SolrQuery();
		params.setQuery("name:*");
		params.setRows(10000);
				
		logger.debug("Querying Solr:"+params.toString());
		SolrResponse<EventRecord> response = solrMongoDB.searchSolrByQuery(params);
		this.setQueryResultEventRecord(response.getDocuments());
    }

	/**
	 * @return the eventRecord
	 */
	public EventRecord getEventRecord() {
		return eventRecord;
	}

	/**
	 * @param eventRecord the eventRecord to set
	 */
	public void setEventRecord(EventRecord eventRecord) {
		this.eventRecord = eventRecord;
	}

	/**
	 * @return the eventTextId
	 */
	public String getEventTextId() {
		return eventTextId;
	}

	/**
	 * @param eventTextId the eventTextId to set
	 */
	public void setEventTextId(String eventTextId) {
		this.eventTextId = eventTextId;
	}

	/**
	 * @return the queryResultEventRecord
	 */
	public List<EventRecord> getQueryResultEventRecord() {
		return queryResultEventRecord;
	}

	/**
	 * @param queryResultEventRecord the queryResultEventRecord to set
	 */
	public void setQueryResultEventRecord(List<EventRecord> queryResultEventRecord) {
		this.queryResultEventRecord = queryResultEventRecord;
	}

	/**
	 * @return the dateRange
	 */
	public String getDateRange() {
		return dateRange;
	}

	/**
	 * @param dateRange the dateRange to set
	 */
	public void setDateRange(String dateRange) {
		this.dateRange = dateRange;
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @return the fromLocation
	 */
	public String getFromLocation() {
		return fromLocation;
	}

	/**
	 * @param fromLocation the fromLocation to set
	 */
	public void setFromLocation(String fromLocation) {
		this.fromLocation = fromLocation;
	}

	/**
	 * @return the distance
	 */
	public String getDistance() {
		return distance;
	}

	/**
	 * @param distance the distance to set
	 */
	public void setDistance(String distance) {
		this.distance = distance;
	}

	/**
	 * @return the fromLocationCoord
	 */
	public String getFromLocationCoord() {
		return fromLocationCoord;
	}

	/**
	 * @param fromLocationCoord the fromLocationCoord to set
	 */
	public void setFromLocationCoord(String fromLocationCoord) {
		this.fromLocationCoord = fromLocationCoord;
	}
	
	public String getFromLocationCoordLat() {
		logger.debug(this.fromLocationCoord);
		if(!this.fromLocationCoord.equals(""))
			return this.fromLocationCoord.split(",")[0];
		else 
			return "";
	}
    
	public String getFromLocationCoordLng() {
		if(!this.fromLocationCoord.equals(""))
			return this.fromLocationCoord.split(",")[1];
		else 
			return "";
	}
	
	 /**
     * Retrieves the correct record in the database to be listed
     */
    public void queryDatabaseProfileId() {

    	String email = SecurityUtils.getSubject().getPrincipal().toString();
	    	
    	SolrMongoDatabase<UserProfile> solrMongoDB = Query.profileDB;
    	this.userProfile = solrMongoDB.searchOneDB(email, "email");
	    	
    	MongoDatabase<UserData> MongoDB = Query.userDB;
    	this.user = MongoDB.searchOneDB(email, "email");
    	logger.debug(email);
    }
    
    /**
	 * @return the userData
	 */
	public UserData getUserData() {
		return user;
	}

	/**
	 * @param userData the userData to set
	 */
	public void setUserData(UserData user) {
		this.user = user;
		logger.debug(user);
	}
	
	/**
	 * @return the userProfile
	 */
	public UserProfile getUserProfile() {
		return userProfile;
	}

	/**
	 * @param userData the userData to set
	 */
	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
		logger.debug(userProfile);
	}

	/**
	 * @return the eventTextId
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param eventTextId the eventTextId to set
	 */
	public void setEmail(String email) {
		this.email = email;
		logger.debug(email);
	}
	
	public void queryUserAll() {
    	
		SolrMongoDatabase<UserProfile> solrMongoDB = Query.profileDB;
		SolrQuery params = new SolrQuery();
		params.setQuery("email:*");
		params.setRows(10000);
				
		logger.debug("Querying Solr:"+params.toString());
		SolrResponse<UserProfile> response = solrMongoDB.searchSolrByQuery(params);
		this.setQueryResultUserRecord(response.getDocuments());
    }

	/**
	 * @return the queryResultEventRecord
	 */
	public List<UserProfile> getQueryResultUserRecord() {
		return queryResultUserRecord;
	}
	
	/**
	 * @param queryResultEventRecord the queryResultEventRecord to set
	 */
	public void setQueryResultUserRecord(List<UserProfile> queryResultUserRecord) {
		this.queryResultUserRecord = queryResultUserRecord;
	}

	public void queryMyPinventsAll() {

    	String email = SecurityUtils.getSubject().getPrincipal().toString();
//	    email = (String) currentUser.getPrincipal();
	    
	    MongoDatabase<UserData> MongoDB = Query.userDB;
	    this.user = MongoDB.searchOneDB(email, "email");

    	String ownerUserId = user.getId().toString();
    	logger.debug(user.getId().toString());

    	SolrMongoDatabase<EventRecord> solrMongoDB = Query.eventDB;
		SolrQuery params = new SolrQuery();
		params.setQuery("ownerUserId:"+ownerUserId);
		params.setRows(10000);
				
		logger.debug("Querying Solr:"+params.toString());
		SolrResponse<EventRecord> response = solrMongoDB.searchSolrByQuery(params);
		this.setQueryResultEventRecord(response.getDocuments());
    }
	
	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}
	
	public String querySearch() {
		
		SolrQuery params = new SolrQuery();
		params.setQuery("*:"+search);
		params.setRows(10000);
		
		return "searchResults";
	}
	
}
