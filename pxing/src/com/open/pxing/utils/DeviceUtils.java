/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-7-24下午4:09:37
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.pxing.utils;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-7-24下午4:09:37
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class DeviceUtils {

	public static String getDeviceId(Context context){
		TelephonyManager TelephonyMgr = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE); 
		String deviceId = TelephonyMgr.getDeviceId(); 
		Log.d("DeviceUtils", "deviceId==="+deviceId);
		return deviceId;
	}

 
}
