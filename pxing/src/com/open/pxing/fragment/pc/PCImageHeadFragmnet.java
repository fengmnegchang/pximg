/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-6-21下午2:52:04
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.pxing.fragment.pc;

import java.util.List;

import com.google.gson.Gson;
import com.open.android.bean.db.OpenDBBean;
import com.open.android.db.service.OpenDBService;
import com.open.android.utils.NetWorkUtils;
import com.open.pxing.fragment.m.MImageHeadFragmnet;
import com.open.pxing.json.m.MArticleJson;
import com.open.pxing.jsoup.m.MArticleJsoupService;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-6-21下午2:52:04
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class PCImageHeadFragmnet extends MImageHeadFragmnet{
	public static PCImageHeadFragmnet newInstance(String url, boolean isVisibleToUser) {
		PCImageHeadFragmnet fragment = new PCImageHeadFragmnet();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
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
		MArticleJson mMArticleJson = new MArticleJson();
		String typename = "MArticleJsoupService-parsePCImageList-"+pageNo;
		if(NetWorkUtils.isNetworkAvailable(getActivity())){
			mMArticleJson.setList(MArticleJsoupService.parsePCImageList(url, pageNo));
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
}
