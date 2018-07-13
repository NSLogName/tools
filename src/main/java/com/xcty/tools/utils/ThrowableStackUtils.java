package com.xcty.tools.utils;

/**
 * ThrowableStackUtils<br/>
 * 描述:
 * @author huangwei
 * @since 2016-1-15<br/>
 */
public class ThrowableStackUtils {

	/**
	 * 得到异常的堆栈信息
	 * @param t
	 * @return
	 */
	public static String getTrackTrace(Throwable t, Boolean isHtml){
		StringBuilder result = new StringBuilder();
		StackTraceElement[] allStackTraceElements = t.getStackTrace();
		for(StackTraceElement element:allStackTraceElements){
			result.append(element.toString());
			if(isHtml){
				result.append("<br/>");
			}else{
				result.append(System.getProperty("line.separator"));
			}
		}
		return result.toString();
	}
}
