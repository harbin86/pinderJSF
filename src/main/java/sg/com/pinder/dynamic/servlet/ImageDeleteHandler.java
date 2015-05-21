package sg.com.pinder.dynamic.servlet;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.mongodb.morphia.query.Query;

import sg.com.pinder.pojo.Images;
import sg.com.pinder.web.WebServiceInternal;

/**
 * Servlet to upload image
 */
public class ImageDeleteHandler extends HttpServlet {
	
	private static Logger logger = Logger.getLogger(ImageDeleteHandler.class);
	
    /**
	 * Serial ID
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String IMAGE_PATTERN = "([^\\s]+(\\.(?i)(jpg|png|gif|bmp))$)";
	
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	// check auth
    	Subject currentUser = SecurityUtils.getSubject();
    	
    	if(currentUser.isAuthenticated()) {
    		
			String file = request.getParameter("file");
			
			if(file!=null) {
				
	        	Pattern pattern = Pattern.compile(IMAGE_PATTERN);
				Matcher matcher = pattern.matcher(file);
	    		
				// check if query is well-formed
				if(matcher.matches()) {
	
					String[] fileParts = file.split("\\.");
					
					fileParts[1] = fileParts[1].toLowerCase(); 
					
					Query<Images> query = WebServiceInternal.getImageDB().getQuery();
					query.and(
								query.criteria("email").equal(currentUser.getPrincipal().toString()),
								query.criteria("ext").equal(fileParts[1]),
								query.criteria("fileName").equal(fileParts[0])
							);
					Images imageFromDb = query.get();
					
					if(imageFromDb!=null) {
						WebServiceInternal.getImageDB().delete(imageFromDb);
					} else {
						logger.fatal("File does not exist!");
					}
				} else {
					logger.fatal("File Name is not well-formed.");
				}
			} else {
				logger.fatal("File parameter does not exist.");
			}
    	}
    }
  
}
