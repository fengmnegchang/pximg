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

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.open.android.bean.db.OpenDBBean;
import com.open.android.db.service.OpenDBService;
import com.open.android.fragment.common.CommonPullToRefreshListFragment;
import com.open.android.utils.NetWorkUtils;
import com.open.pxing.R;
import com.open.pxing.activity.m.MImagePagerAdapterFragmentActivity;
import com.open.pxing.activity.m.MVideoViewActivity;
import com.open.pxing.adapter.m.MImageListAdapter;
import com.open.pxing.bean.m.MArticleBean;
import com.open.pxing.json.m.MArticleJson;
import com.open.pxing.jsoup.m.MArticleJsoupService;
import com.xiaomi.mistatistic.sdk.MiStatInterface;

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
public class MImagePullListFragmnet extends CommonPullToRefreshListFragment<MArticleBean, MArticleJson> {
	public MImageListAdapter mMImageListAdapter;
	public View headview,footview;

	public static MImagePullListFragmnet newInstance(String url, boolean isVisibleToUser) {
		MImagePullListFragmnet fragment = new MImagePullListFragmnet();
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
//		mPullToRefreshListView.getRefreshableView().addFooterView(footview);
		mPullToRefreshListView.getRefreshableView().addHeaderView(headview);
//		MImageHeadFragmnet hfragment = MImageHeadFragmnet.newInstance(url, true);
//		getChildFragmentManager().beginTransaction().replace(R.id.id_expend_foot, hfragment).commit();
//		
//		
		MImageFootExpendGridFragmnet ffragment = MImageFootExpendGridFragmnet.newInstance(url, true);
		getChildFragmentManager().beginTransaction().replace(R.id.id_m_head, ffragment).commit();
		
		mMImageListAdapter = new MImageListAdapter(getActivity(), list);
		mPullToRefreshListView.setAdapter(mMImageListAdapter);
		mPullToRefreshListView.setMode(Mode.PULL_FROM_START);
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
		MArticleJson mMArticleJson;
		String typename = "MArticleJsoupService-parsePXImagePagerList-"+position;
		if(NetWorkUtils.isNetworkAvailable(getActivity())){
			 mMArticleJson = MArticleJsoupService.parsePXImagePagerList(url,position);
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
	 * com.open.android.fragment.common.CommonPullToRefreshListFragment#onCallback
	 * (com.open.android.json.CommonJson)
	 */
	@Override
	public void onCallback(MArticleJson result) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		if(result==null){
			return;
		}
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
		mMImageListAdapter.notifyDataSetChanged();
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
		if(id!=-1 && list.get((int)id)!=null){
			MArticleJson mMArticleJson = new MArticleJson();
			mMArticleJson.setList(list);
			mMArticleJson.setCurrentPosition((int)id);
			if(list.get(0).getHref().contains(".mp4")){
				MiStatInterface.recordCountEvent("视频", "播放视频");
				MVideoViewActivity.startMVideoViewActivity(getActivity(), list.get(0).getHref());
			}else{
				MImagePagerAdapterFragmentActivity.startMImagePagerAdapterFragmentActivity(getActivity(),list.get(0).getHref(), mMArticleJson);
			}
		}
	}
  
}
