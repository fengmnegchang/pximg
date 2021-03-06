/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-6-21下午3:06:58
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.pxing.fragment.pc;

import java.util.List;

import com.open.android.weak.WeakActivityReferenceHandler;
import com.open.pxing.bean.m.MArticleBean;
import com.open.pxing.fragment.m.MImagePagerAdapterFragment;
import com.open.pxing.json.m.MArticleJson;
import com.open.pxing.jsoup.m.MArticleJsoupService;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-6-21下午3:06:58
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class PCImagePagerAdapterFragment extends MImagePagerAdapterFragment{
	public static PCImagePagerAdapterFragment newInstance(String url, boolean isVisibleToUser) {
		PCImagePagerAdapterFragment fragment = new PCImagePagerAdapterFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}
	
	public static PCImagePagerAdapterFragment newInstance(String url, boolean isVisibleToUser,WeakActivityReferenceHandler weakActivityReferenceHandler) {
		PCImagePagerAdapterFragment fragment = new PCImagePagerAdapterFragment();
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		fragment.weakActivityReferenceHandler = weakActivityReferenceHandler;
		return fragment;
	}
	
	public static PCImagePagerAdapterFragment newInstance(String url, boolean isVisibleToUser,WeakActivityReferenceHandler weakActivityReferenceHandler,List<MArticleBean> list,int position) {
		PCImagePagerAdapterFragment fragment = new PCImagePagerAdapterFragment();
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		fragment.weakActivityReferenceHandler = weakActivityReferenceHandler;
		if(list!=null && list.size()>0){
			fragment.list = list;
			fragment.position = position;
		}
		return fragment;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.umei.activity.BaseFragmentActivity#call()
	 */
	@Override
	public MArticleJson call() throws Exception {
		// TODO Auto-generated method stub
		MArticleJson mMArticleJson =null;
		if(pageNo==1){
			mMArticleJson = MArticleJsoupService.parsePCImagePagerList(url,position);
		}else{
			mMArticleJson = new MArticleJson();
			mMArticleJson.setList(MArticleJsoupService.parsePCImageList(list.get(position).getHref(),position));
			mMArticleJson.setCurrentPosition(position);
		}
		return mMArticleJson;
	}
}
