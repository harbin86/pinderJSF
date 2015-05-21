package sg.com.pinder.dynamic.servlet;
 import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.MongoException;

import sg.com.pinder.pojo.Images;
import sg.com.pinder.web.WebServiceInternal;

/**
 * Servlet to upload image, check for all problems before uploading
 * @author Han Liang Wee Eric
 */
public class ImageUploadHandler extends HttpServlet {
	
	private static final long MAX_SIZE_BYTES = 1024*1024*10;
	
	private static Logger logger = Logger.getLogger(ImageUploadHandler.class);
	
	private static JSONObject unauthorized = new JSONObject();
	private static JSONObject exceedDataSize = new JSONObject();
	private static JSONObject unsupportedExtension = new JSONObject();
	private static JSONObject duplicateFileUploaded = new JSONObject();
	private static JSONObject unknownError = new JSONObject();
	
	private static final String IMAGE_PATTERN = "([^\\s]+(\\.(?i)(jpg|png|gif|bmp))$)";
	
	static {
		try {
			unauthorized.put("error", "Unauthorized to upload, please sign in.");
			exceedDataSize.put("error", "Image to upload must be less than 10MB.");
			unsupportedExtension.put("error", "Image Extension not supported.");
			duplicateFileUploaded.put("error", "Duplicate file, file already uploaded.");
			unknownError.put("error", "Unknown error had occured.");
		} catch (JSONException e) {
			logger.fatal(e.getMessage());
		}
	}
	
    /**
	 * Serial ID
	 */
	private static final long serialVersionUID = 1L;
  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      
        // check for multipart content
        if(ServletFileUpload.isMultipartContent(request)){
        	
        	// check for login
        	Subject currentUser = SecurityUtils.getSubject();

        	response.setContentType("application/json");
        	
        	if(currentUser.isAuthenticated()) {

                try {

                	@SuppressWarnings("unchecked")
					List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
                	
                	if(multiparts.size()!=1) {
                		
                    	logger.fatal("Illegal POST, not from form field");
                    	response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    	response.getWriter().write(unknownError.toString());
                		
                	} else {
                		
                		FileItem item = multiparts.get(0);
                		
                        if(!item.isFormField()){
                        	
                        	String fileName = new File(item.getName()).getName();
                        	
                        	// validate file Name and extension
                        	Pattern pattern = Pattern.compile(IMAGE_PATTERN);
							Matcher matcher = pattern.matcher(fileName);

							if(matcher.matches()) {

								// one more check, to check for size
								if(item.getSize()<=MAX_SIZE_BYTES) {
									
									// within limits
									String[] fileParts = fileName.split("\\.");
									fileParts[1]=fileParts[1].toLowerCase();
									Images saveToDatabase = new Images(fileParts[1], 
											currentUser.getPrincipal().toString(),
											fileParts[0],
											item.get());
									
									try {
										WebServiceInternal.getImageDB().addToDB(saveToDatabase);
										JSONObject success = new JSONObject();
					                	try {
											success.put("dImageId", saveToDatabase.getId().toString()+"."+fileParts[1]);
										} catch (JSONException e) {
											logger.fatal(e.getMessage());
										}
										response.getWriter().write(success.toString());
									} catch(MongoException mE) {
										response.setStatus(HttpServletResponse.SC_FORBIDDEN);
						        		response.getWriter().write(duplicateFileUploaded.toString());
									}
									
								} else {
									// outside limits!
									response.setStatus(HttpServletResponse.SC_FORBIDDEN);
					        		response.getWriter().write(exceedDataSize.toString());
								}

							} else {
								// invalid filename
				        		// Okay but let dropzone handle it
				        		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
				        		response.getWriter().write(unsupportedExtension.toString());
							}
                            
                        } else {
                        	logger.fatal("Illegal POST, not from form field");
                        	response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                        	response.getWriter().write(unknownError.toString());
                        }
	               
	                   //File uploaded successfully
	                   logger.fatal("File Uploaded Successfully");
	                   
                	}
                } catch (Exception ex) {
            		// Okay but let dropzone handle it, we just return whatever error it is
                	// it should not reach here anyway
                	response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                	JSONObject errorException = new JSONObject();
                	try {
						errorException.put("error", ex.getMessage());
					} catch (JSONException e) {
						logger.fatal(e.getMessage());
					}
                	logger.fatal(ex.getMessage());
            		response.getWriter().write(errorException.toString());
                }
                
        	} else {
        		// Okay but let dropzone handle it
        		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        		response.getWriter().write(unauthorized.toString());
        	}
        	
        } else {
        	// throw error using status code, invalid request
        	logger.fatal("Invalid Request from client.");
        	response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        	response.getWriter().write(unknownError.toString());
        }
     
    }
  
}
