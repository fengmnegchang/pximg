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
package com.open.pxing.adapter.app;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.open.android.adapter.CommonAdapter;
import com.open.android.bean.db.OpenDBBean;
import com.open.pxing.R;

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
public class MHoizontalAdapter extends CommonAdapter<OpenDBBean> {

	public MHoizontalAdapter(Context mContext, List<OpenDBBean> list) {
		super(mContext, list);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.adapter_app_horizontal, parent, false);
			viewHolder.imageview = (ImageView) convertView.findViewById(R.id.imageview);
			viewHolder.texttitle = (TextView) convertView.findViewById(R.id.texttitle);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		final OpenDBBean bean = (OpenDBBean) getItem(position);
		if (bean != null) {
			viewHolder.texttitle.setText(bean.getTitle());
			if (bean.getImgsrc()!= null && bean.getImgsrc().length() > 0) {
				DisplayImageOptions options = new DisplayImageOptions.Builder().showStubImage(R.drawable.default_img).showImageForEmptyUri(R.drawable.default_img).showImageOnFail(R.drawable.default_img)
//						.cacheInMemory().cacheOnDisc().build();
				.cacheInMemory().cacheOnDisc().imageScaleType(ImageScaleType.EXACTLY_STRETCHED).build();
				ImageLoader.getInstance().displayImage(bean.getImgsrc(), viewHolder.imageview, options, getImageLoadingListener());
			}

//			convertView.setOnClickListener(new OnClickListener() {
//				@Override
//				public void onClick(View v) {
//				}
//			});

		}
		return convertView;
	}

	class ViewHolder {
		ImageView imageview;
		TextView texttitle;
	}
}
