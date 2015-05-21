/**
 * A class to store the FB user response in the mongo Database
 */
package sg.com.pinder.response;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;

import sg.com.pinder.pojo.UserProfile;

import com.restfb.types.User;

/**
 * A POJO container class to store user details
 * @author Han Liang Wee, Eric
 * @version 03/29/2013
 */
public class UserRecord extends UserProfile
{

	@Id
	private String fbId;
	private String locale;
	private String link;
	private String third_party_id;
	private double timezone;
	private Date updated_time;
	private boolean verified;
	private Date _birthday;
	private String political;
	private String quotes;
	
	private String profileImgUrl;
	
	@Embedded
	private User fbDataDump;
	
	/**
	 * Construct an empty UserRecord 
	 */
	public UserRecord()
	{
		
	}

	public String updateRecord(UserProfile incomingProfile)
	{
		if(incomingProfile.getFirstName()==null)
			return "First Name must not be empty.";
		
		if(incomingProfile.getLastName()==null)
			return "Last Name must not be empty.";
		this.setFirstName(incomingProfile.getFirstName());
		this.setLastName(incomingProfile.getLastName());
		this.setName(this.getFirstName()+" "+this.getLastName());
		
		if(incomingProfile.getGender().matches("^(male|female)$"))
			this.setGender(incomingProfile.getGender());
		else 
			return "Gender must be male or female.";
		
//		this.setBio(incomingProfile.getBio());
		
		SimpleDateFormat parserSDF=new SimpleDateFormat("MM/dd/yyyy");
//		String birthday = incomingProfile.getBirthday();
		Date birthday = incomingProfile.getBirthday();
		String b = birthday.toString();
		try{
			parserSDF.parse(b);
		} catch(ParseException pe) {
			return "Invalid date fromat.";
		}
		
//		this.setHomeTown(incomingProfile.getHomeTown());
//		this.setCurrent(incomingProfile.getCurrent());
		//checking??TODO
		this.setMaritalStatus(incomingProfile.getMaritalStatus());
				
		return null;
	}
	
	/**
	 * @return the fbId
	 */
	public String getFbId() {
		return fbId;
	}

	/**
	 * @return the locale
	 */
	public String getLocale() {
		return locale;
	}

	/**
	 * @return the link
	 */
	public String getLink() {
		return link;
	}

	/**
	 * @return the third_party_id
	 */
	public String getThird_party_id() {
		return third_party_id;
	}

	/**
	 * @return the timezone
	 */
	public double getTimezone() {
		return timezone;
	}

	/**
	 * @return the updated_time
	 */
	public Date getUpdated_time() {
		return updated_time;
	}

	/**
	 * @return the verified
	 */
	public boolean isVerified() {
		return verified;
	}

	/**
	 * @return the _birthday
	 */
	public Date get_birthday() {
		return _birthday;
	}

	/**
	 * @return the political
	 */
	public String getPolitical() {
		return political;
	}

	/**
	 * @return the quotes
	 */
	public String getQuotes() {
		return quotes;
	}

	/**
	 * @return the profileImgUrl
	 */
	public String getProfileImgUrl() {
		return profileImgUrl;
	}

	/**
	 * @return the fbDataDump
	 */
	public User getFbDataDump() {
		return fbDataDump;
	}

	/**
	 * @param fbId the fbId to set
	 */
	public void setFbId(String fbId) {
		this.fbId = fbId;
	}

	/**
	 * @param locale the locale to set
	 */
	public void setLocale(String locale) {
		this.locale = locale;
	}

	/**
	 * @param link the link to set
	 */
	public void setLink(String link) {
		this.link = link;
	}

	/**
	 * @param third_party_id the third_party_id to set
	 */
	public void setThird_party_id(String third_party_id) {
		this.third_party_id = third_party_id;
	}

	/**
	 * @param timezone the timezone to set
	 */
	public void setTimezone(double timezone) {
		this.timezone = timezone;
	}

	/**
	 * @param updated_time the updated_time to set
	 */
	public void setUpdated_time(Date updated_time) {
		this.updated_time = updated_time;
	}

	/**
	 * @param verified the verified to set
	 */
	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	/**
	 * @param _birthday the _birthday to set
	 */
	public void set_birthday(Date _birthday) {
		this._birthday = _birthday;
	}

	/**
	 * @param political the political to set
	 */
	public void setPolitical(String political) {
		this.political = political;
	}

	/**
	 * @param quotes the quotes to set
	 */
	public void setQuotes(String quotes) {
		this.quotes = quotes;
	}

	/**
	 * @param profileImgUrl the profileImgUrl to set
	 */
	public void setProfileImgUrl(String profileImgUrl) {
		this.profileImgUrl = profileImgUrl;
	}

	/**
	 * @param fbDataDump the fbDataDump to set
	 */
	public void setFbDataDump(User fbDataDump) {
		this.fbDataDump = fbDataDump;
	}

}
