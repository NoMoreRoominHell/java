package com.accumulate.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class RsaMd5Util {
	private static Logger logger = Logger.getLogger(RsaMd5Util.class);

	public final static String MD5KEY = "r4K+I42vKI";

	private static PublicKey publicKey;
	private static PrivateKey privateKey;

	static {
		publicKey = getPublicKey();
		privateKey = getPrivateKey();
	}

	private static PublicKey getPublicKey() {
		try {
			String key = getKey("/bklc_rsa_public_key.pem");
			byte[] keyBytes = Base64.decodeBase64(key);
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			return keyFactory.generatePublic(keySpec);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static PrivateKey getPrivateKey() {
		try {
			String key = getKey("/bklc_pkcs8_rsa_private_key.pem");
			byte[] keyBytes = Base64.decodeBase64(key);
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			return keyFactory.generatePrivate(keySpec);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String readFile(String fileName) throws IOException {
		StringBuffer sb = new StringBuffer();
		InputStream is = null;
		BufferedReader reader = null;
		try {
			is = RsaMd5Util.class.getResourceAsStream(fileName);
			String line; // 用来保存每行读取的内容
			reader = new BufferedReader(new InputStreamReader(is));
			line = reader.readLine(); // 读取第一行
			while (line != null) { // 如果 line 为空说明读完了
				sb.append(line); // 将读到的内容添加到 buffer 中
				sb.append("\n"); // 添加换行符
				line = reader.readLine(); // 读取下一行
			}
		} catch (Exception e) {
			logger.error(e, e);
		} finally {
			if (reader != null) {
				reader.close();
			}
			if (is != null) {
				is.close();
			}

		}
		return sb.toString();
	}

	public static byte[] getContentBytes(String content, String charset) {
		if (charset == null || "".equals(charset)) {
			return content.getBytes();
		}
		try {
			return content.getBytes(charset);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
		}
	}

	public static String getKey(String keyName) throws IOException {
		String str = null;
		try {
			str = readFile(keyName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String a[] = str.split("-----");
		String str2 = a[2];
		str2 = str2.replace("\n", "");
		return str2;
	}

	/**
	 * 拼接签名字符串 排序
	 * 
	 * @param params
	 * @return
	 */
	public static String createSignLinkString(Map<String, String> params) {
		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);
		StringBuffer prestr = new StringBuffer();
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = params.get(key);

			if (i == keys.size() - 1) {
				prestr.append(key + "=" + value);
			} else {
				prestr.append(key + "=" + value + "&");
			}
		}
		return prestr.toString();
	}

	/**
	 * RSA签名
	 * 
	 * @param text
	 * @param privateKey
	 * @param charset
	 * @return
	 * @throws Exception
	 */
	public static String sign(String text, String privateKey, String charset) throws Exception {
		byte[] keyBytes = Base64.decodeBase64(privateKey);
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
		Signature signature = Signature.getInstance("SHA1withRSA");
		signature.initSign(privateK);
		signature.update(getContentBytes(text, charset));
		byte[] result = signature.sign();
		return Base64.encodeBase64String(result);

	}

	/**
	 * RSA验签
	 * 
	 * @param text
	 * @param sign
	 * @param publicKey
	 * @param charset
	 * @return
	 * @throws Exception
	 */
	public static boolean verify(String text, String sign, String publicKey, String charset) throws Exception {
		byte[] keyBytes = Base64.decodeBase64(publicKey);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey publicK = keyFactory.generatePublic(keySpec);
		Signature signature = Signature.getInstance("SHA1withRSA");
		signature.initVerify(publicK);
		signature.update(getContentBytes(text, charset));
		return signature.verify(Base64.decodeBase64(sign));
	}

	/**
	 * md5签名
	 * 
	 * @param text
	 * @param key
	 * @param charset
	 * @return
	 * @throws Exception
	 */
	public static String md5Sign(String text, String key, String charset) throws Exception {
		text = text + key;
		return DigestUtils.md5Hex(getContentBytes(text, charset));
	}

	/**
	 * md5验签
	 * 
	 * @param text
	 * @param sign
	 * @param key
	 * @param charset
	 * @return
	 * @throws Exception
	 */
	public static boolean md5verify(String text, String sign, String key, String charset) throws Exception {
		text = text + key;
		String mysign = DigestUtils.md5Hex(getContentBytes(text, charset));
		if (mysign.equals(sign)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * RSA加密
	 * 
	 * @param enString
	 * @return
	 */
	public static String enCrypt(String enString) {
		try {
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			byte[] enBytes = cipher.doFinal(enString.getBytes());
			return Base64.encodeBase64String(enBytes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * RSA解密
	 * 
	 * @param deString
	 * @return
	 */
	public static String deCrypt(String deString) {
		if (StringUtils.isBlank(deString))
			return null;

		byte[] deBytes = Base64.decodeBase64(deString.getBytes());
		try {
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			byte[] deByte = cipher.doFinal(deBytes);
			return new String(deByte);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * md5签名
	 * 
	 * @param encryptParam
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> signData(Map<String, String> encryptParam) throws Exception {
		// 生成签名原数据
		String signOrigStr = createSignLinkString(encryptParam);
		// 计算签名
		String sign = md5Sign(signOrigStr, MD5KEY, "utf-8");
		logger.info("sign OrigStr:" + signOrigStr + ",sign:" + sign);
		encryptParam.put("sign", sign);
		return encryptParam;
	}

	/**
	 * md5验签
	 * 
	 * @param rspMap
	 * @return
	 * @throws Exception
	 */
	public static boolean verifySign(Map<String, String> rspMap) throws Exception {
		String sign = rspMap.remove("sign");
		rspMap.remove("token");
		String content = createSignLinkString(rspMap);
		boolean verifyResult = md5verify(content, sign, MD5KEY, "utf-8");
		logger.info("verifySign OrigStr:" + content);
		return verifyResult;
	}
}
