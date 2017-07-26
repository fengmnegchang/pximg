/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-6-8上午10:12:44
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.pxing.adapter.m;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.open.android.adapter.CommonAdapter;
import com.open.pxing.R;
import com.open.pxing.bean.m.MSlideMenuBean;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-6-8上午10:12:44
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class MSlideMenuAdapter extends CommonAdapter<MSlideMenuBean> {

	public MSlideMenuAdapter(Context mContext, List<MSlideMenuBean> list) {
		super(mContext, list);
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.adapter_m_slide_menu, parent, false);
			viewHolder.text_title = (TextView) convertView.findViewById(R.id.text_title);
			viewHolder.img_icon = (ImageView) convertView.findViewById(R.id.img_icon);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		final MSlideMenuBean bean = (MSlideMenuBean) getItem(position);
		if (bean != null) {
			viewHolder.text_title.setText(bean.getTitle());
			if(bean.getResid()!=0){
				viewHolder.img_icon.setImageResource(bean.getResid());
				viewHolder.img_icon.setVisibility(View.VISIBLE);
			}else{
				viewHolder.img_icon.setVisibility(View.GONE);
			}
			
//			convertView.setOnClickListener(new OnClickListener() {
//				@Override
//				public void onClick(View v) {
////					CommonWebViewActivity.startCommonWebViewActivity(mContext, bean.getHref());
//					MArticlePullListActivity.startMArticlePullListActivity(mContext, bean.getHref());
//				}
//			});

		}
		return convertView;
	}

	class ViewHolder {
		TextView text_title;
		ImageView img_icon;
	}

}
