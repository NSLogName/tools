package com.xcty.tools.utils;

/**
 *
 * IAsyncCallback
 * 描述:异步调用的回调
 * @since 2015-12-24
 * @author huangwei
 * 
 */
public interface IAsyncCallback {

	/** 成功时的通知 */
	public void onSuccess();
	
	/** 失败时的通知 */
	public void onFailed();
}
