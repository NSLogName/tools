/**
 * MapSortUtils.java
 * @author huangwei
 * @since 2017-5-24
 *  描述：
 */
package com.xcty.tools.utils;

import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * MapSortUtils.java
 * 
 * @author huangwei
 * @since 2017-5-24 描述：
 * 用于map排序操作
 */
public class MapSortUtils {
	
	
	/**
	 * 根据value的转换int值
	 * @param oriMap
	 * @param asc     是否正序
	 * @return
	 */
	public static Map<String, String> sortMapByValueInteger(Map<String, String> oriMap, final Boolean asc) {
		Map<String, String> sortedMap = new LinkedHashMap<String, String>();
		if (oriMap != null && !oriMap.isEmpty()) {
			List<Entry<String, String>> entryList = new ArrayList<Entry<String, String>>(
					oriMap.entrySet());
			Collections.sort(entryList,
					new Comparator<Entry<String, String>>() {
						public int compare(Entry<String, String> entry1,
								Entry<String, String> entry2) {
							int value1 = 0, value2 = 0;
							try {
								value1 = getInt(entry1.getValue());
								value2 = getInt(entry2.getValue());
							} catch (NumberFormatException e) {
								value1 = 0;
								value2 = 0;
							}
							if(asc){
								return value1 - value2;
							}else{
								return value2 - value1;
							}
						}
					});
			Iterator<Entry<String, String>> iter = entryList.iterator();
			Map.Entry<String, String> tmpEntry = null;
			while (iter.hasNext()) {
				tmpEntry = iter.next();
				sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());
			}
		}
		return sortedMap;
	}



	/**
	 * 根据value的hashCode值排序
	 * @param oriMap
	 * @param asc     是否正序
	 * @return
	 */
	public static Map<String, String> sortMapByValueHashCode(Map<String, String> oriMap, final Boolean asc) {
		Map<String, String> sortedMap = new LinkedHashMap<String, String>();
		if (oriMap != null && !oriMap.isEmpty()) {
			List<Entry<String, String>> entryList = new ArrayList<Entry<String, String>>(
					oriMap.entrySet());
			Collections.sort(entryList,
					new Comparator<Entry<String, String>>() {
						public int compare(Entry<String, String> entry1,
								Entry<String, String> entry2) {
							int value1 = 0, value2 = 0;
							try {
								value1 = entry1.getValue().hashCode();
								value2 = entry2.getValue().hashCode();
							} catch (NumberFormatException e) {
								value1 = 0;
								value2 = 0;
							}
							if(asc){
								return value1 - value2;
							}else{
								return value2 - value1;
							}
						}
					});
			Iterator<Entry<String, String>> iter = entryList.iterator();
			Map.Entry<String, String> tmpEntry = null;
			while (iter.hasNext()) {
				tmpEntry = iter.next();
				sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());
			}
		}
		return sortedMap;
	}



	/**
	 * 根据key的转换int值
	 * @param oriMap
	 * @param asc     是否正序
	 * @return
	 */
	public static Map<String, String> sortMapByKeyInteger(Map<String, String> oriMap, final Boolean asc) {
		Map<String, String> sortedMap = new LinkedHashMap<String, String>();
		if (oriMap != null && !oriMap.isEmpty()) {
			List<Entry<String, String>> entryList = new ArrayList<Entry<String, String>>(
					oriMap.entrySet());
			Collections.sort(entryList,
					new Comparator<Entry<String, String>>() {
						public int compare(Entry<String, String> entry1,
								Entry<String, String> entry2) {
							int value1 = 0, value2 = 0;
							try {
								value1 = getInt(entry1.getKey());
								value2 = getInt(entry2.getKey());
							} catch (NumberFormatException e) {
								value1 = 0;
								value2 = 0;
							}
							if(asc){
								return value1 - value2;
							}else{
								return value2 - value1;
							}
						}
					});
			Iterator<Entry<String, String>> iter = entryList.iterator();
			Map.Entry<String, String> tmpEntry = null;
			while (iter.hasNext()) {
				tmpEntry = iter.next();
				sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());
			}
		}
		return sortedMap;
	}



	/**
	 * 根据Key的hashCode值排序
	 * @param oriMap
	 * @param asc     是否正序
	 * @return
	 */
	public static Map<String, String> sortMapByKeyHashCode(Map<String, String> oriMap, final Boolean asc) {
		Map<String, String> sortedMap = new LinkedHashMap<String, String>();
		if (oriMap != null && !oriMap.isEmpty()) {
			List<Entry<String, String>> entryList = new ArrayList<Entry<String, String>>(
					oriMap.entrySet());
			Collections.sort(entryList,
					new Comparator<Entry<String, String>>() {
						public int compare(Entry<String, String> entry1,
								Entry<String, String> entry2) {
							int value1 = 0, value2 = 0;
							try {
								value1 = entry1.getKey().hashCode();
								value2 = entry2.getKey().hashCode();
							} catch (NumberFormatException e) {
								value1 = 0;
								value2 = 0;
							}
							if(asc){
								return value1 - value2;
							}else{
								return value2 - value1;
							}
						}
					});
			Iterator<Entry<String, String>> iter = entryList.iterator();
			Map.Entry<String, String> tmpEntry = null;
			while (iter.hasNext()) {
				tmpEntry = iter.next();
				sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());
			}
		}
		return sortedMap;
	}
	

	private static int getInt(String str) {
		int i = 0;
		try {
			Pattern p = Pattern.compile("^\\d+");
			Matcher m = p.matcher(str);
			if (m.find()) {
				i = Integer.valueOf(m.group());
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return i;
	}
}
