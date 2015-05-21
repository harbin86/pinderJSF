package sg.com.pinder.shiro.security;

import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.authc.credential.HashingPasswordService;
import org.apache.shiro.authc.credential.PasswordMatcher;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.crypto.hash.Hash;

import sg.com.pinder.bean.UserForm;
import sg.com.pinder.database.MongoDatabase;
import sg.com.pinder.pojo.UserData;
import sg.com.pinder.util.Security;
import sg.com.pinder.web.WebServiceInternal;

/**
 * @author Han Liang Wee Eric(A0065517)
 * @version
 */
public class PinderPasswordMatcher extends PasswordMatcher {
	
	private static Logger logger = Logger.getLogger(PinderPasswordMatcher.class);
	
    /**
     * Password hashing and encoding services 
     */
    public static PinderPasswordService passwordService;
        
    /**
     * 
     */
    public PinderPasswordMatcher() {
        this.passwordService = new PinderPasswordService();
    }
    
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {

    	String email = (String) token.getPrincipal();
    	
    	logger.debug(email);
    	
    	// plaintext
        Object submittedPassword = getSubmittedPassword(token);
        Object storedCredentials = getStoredPassword(info);

        assertStoredCredentialsType(storedCredentials);

        if (storedCredentials instanceof String) {
        	
        	String passwordFromDB = (String) storedCredentials;
        	String[] passwordFormatted = passwordFromDB.split("\\$");
        	
        	String formatted = passwordFormatted[0];
        	String hexedID = passwordFormatted[1];
        	int mode = Integer.parseInt(passwordFormatted[2]);
        	
            //otherwise they are a String (asserted in the 'assertStoredCredentialsType' method call above):
            logger.debug("Status"+passwordService.passwordsMatch(submittedPassword, formatted, 
            		mode, Security.getSalt(hexedID, mode, email)));
            return passwordService.passwordsMatch(submittedPassword, formatted, 
            		mode, Security.getSalt(hexedID, mode, email));
        }
        
        return false;

    }

    private HashingPasswordService assertHashingPasswordService(PasswordService service) {
        if (service instanceof HashingPasswordService) {
            return (HashingPasswordService) service;
        }
        String msg = "AuthenticationInfo's stored credentials are a Hash instance, but the " +
                "configured passwordService is not a " +
                HashingPasswordService.class.getName() + " instance.  This is required to perform Hash " +
                "object password comparisons.";
        throw new IllegalStateException(msg);
    }

    private void assertStoredCredentialsType(Object credentials) {
        if (credentials instanceof String || credentials instanceof Hash) {
            return;
        }

        String msg = "Stored account credentials are expected to be either a " +
                Hash.class.getName() + " instance or a formatted hash String.";
        throw new IllegalArgumentException(msg);
    }

    @Override
    public PasswordService getPasswordService() {
    	throw new RuntimeException("Unable to set new Password Service, change in code.");
    }

    @Override
    public void setPasswordService(PasswordService passwordService) {
    	throw new RuntimeException("Unable to set new Password Service, change in code.");
    }
}
