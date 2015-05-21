/**
 * 
 */
package sg.com.pinder.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.solr.client.solrj.beans.Field;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;
import org.mongodb.morphia.annotations.NotSaved;

import sg.com.pinder.shiro.security.PinderPasswordMatcher;
import sg.com.pinder.util.Security;
/**
 * @author Han Liang Wee, Eric
 *
 */
@Entity(value="userp", noClassnameStored=true)
public class UserProfile extends IndexableDocument
{
	@NotSaved
	private static final long serialVersionUID = 1L;
	
	private String name;
//	@Id
//    private ObjectId userid;
	@Field
	private String firstName;
//	private String middleName;
	@Field
	private String lastName;
	@Field
	private String gender;
//	@Indexed(unique=true, dropDups=true) 
//	private String username;
//	private String bio;
	@Field
	private Date birthday;
	@Field
	@Indexed(unique=true, dropDups=true)
	private String email;
//	@Embedded
//	private Location homeTown;
//	@Embedded
//	private Location current;
	private String maritalStatus;
	private String religion;
	private String website;
	private UserData owner;
//	private int algoMode;
//	@Field
//	private String password;
//	@Field
	private String imageLinks[];
	/**
	 * 
	 */
	public UserProfile() {
		this.type = "profile";
	}
	
	/**
	 * @return the maritalStatus
	 */
	public String getMaritalStatus() {
		return maritalStatus;
	}
	/**
	 * @return the religion
	 */
	public String getReligion() {
		return religion;
	}
	/**
	 * @return the website
	 */
	public String getWebsite() {
		return website;
	}
	/**
	 * @param maritalStatus the maritalStatus to set
	 */
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	/**
	 * @param religion the religion to set
	 */
	public void setReligion(String religion) {
		this.religion = religion;
	}
	/**
	 * @param website the website to set
	 */
	public void setWebsite(String website) {
		this.website = website;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @return the middleName
	 */
//	public String getMiddleName() {
//		return middleName;
//	}
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}
	/**
	 * @return the userName
	 */
//	public String getUsername() {
//		return username;
//	}
	/**
	 * @return the bio
	 */
//	public String getBio() {
//		return bio;
//	}
	/**
	 * @return the birthday
	 */
	public Date getBirthday() {
		return birthday;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @param middleName the middleName to set
	 */
//	public void setMiddleName(String middleName) {
//		this.middleName = middleName;
//	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}
	/**
	 * @param username the userName to set
	 */
//	public void setUsername(String username) {
//		this.username = username;
//	}
	/**
	 * @param bio the bio to set
	 */
//	public void setBio(String bio) {
//		this.bio = bio;
//	}
	/**
	 * @param birthday the birthday to set
	 */
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * @return the password
//	 */
//	public String getPassword() {
//		return password;
//	}

	/**
	 * @param password the password to set
	 */
//	public void setPassword(String password) {
//		if(password!=null&&password.length()>0) {
//			this.password = PinderPasswordMatcher.passwordService.encryptPassword(password, Security.getSalt(this.id.toHexString(), algoMode, this.email), this.algoMode);
//		}
//	}
	
	public String[] getImageLinks() {
		return imageLinks;
	}

	public void setImageLinks(String[] imageLinks) {
		this.imageLinks = imageLinks;
	}

	/**
	 * @return the user
	 */
	public UserData getOwner() {
		return owner;
	}

	/**
	 * @param user the user to set
	 */
	public void setOwner(UserData owner) {
		this.owner = owner;
	}
	
//	public ObjectId getId() {
//		return userid;
//	}
//
//	public void setId(ObjectId id) {
//		this.userid = id;
//	}
//	/**
//	 * @param homeTown the homeTown to set
//	 */
//	public void setHomeTown(Location homeTown) {
//		this.homeTown = homeTown;
//	}
//	/**
//	 * @param current the current to set
//	 */
//	public void setCurrent(Location current) {
//		this.current = current;
//	}
	
}
