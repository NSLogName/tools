/**
 * PIDUtils.java
 * @author huangwei
 * @since 2016-8-30
 *  描述：
 */
package com.xcty.tools.utils;

import java.lang.management.ManagementFactory;

/**
 * PIDUtils.java
 * @author huangwei
 * @since 2016-8-30
 *  描述：
 */
public class PIDUtils {
	
	
	public static void main(String[] args) {
		System.out.println(PIDUtils.getCurrentPid());
	}

	/**
	 * 获取当前进程PID
	 * @return
	 */
	public static String getCurrentPid(){
		String name = ManagementFactory.getRuntimeMXBean().getName();
		String pid = name.split("@")[0];
		return pid;
	}
	
	/**
	 * 获取当前进程唯一标识
	 * @return
	 */
	public static String getCurrentProcessUniqueMarks(){
		return IPUtils.getLocalIP() + "_" + getCurrentPid();
	}
	
}
