/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-6-9下午2:09:41
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.pxing.fragment.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshHeadGridView;
import com.open.android.bean.db.OpenDBBean;
import com.open.android.db.service.OpenDBService;
import com.open.android.fragment.common.CommonPullToRefreshGridFragment;
import com.open.pxing.R;
import com.open.pxing.activity.m.MImagePullListActivity;
import com.open.pxing.activity.pc.PCImagePullListActivity;
import com.open.pxing.adapter.app.MCollectionGridAdapter;
import com.open.pxing.json.m.OpenDBJson;
import com.open.pxing.utils.UrlUtils;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2017-6-9下午2:09:41
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class MCollectionGridFragment extends CommonPullToRefreshGridFragment<OpenDBBean, OpenDBJson> implements OnClickListener {
	public MCollectionGridAdapter mMCollectionGridAdapter;
	public boolean editable;
	public RelativeLayout layout_delete;
	public TextView txt_all_select,txt_delelte;
	
	public static MCollectionGridFragment newInstance(String url, boolean isVisibleToUser) {
		MCollectionGridFragment fragment = new MCollectionGridFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_collection_pullgridview, container, false);
		mPullToRefreshHeadGridView = (PullToRefreshHeadGridView) view.findViewById(R.id.pull_refresh_grid);
		layout_delete = (RelativeLayout) view.findViewById(R.id.layout_delete);
		txt_all_select = (TextView) view.findViewById(R.id.txt_all_select);
		txt_delelte = (TextView) view.findViewById(R.id.txt_delelte);
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
		super.initValues();
		mMCollectionGridAdapter = new MCollectionGridAdapter(getActivity(), list);
		mPullToRefreshHeadGridView.setAdapter(mMCollectionGridAdapter);
	}
	
	/* (non-Javadoc)
	 * @see com.open.android.fragment.common.CommonPullToRefreshGridFragment#bindEvent()
	 */
	@Override
	public void bindEvent() {
		// TODO Auto-generated method stub
		super.bindEvent();
		txt_all_select.setOnClickListener(this);
		txt_delelte.setOnClickListener(this);
	}

	/* (non-Javadoc)
	 * @see com.open.android.fragment.common.CommonPullToRefreshGridFragment#call()
	 */
	@Override
	public OpenDBJson call() throws Exception {
		// TODO Auto-generated method stub
		OpenDBJson mOpenDBJson = new OpenDBJson();
		mOpenDBJson.setList(OpenDBService.queryListType(getActivity(), 0));
		return mOpenDBJson;
	}

	/* (non-Javadoc)
	 * @see com.open.android.fragment.common.CommonPullToRefreshGridFragment#onCallback(com.open.android.json.CommonJson)
	 */
	@Override
	public void onCallback(OpenDBJson result) {
		// TODO Auto-generated method stub
//		super.onCallback(result);
		Log.i(TAG, "getMode ===" + mPullToRefreshHeadGridView.getCurrentMode());
		if (mPullToRefreshHeadGridView.getCurrentMode() == Mode.PULL_FROM_START) {
			list.clear();
			for(OpenDBBean bean:result.getList()){
				if("0".equals(bean.getTypename())){
					list.add(bean);
				}
			}
			pageNo = 1;
		} else {
			if (result.getList() != null && result.getList().size() > 0) {
				for(OpenDBBean bean:result.getList()){
					if("0".equals(bean.getTypename())){
						list.add(bean);
					}
				}
			}
		}
//		mPullToRefreshHeadGridView.getRefreshableView().setNumColumns(result.getList().size());
		mMCollectionGridAdapter.notifyDataSetChanged();
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
			if(editable){
				list.get((int)id).setSelectstate(!list.get((int)id).isSelectstate());
				mMCollectionGridAdapter.notifyDataSetChanged();
			}else{
				if(list.get((int)id).getUrl().contains(UrlUtils.MM_PC)){
					PCImagePullListActivity.startPCImagePullListActivity(getActivity(), list.get((int)id).getUrl());
				}else{
					MImagePullListActivity.startMImagePullListActivity(getActivity(), list.get((int)id).getUrl());
				}
			}
		}
	}
	
	public void setEditable(boolean editable){
		if(editable){
			//完成
			editable = false;
			for(OpenDBBean bean:list){
				bean.setEditable(false);
				bean.setSelectstate(false);
			}
			layout_delete.setVisibility(View.GONE);
			txt_all_select.setText("全选");
			mMCollectionGridAdapter.notifyDataSetChanged();
		}else{
			layout_delete.setVisibility(View.VISIBLE);
			//编辑
			editable = true;
			for(OpenDBBean bean:list){
				bean.setEditable(true);
			}
			mMCollectionGridAdapter.notifyDataSetChanged();
		}
	}
	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.txt_all_select:
			//全选
			if(isAllSelected()){
				setSelectedState(false);
				txt_all_select.setText("全选");
			}else{
				txt_all_select.setText("取消");
				setSelectedState(true);
			}
			break;
		case R.id.txt_delelte:
			//删除
			for(OpenDBBean bean:list){
				bean.setEditable(true);
				if(bean.isSelectstate()){
					OpenDBService.delete(getActivity(), bean);
				}
			}
			weakReferenceHandler.sendEmptyMessage(MESSAGE_HANDLER);
			break;
		default:
			break;
		}
	}
	
	
	/**
	 * 是否全选
	 */
	public boolean isAllSelected(){
		boolean allSelected = true;
		for(OpenDBBean bean:list){
			 if(!bean.isSelectstate()){
				 allSelected = false; 
				 break;
			 }
		}
		return allSelected;
	}
	
	
	/**
	 * 全选或者取消全选
	 */
	public void setSelectedState(boolean state){
		for(OpenDBBean bean:list){
			bean.setSelectstate(state);
		}
		mMCollectionGridAdapter.notifyDataSetChanged();
	}
	
}
