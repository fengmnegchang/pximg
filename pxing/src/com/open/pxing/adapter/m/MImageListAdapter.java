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

import java.util.List;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
import com.open.pxing.R;
import com.open.pxing.bean.m.MArticleBean;

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
public class MImageListAdapter extends CommonAdapter<MArticleBean>{
	public AbstractAnimatedDrawable animatable;
	public MImageListAdapter(Context mContext, List<MArticleBean> list) {
		super(mContext, list);
	}
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.adapter_m_image, parent, false);
			viewHolder.imageview = (ImageView) convertView.findViewById(R.id.imageview);
			viewHolder.draweeview = (SimpleDraweeView) convertView.findViewById(R.id.draweeview);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		final MArticleBean bean = (MArticleBean) getItem(position);
		if (bean != null) {
			if (bean.getDataimg()!= null && bean.getDataimg().length() > 0) {
//				DisplayImageOptions options = new DisplayImageOptions.Builder().showStubImage(R.drawable.default_img).showImageForEmptyUri(R.drawable.default_img).showImageOnFail(R.drawable.default_img)
////						.cacheInMemory().cacheOnDisc().build();
//				.cacheInMemory().cacheOnDisc().imageScaleType(ImageScaleType.EXACTLY_STRETCHED).build();
//				ImageLoader.getInstance().displayImage(bean.getDataimg(), viewHolder.imageview, options, getImageLoadingListener());
			
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
			            .setUri(Uri.parse(bean.getDataimg()))
			            .setControllerListener(controllerListener)
			            .build();
			    viewHolder.draweeview.setController(controller);


////			    final Animatable animatable = sdv.getController().getAnimatable();
//			    viewHolder.draweeview.setOnClickListener(new View.OnClickListener() {
//			        @Override
//			        public void onClick(View v) {
//			            if (animatable != null) {
//			                if (animatable.isRunning()) {
//			                	animatable.stop();
//			                } else {
//			                	animatable.start();
//			                }
//			            }
//			        }
//			    });
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
		SimpleDraweeView draweeview;
	}
}
