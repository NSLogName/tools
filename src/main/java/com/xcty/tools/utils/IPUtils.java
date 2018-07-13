/**
 * IPUtils.java
 * @author huangwei
 * @since 2016-8-30
 *  描述：
 */
package com.xcty.tools.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * IPUtils.java
 * @author huangwei
 * @since 2016-8-30
 *  描述：
 */
public class IPUtils {
	
	
	public static void main(String[] args) {
		System.out.println(IPUtils.getLocalIP());
	}
	
	/**
	 * 获取本地ip
	 * @return
	 */
	public static String getLocalIP(){
		 String ip;
		try {
			ip = InetAddress.getLocalHost().getHostAddress();
			return ip;
		} catch (UnknownHostException e) {
			LoggerUtils.getLogger().error(e.getMessage(), e);
			return null;
		}  
		 
	}
}
