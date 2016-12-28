package babafeng;

import java.io.UnsupportedEncodingException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
* @author babafeng
* @date : 2016年12月28日 下午2:09:05
*/
public class AESCrypto {
	private static final String SHA_MODE = "SHA-256";
	private static final String AES_MODE = "AES/ECB/PKCS5Padding";

	public static SecretKeySpec initKey(String mkey) {
		byte[] key;
		MessageDigest sha = null;
		SecretKeySpec secretKey = null;
		try {
			key = mkey.getBytes("UTF-8");

			sha = MessageDigest.getInstance(SHA_MODE);

			key = sha.digest(key);
			key = Arrays.copyOf(key, 16);

			secretKey = new SecretKeySpec(key, "AES");
			return secretKey;

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return secretKey;
	}

	public static String encrypt(String key, String message) {
		String encode = null;
		try {
			SecretKeySpec secretKey = AESCrypto.initKey(key);
			Cipher cipher = Cipher.getInstance(AES_MODE);
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			encode = Base64.getEncoder().encodeToString(cipher.doFinal(message.getBytes("UTF-8")));
			return encode;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encode;
	}

	public static String decrypt(String key, String enMessage) {
		String message = null;
		try {
			SecretKeySpec secretKey = AESCrypto.initKey(key);
			Cipher cipher = Cipher.getInstance(AES_MODE);
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			message = new String(cipher.doFinal(Base64.getDecoder().decode(enMessage)));
			return message;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return message;
	}

	public static void main(String args[]) {
		final String message = "1234567890987654321";
		final String key = "7894564123698521471";
		
		String enMessage = encrypt(key, message.trim());
		String deMessage = decrypt(key, enMessage.trim());
		
		System.out.println("加密密钥: " + key);
		System.out.println("加密前的内容: " + message);
		System.out.println("加密后的内容: " + enMessage);
		System.out.println("解密后的内容: " + deMessage);
	}

}