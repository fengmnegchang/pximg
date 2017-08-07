/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-6-8上午10:26:02
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.pxing.activity.m;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.open.android.activity.common.CommonCommonFragmentActivity;
import com.open.pxing.R;
import com.open.pxing.fragment.m.MArticlePullListFragmnet;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2017-6-8上午10:26:02
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class MCommonTitleBarActivity extends CommonCommonFragmentActivity implements OnClickListener {
	public TextView txt_left;// 左文字
	public TextView txt_title;// 中标题
	public TextView txt_right;// 右文字
	public ImageView id_iv_left;// 左图片
	public ImageView id_iv_right;// 右图片

	@Override
	public void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_m_common_titlebar);
		init();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.android.activity.CommonFragmentActivity#findView()
	 */
	@Override
	protected void findView() {
		// TODO Auto-generated method stub
		super.findView();
		txt_left = (TextView) findViewById(R.id.txt_left);
		txt_title = (TextView) findViewById(R.id.txt_title);
		txt_right = (TextView) findViewById(R.id.txt_right);

		id_iv_left = (ImageView) findViewById(R.id.id_iv_left);
		id_iv_right = (ImageView) findViewById(R.id.id_iv_right);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.android.activity.CommonFragmentActivity#addfragment()
	 */
	@Override
	public void addfragment() {
		// TODO Auto-generated method stub
		Fragment fragment = MArticlePullListFragmnet.newInstance(url, true);
		getSupportFragmentManager().beginTransaction().replace(R.id.layout_content, fragment).commit();
	}

//	public void showLeftMenu(View view) {
//		// getSlidingMenu().showMenu();
//	}
//
//	public void toSearch(View view) {
//		// MSearchEditFragmentActivity.startMSearchEditFragmentActivity(this,
//		// url);
//	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.android.activity.CommonFragmentActivity#bindEvent()
	 */
	@Override
	protected void bindEvent() {
		// TODO Auto-generated method stub
		super.bindEvent();
		id_iv_right.setOnClickListener(this);
		id_iv_left.setOnClickListener(this);
		txt_left.setOnClickListener(this);
		txt_right.setOnClickListener(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.android.activity.CommonFragmentActivity#onClick(android.view
	 * .View)
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.id_iv_right:
			break;
		case R.id.id_iv_left:
			break;
		case R.id.txt_left:
			break;
		case R.id.txt_right:
			break;
		default:
			break;
		}
	}

	public void setLeftTextVisivable(boolean visiable) {
		if (txt_left != null) {
			if (visiable) {
				txt_left.setVisibility(View.VISIBLE);
				id_iv_left.setVisibility(View.GONE);
			} else {
				txt_left.setVisibility(View.GONE);
				id_iv_left.setVisibility(View.VISIBLE);
			}
		}
	}

	public void setLeftTextValue(String textvalue) {
		if (txt_left != null) {
			txt_left.setText(textvalue);
		}
	}

	public void setRightTextVisivable(boolean visiable) {
		if (txt_right != null) {
			if (visiable) {
				txt_right.setVisibility(View.VISIBLE);
				id_iv_right.setVisibility(View.GONE);
			} else {
				txt_right.setVisibility(View.GONE);
				id_iv_right.setVisibility(View.VISIBLE);
			}
		}
	}

	public void setRightTextValue(String textvalue) {
		if (txt_right != null) {
			txt_right.setText(textvalue);
		}
	}

	public void setCenterTextValue(String textvalue) {
		if (txt_title != null) {
			txt_title.setText(textvalue);
		}
	}

	public void setLeftVisivableGone() {
		txt_left.setVisibility(View.GONE);
		id_iv_left.setVisibility(View.GONE);
	}

	public void setRightVisivableGone() {
		txt_right.setVisibility(View.GONE);
		id_iv_right.setVisibility(View.GONE);
	}

	public void setLeftImageResId(int resid) {
		id_iv_left.setVisibility(View.VISIBLE);
		id_iv_left.setImageResource(resid);
	}

	public void setRightImageResId(int resid) {
		id_iv_right.setVisibility(View.VISIBLE);
		id_iv_right.setImageResource(resid);
	}

	public static void startMCommonTitleBarActivity(Context context, String url) {
		Intent intent = new Intent();
		intent.putExtra("URL", url);
		intent.setClass(context, MCommonTitleBarActivity.class);
		context.startActivity(intent);
	}

}