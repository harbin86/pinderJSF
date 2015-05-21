///**
// * 
// */
//package sg.com.pinder.fbutils;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import org.apache.log4j.Logger;
//
//import org.mongodb.morphia.annotations.Embedded;
//import org.mongodb.morphia.annotations.Id;
//import org.mongodb.morphia.annotations.Indexed;
//import com.restfb.types.User;
//
//import sg.com.pinder.response.UserRecord;
//import sg.com.pinder.response.Location;
//
///**
// * @author Han Liang Wee, Eric
// *
// */
//public class UserMapper {
//	
//	static Logger logger = Logger.getLogger(UserMapper.class);
//	
//	private User fbUser;
//	private String accessToken;
//	
//	private UserRecord dbUser;
//	
//	public UserMapper() 
//	{
//	}
//
//	public boolean map()
//	{
//		if(fbUser==null)
//		{
//			if(dbUser==null)
//			{
//				// do nothing, nothing to map
//				logger.info("Improper use of mapper, no variable initialized");
//				return false;
//			} else {
//				//map db to fb
//				logger.info("stub");
//				return true;
//			}
//		} else {
//			if(dbUser==null)
//			{
//				
//				//map fb to db
//				dbUser=new UserRecord();
//				dbUser.setFbId(fbUser.getId());
//
//				dbUser.setName(fbUser.getName());
//				dbUser.setFirstName(fbUser.getFirstName());
//				dbUser.setMiddleName(fbUser.getMiddleName());
//				dbUser.setLastName(fbUser.getLastName());
//				dbUser.setGender(fbUser.getGender());
//				dbUser.setLocale(fbUser.getLocale());
//				dbUser.setLink(fbUser.getLink());
//				dbUser.setUsername(fbUser.getUsername());
//				
//				dbUser.setThird_party_id(fbUser.getThirdPartyId());
//				dbUser.setTimezone(fbUser.getTimezone());
//				dbUser.setUpdated_time(fbUser.getUpdatedTime());
//				dbUser.setVerified(fbUser.getVerified());
//				dbUser.setBio(fbUser.getBio());
//				dbUser.setBirthday(fbUser.getBirthday());
//		        dbUser.set_birthday(fbUser.getBirthdayAsDate());
//		        dbUser.setEmail(fbUser.getEmail());
//		        
//		        Location homeLocation=null;
//				if(fbUser.getHometown()!=null)
//				{
//					homeLocation = new Location();
//					homeLocation.setAddress(fbUser.getHometown().getName());
//				}
//				dbUser.setHomeTown(homeLocation);
//				
//				Location currentLocation=null;
//		        if(fbUser.getLocation()!=null)
//		        {
//		        	currentLocation = new Location();
//					currentLocation.setAddress(fbUser.getLocation().getName());
//		        }
//				dbUser.setCurrent(currentLocation);
//				
//				dbUser.setPolitical(fbUser.getPolitical());
//				dbUser.setQuotes(fbUser.getQuotes());
//				dbUser.setMaritalStatus(fbUser.getRelationshipStatus());
//				dbUser.setReligion(fbUser.getReligion());
//				dbUser.setWebsite(fbUser.getWebsite());
//				
//				dbUser.setFbDataDump(fbUser);
//
//				/*dbUser.setProfileImgUrl(fbUser);*/
//				return true;
//			} else {
//				//do nothing, already mapped
//				logger.info("Please clear the mapper object before usinging");
//				return false;
//			}
//		}
//	}
//	
//	public void clear()
//	{
//		fbUser=null;
//		dbUser=null;
//		accessToken = null;
//	}
//
//	/**
//	 * @return the accessToken
//	 */
//	public String getAccessToken() {
//		return accessToken;
//	}
//	
//	/**
//	 * @param accessToken the accessToken to set
//	 */
//	public void setAccessToken(String accessToken) {
//		this.accessToken = accessToken;
//	}
//
//	/**
//	 * @return the dbUser
//	 */
//	public UserRecord getDbUser() {
//		return dbUser;
//	}
//
//	/**
//	 * @return the fbUser
//	 */
//	public User getFbUser() {
//		return fbUser;
//	}
//
//	/**
//	 * @param dbUser the dbUser to set
//	 */
//	public void setDbUser(UserRecord dbUser) {
//		this.dbUser = dbUser;
//	}
//
//	/**
//	 * @param fbUser the fbUser to set
//	 */
//	public void setFbUser(User fbUser) {
//		this.fbUser = fbUser;
//	}
//	
//}
