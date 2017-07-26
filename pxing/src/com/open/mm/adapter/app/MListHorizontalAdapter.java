/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-6-9下午2:26:12
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.mm.adapter.app;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.open.android.adapter.CommonAdapter;
import com.open.android.bean.db.OpenDBBean;
import com.open.mm.R;
import com.open.mm.activity.m.MImagePullListActivity;
import com.open.mm.bean.app.OpenDBListBean;
import com.open.mm.widget.HorizontalListView;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2017-6-9下午2:26:12
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class MListHorizontalAdapter extends CommonAdapter<OpenDBListBean> {

	public MListHorizontalAdapter(Context mContext, List<OpenDBListBean> list) {
		super(mContext, list);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.adapter_app_list_horizontial, parent, false);
			viewHolder.mHorizontalListView = (HorizontalListView) convertView.findViewById(R.id.pull_refresh_list);
			viewHolder.textdate = (TextView) convertView.findViewById(R.id.textdate);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		final OpenDBListBean json = (OpenDBListBean) getItem(position);
		String date = json.getList().get(0).getTime().substring(0, 11);
		viewHolder.textdate.setText(date + "看" + json.getList().size() + "图集");
		
		viewHolder.list.clear();
		viewHolder.list.addAll(json.getList());
		viewHolder.mMHoizontalAdapter = new MHoizontalAdapter(mContext, viewHolder.list);
		viewHolder.mHorizontalListView.setAdapter(viewHolder.mMHoizontalAdapter);
		return convertView;
	}

	class ViewHolder {
		HorizontalListView mHorizontalListView;
		MHoizontalAdapter mMHoizontalAdapter;
		List<OpenDBBean> list = new ArrayList<OpenDBBean>();
		TextView textdate;
	}
}
