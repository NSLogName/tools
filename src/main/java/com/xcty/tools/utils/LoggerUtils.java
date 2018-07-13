package com.xcty.tools.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerUtils {

	private static Logger logger = LoggerFactory.getLogger(LoggerUtils.class);
	
	/*
	 * 提供实例,防止每个类去new logger实例
	 */
	public static Logger getLogger() {
		return logger;
	}
	
	public static Logger getLogger(Class<?> clazz){
		return LoggerFactory.getLogger(clazz);
	}

}
