///**
// * 
// */
//package sg.com.pinder.web;
//
//import java.math.BigInteger;
//import java.net.UnknownHostException;
//import java.security.NoSuchAlgorithmException;
//import java.util.Date;
//
//import javax.crypto.SealedObject;
//
//import org.apache.log4j.Logger;
//
//import com.mongodb.Mongo;
//import com.restfb.types.User;
//
//import sg.com.pinder.database.MongoDatabase;
//import sg.com.pinder.fbutils.FacebookLogin;
//import sg.com.pinder.response.LoginInfo;
//import sg.com.pinder.response.PinderStatus;
//import sg.com.pinder.response.Response;
//import sg.com.pinder.response.SessionObject;
//import sg.com.pinder.response.UserRecord;
//
///**
// * The Login class handles  Facebook Authentication and data services. 
// * @author Han Liang Wee, Eric
// * @version 04/18/2013
// */
//public class Login extends WebObject
//{
////	protected static Logger logger = Logger.getLogger(Login.class);
////
////	/**
////	 * Constructs a default Login object to intialize objects to handle login.
////	 */
////	public Login()
////	{
////		super();
////	}
////	
////	public String loginUser(String fbID, String accessToken)
////	{
////		if(!accessToken.equals(""))
////		{
////			UserRecord currentUser;
////			
////			userSession = sDB.searchOneDB(fbID, "fbID");
////			String secretKeyHashText="";
////			boolean newUser=false;
////			
////			//check if the time is more than 2 hours
////			if(!isValidateSession(userSession))
////			{
////				FacebookLogin fbClient = new FacebookLogin(accessToken);
////				
////				fbClient.updateDatabase(uDB);
////				
////				//We record user session for analysis
////				//Then delete it
////				if(userSession!=null)
////				{
////					logger.debug("Dump old session and create new session.");
////					sDumpDB.addToDB(userSession);
////					sDB.delete(userSession);
////				} else {
////					newUser=true;
////				}
////				
////				userSession = new SessionObject();
////				userSession.setAccessToken(accessToken);
////				userSession.setFbID(fbID);
////				
////				String salt = new Date()+"1!2@3#4$5%6^7&8*9(0)_-+=[];',./{}|:<>?";
////				
////				java.security.MessageDigest md=null;
////				try {
////					md = java.security.MessageDigest.getInstance("SHA-512");
////				} catch (NoSuchAlgorithmException e) {
////				}
////				byte[] secretKeyHash = md.digest((accessToken+salt).getBytes());
////				
////				BigInteger bigInt = new BigInteger(1,secretKeyHash);
////				secretKeyHashText = bigInt.toString(16);
////				while(secretKeyHashText.length() < 32 ){
////				  secretKeyHashText = "0"+secretKeyHashText;
////				}
////				
////				userSession.setSecretKey(secretKeyHashText);
////				userSession.setLastLogin(new Date());
////
////			} else {
////				
////				logger.debug("Extend Session: "+accessToken);
////				userSession.setAccessToken(accessToken);
////				secretKeyHashText = userSession.getSecretKey();
////				userSession.setLastLogin(new Date());
////				
////			}
////			
////			sDB.addToDB(userSession);
////			
////			currentUser=(UserRecord) uDB.searchOneDB(fbID, "fbId");
////			
////			Response returnThis = new Response(PinderStatus.OK);
////			returnThis.setLoginInfo(buildLoginInfo(currentUser,secretKeyHashText, newUser));
////			
////			logger.debug("Request served in "+(new Date().getTime()-startTime.getTime())+"ms.");
////			return gson.toJson(returnThis);
////			
////		} else {
////			logger.fatal("No access token recieved.");
////			return null;
////		}
////		
////	}
//	
//}
