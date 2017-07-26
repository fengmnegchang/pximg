/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-6-12下午5:42:42
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.pxing.utils;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2017-6-12下午5:42:42
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class DialogUitls {
	/**
	 * 
	 */
	public static void commondialog(final Context mContext, String title, String msg, String ok, String cancel, OnClickListener positiveListener, OnClickListener negativeListener) {
		AlertDialog.Builder builder = new Builder(mContext);
		builder.setMessage(msg);
		builder.setTitle(title);
		builder.setPositiveButton(ok, positiveListener);
		builder.setNegativeButton(cancel, negativeListener);
		builder.create().show();
	}
}
