package com.accumulate.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.Properties;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * 脱敏
 */
public class DesUtil {

	private static final Logger log = Logger.getLogger(DesUtil.class);

	private static final String UTF8 = "UTF-8";

	private static String password;

	static {
		Properties properties = new Properties();
		try {
			properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("common.properties"));
			password = properties.getProperty("des.password");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public DesUtil() {
	}

	/**
	 * 加密
	 * 
	 * @param datasource
	 * @return
	 */
	public static String encrypt(String datasource) {
		if (StringUtils.isBlank(datasource))
			return null;
		try {
			SecureRandom random = new SecureRandom();
			DESKeySpec desKey = new DESKeySpec(password.getBytes());
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey securekey = keyFactory.generateSecret(desKey);
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
			return Base64.getEncoder().encodeToString(cipher.doFinal(datasource.getBytes(UTF8)));
		} catch (InvalidKeyException e) {
			log.error(e.getMessage(), e);
		} catch (NoSuchAlgorithmException e) {
			log.error(e.getMessage(), e);
		} catch (InvalidKeySpecException e) {
			log.error(e.getMessage(), e);
		} catch (NoSuchPaddingException e) {
			log.error(e.getMessage(), e);
		} catch (IllegalBlockSizeException e) {
			log.error(e.getMessage(), e);
		} catch (BadPaddingException e) {
			log.error(e.getMessage(), e);
		} catch (UnsupportedEncodingException e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 解密
	 * 
	 * @param src
	 * @return
	 */
	public static String decrypt(String src) {
		if (StringUtils.isBlank(src))
			return null;
		try {
			SecureRandom random = new SecureRandom();
			DESKeySpec desKey = new DESKeySpec(password.getBytes());
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey securekey = keyFactory.generateSecret(desKey);
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, securekey, random);
			return new String(cipher.doFinal(Base64.getDecoder().decode(src)));
		} catch (InvalidKeyException e) {
			log.error(e.getMessage(), e);
		} catch (NoSuchAlgorithmException e) {
			log.error(e.getMessage(), e);
		} catch (InvalidKeySpecException e) {
			log.error(e.getMessage(), e);
		} catch (NoSuchPaddingException e) {
			log.error(e.getMessage(), e);
		} catch (IllegalBlockSizeException e) {
			log.error(e.getMessage(), e);
		} catch (BadPaddingException e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	public static void main(String[] args) throws Exception {
		// 待加密内容
		String identity = "370402199105226666";
		String result = DesUtil.encrypt(identity);
		System.out.println("加密后：" + result);
		System.out.println("解密后：" + DesUtil.decrypt(result));
	}
}
