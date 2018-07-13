/**
 * MapUtils.java
 * @author huangwei
 * @since 2016-3-24
 *  描述：
 */
package com.xcty.tools.utils;

/**
 * MapUtils.java
 * @author huangwei
 * @since 2016-3-24
 *  描述：
 */
public class MapUtils {
	/**
	 * 计算地球上任意两点(经纬度)距离
	 * 
	 * @param lon1
	 *            第一点经度
	 * @param lat1
	 *            第一点纬度
	 * @param lon2
	 *            第二点经度
	 * @param lat2
	 *            第二点纬度
	 * @return 返回距离 单位：米
	 */
	public static double Distance(double lon1, double lat1, double lon2,
			double lat2) {
		if(lon1 == 0.0 || lat1 == 0.0 || lon2 == 0.0 || lat2 == 0.0){
			return 10000.00;
		}
		double a, b, R;
		R = 6378137; // 地球半径
		lat1 = lat1 * Math.PI / 180.0;
		lat2 = lat2 * Math.PI / 180.0;
		a = lat1 - lat2;
		b = (lon1 - lon2) * Math.PI / 180.0;
		double d;
		double sa2, sb2;
		sa2 = Math.sin(a / 2.0);
		sb2 = Math.sin(b / 2.0);
		d = 2
				* R
				* Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1)
						* Math.cos(lat2) * sb2 * sb2));
		return d;
	}
	
	public static void main(String[] args) {
		
	}
}
