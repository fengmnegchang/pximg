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
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.animated.base.AbstractAnimatedDrawable;
import com.facebook.imagepipeline.image.ImageInfo;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.open.android.adapter.CommonAdapter;
import com.open.android.bean.db.OpenDBBean;
import com.open.pxing.R;
import com.open.pxing.activity.m.MImagePullListActivity;
import com.open.pxing.bean.app.OpenDBListBean;

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
public class MHistoryGridAdapter extends CommonAdapter<OpenDBListBean> {
	public AbstractAnimatedDrawable animatable;
	public MHistoryGridAdapter(Context mContext, List<OpenDBListBean> list) {
		super(mContext, list);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.adapter_app_history,
					parent, false);
			viewHolder.lay_grid = (LinearLayout) convertView
					.findViewById(R.id.lay_grid);
			viewHolder.textdate = (TextView) convertView.findViewById(R.id.textdate);
			
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		final OpenDBListBean json = (OpenDBListBean) getItem(position);
		String date = json.getList().get(0).getTime().substring(0, 11);
		viewHolder.textdate.setText(date+"看"+json.getList().size()+"图集");
		viewHolder.lay_grid.removeAllViews();
		for(final OpenDBBean bean:json.getList()){
			View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_app_collection, null);
			SimpleDraweeView draweeview = (SimpleDraweeView) convertView.findViewById(R.id.draweeview);
			TextView texttitle = (TextView) view.findViewById(R.id.texttitle);
			 texttitle.setText(bean.getTitle());
			if (bean.getImgsrc()!= null && bean.getImgsrc().length() > 0) {
//				DisplayImageOptions options = new DisplayImageOptions.Builder().showStubImage(R.drawable.default_img).showImageForEmptyUri(R.drawable.default_img).showImageOnFail(R.drawable.default_img)
////						.cacheInMemory().cacheOnDisc().build();
//				.cacheInMemory().cacheOnDisc().imageScaleType(ImageScaleType.EXACTLY_STRETCHED).build();
//				ImageLoader.getInstance().displayImage(bean.getImgsrc(), imageview, options, getImageLoadingListener());
			
				ControllerListener controllerListener = new BaseControllerListener<ImageInfo>() {
			        @Override
			        public void onFinalImageSet(String id,  ImageInfo imageInfo,  android.graphics.drawable.Animatable anim) {
			            if (anim != null) {
			                anim.start();
			            }
			            animatable = (AbstractAnimatedDrawable) anim;
			        }
			    };

			    DraweeController controller = Fresco.newDraweeControllerBuilder()
			            .setUri(Uri.parse(bean.getImgsrc()))
			            .setControllerListener(controllerListener)
			            .build();
			    draweeview.setController(controller);


//			    final Animatable animatable = sdv.getController().getAnimatable();
			    draweeview.setOnClickListener(new View.OnClickListener() {
			        @Override
			        public void onClick(View v) {
			            if (animatable != null) {
			                if (animatable.isRunning()) {
			                	animatable.stop();
			                } else {
			                	animatable.start();
			                }
			            }
			        }
			    });
			}
			view.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					MImagePullListActivity.startMImagePullListActivity(mContext, bean.getUrl());
				}
			});
			viewHolder.lay_grid.addView(view, new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
		}
		return convertView;
	}

	class ViewHolder {
		LinearLayout lay_grid;
		TextView textdate;
	}
}
