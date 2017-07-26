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
package com.open.mm.adapter.m;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.open.android.activity.common.CommonWebViewActivity;
import com.open.android.adapter.CommonAdapter;
import com.open.mm.R;
import com.open.mm.bean.m.MArticleBean;

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
public class MArticleListAdapter extends CommonAdapter<MArticleBean>{

	public MArticleListAdapter(Context mContext, List<MArticleBean> list) {
		super(mContext, list);
	}
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.adapter_m_article, parent, false);
			viewHolder.text_title = (TextView) convertView.findViewById(R.id.text_title);
			viewHolder.text_camLiDes = (TextView) convertView.findViewById(R.id.text_camLiDes);
			viewHolder.imageview = (ImageView) convertView.findViewById(R.id.imageview);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		final MArticleBean bean = (MArticleBean) getItem(position);
		if (bean != null) {
			viewHolder.text_camLiDes.setText(bean.getPostmeta());
			viewHolder.text_title.setText(bean.getAlt());
			if (bean.getDataimg()!= null && bean.getDataimg().length() > 0) {
				DisplayImageOptions options = new DisplayImageOptions.Builder().showStubImage(R.drawable.default_img).showImageForEmptyUri(R.drawable.default_img).showImageOnFail(R.drawable.default_img)
//						.cacheInMemory().cacheOnDisc().build();
				.cacheInMemory().cacheOnDisc().imageScaleType(ImageScaleType.EXACTLY_STRETCHED).build();
				ImageLoader.getInstance().displayImage(bean.getDataimg(), viewHolder.imageview, options, getImageLoadingListener());
			}

			viewHolder.imageview.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					CommonWebViewActivity.startCommonWebViewActivity(mContext, bean.getHref());
				}
			});

		}
		return convertView;
	}

	class ViewHolder {
		TextView text_title,text_camLiDes;
		ImageView imageview;
	}
}
