/**
 * BaseConfig.java
 * @author huangwei
 * @since 2016-3-14
 *  描述：
 */
package com.xcty.tools.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * BaseConfig.java
 * @author huangwei
 * @since 2016-3-14
 *  描述：
 */
public class BaseConfig {
	
	/**
	 * 
	 */
	private static String configPath = null;
	
	public static void setConfigPath(String configPath){
		BaseConfig.configPath = configPath;
	}
	
	public enum ConfigType{
		
		
		REDIS("redis"),
		
		MONGO("mongo"),
		
		SMS("sms"),
		
		BAIDUMAP("baidumap"),
		
		MAIL("mail");
		
		String name;
		
		ConfigType(String name){
			this.name = name;
		}
		
		public String getName(){
			return name;
		}
		
	}
	
	private static Logger logger = LoggerFactory.getLogger(BaseConfig.class);
	
	
	public static InputStream getConfigPath(String fileName){
		String fileRealPath = BaseConfig.configPath + fileName + ".properties";
		logger.info("=====配置文件路径============");
		logger.info(fileRealPath);
		logger.info("==============================");
		try {
			if(BaseConfig.configPath.toLowerCase().startsWith("http")
					|| BaseConfig.configPath.toLowerCase().startsWith("https")
					|| BaseConfig.configPath.toLowerCase().startsWith("ftp")){
				Resource resource = new UrlResource(fileRealPath);
				return resource.getInputStream();
			}else{
				return new FileInputStream(new File(fileRealPath));
			}
		} catch (IOException e) {
			logger.error("配置文件未找到:" + fileRealPath);
			logger.error(e.getMessage(), e);
			System.exit(-1);
			return null;
		}
	}

	public static InputStream getConfigPath(ConfigType configType){
		return getConfigPath(configType.getName());
		
	}
}
