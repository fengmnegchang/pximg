/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-6-9上午11:38:33
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.pxing.fragment.app;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.open.pxing.R;
import com.open.pxing.activity.app.MCollectionGridFragmentActivity;
import com.open.pxing.activity.app.MHistoryListGridFragmentActivity;
import com.open.pxing.adapter.m.MSlideMenuAdapter;
import com.open.pxing.bean.m.MSlideMenuBean;
import com.open.pxing.fragment.m.MLeftMenuPullListFragmnet;
import com.open.pxing.json.m.MSlideMenuJson;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2017-6-9上午11:38:33
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class MAppLeftMenuPullListFragmnet extends MLeftMenuPullListFragmnet {
	public View headview;

	public static MAppLeftMenuPullListFragmnet newInstance(String url, boolean isVisibleToUser) {
		MAppLeftMenuPullListFragmnet fragment = new MAppLeftMenuPullListFragmnet();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_common_pulllistview, container, false);
		mPullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.pull_refresh_list);
		headview = LayoutInflater.from(getActivity()).inflate(R.layout.layout_m_app_menu_head, null);
		return view;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.Fragment#onViewCreated(android.view.View,
	 * android.os.Bundle)
	 */
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
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
		// mMSlideMenuJson.setList(MLeftMenuJsoupService.parseList(url,
		// pageNo));
		List<MSlideMenuBean> list = new ArrayList<MSlideMenuBean>();
		MSlideMenuBean bean = new MSlideMenuBean();
		bean.setTitle("我的收藏");
		bean.setResid(getResources().getIdentifier("icon_my_favorite", "drawable", getActivity().getPackageName()));
		list.add(bean);

		bean = new MSlideMenuBean();
		bean.setTitle("浏览历史");
		bean.setResid(getResources().getIdentifier("icon_browsing_history", "drawable", getActivity().getPackageName()));
		list.add(bean);

		bean = new MSlideMenuBean();
		bean.setTitle("清空缓存");
		bean.setResid(getResources().getIdentifier("icon_clear_cache", "drawable", getActivity().getPackageName()));
		list.add(bean);

		bean = new MSlideMenuBean();
		bean.setTitle("关于我们");
		bean.setResid(getResources().getIdentifier("icon_about_us", "drawable", getActivity().getPackageName()));
		list.add(bean);
		mMSlideMenuJson.setList(list);
		return mMSlideMenuJson;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.mm.fragment.m.MLeftMenuPullListFragmnet#onItemClick(android.
	 * widget.AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		// super.onItemClick(parent, view, position, id);
		if (id != -1 && list.get((int) id) != null) {
			String title = list.get((int) id).getTitle();
			if (title.equals("我的收藏")) {
				MCollectionGridFragmentActivity.startMCollectionGridFragmentActivity(getActivity(), title);
			} else if (title.equals("浏览历史")) {
				MHistoryListGridFragmentActivity.startMHistoryListGridFragmentActivity(getActivity(), title);
			} else if (title.equals("关于我们")) {

			}
		}
	}

}
