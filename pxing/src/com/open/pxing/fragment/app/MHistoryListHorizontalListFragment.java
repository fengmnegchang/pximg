/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-6-12下午2:08:00
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.pxing.fragment.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.util.Log;

import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.open.android.bean.db.OpenDBBean;
import com.open.pxing.adapter.app.MListHorizontalAdapter;
import com.open.pxing.bean.app.OpenDBListBean;
import com.open.pxing.json.m.OpenDBJson;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-6-12下午2:08:00
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class MHistoryListHorizontalListFragment extends MHistoryListGridFragment{
	public MListHorizontalAdapter mMListHorizontalAdapter;
	
	public static MHistoryListHorizontalListFragment newInstance(String url, boolean isVisibleToUser) {
		MHistoryListHorizontalListFragment fragment = new MHistoryListHorizontalListFragment();
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
		mMListHorizontalAdapter = new MListHorizontalAdapter(getActivity(), list);
		mPullToRefreshListView.setAdapter(mMListHorizontalAdapter);
	}
	
	/* (non-Javadoc)
	 * @see com.open.android.fragment.common.CommonPullToRefreshGridFragment#onCallback(com.open.android.json.CommonJson)
	 */
	@Override
	public void onCallback(OpenDBJson result) {
		// TODO Auto-generated method stub
//		super.onCallback(result);
		Log.i(TAG, "getMode ===" + mPullToRefreshListView.getCurrentMode());
		if (mPullToRefreshListView.getCurrentMode() == Mode.PULL_FROM_START) {
			list.clear();
//			list.addAll(result.getList());
			Map<String,ArrayList<OpenDBBean>> map= new HashMap<String,ArrayList<OpenDBBean>>();
			String date;
			for(OpenDBBean bean:result.getList()){
				//2017年06月09日 21:00
				date =  bean.getTime().substring(0, 11);
				Log.d(TAG, "date=="+date);
				if(map.containsKey(date)){
					map.get(date).add(bean);
				}else{
					ArrayList<OpenDBBean> list = new ArrayList<OpenDBBean>();
					list.add(bean);
					map.put(date, list);
				}
			}
			
			for(String time:map.keySet()){
				OpenDBListBean bean = new OpenDBListBean();
				bean.setList(map.get(time));
				list.add(bean);
			}
			pageNo = 1;
		} else {
			if (result.getList() != null && result.getList().size() > 0) {
//				list.addAll(result.getList());
			}
		}
//		mPullToRefreshHeadGridView.getRefreshableView().setNumColumns(result.getList().size());
		mMListHorizontalAdapter.notifyDataSetChanged();
		// Call onRefreshComplete when the list has been refreshed.
		mPullToRefreshListView.onRefreshComplete();
	}
}
