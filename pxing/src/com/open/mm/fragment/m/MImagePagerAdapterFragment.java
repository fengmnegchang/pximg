/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-6-8下午5:36:54
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.mm.fragment.m;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.open.android.fragment.BaseV4Fragment;
import com.open.android.weak.WeakActivityReferenceHandler;
import com.open.mm.R;
import com.open.mm.adapter.m.MImagePagerAdapter;
import com.open.mm.bean.m.MArticleBean;
import com.open.mm.json.m.MArticleJson;
import com.open.mm.jsoup.m.MArticleJsoupService;
import com.open.mm.utils.UrlUtils;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-6-8下午5:36:54
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class MImagePagerAdapterFragment extends BaseV4Fragment<MArticleJson, MImagePagerAdapterFragment>{
	
	public static MImagePagerAdapterFragment newInstance(String url, boolean isVisibleToUser) {
		MImagePagerAdapterFragment fragment = new MImagePagerAdapterFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}
	
	public ViewPager viewpager;
	public MImagePagerAdapter mMImagePagerAdapter;
	public List<MArticleBean> list = new ArrayList<MArticleBean>();
	public String url = UrlUtils.MM_M_IMAGE;
	public WeakActivityReferenceHandler weakActivityReferenceHandler;
	public int position;
	public TextView text_page_foot;
	
	public static MImagePagerAdapterFragment newInstance(String url, boolean isVisibleToUser,WeakActivityReferenceHandler weakActivityReferenceHandler) {
		MImagePagerAdapterFragment fragment = new MImagePagerAdapterFragment();
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		fragment.weakActivityReferenceHandler = weakActivityReferenceHandler;
		return fragment;
	}
	
	public static MImagePagerAdapterFragment newInstance(String url, boolean isVisibleToUser,WeakActivityReferenceHandler weakActivityReferenceHandler,List<MArticleBean> list,int position) {
		MImagePagerAdapterFragment fragment = new MImagePagerAdapterFragment();
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		fragment.weakActivityReferenceHandler = weakActivityReferenceHandler;
		if(list!=null && list.size()>0){
			fragment.list = list;
			fragment.position = position;
		}
		return fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_m_image_viewpager, container, false);
		viewpager = (ViewPager) view.findViewById(R.id.viewpager);
		text_page_foot = (TextView) view.findViewById(R.id.text_page_foot);
		return view;
	}
	
	 /* (non-Javadoc)
	 * @see com.open.qianbailu.fragment.BaseV4Fragment#initValues()
	 */
	@Override
	public void initValues() {
		// TODO Auto-generated method stub
		super.initValues();
		mMImagePagerAdapter = new MImagePagerAdapter(getActivity(), list,weakActivityReferenceHandler);
		viewpager.setAdapter(mMImagePagerAdapter);
		if(list!=null && list.size()>0){
			mMImagePagerAdapter.notifyDataSetChanged();
			viewpager.setCurrentItem(position);
			text_page_foot.setText((position+1)+" / "+list.size());
		}else{
//			doAsync(this, this, this);
		}
		doAsync(this, this, this);
	}
	
	/* (non-Javadoc)
	 * @see com.open.qianbailu.fragment.BaseV4Fragment#bindEvent()
	 */
	@Override
	public void bindEvent() {
		// TODO Auto-generated method stub
		super.bindEvent();
		viewpager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				text_page_foot.setText((position+1)+" / "+list.size());
				MImagePagerAdapterFragment.this.position = position;
				pageNo=0;
				doAsync(MImagePagerAdapterFragment.this, MImagePagerAdapterFragment.this, MImagePagerAdapterFragment.this);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.umei.activity.BaseFragmentActivity#call()
	 */
	@Override
	public MArticleJson call() throws Exception {
		// TODO Auto-generated method stub
		MArticleJson mMArticleJson =null;
		if(pageNo==1){
			mMArticleJson = MArticleJsoupService.parseImagePagerList(url,position);
		}else{
			mMArticleJson = new MArticleJson();
			mMArticleJson.setList(MArticleJsoupService.parseImageList(list.get(position).getHref(),position));
			mMArticleJson.setCurrentPosition(position);
		}
		return mMArticleJson;
	}
 
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.umei.activity.BaseFragmentActivity#onCallback(java.lang.Object)
	 */
	@Override
	public void onCallback(MArticleJson result) {
		// TODO Auto-generated method stub
//		super.onCallback(result);
		if(result.getList().size()>1){
			list.clear();
			list.addAll(result.getList());
		}else{
			list.set(result.getCurrentPosition(), result.getList().get(0));
		}
		
		mMImagePagerAdapter.notifyDataSetChanged();
		text_page_foot.setText((position+1)+" / "+list.size());
	}
}
