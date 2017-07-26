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
package com.open.mm.fragment.m;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.open.android.fragment.common.CommonPullToRefreshListFragment;
import com.open.mm.activity.m.MArticlePullListActivity;
import com.open.mm.adapter.m.MSlideMenuAdapter;
import com.open.mm.bean.m.MSlideMenuBean;
import com.open.mm.json.m.MSlideMenuJson;
import com.open.mm.jsoup.m.MLeftMenuJsoupService;

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
public class MLeftMenuPullListFragmnet extends CommonPullToRefreshListFragment<MSlideMenuBean, MSlideMenuJson> {
	public MSlideMenuAdapter mMSlideMenuAdapter;

	public static MLeftMenuPullListFragmnet newInstance(String url, boolean isVisibleToUser) {
		MLeftMenuPullListFragmnet fragment = new MLeftMenuPullListFragmnet();
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
		mMSlideMenuAdapter = new MSlideMenuAdapter(getActivity(), list);
		mPullToRefreshListView.setAdapter(mMSlideMenuAdapter);
		mPullToRefreshListView.setMode(Mode.PULL_FROM_START);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.android.fragment.common.CommonPullToRefreshListFragment#call()
	 */
	@Override
	public MSlideMenuJson call() throws Exception {
		// TODO Auto-generated method stub
		MSlideMenuJson mMSlideMenuJson = new MSlideMenuJson();
		mMSlideMenuJson.setList(MLeftMenuJsoupService.parseList(url, pageNo));
		return mMSlideMenuJson;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.android.fragment.common.CommonPullToRefreshListFragment#onCallback
	 * (com.open.android.json.CommonJson)
	 */
	@Override
	public void onCallback(MSlideMenuJson result) {
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
		mMSlideMenuAdapter.notifyDataSetChanged();
		// Call onRefreshComplete when the list has been refreshed.
		mPullToRefreshListView.onRefreshComplete();
	}
	
	 /* (non-Javadoc)
	 * @see com.open.android.fragment.common.CommonPullToRefreshListFragment#onItemClick(android.widget.AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		super.onItemClick(parent, view, position, id);
		if(id!=1 && list.get((int)id)!=null){
			MArticlePullListActivity.startMArticlePullListActivity(getActivity(), list.get((int)id).getHref());
		}
		
	}

}
