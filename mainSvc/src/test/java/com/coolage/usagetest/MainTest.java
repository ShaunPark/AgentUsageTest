package com.coolage.usagetest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.Test;

import com.coolage.usagetest.Main;
import com.coolage.usagetest.TestInfo;


public class MainTest {

	@Test
	void test() {
		System.out.println("ETST");
		Main m = new Main();
		TestInfo inf = m.parseTest("{ \"subcall\": \"20\", \"logMessages\":\"10\", \"sqls\":\"10\", \"sqlType\":\"long\", \"errInSubcall\":\"true\", \"targetHost\":\"172.0.0.1\"}");
		if ( inf.getSubcall() != 20 ) 
			fail("sub call fail");
		if ( inf.getLogMessages() != 10 ) 
			fail("sub call fail");
		if ( inf.getSqls() != 10 ) 
			fail("sub call fail");
		if ( !inf.getSqlType().equals("long")) 
			fail("sub call fail");
		if ( !inf.isErrInSubcall() ) 
			fail("sub call fail");
		if ( !inf.getTargetHost().equals("172.0.0.1") ) 
			fail("sub call fail");
	}
	
	void makeJobTest() {
		Main m = new Main();
		TestInfo inf = m.parseTest("{ \"subcall\": \"20\", \"logMessages\":\"10\", \"sqls\":\"10\", \"sqlType\":\"long\", \"errInSubcall\":\"true\", \"targetHost\":\"172.0.0.1\"}");
		m.makeJobsTest(inf);
	}
	
	@Test
	void configTest() {
		Main m = new Main();
		Map<String, Object>prop = m.configTest();
		if( !prop.get("targeturl").equals("http://localhost:8888/request")) {
			fail("sub call fail");
		}
		Map<String, Object> db = (Map<String, Object>) prop.get("database");
		if( !db.get("host").equals("localhost")) {
			fail("sub call fail");
		}
	}

}
