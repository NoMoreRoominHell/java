package com.accumulate.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public class HttpClientTool {
	private static final Logger log = Logger.getLogger(HttpClientTool.class);

	private final static String URL = "http://10.20.30.73:8080/bklc-user/user/checkUserUsable";

	public static String sendPost(String url, List<NameValuePair> nvps) {
		String response = null;
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpPost httpPost = new HttpPost(url);

			httpPost.setEntity(new UrlEncodedFormEntity(nvps));
			CloseableHttpResponse closeableHttpResponse = httpclient.execute(httpPost);

			try {
				log.info(closeableHttpResponse.getStatusLine());
				HttpEntity entity = closeableHttpResponse.getEntity();
				if (entity != null) {
					response = EntityUtils.toString(entity, "utf-8");
					log.info("HttpClientTool send response:" + response);
				}
				EntityUtils.consume(entity);
			} finally {
				closeableHttpResponse.close();
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return response;
	}

	public static void main(String[] args) {
		// http://10.20.30.73:8080/bklc-user/user/checkUserUsable?phone=15214379145&userId=1

		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("phone", "15214379145"));
		nvps.add(new BasicNameValuePair("userId", "1"));
		String result = sendPost(URL, nvps);
		System.out.println(result);

	}
}
