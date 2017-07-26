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
package com.open.mm.fragment.pc;

import android.support.v4.app.Fragment;

import com.open.mm.bean.m.MSlideMenuBean;
import com.open.mm.fragment.m.MArticlePullListFragmnet;
import com.open.mm.fragment.m.MMainIndicatorFragment;
import com.open.mm.json.m.MSlideMenuJson;
import com.open.mm.jsoup.m.MLeftMenuJsoupService;

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
		mMSlideMenuJson.setList(MLeftMenuJsoupService.parsePCList(url, pageNo));
		return mMSlideMenuJson;
	}

	@Override
	public void onCallback(MSlideMenuJson result) {
		// TODO Auto-generated method stub
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
