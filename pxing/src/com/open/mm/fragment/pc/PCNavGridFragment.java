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
package com.open.mm.fragment.pc;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshHeadGridView;
import com.open.android.fragment.common.CommonPullToRefreshGridFragment;
import com.open.mm.R;
import com.open.mm.adapter.pc.PCNavGridAdapter;
import com.open.mm.bean.m.MArticleBean;
import com.open.mm.json.m.MArticleJson;
import com.open.mm.jsoup.pc.PCNavJsoupService;

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
public class PCNavGridFragment  extends CommonPullToRefreshGridFragment<MArticleBean, MArticleJson> {
	public PCNavGridAdapter mPCNavGridAdapter;
	public View headview;
	
	
	public static PCNavGridFragment newInstance(String url, boolean isVisibleToUser) {
		PCNavGridFragment fragment = new PCNavGridFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_common_pullgridview, container, false);
		mPullToRefreshHeadGridView = (PullToRefreshHeadGridView) view.findViewById(R.id.pull_refresh_grid);
//		headview = LayoutInflater.from(getActivity()).inflate(R.layout.layout_m_head, null);
		return view;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.android.fragment.BaseV4Fragment#initValues()
	 */
	@Override
	public void initValues() {
//		mPullToRefreshHeadGridView.getRefreshableView().addHeaderView(headview);
//		PCNavHeadExpendListFragmnet ffragment = PCNavHeadExpendListFragmnet.newInstance(url, true);
//		getChildFragmentManager().beginTransaction().replace(R.id.id_m_head, ffragment).commit();
		
		// TODO Auto-generated method stub
		mPCNavGridAdapter = new PCNavGridAdapter(getActivity(), list);
		mPullToRefreshHeadGridView.setAdapter(mPCNavGridAdapter);
		mPullToRefreshHeadGridView.setMode(Mode.BOTH);
	}
	
	/* (non-Javadoc)
	 * @see com.open.android.fragment.common.CommonPullToRefreshGridFragment#call()
	 */
	@Override
	public MArticleJson call() throws Exception {
		// TODO Auto-generated method stub
		MArticleJson mMArticleJson = new MArticleJson();
		mMArticleJson.setList(PCNavJsoupService.parseList(url, pageNo));
		return mMArticleJson;
	}

	/* (non-Javadoc)
	 * @see com.open.android.fragment.common.CommonPullToRefreshGridFragment#onCallback(com.open.android.json.CommonJson)
	 */
	@Override
	public void onCallback(MArticleJson result) {
		// TODO Auto-generated method stub
//		super.onCallback(result);
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
			 
		}
	}
}
