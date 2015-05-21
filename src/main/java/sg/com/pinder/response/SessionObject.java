/**
 * 
 */
package sg.com.pinder.response;

import java.util.Date;

import org.bson.types.ObjectId;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;
import org.mongodb.morphia.utils.IndexDirection;

/**
 * A POJO container class to contain the session object
 * @author Han Liang Wee, Eric
 * @version 04/18/2013
 */
@Entity(value="session", noClassnameStored=true)
public class SessionObject {

	@Id
	private ObjectId id;
	@Indexed(value=IndexDirection.ASC, unique=true, dropDups=true)
	private String secretKey;
	@Indexed(value=IndexDirection.ASC, unique=false, dropDups=false)
	private String fbID;
	@Indexed(value=IndexDirection.ASC, unique=true, dropDups=true)
	private String accessToken;
	@Indexed(value=IndexDirection.ASC)
	private Date lastLogin;
	
	/**
	 * Constructs a session Object
	 */
	public SessionObject() {
	}
	
	/**
	 * @return the lastLogin
	 */
	public Date getLastLogin() {
		return lastLogin;
	}

	/**
	 * @param lastLogin the lastLogin to set
	 */
	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	/**
	 * @return the fbID
	 */
	public String getFbID() {
		return fbID;
	}

	/**
	 * @param fbID the fbID to set
	 */
	public void setFbID(String fbID) {
		this.fbID = fbID;
	}

	/**
	 * @return the secretKey
	 */
	public String getSecretKey() {
		return secretKey;
	}

	/**
	 * @param secretKey the secretKey to set
	 */
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	/**
	 * @return the id
	 */
	public ObjectId getId() {
		return id;
	}

	/**
	 * @return the accessToken
	 */
	public String getAccessToken() {
		return accessToken;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(ObjectId id) {
		this.id = id;
	}

	/**
	 * @param accessToken the accessToken to set
	 */
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
}
