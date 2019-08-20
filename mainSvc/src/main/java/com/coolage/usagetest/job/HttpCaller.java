package com.coolage.usagetest.job;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpCaller extends Job {
	Logger logger = LoggerFactory.getLogger(HttpCaller.class);
	private String url;
	@Override
	public int execute() {
		try {
			//http client 생성
	        CloseableHttpClient httpClient = HttpClients.createDefault();
	 
	        //get 메서드와 URL 설정
	        logger.info("Send message to url");
	        String targetUrl = url;
	        HttpGet httpGet = new HttpGet(targetUrl);
	 
	        //get 요청
	        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
	        
 
	        BufferedReader reader = new BufferedReader(new InputStreamReader(
	                httpResponse.getEntity().getContent()));
	 
	        String inputLine;
	        StringBuffer response = new StringBuffer();
	 
	        while ((inputLine = reader.readLine()) != null) {
	            response.append(inputLine);
	        }
	        
	        reader.close();
	 
	        //Print result
	        System.out.println(response.toString());
	        httpClient.close();
			// TODO Auto-generated method stub
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
