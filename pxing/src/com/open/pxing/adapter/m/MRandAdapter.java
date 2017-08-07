/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-6-7下午5:24:17
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.pxing.adapter.m;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.open.android.adapter.CommonAdapter;
import com.open.android.view.ExpendGridView;
import com.open.pxing.R;
import com.open.pxing.activity.m.MSearchArticlePullListActivity;
import com.open.pxing.bean.m.MArticleBean;
import com.open.pxing.bean.pc.HomeArticleBean;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2017-6-7下午5:24:17
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class MRandAdapter extends CommonAdapter<HomeArticleBean> {
	public MRandAdapter(Context mContext, List<HomeArticleBean> list) {
		super(mContext, list);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.adapter_m_rand_expend, parent, false);
			viewHolder.text_title = (TextView) convertView.findViewById(R.id.text_title);
			viewHolder.mExpendGridView = (ExpendGridView) convertView.findViewById(R.id.expend_grid);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		final HomeArticleBean bean = (HomeArticleBean) getItem(position);
		if (bean != null) {
			viewHolder.text_title.setText(bean.getAlt());
			viewHolder.list = bean.getList();
			viewHolder.mMRandGridAdapter = new MRandGridAdapter(mContext, viewHolder.list);
			viewHolder.mExpendGridView.setAdapter(viewHolder.mMRandGridAdapter);
			final List<MArticleBean> list  = viewHolder.list;
			viewHolder.mExpendGridView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					if(id!=-1 && list!=null && list.size()>0 && list.get((int)id)!=null){
						MSearchArticlePullListActivity.startMSearchArticlePullListActivity(mContext, list.get((int)id).getHref());
					}
					
				}
			});
		}
		return convertView;
	}

	class ViewHolder {
		TextView text_title;
		ExpendGridView mExpendGridView;
		List<MArticleBean> list = new ArrayList<MArticleBean>();
		MRandGridAdapter mMRandGridAdapter;
		
	}
}
