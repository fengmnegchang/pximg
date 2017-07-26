/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-6-21下午1:30:55
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.pxing.fragment.pc;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.open.android.fragment.common.CommonPullToRefreshListFragment;
import com.open.pxing.R;
import com.open.pxing.adapter.pc.PCHomeGridAdapter;
import com.open.pxing.bean.pc.HomeArticleBean;
import com.open.pxing.json.pc.HomeArticleJson;
import com.open.pxing.jsoup.pc.PCNavJsoupService;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-6-21下午1:30:55
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class PCHomeArticlePullListFragmnet extends CommonPullToRefreshListFragment<HomeArticleBean, HomeArticleJson> {
	public PCHomeGridAdapter mPCHomeGridAdapter;
	public View headview;
	public View footview;
	
	public static PCHomeArticlePullListFragmnet newInstance(String url, boolean isVisibleToUser) {
		PCHomeArticlePullListFragmnet fragment = new PCHomeArticlePullListFragmnet();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_common_pulllistview, container, false);
		mPullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.pull_refresh_list);
		headview = LayoutInflater.from(getActivity()).inflate(R.layout.layout_m_head, null);
		footview = LayoutInflater.from(getActivity()).inflate(R.layout.layout_expend_footview, null);
		return view;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.android.fragment.BaseV4Fragment#initValues()
	 */
	@Override
	public void initValues() {
		// TODO Auto-generated method stub
		mPullToRefreshListView.getRefreshableView().addHeaderView(headview);
		mPullToRefreshListView.getRefreshableView().addFooterView(footview);
		
		PCNavHeadExpendListFragmnet ffragment = PCNavHeadExpendListFragmnet.newInstance(url, true);
		getChildFragmentManager().beginTransaction().replace(R.id.id_expend_foot, ffragment).commit();
		
		PCHomeHeadExpendListFragmnet hfragment = PCHomeHeadExpendListFragmnet.newInstance(url, true);
		getChildFragmentManager().beginTransaction().replace(R.id.id_m_head, hfragment).commit();
		
		PCFocusViewPagerFragment pfragment = PCFocusViewPagerFragment.newInstance("http://www.mm131.com/css/focus.js", true);
		getChildFragmentManager().beginTransaction().replace(R.id.id_m_page, pfragment).commit();
		
		mPCHomeGridAdapter = new PCHomeGridAdapter(getActivity(), list);
		mPullToRefreshListView.setAdapter(mPCHomeGridAdapter);
		mPullToRefreshListView.setMode(Mode.PULL_FROM_START);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.android.fragment.common.CommonPullToRefreshListFragment#call()
	 */
	@Override
	public HomeArticleJson call() throws Exception {
		// TODO Auto-generated method stub
		HomeArticleJson mMArticleJson = new HomeArticleJson();
		mMArticleJson.setList(PCNavJsoupService.parseHomeList(url, pageNo));
		return mMArticleJson;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.android.fragment.common.CommonPullToRefreshListFragment#onCallback
	 * (com.open.android.json.CommonJson)
	 */
	@Override
	public void onCallback(HomeArticleJson result) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		Log.i(TAG, "getMode ===" + mPullToRefreshListView.getCurrentMode());
		if (mPullToRefreshListView.getCurrentMode() == Mode.PULL_FROM_START) {
			list.clear();
			list.addAll(result.getList());
			pageNo = 1;
		} else {
			if (result.getList() != null && result.getList().size() > 0) {
				list.addAll(result.getList());
			}
		}
		mPCHomeGridAdapter.notifyDataSetChanged();
		// Call onRefreshComplete when the list has been refreshed.
		mPullToRefreshListView.onRefreshComplete();
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
