package com.coolage.targetsvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spark.Spark;

public class Main {
	static Logger logger = LoggerFactory.getLogger(Main.class);
	public static void main(String[] argv) {
		
		Main m = new Main();
		m.execute();
	}

	private void execute() {
		Spark.port(8080);
		Spark.get("/request", (req, res) -> {
			int pause = 0;
			if( Math.random() > 0.7) {
				pause = (int)(Math.random() * 950) + 50;
			} else {
				pause = (int)(Math.random() * 300) + 50;
			}
			Thread.sleep(pause);
			logger.info("Pause  " + pause + "ms");
			return "Call Success!!";
		});
	}
}
