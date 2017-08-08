/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-7-26下午5:31:01
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.pxing;

import android.content.Context;
import android.content.Intent;

import com.open.android.activity.common.CommonWebViewActivity;
import com.open.pxing.utils.UrlUtils;
import com.xiaomi.mistatistic.sdk.MiStatInterface;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-7-26下午5:31:01
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class PXingWebViewActivity extends CommonWebViewActivity {
	/* (non-Javadoc)
	 * @see com.open.android.activity.common.CommonWebViewActivity#initValue()
	 */
	@Override
	protected void initValue() {
		// TODO Auto-generated method stub
		super.initValue();
	}
	/* (non-Javadoc)
	 * @see com.open.android.activity.common.CommonWebViewActivity#loadUrl()
	 */
	@Override
	public void loadUrl() {
		// TODO Auto-generated method stub
		if(url==null || url.length()==0){
			url = UrlUtils.PXING;
		}
		webview.loadUrl(url);
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MiStatInterface.recordPageStart(PXingWebViewActivity.this, "pxweb page");
	}
	/* (non-Javadoc)
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MiStatInterface.recordPageEnd();
	}
	
	public static void startPXingWebViewActivity(Context context, String url) {
		Intent intent = new Intent();
		intent.putExtra("URL", url);
		intent.setClass(context, PXingWebViewActivity.class);
		context.startActivity(intent);
	}
}
