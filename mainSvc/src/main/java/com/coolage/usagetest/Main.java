package com.coolage.usagetest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import com.coolage.usagetest.job.DatabaseCaller;
import com.coolage.usagetest.job.HttpCaller;
import com.coolage.usagetest.job.Job;
import com.coolage.usagetest.job.LogTester;
import com.google.gson.Gson;

import spark.Spark;

public class Main {
	static Logger logger = LoggerFactory.getLogger(Main.class);
	public static Map<String, Object> prop;

	public static void main(String[] argv) {
		
		Main m = new Main();
		m.loadConfig(null);
		m.execute();
	}
	
	private void loadConfig(String configFile) {
		Yaml yaml = new Yaml();
		InputStream inputStream;
		if ( configFile == null || configFile.isBlank()) {
			configFile = System.getenv("configFile");
		}
		
		if ( configFile == null || configFile.isBlank()) {
			inputStream = getClass().getClassLoader().getResourceAsStream("prop.yml");
		} else {
			try {
				inputStream = new FileInputStream(configFile);
			} catch (FileNotFoundException e) {
				logger.error("Config file "+ configFile +" doesn't exist.");
				return;
			}
		}
		
		prop = yaml.load(inputStream);
	}
	
	private void execute() {
		Spark.port(8080);
		Spark.post("/request", (req, res) -> {
			TestInfo info = parse(req.body());
						
			makeJobs(info).forEach(item -> item.execute());
			
			return "Call Success!";
		});
		
		Spark.get("/reloadconfig", (req,res) -> {
			String configFile = req.params("configfile");
			loadConfig(configFile);
			return "Config file reloaded successfully";
		});
	}
	
	private List<Job> makeJobs(TestInfo info) {
		ArrayList<Job> jobs = new ArrayList<Job>();
		
		for( int i = 0 ; i < info.getSubcall() ; i++  ) {
			HttpCaller c = new HttpCaller();
			c.setMessage("" + i);
			c.setJobIdx(i);
			c.setParam(info.getUrlParam());
			c.setUrl("http://" + info.getTargetHost());
			jobs.add(c);
		}
		
		for( int i = 0 ; i < info.getLogMessages() ; i++  ) {
			LogTester c = new LogTester();
			c.setMessage("" + i);
			jobs.add(c);
		}
		
		for( int i = 0 ; i < info.getSqls() ; i++  ) {
			DatabaseCaller c = new DatabaseCaller();
			c.setUrl((String)prop.get("db.connect.url"));
			jobs.add(c);
		}


		Collections.shuffle(jobs);
		
		//jobs.forEach( item -> System.out.println(item.toString()) );

		return jobs;
	}
	
	private TestInfo parse(String body) {
        Gson gson = new Gson();
        TestInfo info = gson.fromJson(body, TestInfo.class);
        return info;
	}
	
	public TestInfo parseTest(String body) {
		return parse(body);
	}
	
	public void makeJobsTest(TestInfo inf) {
		makeJobs(inf);
	}
	
	public Map<String, Object> configTest() {
		loadConfig(null);
		return prop;
	}
}
