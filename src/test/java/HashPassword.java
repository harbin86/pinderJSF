

import org.apache.shiro.crypto.hash.Sha256Hash;

public class HashPassword {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Sha256Hash sha256Hash = new Sha256Hash("password");
		System.out.println(sha256Hash.toHex());
	}

}
