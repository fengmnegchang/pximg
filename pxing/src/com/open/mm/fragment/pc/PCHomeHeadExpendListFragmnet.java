/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-6-20下午4:26:34
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.mm.fragment.pc;

import com.open.mm.fragment.m.MImageFootExpendListFragmnet;
import com.open.mm.json.m.MArticleJson;
import com.open.mm.jsoup.pc.PCNavJsoupService;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-6-20下午4:26:34
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class PCHomeHeadExpendListFragmnet extends MImageFootExpendListFragmnet{
	public static PCHomeHeadExpendListFragmnet newInstance(String url, boolean isVisibleToUser) {
		PCHomeHeadExpendListFragmnet fragment = new PCHomeHeadExpendListFragmnet();
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
		mMArticleJson.setList(PCNavJsoupService.parseHomeHeadList(url, pageNo));
		return mMArticleJson;
	}

}
