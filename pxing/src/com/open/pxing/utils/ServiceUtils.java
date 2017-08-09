/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-8-9下午4:50:15
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.pxing.utils;

import java.util.List;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2017-8-9下午4:50:15
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class ServiceUtils {
	
	//开启轮询服务
		public static void startPollingService(Context context, int seconds, Class<?> cls,String action) {
			//获取AlarmManager系统服务
			AlarmManager manager = (AlarmManager) context
					.getSystemService(Context.ALARM_SERVICE);
			//包装需要执行Service的Intent
			Intent intent = new Intent(context, cls);
			intent.setAction(action);
			PendingIntent pendingIntent = PendingIntent.getService(context, 0,
					intent, PendingIntent.FLAG_UPDATE_CURRENT);
			//触发服务的起始时间
			long triggerAtTime = SystemClock.elapsedRealtime();
			//使用AlarmManger的ELAPSED_REALTIME setRepeating方法设置定期执行的时间间隔（seconds秒）和需要执行的Service
			manager.setRepeating(AlarmManager.RTC, triggerAtTime,
					seconds * 1000, pendingIntent);
		}

		//停止轮询服务
		public static void stopPollingService(Context context, Class<?> cls,String action) {
			AlarmManager manager = (AlarmManager) context
					.getSystemService(Context.ALARM_SERVICE);
			Intent intent = new Intent(context, cls);
			intent.setAction(action);
			PendingIntent pendingIntent = PendingIntent.getService(context, 0,
					intent, PendingIntent.FLAG_UPDATE_CURRENT);
			//取消正在执行的服务
			manager.cancel(pendingIntent);
		}
	
	/**
	 * 判断service是否运行
	 * 
	 * @return
	 */
	public static boolean isServiceExisted(Context context, String className) {
		ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningServiceInfo> serviceList = activityManager.getRunningServices(Integer.MAX_VALUE);
		if (!(serviceList.size() > 0)) {
			return false;
		}
		for (int i = 0; i < serviceList.size(); i++) {
			ActivityManager.RunningServiceInfo serviceInfo = serviceList.get(i);
			ComponentName serviceName = serviceInfo.service;
			if (serviceName.getClassName().equals(className)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isProessRunning(Context context, String proessName) {
		boolean isRunning = false;
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningAppProcessInfo> lists = am.getRunningAppProcesses();
		for (ActivityManager.RunningAppProcessInfo info : lists) {
			if (info.processName.equals(proessName)) {
				isRunning = true;
			}
		}
		return isRunning;
	}
}
