/**
 * 
 */
package sg.com.pinder.pojo;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.beans.Field;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;
import org.mongodb.morphia.annotations.NotSaved;
import org.mongodb.morphia.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import static org.apache.commons.lang.StringEscapeUtils.escapeHtml;
import static org.apache.commons.lang.StringEscapeUtils.escapeJavaScript;
import sg.com.pinder.database.SolrMongoDatabase;
import sg.com.pinder.shiro.authc.PrimaryPrincipalSameAuthenticationStrategy;
import sg.com.pinder.util.FacesUtil;
import sg.com.pinder.web.WebService;

/**
 * @author Han Liang Wee Eric(A0065517)
 * @version
 */
@Entity(value = "event", noClassnameStored = true)
public class EventRecord extends IndexableDocument implements Serializable{

	@NotSaved
	private static final long serialVersionUID = 1L;
	@Field
	@Indexed(unique=true, dropDups=true) 
	private String name;
	@Field
	private String eventLocationLatLng;
	@Field
	private String eventLocation;
	@Field
	private String description;
	@Field
	private Date startDate;
	@Field
	private Date endDate;
	@Field
	private String cost; // cost in smallest integer unit
	@Field
	private String url;
	@Field
	@Indexed(unique=true, dropDups=true)
	private String eventTextId;
	@Field
	private String category;
	
	@NotSaved
	private UserProfile[] guestList;
	@Field
	private ObjectId[] guestListID;
	
	@NotSaved
	private UserProfile ownerUser;
	@Field
	private ObjectId ownerUserId;

//	@Field
	private int likes;
	
//	@Field
	private String htmlBox;
	
	//banners
//	@Field
	private String[] imageLinks;
	
	/**
	 * Initialize the event object with the correct type
	 */
	public EventRecord() {
		this.type="event";
	}

// --- SHIFT TO FRONT END
	/**
	 * Temporary fix, shift this to front-end.
	 * @return
	 */
	public String getInfoBox() {
		return "<div class='infoBox'>"
				+"<h4><a href='/event/"+this.eventTextId+"'>"+this.name+"</a></h4>"
				+"<details class='infoBoxDetails'>"+escapeHtml(escapeJavaScript(this.description))+"</details>"
				+"</div>";
	}
	
    /**
	 * @return the eventLocationLatLng
	 */
	public String getEventLocationLat() {
		return eventLocationLatLng.split(",")[0];
	}
	
	/**
	 * @return the eventLocationLatLng
	 */
	public String getEventLocationLng() {
		return eventLocationLatLng.split(",")[1];
	}
// --- SHIFT TO FRONT END

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the eventLocationLatLng
	 */
	public String getEventLocationLatLng() {
		return eventLocationLatLng;
	}

	/**
	 * @param eventLocationLatLng the eventLocationLatLng to set
	 */
	public void setEventLocationLatLng(String eventLocationLatLng) {
		this.eventLocationLatLng = eventLocationLatLng;
	}

	/**
	 * @return the eventLocation
	 */
	public String getEventLocation() {
		return eventLocation;
	}

	/**
	 * @param eventLocation the eventLocation to set
	 */
	public void setEventLocation(String eventLocation) {
		this.eventLocation = eventLocation;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the cost
	 */
	public String getCost() {
		return cost;
	}

	/**
	 * @param cost the cost to set
	 */
	public void setCost(String cost) {
		this.cost = cost;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
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
	 * @return the guestListID
	 */
	public ObjectId[] getGuestListID() {
		return guestListID;
	}

	/**
	 * @param guestListID the guestListID to set
	 */
	public void setGuestListID(ObjectId[] guestListID) {
		this.guestListID = guestListID;
	}

	/**
	 * @return the ownerUserId
	 */
	public ObjectId getOwnerUserId() {
		return ownerUserId;
	}

	/**
	 * @param ownerUserId the ownerUserId to set
	 */
	public void setOwnerUserId(ObjectId ownerUserId) {
		this.ownerUserId = ownerUserId;
	}

	/**
	 * @return the likes
	 */
	public int getLikes() {
		return likes;
	}

	/**
	 * @param likes the likes to set
	 */
	public void setLikes(int likes) {
		this.likes = likes;
	}

	/**
	 * @return the htmlBox
	 */
	public String getHtmlBox() {
		return htmlBox;
	}

	/**
	 * @param htmlBox the htmlBox to set
	 */
	public void setHtmlBox(String htmlBox) {
		this.htmlBox = htmlBox;
	}

	/**
	 * @return the imageLinks
	 */
	public String[] getImageLinks() {
		return imageLinks;
	}

	/**
	 * @param imageLinks the imageLinks to set
	 */
	public void setImageLinks(String[] imageLinks) {
		this.imageLinks = imageLinks;
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
	
}
