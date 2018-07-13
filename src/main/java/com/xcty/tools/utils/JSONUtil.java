/**
 * JSONUtil.java
 * @author huangwei
 * @since 2016-3-29
 *  描述：
 */
package com.xcty.tools.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.List;

/**
 * JSONUtil.java
 * @author huangwei
 * @since 2016-3-29
 *  描述：
 */
public class JSONUtil {

	/**
	 * 序列化
	 */
	public static String toJSON(Object obj){
		return JSON.toJSONString(obj);
	}
	
	/**
	 * 反序列化
	 */
	public static final <T> T parseObject(String text, Class<T> clazz){
		return JSON.parseObject(text, clazz);
	}
	
	/**
	 * 反序列化
	 */
	public static final <T> List<T> parseArray(String text, Class<T> clazz){
		return JSON.parseArray(text, clazz);
	}
	
	/**
	 * 泛型反序列化
	 */
	public static final <T> T parseObject(String text, TypeReference<T> type){
		return JSON.parseObject(text, type);
	}
	
}
