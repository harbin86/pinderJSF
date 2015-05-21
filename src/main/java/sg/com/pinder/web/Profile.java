///**
// * 
// */
//package sg.com.pinder.web;
//
//import org.apache.log4j.Logger;
//
//import com.google.gson.Gson;
//
//import sg.com.pinder.PinderDriver;
//import sg.com.pinder.database.MongoDatabase;
//import sg.com.pinder.response.PinderStatus;
//import sg.com.pinder.response.Response;
//import sg.com.pinder.response.SessionObject;
//import sg.com.pinder.response.UserProfile;
//import sg.com.pinder.response.UserRecord;
//
///**
// * @author Han Liang Wee, Eric
// * 				
//
// */
//public class Profile extends WebObject
//{
////	protected static Logger logger = Logger.getLogger(Profile.class);
////	
////	private UserRecord user;
////	
////	public Profile()
////	{
////		super();
////	}
////	
////	public String getProfile(String secretKey)
////	{
////		if(LogAndValidateSession(secretKey,"getProfile"))
////		{
////			user = (UserRecord) uDB.searchOneDB(userSession.getFbID(), "fbId");
////
////			String JSONdownCast = gson.toJson(user);
////			UserProfile downcasted = gson.fromJson(JSONdownCast, UserProfile.class);
////			Response returnThis = new Response();
////			returnThis.setUserProfile(downcasted);
////			returnThis.setStatus(PinderStatus.OK);
////			return gson.toJson(returnThis);
////		} else {
////			return gson.toJson(new Response(PinderStatus.NOT_AUTHENTICATED));
////		}
////	}
////	
////	public String setSyncOption(String secretKey, boolean isUpdate)
////	{
////		if(LogAndValidateSession(secretKey, "SetUpdateFromFB"))
////		{
////			user = (UserRecord) uDB.searchOneDB(userSession.getFbID(), "fbId");
////			user.setUpdateFromFB(isUpdate);
////			uDB.addToDBandSolr(user);
////			return gson.toJson(new Response(PinderStatus.OK));
////		} else {
////			return gson.toJson(new Response(PinderStatus.NOT_AUTHENTICATED));
////		}
////	}
////	
////	public String setProfile(String secretKey, String profile)
////	{
////		if(LogAndValidateSession(secretKey, "setProfile"))
////		{
////			UserProfile incomingUserProfile = gson.fromJson(profile, UserProfile.class);
////			user = (UserRecord) uDB.searchOneDB(userSession.getFbID(), "fbId");
////			String errorMessage = user.updateRecord(incomingUserProfile);
////			if(errorMessage==null)
////				return gson.toJson(new Response(PinderStatus.OK));
////			else {
////				Response returnThis = new Response(PinderStatus.FIELD_CONFLICT);
////				returnThis.setErrorMessage(errorMessage);
////				return gson.toJson(returnThis);
////			}
////			
////		} else {
////			return gson.toJson(new Response(PinderStatus.NOT_AUTHENTICATED));
////		}
////		
////	}
////
////	/**
////	 * @param secretKey
////	 * @param isTest
////	 * @return
////	 */
////	public String setUsername(String secretKey, String username, boolean isTest) {
////		if(LogAndValidateSession(secretKey, "setUsername"))
////		{
////			if(username==null||username.equals("")||username.toCharArray()[0]==' '||!username.matches("^[a-zA-Z0-9_.]*$"))
////			{
////				Response returnThis = new Response(PinderStatus.FIELD_CONFLICT);
////				if(username==null||username.equals("")) 
////					returnThis.setErrorMessage("Username must not be empty.");
////				else
////					if(username.toCharArray()[0]==' ')
////						returnThis.setErrorMessage("Username must not begin with ' '.");
////					else
////						returnThis.setErrorMessage("Username should be meaningful(contains alphabets, numbers, _ or .).");
////				return gson.toJson(returnThis);
////			} else {
////				user = (UserRecord) uDB.searchOneDB(username, "username");
////				if(user==null)
////				{
////					if(isTest)
////					{
////						return gson.toJson(new Response(PinderStatus.OK));
////					} else {
////						user = (UserRecord) uDB.searchOneDB(userSession.getFbID(), "fbId");
////						user.setUsername(username);
////						uDB.addToDBandSolr(user);
////						Response returnThis = new Response(PinderStatus.OK);
////						returnThis.setLoginInfo(buildLoginInfo(user,null,false));
////						return gson.toJson(returnThis);
////					}
////				} else {
////					Response returnThis = new Response(PinderStatus.FIELD_CONFLICT);
////					returnThis.setErrorMessage("Username already taken.");
////					return gson.toJson(returnThis);
////				}
////			}
////		} else {
////			return gson.toJson(new Response(PinderStatus.NOT_AUTHENTICATED));
////		}
////	}
//	
//}
