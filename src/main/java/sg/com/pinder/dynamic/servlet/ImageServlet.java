package sg.com.pinder.dynamic.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;

import sg.com.pinder.exception.MongoDBException;
import sg.com.pinder.pojo.Images;
import sg.com.pinder.web.WebServiceInternal;

/**
 * @author Han Liang Wee, Eric & Harbin
 * Image Servlet to handle images from the database.
 * 
 * Note, 2 files reference this class:
 * 
 * 1. pretty-config.xml : rewrites the request :
 * 
 * 		https://0.0.0.0:8443/image/549fd0a0f14c7108dc83c81f.png
 * 			to
 * 		https://0.0.0.0:8443/dynamic/image?file=549fd0a0f14c7108dc83c81f.png
 * 
 * 2. web.xml : loads the image servlet
 * 
 * Its parameter is fileId
 * 
 */
public class ImageServlet extends HttpServlet {

	private static String extPrefix = "image/";

	private static Logger logger = Logger.getLogger(ImageDeleteHandler.class);
	
     /**
	 * Version ID
	 */
	private static final long serialVersionUID = 1L;

	// defines what to do on Get
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
          throws ServletException, IOException {
		try {
			
			ServletOutputStream sos = response.getOutputStream();
						
			try {
				
				String file = request.getParameter("file");
				
				if(file.length()<=1||file==null) {
					throw new IllegalArgumentException("Invalid query, need to have parameter \"file\".");
				}
				
				String[] fileParts = file.split("\\.");
				
				if(fileParts==null||fileParts.length!=2) {
					throw new IllegalArgumentException("Invalid query, need to have file name and extension.");
				}
				
				String fileId = fileParts[0];
				String extFromUser = fileParts[1].toLowerCase();
				String contentType = extPrefix.concat(extFromUser);
				
				ObjectId id = new ObjectId(fileId);
				Images getImgId = WebServiceInternal.getImageDB().searchOneDB(id, "_id");
				
				if(getImgId==null) {
					throw new IllegalArgumentException("File does not exist in database.");
				}
				
				byte[] img = getImgId.getBytes();
				String extFromDb = getImgId.getExtention();
				
				if(!extFromDb.equals(extFromUser)) {
					throw new IllegalArgumentException("Inconsistent filetype.");
				}
				
				response.setContentType( contentType );
	            sos.write(img);
	            sos.close();
	            
			} catch(IllegalArgumentException IAE) {
				// thrown by ObjectId
				// silent close, to hardern security, can comment out for testing
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
	            sos.close();
			}
			
		} catch (MongoDBException e) {
			// thrown by ObjectId
			// silent close, to hardern security, can comment out for testing
			logger.fatal(e.getMessage());
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
     }
	
}