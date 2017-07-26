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
package com.open.mm.fragment.pc;

import android.view.View;
import android.widget.AdapterView;

import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.open.mm.R;
import com.open.mm.activity.m.MImagePagerAdapterFragmentActivity;
import com.open.mm.activity.pc.PCImagePagerAdapterFragmentActivity;
import com.open.mm.adapter.m.MImageListAdapter;
import com.open.mm.fragment.m.MImageFootExpendListFragmnet;
import com.open.mm.fragment.m.MImageHeadFragmnet;
import com.open.mm.fragment.m.MImagePullListFragmnet;
import com.open.mm.json.m.MArticleJson;
import com.open.mm.jsoup.m.MArticleJsoupService;

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
		mMArticleJson.setList(MArticleJsoupService.parsePCImageList(url, pageNo));
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
