/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-6-20下午4:51:15
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.pxing.fragment.pc;

import java.util.List;

import android.support.v4.app.Fragment;

import com.google.gson.Gson;
import com.open.android.bean.db.OpenDBBean;
import com.open.android.db.service.OpenDBService;
import com.open.android.utils.NetWorkUtils;
import com.open.pxing.bean.m.MSlideMenuBean;
import com.open.pxing.fragment.m.MMainIndicatorFragment;
import com.open.pxing.json.m.MArticleJson;
import com.open.pxing.json.m.MSlideMenuJson;
import com.open.pxing.jsoup.m.MArticleJsoupService;
import com.open.pxing.jsoup.m.MLeftMenuJsoupService;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-6-20下午4:51:15
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class PCTabIndicatorFragment extends MMainIndicatorFragment{
	public static PCTabIndicatorFragment newInstance(String url, boolean isVisibleToUser) {
		PCTabIndicatorFragment fragment = new PCTabIndicatorFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}
	
	@Override
	public MSlideMenuJson call() throws Exception {
		// TODO Auto-generated method stub
		MSlideMenuJson mMSlideMenuJson = new MSlideMenuJson();
		String typename = "MLeftMenuJsoupService-parsePCList-"+pageNo;
		if(NetWorkUtils.isNetworkAvailable(getActivity())){
			mMSlideMenuJson.setList(MLeftMenuJsoupService.parsePCList(url, pageNo));
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

	@Override
	public void onCallback(MSlideMenuJson result) {
		// TODO Auto-generated method stub
		if(result==null){
			return;
		}
		list.clear();
		list.addAll(result.getList());
		titleList.clear();

		Fragment fragment;
		for (int i=0;i< result.getList().size();i++) {
			MSlideMenuBean bean = result.getList().get(i);
			titleList.add(bean.getTitle());
			if(i==0){
				fragment = PCHomeArticlePullListFragmnet.newInstance(bean.getHref(),true);
			}else{
				fragment = PCNavGridAddHeadFragment.newInstance(bean.getHref(),false);
			}
			listRankFragment.add(fragment);
		}
		mRankPagerAdapter.notifyDataSetChanged();
		indicator.notifyDataSetChanged();
	}
}
