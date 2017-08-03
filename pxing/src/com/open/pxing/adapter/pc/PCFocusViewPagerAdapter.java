/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-6-21上午11:30:18
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
import com.open.android.activity.common.CommonWebViewActivity;
import com.open.android.adapter.CommonPagerAdapter;
import com.open.pxing.R;
import com.open.pxing.activity.pc.PCImagePullListActivity;
import com.open.pxing.bean.m.MArticleBean;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-6-21上午11:30:18
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class PCFocusViewPagerAdapter extends CommonPagerAdapter<MArticleBean> {
	public AbstractAnimatedDrawable animatable;
	public PCFocusViewPagerAdapter(Context mContext, List<MArticleBean> list) {
		super(mContext, list);
	}
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		final MArticleBean bean = (MArticleBean) getItem(position);
		final ViewHolder mViewHolder = new ViewHolder();
		View convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_pc_focus, null);
		mViewHolder.imageview = (ImageView) convertView.findViewById(R.id.imageview);
		mViewHolder.txt_alt = (TextView) convertView.findViewById(R.id.txt_alt);
		mViewHolder.draweeview = (SimpleDraweeView) convertView.findViewById(R.id.draweeview);
		if (bean != null) {
			if (bean.getSrc() != null && bean.getSrc().length() > 0) {
//				DisplayImageOptions options = new DisplayImageOptions.Builder().showStubImage(R.drawable.default_img).showImageForEmptyUri(R.drawable.default_img).showImageOnFail(R.drawable.default_img)
//						.cacheInMemory().cacheOnDisc().build();
//				ImageLoader.getInstance().displayImage(bean.getSrc(), mViewHolder.imageview, options, null);
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
			            .setUri(Uri.parse(bean.getSrc()))
			            .setControllerListener(controllerListener)
			            .build();
			    mViewHolder.draweeview.setController(controller);


//			    final Animatable animatable = sdv.getController().getAnimatable();
			    mViewHolder.draweeview.setOnClickListener(new View.OnClickListener() {
			        @Override
			        public void onClick(View v) {
			            if (animatable != null) {
			                if (animatable.isRunning()) {
			                	animatable.stop();
			                } else {
			                	animatable.start();
			                }
			            }
			            PCImagePullListActivity.startPCImagePullListActivity(mContext, bean.getHref());
//			            CommonWebViewActivity.startCommonWebViewActivity(mContext, bean.getHref());
			        }
			    });
			}
			mViewHolder.txt_alt.setText(bean.getAlt());
			convertView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
//					YokaWebViewActivity.startYokaWebViewActivity(mContext, bean.getHref());
					PCImagePullListActivity.startPCImagePullListActivity(mContext, bean.getHref());
				}
			});
		}
		container.addView(convertView);
		return convertView;
	}

	private class ViewHolder {
		ImageView imageview;
		TextView txt_alt;
		SimpleDraweeView draweeview;
	}
}
