///**
// * 
// */
//package sg.com.pinder.fbutils;
//
//import java.net.URL;
//import java.util.Arrays;
//import java.util.List;
//
//import org.apache.log4j.Logger;
//
//import sg.com.pinder.database.MongoDatabase;
//import sg.com.pinder.database.SolrMongoDatabase;
//import sg.com.pinder.pojo.UserProfile;
//import sg.com.pinder.response.UserRecord;
//
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.restfb.Connection;
//import com.restfb.DefaultFacebookClient;
//import com.restfb.Facebook;
//import com.restfb.FacebookClient;
//import com.restfb.Parameter;
//import com.restfb.exception.FacebookException;
//import com.restfb.json.JsonObject;
//import com.restfb.types.User;
///**
// * A facebook login object to handle user login
// * @author Han Liang Wee, Eric
// *
// */
//public class FacebookLogin {
//	
//	private static Logger logger = Logger.getLogger(FacebookLogin.class);
//	private FacebookClient facebookClient;
//	
//	private String accessToken;
//	
//	/**
//	 * Constructor to create a FacebookLogin object with the specified accessToken and user Database.
//	 * @param accessToken The access token retrieved by Facebook's Javascript API
//	 * @param uDB The database object to store and retrieve user records
//	 */
//	public FacebookLogin(String accessToken)
//	{ 
//		this.accessToken = accessToken;
//	}
//	
//	/**
//	 * @param uDB
//	 */
//	public void updateDatabase(SolrMongoDatabase uDB)
//	{
//		logger.info("Request: "+accessToken);
//		
//		//fetch and parse new data
//		facebookClient = new DefaultFacebookClient(accessToken);
//		User newUserData;
//		newUserData = fetchMe();
//		UserMapper userMapper = new UserMapper();
//		userMapper.setFbUser(newUserData);
//		userMapper.map();
//		UserRecord newUserToDb = userMapper.getDbUser();
//		newUserToDb.setProfileImgUrl(getProfileImage(newUserData.getId()));
//		
//		//get the previous data
//		UserRecord currentUserFromDb = (UserRecord) uDB.searchOneDB(newUserData.getId());
//		
//		if(currentUserFromDb==null)
//		{
//			UserRecord testForSameUsername = (UserRecord) uDB.searchOneDB(newUserToDb.getUsername(),"username");
//			
//			if(testForSameUsername!=null)
//			{
//				newUserToDb.setUsername(newUserToDb.getUsername()+newUserToDb.getFbId());
//			}
//			newUserToDb.setUpdateFromFB(true);
//			uDB.addToDBandSolr(newUserToDb);
//
//			logger.info("["+newUserData.getId()+":"+newUserData.getName()+"] New account created successfully");
//
//		} else {
//			
//				//compare update times
//				if(!newUserData.getUpdatedTime().equals(currentUserFromDb.getFbDataDump().getUpdatedTime()))
//				{
//					if(currentUserFromDb.isUpdateFromFB())
//					{
//						// if it is different, update the copy in mongoDB
//						newUserToDb.setUsername(currentUserFromDb.getUsername());
//						newUserToDb.setUpdateFromFB(currentUserFromDb.isUpdateFromFB());
//						
//						logger.info("["+newUserData.getId()+":"+newUserData.getName()+"] New account data, dump added to record");
//					} else {
//						User fbDataDump = newUserToDb.getFbDataDump();
//						newUserToDb = currentUserFromDb;
//						newUserToDb.setFbDataDump(fbDataDump);
//						
//						logger.info("["+newUserData.getId()+":"+newUserData.getName()+"] New account data, dump appended to record");
//					}
//					
//					uDB.addToDBandSolr(newUserToDb);
//					
//					//state object to update user profile
//				} else {
//					logger.info("["+newUserData.getId()+":"+newUserData.getName()+"] Nothing to do, all data is current");
//				}
//
//		}
//
//		//login successful, create session object
//		logger.info("["+newUserData.getId()+":"+newUserData.getName()+"] Facebook Login successful");
//	}
//	
//	/**
//	 * Returns the current user's data
//	 * @return Current user's data
//	 * @throws FacebookException Exception thrown when it cannot be retrieved
//	 */
//	public User fetchMe() throws FacebookException
//	{
//		return facebookClient.fetchObject("me", User.class);
//	}
//
//	public String getProfileImage(String fbId)
//	{
//		//HttpJSONHelper.initURL("graph.facebook.com", 80, "/shaverm/picture", "redirect=false");
//		ImageResponse imageResponse = new Gson().fromJson(HttpJSONHelper.initJSON(HttpJSONHelper.initURL("graph.facebook.com", 80, "/"+fbId+"/picture", "redirect=false")),ImageResponse.class);
//		return imageResponse.getData().getUrl();
//	}
//	
//public static class ImageResponse {
//	@Facebook
//	Data data;
//	
//	/**
//	 * @return the data
//	 */
//	public Data getData() {
//		return data;
//	}
//
//	/**
//	 * @param data the data to set
//	 */
//	public void setData(Data data) {
//		this.data = data;
//	}
//
//	public static class Data{
//		@Facebook
//		String url;
//		@Facebook
//		boolean is_silhouette;
//		/**
//		 * @return the url
//		 */
//		public String getUrl() {
//			return url;
//		}
//		/**
//		 * @return the is_silhouette
//		 */
//		public boolean isIs_silhouette() {
//			return is_silhouette;
//		}
//		/**
//		 * @param url the url to set
//		 */
//		public void setUrl(String url) {
//			this.url = url;
//		}
//		/**
//		 * @param is_silhouette the is_silhouette to set
//		 */
//		public void setIs_silhouette(boolean is_silhouette) {
//			this.is_silhouette = is_silhouette;
//		}
//		
//	}
//	
//}
//	
//	/**
//	 * Returns a specific user's data given a specific Facebook ID
//	 * @param fbId The Facebook ID of the person
//	 * @return The specified user's data
//	 */
//	public User fetchUser(String fbId)
//	{
//		return facebookClient.fetchObject(fbId, User.class);
//	}
//	
//	/**
//	 * Returns the list of the current user's friends
//	 * @return List of Friends 
//	 */
//	public List<User> fetchFriendList()
//	{
//		Connection<User> myFriends = facebookClient.fetchConnection("me/friends", User.class);
//		return myFriends.getData();
//	}
//}
