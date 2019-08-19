package com.coolage.usagetest.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LogTester extends Job {
	Logger logger = LoggerFactory.getLogger(LogTester.class);

	@Override
	public int execute() {
        logger.warn("Logger Test Message. " + System.currentTimeMillis()); 
        return 0;
	}

}
