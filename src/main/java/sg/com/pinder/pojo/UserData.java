/**
 * Class to store all User data and settings
 */
package sg.com.pinder.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ValueChangeEvent;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.crypto.hash.Sha512Hash;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;
import org.mongodb.morphia.annotations.NotSaved;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.subject.Subject;

import com.google.gson.Gson;

import sg.com.pinder.database.MongoDatabase;
import sg.com.pinder.database.SolrMongoDatabase;
import sg.com.pinder.shiro.security.PinderPasswordMatcher;
import sg.com.pinder.shiro.security.SecurityConstants;
import sg.com.pinder.util.FacesUtil;
import sg.com.pinder.util.Security;
import sg.com.pinder.web.WebService;

/**
 * @author Han Liang Wee Eric(A0065517)
 * @version
 */
@Entity(value = "user", noClassnameStored = true)
public class UserData implements Serializable {

	@NotSaved
	private static final long serialVersionUID = 1L;
	
    @Id
    private ObjectId id;
    
	@Indexed(unique=true, dropDups=true) 
    private String email;
	
    private String password;
    
    private String newPassword;

    private int algoMode;
    
    private boolean useFacebook=false;
    
    private Set<String> roles = new HashSet<>();

    private String locale;
	
	/**
	 * Create a new User Data
	 */
	public UserData() {
		this.id=new ObjectId();
		this.algoMode = 0;
		this.locale = "en_SG";
	}

	/**
	 * @return locale of current user
	 */
	public String getLocale() {
		return locale;
	}

	/**
	 * @param locale
	 */
	public void setLocale(String locale) {
		this.locale = locale;
	}
	
	/**
	 * @return the id
	 */
	public ObjectId getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(ObjectId id) {
		this.id = id;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * @return the password
	 */
	public String getNewPassword() {
		return newPassword;
	}

	/**
	 * @param password the password to set
	 */
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	/**
	 * @return the roles
	 */
	public Set<String> getRoles() {
		return roles;
	}

	/**
	 * @param roles the roles to set
	 */
	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}

	/**
	 * @return the useFacebook
	 */
	public boolean isUseFacebook() {
		return useFacebook;
	}

	/**
	 * @param useFacebook the useFacebook to set
	 */
	public void setUseFacebook(boolean useFacebook) {
		this.useFacebook = useFacebook;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * @return the algoMode
	 */
	public int getAlgoMode() {
		return algoMode;
	}

	/**
	 * @param algoMode the algoMode to set
	 */
	public void setAlgoMode(int algoMode) {
		this.algoMode = algoMode;
	}
	
}