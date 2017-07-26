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
package com.open.mm.fragment.pc;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.open.android.weak.WeakActivityReferenceHandler;
import com.open.mm.adapter.m.MImagePagerAdapter;
import com.open.mm.bean.m.MArticleBean;
import com.open.mm.fragment.m.MImagePagerAdapterFragment;
import com.open.mm.json.m.MArticleJson;
import com.open.mm.jsoup.m.MArticleJsoupService;
import com.open.mm.utils.UrlUtils;

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
