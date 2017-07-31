/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-6-9下午2:08:56
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.pxing.activity.app;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;

import com.open.pxing.R;
import com.open.pxing.activity.m.MCommonTitleBarActivity;
import com.open.pxing.fragment.app.MCollectionGridFragment;
import com.open.pxing.utils.UrlUtils;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-6-9下午2:08:56
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class MCollectionGridFragmentActivity extends MCommonTitleBarActivity{
	public boolean editable;
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.enrz.activity.CommonFragmentActivity#initValue()
	 */
	@Override
	protected void initValue() {
		// TODO Auto-generated method stub
		super.initValue();
		if (getIntent().getStringExtra("URL") != null) {
			url = getIntent().getStringExtra("URL");
		} else {
			url = UrlUtils.PXING_NEW;
		}
		addfragment();
		
		setCenterTextValue("我的收藏");
		setRightTextValue("编辑");
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
			if(editable){
				setRightTextValue("编辑");
			}else{
				setRightTextValue("完成");
			}
			MCollectionGridFragment fragment = (MCollectionGridFragment) getSupportFragmentManager()
					.findFragmentById(R.id.layout_content);
			fragment.setEditable(editable);
			editable = !editable;
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
		Fragment fragment = MCollectionGridFragment.newInstance(url, true);
		getSupportFragmentManager().beginTransaction().replace(R.id.layout_content, fragment).commit();
	}

	public static void startMCollectionGridFragmentActivity(Context context, String url) {
		Intent intent = new Intent();
		intent.putExtra("URL", url);
		intent.setClass(context, MCollectionGridFragmentActivity.class);
		context.startActivity(intent);
	}
}

