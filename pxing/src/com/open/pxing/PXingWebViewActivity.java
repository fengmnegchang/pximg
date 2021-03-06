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
	
	public static void startPXingWebViewActivity(Context context, String url) {
		Intent intent = new Intent();
		intent.putExtra("URL", url);
		intent.setClass(context, PXingWebViewActivity.class);
		context.startActivity(intent);
	}
}
