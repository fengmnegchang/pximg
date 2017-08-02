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

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.open.android.bean.db.OpenDBBean;
import com.open.android.db.service.OpenDBService;
import com.open.android.fragment.common.CommonPullToRefreshListFragment;
import com.open.android.utils.NetWorkUtils;
import com.open.pxing.PXingWebViewActivity;
import com.open.pxing.activity.m.MArticlePullListActivity;
import com.open.pxing.adapter.m.MSlideMenuAdapter;
import com.open.pxing.bean.m.MSlideMenuBean;
import com.open.pxing.json.m.MArticleJson;
import com.open.pxing.json.m.MSlideMenuJson;
import com.open.pxing.jsoup.m.MArticleJsoupService;
import com.open.pxing.jsoup.m.MLeftMenuJsoupService;

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
		String typename = "MLeftMenuJsoupService-parseList-"+pageNo;
		if(NetWorkUtils.isNetworkAvailable(getActivity())){
			mMSlideMenuJson.setList(MLeftMenuJsoupService.parseList(url, pageNo));
			try {
				//数据存储
				Gson gson = new Gson();
				OpenDBBean  openbean = new OpenDBBean();
	    	    openbean.setUrl(url);
	    	    openbean.setTypename(typename);
			    openbean.setTitle(gson.toJson(mMSlideMenuJson));
			    OpenDBService.insert(getActivity(), openbean);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			List<OpenDBBean> beanlist =  OpenDBService.queryListType(getActivity(), url,typename);
			String result = beanlist.get(0).getTitle();
			Gson gson = new Gson();
			mMSlideMenuJson = gson.fromJson(result, MSlideMenuJson.class);
		}
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
		if(id!=-1){
			MSlideMenuBean bean = list.get((int)id);
			if(bean!=null){
				if("动态图片".equals(bean.getTitle()) || "性爱技巧".equals(bean.getTitle())){
					PXingWebViewActivity.startPXingWebViewActivity(getActivity(), bean.getHref());
				}else{
					MArticlePullListActivity.startMArticlePullListActivity(getActivity(), bean.getHref());
				}
				
			}
		}
		
	}

}
