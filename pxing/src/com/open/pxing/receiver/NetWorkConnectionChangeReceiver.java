/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-8-9下午4:41:11
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.pxing.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.open.pxing.service.PXingMainPagerPushService;
import com.open.pxing.utils.ServiceUtils;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2017-8-9下午4:41:11
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class NetWorkConnectionChangeReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
		NetworkInfo mobNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if (activeNetInfo != null) {
			// Toast.makeText( context, "Active Network Type : " +
			// activeNetInfo.getTypeName(), Toast.LENGTH_SHORT ).show();
			Log.d("NetWorkConnectionChangeReceiver", "Active Network Type : " +activeNetInfo.getTypeName());
			if(!ServiceUtils.isServiceExisted(context, PXingMainPagerPushService.class.getSimpleName())){
				ServiceUtils.startPollingService(context, 5, PXingMainPagerPushService.class, PXingMainPagerPushService.ACTION);
			}
		}
		if (mobNetInfo != null) {
			// Toast.makeText( context, "Mobile Network Type : " +
			// mobNetInfo.getTypeName(), Toast.LENGTH_SHORT ).show();
			Log.d("NetWorkConnectionChangeReceiver", "Mobile Network Type : " +mobNetInfo.getTypeName());
		}
	}
}
