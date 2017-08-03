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
import android.view.View;
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
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.open.android.activity.common.CommonWebViewActivity;
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
	public AbstractAnimatedDrawable animatable;
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
			viewHolder.draweeview = (SimpleDraweeView) convertView.findViewById(R.id.draweeview);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		final OpenDBBean bean = (OpenDBBean) getItem(position);
		if (bean != null) {
			viewHolder.texttitle.setText(bean.getTitle());
			if (bean.getImgsrc()!= null && bean.getImgsrc().length() > 0) {
//				DisplayImageOptions options = new DisplayImageOptions.Builder().showStubImage(R.drawable.default_img).showImageForEmptyUri(R.drawable.default_img).showImageOnFail(R.drawable.default_img)
////						.cacheInMemory().cacheOnDisc().build();
//				.cacheInMemory().cacheOnDisc().imageScaleType(ImageScaleType.EXACTLY_STRETCHED).build();
//				ImageLoader.getInstance().displayImage(bean.getImgsrc(), viewHolder.imageview, options, getImageLoadingListener());
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
			    viewHolder.draweeview.setController(controller);


//			    final Animatable animatable = sdv.getController().getAnimatable();
			    viewHolder.draweeview.setOnClickListener(new View.OnClickListener() {
			        @Override
			        public void onClick(View v) {
			            if (animatable != null) {
			                if (animatable.isRunning()) {
			                	animatable.stop();
			                } else {
			                	animatable.start();
			                }
			            }
//			            CommonWebViewActivity.startCommonWebViewActivity(mContext, bean.getHref());
			        }
			    });
			
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
		SimpleDraweeView draweeview;
	}
}
