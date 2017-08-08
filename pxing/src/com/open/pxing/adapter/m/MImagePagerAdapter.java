/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-6-8下午5:32:41
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.pxing.adapter.m;

import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
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
import com.open.android.adapter.CommonPagerAdapter;
import com.open.android.bean.db.OpenDBBean;
import com.open.android.db.service.OpenDBService;
import com.open.android.utils.ImageAsyncTask;
import com.open.android.view.ZoomImageView;
import com.open.android.weak.WeakActivityReferenceHandler;
import com.open.pxing.R;
import com.open.pxing.bean.m.MArticleBean;
import com.xiaomi.mistatistic.sdk.MiStatInterface;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-6-8下午5:32:41
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class MImagePagerAdapter extends CommonPagerAdapter<MArticleBean>{
	public AbstractAnimatedDrawable animatable;
	public MImagePagerAdapter(Context mContext, List<MArticleBean> list) {
		super(mContext, list);
	}

	WeakActivityReferenceHandler weakActivityReferenceHandler;
	public MImagePagerAdapter(Context mContext, List<MArticleBean> list,WeakActivityReferenceHandler weakActivityReferenceHandler) {
		super(mContext, list);
		this.weakActivityReferenceHandler = weakActivityReferenceHandler;
	}
	
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		final MArticleBean bean = (MArticleBean) getItem(position);
		final ViewHolder mViewHolder = new ViewHolder();
		View convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_m_image_viewpager, null);
		mViewHolder.imageview = (ZoomImageView) convertView.findViewById(R.id.imageview);
		mViewHolder.txt_save = (TextView) convertView.findViewById(R.id.txt_save);
		mViewHolder.draweeview = (SimpleDraweeView) convertView.findViewById(R.id.draweeview);
		if (bean != null) {
			if (bean.getDataimg() != null && bean.getDataimg().length() > 0) {
//				DisplayImageOptions options = new DisplayImageOptions.Builder().showStubImage(R.drawable.common_v4).showImageForEmptyUri(R.drawable.common_v4).showImageOnFail(R.drawable.common_v4)
//						.cacheInMemory().cacheOnDisc().build();
//				ImageLoader.getInstance().displayImage(bean.getDataimg(), mViewHolder.imageview, options, null);
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
			        }
			    });
				
			}
		}

		mViewHolder.imageview.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				UmeiWebViewActivity.startUmeiWebViewActivity(mContext, bean.getSrc());
				weakActivityReferenceHandler.sendEmptyMessage(7000);
			}
		});
		mViewHolder.txt_save.setOnClickListener(new View.OnClickListener() {  
	           @Override  
	           public void onClick(View v) {  
	               AlertDialog.Builder builder = new AlertDialog.Builder(mContext);  
	               builder.setItems(new String[]{mContext.getResources().getString(R.string.save_picture)}, new DialogInterface.OnClickListener() {  
	                   @Override  
	                   public void onClick(DialogInterface dialog, int which) {  
	                	   //保存收藏
	                	   MiStatInterface.recordCountEvent("美图", "保存图片");
	                	   String href="";
	                	   if(bean.getHref()!=null){
	                		   if(bean.getHref().contains("_")){
		                		   href = bean.getHref().split("_")[0]+".html";
		                	   }else{
		                		   href = bean.getHref();
		                	   }
	                	   }
	                	   OpenDBBean  openbean = new OpenDBBean();
	                	   openbean.setImgsrc(bean.getDataimg());
	                	   openbean.setUrl(href);
		       		       openbean.setType(0);
		       		       openbean.setTitle(bean.getAlt());
		       		       openbean.setTypename(0+"");
	                	   OpenDBService.insert(mContext, openbean);
	                	   
	                	   mViewHolder.draweeview.setDrawingCacheEnabled(true);  
	                       Bitmap imageBitmap = mViewHolder.draweeview.getDrawingCache();  
	                       if (imageBitmap != null) {  
	                           new ImageAsyncTask(mContext,  mViewHolder.draweeview,bean.getDataimg()).execute(imageBitmap);  
	                       }  
	                   }  
	               });  
	               builder.show();  
	           }  
	       });  
		container.addView(convertView);
		return convertView;
	}
	
//	@Override
//	public Object instantiateItem(ViewGroup container, int position) {
//		final UmeiArticleBean bean = (UmeiArticleBean) getItem(position);
//		final ImageView imageview = new ImageView (mContext);
//		if (bean != null) {
//			if (bean.getSrc() != null && bean.getSrc().length() > 0) {
//				DisplayImageOptions options = new DisplayImageOptions.Builder().showStubImage(R.drawable.common_v4).showImageForEmptyUri(R.drawable.common_v4).showImageOnFail(R.drawable.common_v4)
//						.cacheInMemory().cacheOnDisc().build();
//				ImageLoader.getInstance().displayImage(bean.getSrc(),imageview, options, null);
//			}
//		}
//
//		 imageview.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
////				UmeiWebViewActivity.startUmeiWebViewActivity(mContext, bean.getSrc());
//				weakReferenceHandler.sendEmptyMessage(7000);
//			}
//		});
//		imageview.setOnLongClickListener(new View.OnLongClickListener() {  
//	           @Override  
//	           public boolean onLongClick(View v) {  
//	               AlertDialog.Builder builder = new AlertDialog.Builder(mContext);  
//	               builder.setItems(new String[]{mContext.getResources().getString(R.string.save_picture)}, new DialogInterface.OnClickListener() {  
//	                   @Override  
//	                   public void onClick(DialogInterface dialog, int which) {  
//	                	   imageview.setDrawingCacheEnabled(true);  
//	                       Bitmap imageBitmap = imageview.getDrawingCache();  
//	                       if (imageBitmap != null) {  
//	                           new ImageAsyncTask(mContext,  imageview,bean.getSrc()).execute(imageBitmap);  
//	                       }  
//	                   }  
//	               });  
//	               builder.show();  
//	               return true;  
//	           }  
//	       });  
//		container.addView(imageview, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
//		return imageview;
//	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View)object);
	}

	@Override
	public void startUpdate(ViewGroup container) {
		// TODO Auto-generated method stub
		super.startUpdate(container);
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}

	@Override
	public int getItemPosition(Object object) {
		// TODO Auto-generated method stub
		return POSITION_NONE;
	}

	private class ViewHolder {
		ZoomImageView imageview;
		TextView txt_save;
		SimpleDraweeView draweeview;
	}
}
