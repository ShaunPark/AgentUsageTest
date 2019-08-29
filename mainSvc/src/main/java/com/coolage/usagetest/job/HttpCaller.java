package com.coolage.usagetest.job;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpCaller extends Job {
	Logger logger = LoggerFactory.getLogger(HttpCaller.class);
//	private String host;
//	private int port;
	private String url;
	private String param;
	
	private static PoolingHttpClientConnectionManager connectionManager = null;
	public HttpCaller() {
		if( connectionManager == null ) {
			connectionManager = new PoolingHttpClientConnectionManager();
		}
	    connectionManager.setMaxTotal(500);
	    connectionManager.setDefaultMaxPerRoute(20);

	}
	@Override
	public int execute() {
		try {
		    try (CloseableHttpClient httpClient = HttpClients.custom()
		                                                     .setConnectionManager(connectionManager)
		                                                     .setConnectionManagerShared(true)
		                                                     .build()) {
		    	String rUrl = url.trim() + "_" + (this.getJobIdx() % 5);
		    	if( param != null && !param.isBlank()) {
		    		rUrl = rUrl + "?" + param.trim();
		    	}
		        final HttpGet httpGet = new HttpGet(rUrl);
		        httpGet.setHeader(HttpHeaders.HOST, "TESTTARGET");
		        try (CloseableHttpResponse httpResponse = httpClient.execute(httpGet)) {
			        BufferedReader reader = new BufferedReader(new InputStreamReader(
			                httpResponse.getEntity().getContent()));
			 
			        String inputLine;
			        StringBuffer response = new StringBuffer();
			 
			        while ((inputLine = reader.readLine()) != null) {
			            response.append(inputLine);
			        }
			        
			        reader.close();
		        }
		    }
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
	public void setParam(String param) {
		this.param = param;
	}
}
