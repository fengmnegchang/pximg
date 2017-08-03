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
package com.open.pxing.adapter.pc;

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

import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.animated.base.AbstractAnimatedDrawable;
import com.open.android.activity.common.CommonWebViewActivity;
import com.open.android.adapter.CommonAdapter;
import com.open.pxing.R;
import com.open.pxing.bean.m.MArticleBean;
import com.open.pxing.bean.pc.HomeArticleBean;

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
public class PCHomeGridAdapter extends CommonAdapter<HomeArticleBean> {
	public AbstractAnimatedDrawable animatable;
	public PCHomeGridAdapter(Context mContext, List<HomeArticleBean> list) {
		super(mContext, list);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.adapter_pc_home, parent, false);
			viewHolder.lay_grid = (LinearLayout) convertView.findViewById(R.id.lay_grid);
			viewHolder.textdate = (TextView) convertView.findViewById(R.id.textdate);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		final HomeArticleBean json = (HomeArticleBean) getItem(position);
		viewHolder.textdate.setText(json.getAlt());
		viewHolder.lay_grid.removeAllViews();
		for (final MArticleBean bean : json.getList()) {
			View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_app_collection, null);
//			ImageView imageview = (ImageView) view.findViewById(R.id.imageview);
			TextView texttitle = (TextView) view.findViewById(R.id.texttitle);
			ImageView image_select = (ImageView) view.findViewById(R.id.image_select);
			SimpleDraweeView  draweeview = (SimpleDraweeView) view.findViewById(R.id.draweeview);
			image_select.setVisibility(View.GONE);
			texttitle.setText(bean.getAlt());
			if (bean.getSrc() != null && bean.getSrc().length() > 0) {
//				DisplayImageOptions options = new DisplayImageOptions.Builder().showStubImage(R.drawable.default_img).showImageForEmptyUri(R.drawable.default_img)
//						.showImageOnFail(R.drawable.default_img)
//						// .cacheInMemory().cacheOnDisc().build();
//						.cacheInMemory().cacheOnDisc().imageScaleType(ImageScaleType.EXACTLY_STRETCHED).build();
//				ImageLoader.getInstance().displayImage(bean.getSrc(), imageview, options, getImageLoadingListener());
//				
				Uri uri = Uri.parse(bean.getSrc());
				draweeview.setImageURI(uri);
//				ControllerListener controllerListener = new BaseControllerListener<ImageInfo>() {
//			        @Override
//			        public void onFinalImageSet(String id,  ImageInfo imageInfo,  android.graphics.drawable.Animatable anim) {
//			            if (anim != null) {
//			                anim.start();
//			            }
//			            animatable = (AbstractAnimatedDrawable) anim;
//			        }
//			    };
//
//			    DraweeController controller = Fresco.newDraweeControllerBuilder()
//			            .setUri(Uri.parse(bean.getSrc()))
//			            .setControllerListener(controllerListener)
//			            .build();
//			    draweeview.setController(controller);
//
//
////			    final Animatable animatable = sdv.getController().getAnimatable();
//			    draweeview.setOnClickListener(new View.OnClickListener() {
//			        @Override
//			        public void onClick(View v) {
//			            if (animatable != null) {
//			                if (animatable.isRunning()) {
//			                	animatable.stop();
//			                } else {
//			                	animatable.start();
//			                }
//			            }
//			            PCImagePullListActivity.startPCImagePullListActivity(mContext, bean.getHref());
////			            CommonWebViewActivity.startCommonWebViewActivity(mContext, bean.getHref());
//			        }
//			    });
			}
			view.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
//					PCImagePullListActivity.startPCImagePullListActivity(mContext, bean.getHref());
					CommonWebViewActivity.startCommonWebViewActivity(mContext, bean.getHref());
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
