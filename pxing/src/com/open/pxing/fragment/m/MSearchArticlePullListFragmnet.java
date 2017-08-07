/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-6-7下午5:33:37
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.pxing.fragment.m;

import java.util.List;

import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.animated.base.AbstractAnimatedDrawable;
import com.facebook.imagepipeline.image.ImageInfo;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshHeadGridView;
import com.open.andenginetask.CallEarliest;
import com.open.andenginetask.Callable;
import com.open.andenginetask.Callback;
import com.open.android.bean.db.OpenDBBean;
import com.open.android.db.service.OpenDBService;
import com.open.android.utils.NetWorkUtils;
import com.open.pxing.R;
import com.open.pxing.adapter.m.MArticleGridAdapter;
import com.open.pxing.bean.m.MArticleBean;
import com.open.pxing.json.m.MArticleJson;
import com.open.pxing.jsoup.m.MArticleJsoupService;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2017-6-7下午5:33:37
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class MSearchArticlePullListFragmnet extends MArticlePullGridFragmnet{
	public View headview;
	public TextView text_camLiDes;
	public SimpleDraweeView mSimpleDraweeView;
	public AbstractAnimatedDrawable animatable;
	
	public static MSearchArticlePullListFragmnet newInstance(String url, boolean isVisibleToUser) {
		MSearchArticlePullListFragmnet fragment = new MSearchArticlePullListFragmnet();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_common_pullgridview, container, false);
		mPullToRefreshHeadGridView = (PullToRefreshHeadGridView) view.findViewById(R.id.pull_refresh_grid);
		headview = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_m_rand, null);
		text_camLiDes = (TextView) headview.findViewById(R.id.text_camLiDes);
		mSimpleDraweeView = (SimpleDraweeView) headview.findViewById(R.id.draweeview);
		return view;
	}
	
	/* (non-Javadoc)
	 * @see com.open.pxing.fragment.m.MArticlePullGridFragmnet#initValues()
	 */
	@Override
	public void initValues() {
		// TODO Auto-generated method stub
		mPullToRefreshHeadGridView.getRefreshableView().addHeaderView(headview);
//		MRandHeadFragmnet fragment =  MRandHeadFragmnet.newInstance(url, true);
//		getChildFragmentManager().beginTransaction().replace(R.id.id_expend_foot, fragment).commit();
		
		mMArticleGridAdapter = new MArticleGridAdapter(getActivity(), list);
		mPullToRefreshHeadGridView.setAdapter(mMArticleGridAdapter);
		mPullToRefreshHeadGridView.setMode(Mode.BOTH);
		
		doAsync(new CallEarliest<MArticleJson>() {
			@Override
			public void onCallEarliest() throws Exception {
			}
		}, new Callable<MArticleJson>() {
			@Override
			public MArticleJson call() throws Exception {
				MArticleJson mMArticleJson = new MArticleJson();
				String typename = "MArticleJsoupService-parsePXRandHeadList-"+pageNo;
				if(NetWorkUtils.isNetworkAvailable(getActivity())){
					mMArticleJson.setList(MArticleJsoupService.parsePXRandHeadList(url, pageNo));
					try {
						//数据存储
						Gson gson = new Gson();
						OpenDBBean  openbean = new OpenDBBean();
			    	    openbean.setUrl(url);
			    	    openbean.setTypename(typename);
					    openbean.setTitle(gson.toJson(mMArticleJson));
					    OpenDBService.insert(getActivity(), openbean);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else{
					List<OpenDBBean> beanlist =  OpenDBService.queryListType(getActivity(), url,typename);
					String result = beanlist.get(0).getTitle();
					Gson gson = new Gson();
					mMArticleJson = gson.fromJson(result, MArticleJson.class);
				}
				return mMArticleJson;
			}
		} , new Callback<MArticleJson>() {
			@Override
			public void onCallback(MArticleJson result) {
				if(result==null){
					return;
				}
				try {
					MArticleBean bean = result.getList().get(0); 
					if(bean!=null){
						text_camLiDes.setText(bean.getPostmeta());
						if (bean.getSrc()!= null && bean.getSrc().length() > 0) {
//							DisplayImageOptions options = new DisplayImageOptions.Builder().showStubImage(R.drawable.default_img).showImageForEmptyUri(R.drawable.default_img).showImageOnFail(R.drawable.default_img)
////									.cacheInMemory().cacheOnDisc().build();
//							.cacheInMemory().cacheOnDisc().imageScaleType(ImageScaleType.EXACTLY_STRETCHED).build();
//							ImageLoader.getInstance().displayImage(bean.getSrc(), viewHolder.imageview, options, getImageLoadingListener());
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
						    mSimpleDraweeView.setController(controller);


//						    final Animatable animatable = sdv.getController().getAnimatable();
						    mSimpleDraweeView.setOnClickListener(new View.OnClickListener() {
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
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.android.fragment.common.CommonPullToRefreshListFragment#call()
	 */
	@Override
	public MArticleJson call() throws Exception {
		// TODO Auto-generated method stub
		MArticleJson mMArticleJson = new MArticleJson();
		//https://www.pximg.com /? s=
		//https://www.pximg.com /page/2? s=
		
		String href = url;
		if(pageNo>1){
			if(href.contains("/?")){
				href = url.replace("/?", "/page/"+pageNo+"?");
			}else{
				//https://www.pximg.com/topic/xingganmeinv/page/2
				//https://www.pximg.com/topic/xingganmeinv
				href = url+"/page/"+pageNo;
			}
		}
		String typename = "MArticleJsoupService-parseSearchList-"+pageNo;
		if(NetWorkUtils.isNetworkAvailable(getActivity())){
			mMArticleJson.setList(MArticleJsoupService.parseSearchList(href, pageNo));
			try {
				//数据存储
				Gson gson = new Gson();
				OpenDBBean  openbean = new OpenDBBean();
	    	    openbean.setUrl(url);
	    	    openbean.setTypename(typename);
			    openbean.setTitle(gson.toJson(mMArticleJson));
			    OpenDBService.insert(getActivity(), openbean);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			List<OpenDBBean> beanlist =  OpenDBService.queryListType(getActivity(), url,typename);
			String result = beanlist.get(0).getTitle();
			Gson gson = new Gson();
			mMArticleJson = gson.fromJson(result, MArticleJson.class);
		}
		return mMArticleJson;
	}

 
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.enrz.fragment.BaseV4Fragment#handlerMessage(android.os.Message)
	 */
	@Override
	public void handlerMessage(Message msg) {
		// TODO Auto-generated method stub
		switch (msg.what) {
		case MESSAGE_HANDLER:
			doAsync(this, this, this);
			break;
		default:
			break;
		}
	}

}
