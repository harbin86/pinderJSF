package sg.com.pinder.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.faces.event.ValueChangeEvent;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.solr.client.solrj.SolrQuery;
import org.bson.types.ObjectId;

import com.google.gson.Gson;

import sg.com.pinder.database.MongoDatabase;
import sg.com.pinder.database.SolrMongoDatabase;
import sg.com.pinder.pojo.EventRecord;
import sg.com.pinder.pojo.UserData;
import sg.com.pinder.pojo.UserProfile;
import sg.com.pinder.util.FacesUtil;
import sg.com.pinder.web.WebService;

/**
 * @author Han Liang Wee Eric(A0065517)
 * @version
 */
public class EventForm extends WebService  implements Serializable {

	private static Logger logger = Logger.getLogger(EventForm.class);
	
	private EventRecord event;
	
	private UserData user;
	
	private String imageLinks;

	private static Map<String,Object> tagsValue;
	static{
		//TODO
		//use config file to load
		tagsValue = new LinkedHashMap<String,Object>();
		tagsValue.put("-Select One of the Following-", "NA"); //label, value
		tagsValue.put("Night Life", "nightlife"); //label, value
		for(int x=0; x<20; x++) {
			tagsValue.put("Category"+x, "category"+x); //label, value
		}
	}
	public Map<String,Object> getTagsValue() {
		return tagsValue;
	}
	
	public EventForm() {
		this.event = new EventRecord();
	}
	
	/**
	 * @return the user
	 */
	public UserData getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(UserData user) {
		this.user = user;
	}
	
	/**
	 * form submit action
	 * @return the action to do next
	 */
	public String submit() {
		//TODO
		Gson gson = new Gson();
		String json = gson.toJson(this);
		String email = SecurityUtils.getSubject().getPrincipal().toString();
		
        FacesUtil.setSessionMapValue("EventRecord.eventTextId", event.getEventTextId());
        
        MongoDatabase<UserData> uDB = UserForm.userDB;
		this.user = uDB.searchOneDB(email, "email");
        this.event.setOwnerUserId(user.getId());
//        event.setOwnerUserId(new ObjectId());
        
        String[] imageLinksArray = imageLinks.split("\\[");
        this.event.setImageLinks(imageLinksArray);
        
        SolrMongoDatabase<EventRecord> solrMongoDB = EventForm.eventDB;
        solrMongoDB.addToDBandSolr(this.event);
		logger.debug("Post this Json to Solr Server: "+json);
		return "createEventSuccess";
	}
	
	/**
	 * @return the event
	 */
	public EventRecord getEvent() {
		return event;
	}

	/**
	 * @param event the event to set
	 */
	public void setEvent(EventRecord event) {
		this.event = event;
	}

	public String getImageLinks() {
		return imageLinks;
	}

	public void setImageLinks(String imageLinks) {
		this.imageLinks = imageLinks;
	}
	
	public String delete() {
		
		Gson gson = new Gson();
		String json = gson.toJson(this);
		
		String eventId = event.getEventTextId();
		logger.debug(eventId);
		
		MongoDatabase<EventRecord> uDB = EventForm.eventDB;
		this.event = uDB.searchOneDB(eventId, "eventTextId");
		
		SolrMongoDatabase<EventRecord> solrMongoDB = EventForm.eventDB;
		
		SolrQuery params = new SolrQuery();
		params.setQuery("eventTextId:"+ eventId);
		params.setRows(10000);
		
		uDB.delete(event);
		solrMongoDB.deleteSolrByQuery(params);
		
		logger.debug("Post this Json to Solr Server: "+json);
		
		return "myPinvents";
	}
	
	public void update(Date startDate, Date endDate, String eventName, String description, String url, String cost, String category, String website) {
		
		Gson gson = new Gson();
		String json = gson.toJson(this);
		
		String eventId = event.getEventTextId();
		logger.debug(eventId);
		
		MongoDatabase<EventRecord> uDB = EventForm.eventDB;
		this.event = uDB.searchOneDB(eventId, "eventTextId");
		ObjectId id = event.getId();
		
		SolrMongoDatabase<EventRecord> solrMongoDB = EventForm.eventDB;
		
//		SolrQuery params = new SolrQuery();
//		params.setQuery("eventTextId:"+ eventId);
//		params.setRows(10000);
//		
//		uDB.delete(event);
//		solrMongoDB.deleteSolrByQuery(params);
		
		event.setId(id);
		event.setEventTextId(url);
		event.setStartDate(startDate);
		event.setEndDate(endDate);
		event.setName(eventName);
		event.setDescription(description);
		event.setCost(cost);
		event.setCategory(category);
		event.setUrl(website);

		solrMongoDB.addToDBandSolr(event);

		logger.debug("Post this Json to Solr Server: "+json);

//		return "settings";
	}

}
