/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-6-20下午3:52:27
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.pxing.fragment.pc;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshHeadGridView;
import com.open.andenginetask.CallEarliest;
import com.open.andenginetask.Callable;
import com.open.andenginetask.Callback;
import com.open.android.bean.db.OpenDBBean;
import com.open.android.db.service.OpenDBService;
import com.open.android.fragment.common.CommonPullToRefreshGridFragment;
import com.open.android.utils.NetWorkUtils;
import com.open.android.view.ExpendListView;
import com.open.pxing.R;
import com.open.pxing.activity.pc.PCImagePullListActivity;
import com.open.pxing.adapter.m.MImageFootListAdapter;
import com.open.pxing.adapter.pc.PCNavGridAdapter;
import com.open.pxing.bean.m.MArticleBean;
import com.open.pxing.json.m.MArticleJson;
import com.open.pxing.jsoup.m.MArticleJsoupService;
import com.open.pxing.jsoup.pc.PCNavJsoupService;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-6-20下午3:52:27
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class PCNavGridAddHeadFragment  extends CommonPullToRefreshGridFragment<MArticleBean, MArticleJson> {
	public PCNavGridAdapter mPCNavGridAdapter;
	public View headview;
	public MImageFootListAdapter mMImageFootListAdapter;
	public ExpendListView mExpendListView;
	public List<MArticleBean> hlist = new ArrayList<MArticleBean>();
	
	public static PCNavGridAddHeadFragment newInstance(String url, boolean isVisibleToUser) {
		PCNavGridAddHeadFragment fragment = new PCNavGridAddHeadFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_common_pullgridview, container, false);
		mPullToRefreshHeadGridView = (PullToRefreshHeadGridView) view.findViewById(R.id.pull_refresh_grid);
		headview = LayoutInflater.from(getActivity()).inflate(R.layout.layout_pc_nav_head, null);
		mExpendListView = (ExpendListView) headview.findViewById(R.id.pull_refresh_list);
		return view;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.android.fragment.BaseV4Fragment#initValues()
	 */
	@Override
	public void initValues() {
		mPullToRefreshHeadGridView.getRefreshableView().addHeaderView(headview);
//		PCNavHeadExpendListFragmnet ffragment = PCNavHeadExpendListFragmnet.newInstance(url, true);
//		getChildFragmentManager().beginTransaction().replace(R.id.id_m_head, ffragment).commit();
		
		mMImageFootListAdapter = new MImageFootListAdapter(getActivity(), hlist);
		mExpendListView.setAdapter(mMImageFootListAdapter);
		
		// TODO Auto-generated method stub
		mPCNavGridAdapter = new PCNavGridAdapter(getActivity(), list);
		mPullToRefreshHeadGridView.setAdapter(mPCNavGridAdapter);
		mPullToRefreshHeadGridView.setMode(Mode.BOTH);
		
		
		//head
		doAsync(new CallEarliest<MArticleJson>() {
			@Override
			public void onCallEarliest() throws Exception {
				
			}
		}, new Callable<MArticleJson>(){
			@Override
			public MArticleJson call() throws Exception {
				MArticleJson mMArticleJson = new MArticleJson();
				String typename = "PCNavJsoupService-parseFootList-"+pageNo;
				if(NetWorkUtils.isNetworkAvailable(getActivity())){
					mMArticleJson.setList(PCNavJsoupService.parseFootList(url, pageNo));
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
		}  , new Callback<MArticleJson>(){
			@Override
			public void onCallback(MArticleJson result) {
				// TODO Auto-generated method stub
				if(result==null){
					return;
				}
				hlist.clear();
				hlist.addAll(result.getList());
				mMImageFootListAdapter.notifyDataSetChanged();
			}
		});
	}
	
	/* (non-Javadoc)
	 * @see com.open.android.fragment.common.CommonPullToRefreshGridFragment#call()
	 */
	@Override
	public MArticleJson call() throws Exception {
		// TODO Auto-generated method stub
		MArticleJson mMArticleJson = new MArticleJson();
		String typename = "PCNavJsoupService-parseList-"+pageNo;
		if(NetWorkUtils.isNetworkAvailable(getActivity())){
			mMArticleJson.setList(PCNavJsoupService.parseList(url, pageNo));
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
	 * @see com.open.android.fragment.common.CommonPullToRefreshGridFragment#onCallback(com.open.android.json.CommonJson)
	 */
	@Override
	public void onCallback(MArticleJson result) {
		// TODO Auto-generated method stub
//		super.onCallback(result);
		if(result==null){
			return;
		}
		Log.i(TAG, "getMode ===" + mPullToRefreshHeadGridView.getCurrentMode());
		if (mPullToRefreshHeadGridView.getCurrentMode() == Mode.PULL_FROM_START) {
			list.clear();
			list.addAll(result.getList());
			pageNo = 1;
		} else {
			if (result.getList() != null && result.getList().size() > 0) {
				list.addAll(result.getList());
			}
		}
//		mPullToRefreshHeadGridView.getRefreshableView().setNumColumns(result.getList().size());
		mPCNavGridAdapter.notifyDataSetChanged();
		// Call onRefreshComplete when the list has been refreshed.
		mPullToRefreshHeadGridView.onRefreshComplete();
	}
	
	/* (non-Javadoc)
	 * @see com.open.android.fragment.common.CommonPullToRefreshGridFragment#onItemClick(android.widget.AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		super.onItemClick(parent, view, position, id);
		if(id!=-1 && list.get((int)id)!=null){
			PCImagePullListActivity.startPCImagePullListActivity(getActivity(), list.get((int)id).getHref());
		}
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
