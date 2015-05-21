package sg.com.pinder.bean;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ComponentSystemEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

public class IndexRedirect implements Serializable {
	
	private static Logger logger = Logger.getLogger(IndexRedirect.class);
	
	/**
	 * Initiates Logout from Shiro
	 * @param event
	 * @return
	 * @throws AbortProcessingException
	 */
	public void redirect(ComponentSystemEvent event) throws IOException {
        Subject currentUser = SecurityUtils.getSubject();
		if (currentUser.isAuthenticated()) {
			FacesContext.getCurrentInstance().getExternalContext().redirect("application/map.xhtml");
		}
	}
	
//	/**
//	 * A method to redirect the user to the required page
//	 */
//	public void redirect() {
//		Subject currentUser = SecurityUtils.getSubject();
//		logger.debug("REDIRECT0!");
//		if (currentUser.isAuthenticated()) {
//			try {
//				 
//			    FacesContext facesContext = FacesContext.getCurrentInstance();
//			    ExternalContext context = facesContext.getExternalContext(); 
//			    HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
//			 
//		        HttpServletRequest request =  (HttpServletRequest)context.getRequest();
//		        HttpServletResponse response =  (HttpServletResponse)context.getResponse();
//		        
//		        logger.debug("REDIRECT!");
//		        
//		        response.sendRedirect("application/map.xhtml");
//		        //response.sendRedirect("index.jsf");
//			 
//			        return;
//			} catch (Exception e) {
//			    e.printStackTrace();
//			}
//			
//			//TODO: is there a better way, to get attribute from config file
//		}
//	}
}
