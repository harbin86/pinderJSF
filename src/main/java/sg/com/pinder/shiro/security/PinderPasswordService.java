package sg.com.pinder.shiro.security;

import java.security.InvalidParameterException;

import org.apache.log4j.Logger;
import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.crypto.hash.HashRequest;
import org.apache.shiro.crypto.hash.HashService;
import org.apache.shiro.crypto.hash.format.DefaultHashFormatFactory;
import org.apache.shiro.crypto.hash.format.HashFormat;
import org.apache.shiro.crypto.hash.format.HashFormatFactory;
import org.apache.shiro.crypto.hash.format.ParsableHashFormat;
import org.apache.shiro.crypto.hash.format.Shiro1CryptFormat;
import org.apache.shiro.util.ByteSource;

/**
 * @author Han Liang Wee Eric(A0065517)
 * @version
 */
public class PinderPasswordService {

	private static Logger logger = Logger.getLogger(PinderPasswordMatcher.class);
	
    private HashService[] hashServices;

    public PinderPasswordService() {
        this.hashServices = new DefaultHashService[SecurityConstants.AVALIABLE_MODES];
        
    	for(int x=0; x<SecurityConstants.AVALIABLE_MODES; x++) {
    		DefaultHashService hashService = new DefaultHashService();
            hashService.setHashAlgorithmName(SecurityConstants.ALGO_PASS_ARRAY[x]);
            hashService.setHashIterations(SecurityConstants.ITERATIONS_PASS_ARRAY[x]);
            hashService.setGeneratePublicSalt(false); //always want generated salts for user passwords to be most secure
    		hashServices[x] = hashService;
    	}
    }

    public String encryptPassword(String plaintext, byte[] salt, int algorithmMode) {
    	ByteSource plaintextBytes = ByteSource.Util.bytes(plaintext);
        HashRequest request = (new HashRequest.Builder()).setSource(plaintextBytes).setSalt(salt).build();
        Hash computed = this.hashServices[algorithmMode].computeHash(request);
        return computed.toBase64();
    }

    public boolean passwordsMatch(Object submittedPlaintext, String saved, int algorithmMode, byte[] salt) {
        ByteSource plaintextBytes = ByteSource.Util.bytes(submittedPlaintext);

        if (saved == null || saved.length() == 0) {
            return plaintextBytes == null || plaintextBytes.isEmpty();
        } else {
            if (plaintextBytes == null || plaintextBytes.isEmpty()) {
                return false;
            }
        }

        // We only need to verify the Password encoded String to the base64 one.
        HashRequest request = (new HashRequest.Builder()).setSource(plaintextBytes).setSalt(salt).build();
        Hash computed = this.hashServices[algorithmMode].computeHash(request);
        String formatted = computed.toBase64();

        return saved.equals(formatted);
    }
    
}
