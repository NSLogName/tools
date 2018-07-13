package com.xcty.tools.utils;

import java.util.UUID;

/**
 * IDGenerator.java
 * 描述:
 * ID 生成器
 * @author huangwei
 * @since 2016-1-4<br/>
 */
public class IDGenerator {

	/**
	 * 生成id
	 * @return 生成的id
	 */
	public static String getID(){
		return UUID.randomUUID().toString();
	}
}
