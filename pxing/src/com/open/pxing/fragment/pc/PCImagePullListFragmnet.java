/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-6-21下午2:38:05
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.pxing.fragment.pc;

import java.util.List;

import android.view.View;
import android.widget.AdapterView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.open.android.bean.db.OpenDBBean;
import com.open.android.db.service.OpenDBService;
import com.open.android.utils.NetWorkUtils;
import com.open.pxing.R;
import com.open.pxing.activity.pc.PCImagePagerAdapterFragmentActivity;
import com.open.pxing.adapter.m.MImageListAdapter;
import com.open.pxing.fragment.m.MImagePullListFragmnet;
import com.open.pxing.json.m.MArticleJson;
import com.open.pxing.jsoup.m.MArticleJsoupService;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-6-21下午2:38:05
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class PCImagePullListFragmnet extends MImagePullListFragmnet {
	
	public static PCImagePullListFragmnet newInstance(String url, boolean isVisibleToUser) {
		PCImagePullListFragmnet fragment = new PCImagePullListFragmnet();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.android.fragment.BaseV4Fragment#initValues()
	 */
	@Override
	public void initValues() {
		// TODO Auto-generated method stub
//		mPullToRefreshListView.getRefreshableView().addFooterView(footview);
		mPullToRefreshListView.getRefreshableView().addHeaderView(headview);
		PCImageHeadFragmnet hfragment = PCImageHeadFragmnet.newInstance(url, true);
		getChildFragmentManager().beginTransaction().replace(R.id.id_m_head, hfragment).commit();
		
		PCImageFootViewPagerFragment ffragment = PCImageFootViewPagerFragment.newInstance(url, true);
		getChildFragmentManager().beginTransaction().replace(R.id.id_m_page, ffragment).commit();
 		
		mMImageListAdapter = new MImageListAdapter(getActivity(), list);
		mPullToRefreshListView.setAdapter(mMImageListAdapter);
		mPullToRefreshListView.setMode(Mode.BOTH);
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
		String typename = "MArticleJsoupService-parsePCImageList-"+pageNo;
		if(NetWorkUtils.isNetworkAvailable(getActivity())){
			mMArticleJson.setList(MArticleJsoupService.parsePCImageList(url, pageNo));
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
	
	/* (non-Javadoc)
	 * @see com.open.android.fragment.common.CommonPullToRefreshListFragment#onItemClick(android.widget.AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		if(id!=-1 && list.get((int)id)!=null){
			MArticleJson mMArticleJson = new MArticleJson();
			mMArticleJson.setList(list);
			mMArticleJson.setCurrentPosition((int)id);
			PCImagePagerAdapterFragmentActivity.startPCImagePagerAdapterFragmentActivity(getActivity(), list.get(0).getHref(), mMArticleJson);
		}
	}
}
