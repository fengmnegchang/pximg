package com.open.pxing.fragment.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.open.android.bean.db.OpenDBBean;
import com.open.android.db.service.OpenDBService;
import com.open.android.fragment.common.CommonPullToRefreshListFragment;
import com.open.pxing.adapter.app.MHistoryGridAdapter;
import com.open.pxing.bean.app.OpenDBListBean;
import com.open.pxing.json.m.OpenDBJson;

public class MHistoryListGridFragment extends CommonPullToRefreshListFragment<OpenDBListBean, OpenDBJson> {
	public MHistoryGridAdapter mMHistoryGridAdapter;
	
	public static MHistoryListGridFragment newInstance(String url, boolean isVisibleToUser) {
		MHistoryListGridFragment fragment = new MHistoryListGridFragment();
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
		super.initValues();
		mMHistoryGridAdapter = new MHistoryGridAdapter(getActivity(), list);
		mPullToRefreshListView.setAdapter(mMHistoryGridAdapter);
	}

	/* (non-Javadoc)
	 * @see com.open.android.fragment.common.CommonPullToRefreshGridFragment#call()
	 */
	@Override
	public OpenDBJson call() throws Exception {
		// TODO Auto-generated method stub
		OpenDBJson mOpenDBJson = new OpenDBJson();
		mOpenDBJson.setList(OpenDBService.queryListType(getActivity(), 1));
		return mOpenDBJson;
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
		mMHistoryGridAdapter.notifyDataSetChanged();
		// Call onRefreshComplete when the list has been refreshed.
		mPullToRefreshListView.onRefreshComplete();
	}
	
	/* (non-Javadoc)
	 * @see com.open.android.fragment.common.CommonPullToRefreshGridFragment#onItemClick(android.widget.AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
//		super.onItemClick(parent, view, position, id);
//		if(id!=-1 && list.get((int)id)!=null){
//			MImagePullListActivity.startMImagePullListActivity(getActivity(), list.get((int)id).getUrl());
//		}
	}
	
	
	public void cleanHistory(){
		//OpenDBService.deleteAll(mContext);
	}
	
}
