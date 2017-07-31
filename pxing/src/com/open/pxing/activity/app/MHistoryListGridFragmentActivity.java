package com.open.pxing.activity.app;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;

import com.open.pxing.R;
import com.open.pxing.activity.m.MCommonTitleBarActivity;
import com.open.pxing.fragment.app.MHistoryListGridFragment;
import com.open.pxing.utils.DialogUitls;
import com.open.pxing.utils.UrlUtils;

public class MHistoryListGridFragmentActivity  extends MCommonTitleBarActivity{
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.enrz.activity.CommonFragmentActivity#initValue()
	 */
	@Override
	protected void initValue() {
		// TODO Auto-generated method stub
//		super.initValue();
		if (getIntent().getStringExtra("URL") != null) {
			url = getIntent().getStringExtra("URL");
		} else {
			url = UrlUtils.PXING_NEW;
		}
		addfragment();
		
		setCenterTextValue("浏览历史");
		setRightTextValue("清空");
		setRightTextVisivable(true);
		setLeftImageResId(R.drawable.left01);
		setLeftTextVisivable(false);
	}
	
	/* (non-Javadoc)
	 * @see com.open.mm.activity.m.MCommonTitleBarActivity#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.txt_right:
			 //清空
			DialogUitls.commondialog(this, "提示", "您看过的内容会被记录下来，方便您再次快速浏览。确认要清空历史记录吗？", "残忍清除", "容我看看", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					MHistoryListGridFragment fragment = (MHistoryListGridFragment) getSupportFragmentManager()
							.findFragmentById(R.id.layout_content);
					fragment.cleanHistory();
					dialog.dismiss();
				}
			}, new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
			
			break;
		case R.id.id_iv_left:
			finish();
			break;
		default:
			super.onClick(v);
			break;
		}
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.android.activity.CommonFragmentActivity#addfragment()
	 */
	@Override
	public void addfragment() {
		// TODO Auto-generated method stub
		Fragment fragment = MHistoryListGridFragment.newInstance(url, true);
		getSupportFragmentManager().beginTransaction().replace(R.id.layout_content, fragment).commit();
	}

	public static void startMHistoryListGridFragmentActivity(Context context, String url) {
		Intent intent = new Intent();
		intent.putExtra("URL", url);
		intent.setClass(context, MHistoryListGridFragmentActivity.class);
		context.startActivity(intent);
	}
}

