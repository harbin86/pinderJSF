package sg.com.pinder.util;

import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.bson.types.ObjectId;

import sg.com.pinder.shiro.security.SecurityConstants;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

public class Security {
	private static final String ALGO = "AES";
	private static final byte[] keyValue = new byte[] { '%', '!', '@', '#', '$', '%', '^', '1', '2', '3', 'q', 'W', 'e', 'r', 'T', 'y' };

	public static String encrypt(String Data) throws Exception {
		Key key = generateKey();
		Cipher c = Cipher.getInstance(ALGO);
		c.init(Cipher.ENCRYPT_MODE, key);
		byte[] encVal = c.doFinal(Data.getBytes());
		String encryptedValue = Base64.encodeBase64String(encVal);
		return encryptedValue;
	}

	public static String decrypt(String encryptedData) throws Exception {
		Key key = generateKey();
		Cipher c = Cipher.getInstance(ALGO);
		c.init(Cipher.DECRYPT_MODE, key);
		byte[] decordedValue = Base64.decodeBase64(encryptedData);
		byte[] decValue = c.doFinal(decordedValue);
		String decryptedValue = new String(decValue);
		return decryptedValue;
	}

	private static Key generateKey() throws Exception {
		Key key = new SecretKeySpec(keyValue, ALGO);
		return key;
	}
	
	/**
	 * Generates the Salt used by the hashing algorithm, to make things extremely uncrackable
	 * @param id Object Is of the user
	 * @param algorithm algorithm to be used
	 * @param email Email of the user
	 * @return byte array of 512 bits
	 */
	public static byte[] getSalt(String id, int algoMode, String email) {
		try {
			MessageDigest md = MessageDigest.getInstance(SecurityConstants.ALGO_SALT_ARRAY[algoMode]);
			md.update((id+email).getBytes());
			return md.digest();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return new byte[]{};
	}

}