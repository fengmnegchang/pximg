/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-6-7下午5:38:26
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.pxing.activity.m;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;

import com.open.android.utils.ScreenUtils;
import com.open.pxing.R;
import com.open.pxing.fragment.m.MRandTagListFragment;
import com.open.pxing.fragment.m.MSearchArticlePullListFragmnet;
import com.open.pxing.utils.UrlUtils;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-6-7下午5:38:26
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class MSearchArticlePullListActivity extends MCommonTitleBarActivity{
	private PopupWindow mPopTop;
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
			url = UrlUtils.PXING_SEARCH;
		}
		setRightImageResId(R.drawable.icon_down);
		addfragment();
		
		mPopTop = new PopupWindow(this);
		int w = ScreenUtils.getScreenWidth(this);
		int h = ScreenUtils.getScreenHeight(this);
		mPopTop.setWidth(w / 2);
		mPopTop.setHeight(LayoutParams.WRAP_CONTENT);
		mPopTop.setFocusable(true);////获取焦点    
		mPopTop.setTouchable(true);
		mPopTop.setOutsideTouchable(true);//设置popupwindow外部可点击    
	  //	mPopTop.update();// 刷新状态	
		ColorDrawable dw = new ColorDrawable(0000000000);// 实例化一个ColorDrawable颜色为半透明	
		mPopTop.setBackgroundDrawable(dw);// 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
		mPopTop.setAnimationStyle(R.style.AnimationPreview);//设置显示和消失动画
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);		
		View conentView = inflater.inflate(R.layout.pop_rand_tag, null);
//		MRandTagListFragment fragment = MRandTagListFragment.newInstance(url, false);
//		getSupportFragmentManager().beginTransaction().replace(R.id.id_layout_pop, fragment).commit();
		mPopTop.setContentView(conentView);
	}
	
	/* (non-Javadoc)
	 * @see com.open.pxing.activity.m.MCommonTitleBarActivity#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.id_iv_right:
			 mPopTop.showAtLocation(id_iv_right, Gravity.TOP, 740, 60+ScreenUtils.getStatusHeight(this)); //titleBar 正下方中间位置
			break;
		default:
			break;
		}
		super.onClick(v);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.android.activity.CommonFragmentActivity#addfragment()
	 */
	@Override
	public void addfragment() {
		// TODO Auto-generated method stub
		Fragment fragment = MSearchArticlePullListFragmnet.newInstance(url, true);
		getSupportFragmentManager().beginTransaction().replace(R.id.layout_content, fragment).commit();
	}

	public static void startMSearchArticlePullListActivity(Context context, String url) {
		Intent intent = new Intent();
		intent.putExtra("URL", url);
		intent.setClass(context, MSearchArticlePullListActivity.class);
		context.startActivity(intent);
	}
}
