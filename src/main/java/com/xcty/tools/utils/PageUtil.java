package com.xcty.tools.utils;

/**
 * 分页工具类
 */
public class PageUtil {

	/**
	 * 分页中每页最大记录数
	 */
	// public static final int PAGE_SIZE = 15;

	/**
	 * 根据当前页面序号得到查询数据的起始记录位
	 *
	 * @param pageNo当前页面数
	 */
	public static int getStart(int pageNo, int count, int pageSize) {
		if (pageNo < 1) {
			pageNo = 1;
		}

		if ((pageNo - 1) * pageSize >= count) {

			if (count % pageSize > 0) {
				pageNo = count / pageSize + 1;
			} else {
				pageNo = count / pageSize;
			}
		}
		return (pageNo - 1) * pageSize;
	}

	/**
	 * 根据当前页面序号得到查询数据的起始记录位
	 *
	 * @param pageNo当前页面数
	 */
	public static int getStart(int pageNo, int pageSize) {
		if (pageNo < 1) {
			pageNo = 1;
		}
		return (pageNo - 1) * pageSize;
	}


	/**
	 * 根据当前页面序号和记录总数得到查询数据的结束的最大记录位
	 *
	 * @param pageNo当前页面数
	 * @param count记录总数
	 */
	public static int getEnd(int pageNo, int count, int pageSize) {
		if (pageNo < 1) {
			pageNo = 1;
		}
		if (count < 0) {
			count = 0;
		}
		int end = getStart(pageNo, pageSize) + pageSize - 1;
		return end;
	}
	
	/**
	 * 根据总数和每页条数的返回总页数
	 * @param count		记录条数
	 * @param pageSize	每页条数
	 * @return
	 */
	public static int getPageCount(int count, int pageSize){
		if(count <= 0 || pageSize <= 0){
			return 0;
		}
		
		int pageCount = count / pageSize;
		if(count % pageSize != 0){
			pageCount ++;
		}
		return pageCount;
	}
}
