package com.coolage.targetsvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spark.Request;
import spark.Response;
import spark.Spark;

public class Main {
	static Logger logger = LoggerFactory.getLogger(Main.class);
	public static void main(String[] argv) {
		
		Main m = new Main();
		m.execute();
	}

	private static int count = 0;
	private void execute() {
		Spark.port(8080);
		Spark.get("/request_0", (req, res) -> {
			return process(req,res);
		}
		);
		Spark.get("/request_1", (req, res) -> {
			return process(req,res);
		}
		);
		Spark.get("/request_2", (req, res) -> {
			return process(req,res);
		}
		);
		Spark.get("/request_3", (req, res) -> {
			return process(req,res);
		}
		);
		Spark.get("/request_4", (req, res) -> {
			return process(req,res);
		}
		);
		Spark.get("/request", (req, res) -> {
			return process(req, res);
		});
	}
	
	private String process(Request req, Response res) {
		count++;
		if( count > 100000 ) {
			count = 0;
		}
		
		int delay = 100;
		String delayStr = req.params("delay");
		logger.info("delayStr " + delayStr);
		if (delayStr != null && !delayStr.isBlank()) {
			try {
				delay = Integer.parseInt(delayStr);
			} catch(NumberFormatException e) {
				
			}
		}
		int pause = 0;
		if( Math.random() > 0.7) {
			pause = (int)(Math.random() * (delay * 3) ) + 10;
		} else {
			pause = (int)(Math.random() * delay) + 10;
		}
		try {
			Thread.sleep(pause);
		} catch (InterruptedException e) {
		}
		if( (count % 50) == 0) {
			logger.info("Pause  " + pause + "ms");
		}
		return "Call Success!!";

	}
}
