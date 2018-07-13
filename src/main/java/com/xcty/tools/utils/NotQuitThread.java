package com.xcty.tools.utils;


/**
 * DaemonThread<br/>
 * 描述:不退出的线程
 * @author huangwei
 * @since 2016-1-6<br/>
 */
public class NotQuitThread extends Thread {
	
	private Object obj = new Object();
	
	public void run(){
		synchronized (obj) {
			try {
				obj.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
