package sg.com.pinder.bean;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.solr.client.solrj.SolrQuery;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Reference;

import sg.com.pinder.database.MongoDatabase;
import sg.com.pinder.database.SolrMongoDatabase;
import sg.com.pinder.pojo.UserData;
import sg.com.pinder.pojo.UserProfile;
import sg.com.pinder.shiro.security.PinderPasswordMatcher;
import sg.com.pinder.util.Security;
import sg.com.pinder.web.WebService;

import com.google.gson.Gson;

/**
 * A bean to back the form actions in the User Creation Form
 *
 * @author Han Liang Wee Eric(A0065517)
 * @version
 */
public class UserForm extends WebService implements Serializable {

    private static Map<String, Object> countries;

    private static Map<String, Object> gender;

    private static Logger logger = Logger.getLogger(UserForm.class);

    private String imageLinks;

    private String newPassword;

    @Reference
    private UserData owner;

    private UserData user;

    private UserProfile userProfile;

    static {
        // Languages supported
        countries = new LinkedHashMap<String, Object>();
        countries.put("English (Singapore)", new Locale("en", "SG"));
        countries.put("German", Locale.GERMAN);

        // Gender
        gender = new LinkedHashMap<String, Object>();
        gender.put("Male", "Male");
        gender.put("Female", "Female");
    }

    public UserForm() {
        this.user = new UserData();
        this.userProfile = new UserProfile();
    }

    public String delete() {

        Gson gson = new Gson();
        String json = gson.toJson(this);

        String email = SecurityUtils.getSubject().getPrincipal().toString();

        MongoDatabase<UserData> uDB = UserForm.userDB;
        this.user = uDB.searchOneDB(email, "email");

        SolrMongoDatabase<UserProfile> solrMongoDB = UserForm.profileDB;
        this.userProfile = solrMongoDB.searchOneDB(email, "email");

        SolrQuery params = new SolrQuery();
// params.setQuery("ownerUserId:"+ user.getId());
// params.setRows(10000);
//
// eventDB.delete(event);
// eventDB.deleteSolrByQuery(params);

        params.setQuery("email:" + email);
        params.setRows(10000);

        uDB.delete(this.user);
        solrMongoDB.delete(this.userProfile);
        solrMongoDB.deleteSolrByQuery(params);

        logger.debug("Post this Json to Solr Server: " + json);

        Subject currentUser = SecurityUtils.getSubject();
        try {
            currentUser.logout();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "logoutSuccess";
    }

    // value change event listener
    public void genderChanged(ValueChangeEvent e) {
        this.userProfile.setGender(gender.toString());
    }

    public Map<String, Object> getCountries() {
        return countries;
    }

    public Map<String, Object> getGender() {
        return gender;
    }

    public String getImageLinks() {
        return this.imageLinks;
    }

    /**
     * @return the newPassword
     */
    public String getNewPassword() {
        return this.newPassword;
    }

    /**
     * @return the user
     */
    public UserData getOwner() {
        return this.owner;
    }

    /**
     * @return the user
     */
    public UserData getUser() {
        return this.user;
    }

    /**
     * @return the user
     */
    public UserProfile getUserP() {
        return this.userProfile;
    }

    // value change event listener
    public void localeChanged(ValueChangeEvent e) {
        String newLocaleValue = e.getNewValue().toString();
        for (Map.Entry<String, Object> entry : countries.entrySet()) {
            if (entry.getValue().toString().equals(newLocaleValue)) {
                Locale currentLocale = (Locale) entry.getValue();
                FacesContext.getCurrentInstance().getViewRoot()
                .setLocale(currentLocale);
                this.user.setLocale(currentLocale.toString());
            }
        }
    }

    public void setImageLinks(String imageLinks) {
        this.imageLinks = imageLinks;
    }

    /**
     * @param newPassword
     *            the newPassword to set
     */
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    /**
     * @param user
     *            the user to set
     */
    public void setOwner(UserData owner) {
        this.owner = owner;
    }

    /**
     * @param user
     *            the user to set
     */
    public void setUser(UserData user) {
        this.user = user;
    }

    /**
     * @param user
     *            the user to set
     */
    public void setUserP(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    /**
     * custom submit action
     *
     * @return
     */
    public String submit() {
        // TODO
        Gson gson = new Gson();
        String json = gson.toJson(this);
        MongoDatabase<UserData> uDB = UserForm.userDB;
        this.encryptPassword(this.user.getPassword(), this.user);
        uDB.addToDB(this.user);

        logger.debug("Post this Json to Solr Server: " + json);
        return "createUserSuccess";
    }

    /**
     * custom submit action
     *
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public String submitP() {
        // TODO
        Gson gson = new Gson();
        String json = gson.toJson(this);
        // copy properties from (target, source)
// BeanUtils.copyProperties(userProfile, user);

        // TODO temp fix
        String email = SecurityUtils.getSubject().getPrincipal().toString();
        this.userProfile.setEmail(email);
        this.userProfile.setOwner(userDB.searchOneDB(email, "email"));
        String[] imageLinksArray = this.imageLinks.split("\\[");
        this.userProfile.setImageLinks(imageLinksArray);

        SolrMongoDatabase<UserProfile> solrMongoDB = UserForm.profileDB;
        logger.fatal(new Gson().toJson(this.userProfile));
        solrMongoDB.addToDBandSolr(this.userProfile);
        logger.debug("Post this Json to Solr Server: " + json);

        return "createProfileSuccess";
    }

    public void update(String email, String password, String firstName,
            String lastName, String gender, Date birthday) {

        Gson gson = new Gson();
        String json = gson.toJson(this);
// if(password!=null&&password.length()>0) {
// password = PinderPasswordMatcher.passwordService.encryptPassword(password,
// Security.getSalt(this.id.toHexString(), algoMode, this.email),
// this.algoMode);
// }
        String currentEmail = SecurityUtils.getSubject().getPrincipal()
                .toString();

        MongoDatabase<UserData> uDB = UserForm.userDB;
        this.user = uDB.searchOneDB(currentEmail, "email");
        ObjectId userDataId = this.user.getId();
        String currentPassword = this.user.getPassword();
        logger.fatal("PASSWORD" + currentPassword);
        logger.fatal("NEW_PASSWORD" + this.newPassword);

        SolrMongoDatabase<UserProfile> solrMongoDB = UserForm.profileDB;
        this.userProfile = solrMongoDB.searchOneDB(currentEmail, "email");
        ObjectId userProfileId = this.userProfile.getId();

        SolrQuery params = new SolrQuery();
        params.setQuery("email:" + currentEmail);
        params.setRows(10000);

// uDB.delete(user);
// solrMongoDB.delete(userProfile);
// solrMongoDB.deleteSolrByQuery(params);

        this.user.setId(userDataId);
        this.user.setEmail(email);

        if (this.newPassword.length() > 0) {
            this.encryptPassword(this.newPassword, this.user);
            this.logger.fatal("ENCRYPTED");
        } else {
            this.user.setPassword(currentPassword);
            this.logger.fatal("SAME SAME");
        }

        logger.fatal("USER" + new Gson().toJson(this.user));
        uDB.addToDB(this.user);

        this.userProfile.setId(userProfileId);
        this.userProfile.setEmail(email);
        this.userProfile.setFirstName(firstName);
        this.userProfile.setLastName(lastName);
        this.userProfile.setGender(gender);
        this.userProfile.setBirthday(birthday);
        this.userProfile.setOwner(userDB.searchOneDB(email, "email"));
        solrMongoDB.addToDBandSolr(this.userProfile);

        logger.debug("Post this Json to Solr Server: " + json);

// HttpServletRequest req = (HttpServletRequest)
// FacesContext.getCurrentInstance().getExternalContext().getRequest();
// String email = req.getParameter("j_idt12:username");
// String password = req.getParameter("j_idt12:password");
// String firstName = req.getParameter("j_idt12:firstName");
// String gender = req.getParameter("j_idt12:gender");
// String birthday = req.getParameter("j_idt12:birthday");

        logger.debug(email + " " + password);
        logger.debug(firstName + " " + lastName);
        logger.debug(gender + " " + birthday);

// return "settings";
    }

    private void encryptPassword(String password, UserData user) {

        String encryptedPassword = PinderPasswordMatcher.passwordService
                .encryptPassword(
                        password,
                        Security.getSalt(user.getId().toHexString(),
                                user.getAlgoMode(), user.getEmail()),
                                user.getAlgoMode());
        user.setPassword(encryptedPassword);
    }

}
