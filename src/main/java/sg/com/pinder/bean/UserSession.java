package sg.com.pinder.bean;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ValueChangeEvent;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.mongodb.morphia.annotations.NotSaved;

import sg.com.pinder.pojo.UserData;
import sg.com.pinder.util.FacesUtil;
import sg.com.pinder.web.WebService;

/**
 * Creates a user Session Object to track user sessions
 * @author Han Liang Wee Eric(A0065517)
 * @version
 */
public class UserSession extends WebService implements Serializable {
	
	private static Logger logger = Logger.getLogger(UserSession.class);
	
	private UserData user;
	private String email;

	/**
	 * User Logged in.
	 */
	public UserSession() {
		Subject currentUser = SecurityUtils.getSubject();
		if (currentUser.isAuthenticated()) {
			logger.info(currentUser.getPrincipal());
			email = (String) currentUser.getPrincipal();
			this.user = UserSession.userDB.searchOneDB(email, "email");
		}
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
	 * @return the user
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(String email) {
		this.email = email;
	}
	
	/**
	 * Initiates Logout from Shiro
	 * @param event
	 * @return
	 * @throws AbortProcessingException
	 */
	public String logout(ComponentSystemEvent event) throws AbortProcessingException {
        Subject currentUser = SecurityUtils.getSubject();
        try {
            currentUser.logout();
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return "logoutSuccess";
	}

	/**
	 * @return the eventTextIdRedirect
	 */
	public String getEventTextIdRedirect() {
		this.eventTextIdRedirect = (String) FacesUtil.getSessionMapValue("EventRecord.eventTextId");
		return eventTextIdRedirect;
	}

	@NotSaved
	private String eventTextIdRedirect;

	/**
	 * @param eventTextIdRedirect the eventTextIdRedirect to set
	 */
	public void setEventTextIdRedirect(String eventTextIdRedirect) {
		this.eventTextIdRedirect = eventTextIdRedirect;
	}
	
}
