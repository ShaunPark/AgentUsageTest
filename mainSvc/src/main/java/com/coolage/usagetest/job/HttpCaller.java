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
		        final HttpGet httpGet = new HttpGet(url);
		        httpGet.setHeader(HttpHeaders.HOST, "localhost");
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
			
//			BasicHttpClientConnectionManager connManager
//			 = new BasicHttpClientConnectionManager();
//			HttpRoute route = new HttpRoute(new HttpHost(host, port));
//			ConnectionRequest connRequest = connManager.requestConnection(route, null);
//			
//			//http client 생성
//	        CloseableHttpClient httpClient =  HttpClients.custom().setConnectionManager(connManager).build();
//	 
//	        //get 메서드와 URL 설정
//	        logger.info("Send message to url");
//	        String targetUrl = url;
//	        
//	        HttpGet httpGet = new HttpGet(targetUrl);
//	        httpGet.setHeader(HttpHeaders.HOST, "localhost");
//	 
//	        //get 요청
//	        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
//	        
// 
//	        BufferedReader reader = new BufferedReader(new InputStreamReader(
//	                httpResponse.getEntity().getContent()));
//	 
//	        String inputLine;
//	        StringBuffer response = new StringBuffer();
//	 
//	        while ((inputLine = reader.readLine()) != null) {
//	            response.append(inputLine);
//	        }
//	        
//	        reader.close();
//	 
//	        //Print result
//	        System.out.println(response.toString());
//	        httpClient.close();
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
