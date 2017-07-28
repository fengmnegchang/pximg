/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-6-8上午11:27:26
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.pxing.fragment.m;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import com.open.android.fragment.BaseV4Fragment;
import com.open.android.json.CommonJson;
import com.open.pxing.R;
import com.open.pxing.activity.m.MSearchArticlePullListActivity;
import com.open.pxing.adapter.m.MSlideMenuAdapter;
import com.open.pxing.bean.m.MSlideMenuBean;
import com.open.pxing.utils.UrlUtils;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-6-8上午11:27:26
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class MSearchEditFragment extends BaseV4Fragment<CommonJson, MSearchEditFragment> implements OnClickListener{
	private EditText edit_search;
	private Button btn_search;
	private GridView grid_hot;
	private MSlideMenuAdapter mMSlideMenuAdapter;
	private List<MSlideMenuBean> list = new ArrayList<MSlideMenuBean>();
	
	public static MSearchEditFragment newInstance(String url, boolean isVisibleToUser) {
		MSearchEditFragment fragment = new MSearchEditFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_m_search_edit, container, false);
		edit_search = (EditText) view.findViewById(R.id.edit_search);
		btn_search = (Button) view.findViewById(R.id.btn_search);
		grid_hot =   (GridView) view.findViewById(R.id.grid_hot);
		return view;
	}
	/* (non-Javadoc)
	 * @see com.open.android.fragment.BaseV4Fragment#initValues()
	 */
	@Override
	public void initValues() {
		// TODO Auto-generated method stub
		super.initValues();
		MSlideMenuBean bean = new MSlideMenuBean();
		bean.setTitle("清纯");
		list.add(bean);
		
		bean = new MSlideMenuBean();
		bean.setTitle("气质");
		list.add(bean);
		
		bean = new MSlideMenuBean();
		bean.setTitle("模特");
		list.add(bean);
		
		bean = new MSlideMenuBean();
		bean.setTitle("明星");
		list.add(bean);
		
		bean = new MSlideMenuBean();
		bean.setTitle("时尚");
		list.add(bean);
		
		bean = new MSlideMenuBean();
		bean.setTitle("时装");
		list.add(bean);
		
		bean = new MSlideMenuBean();
		bean.setTitle("清新");
		list.add(bean);
		
		bean = new MSlideMenuBean();
		bean.setTitle("古装");
		list.add(bean);
		
		mMSlideMenuAdapter = new MSlideMenuAdapter(getActivity(), list);
		grid_hot.setAdapter(mMSlideMenuAdapter);
	}
	
	/* (non-Javadoc)
	 * @see com.open.android.fragment.BaseV4Fragment#bindEvent()
	 */
	@Override
	public void bindEvent() {
		// TODO Auto-generated method stub
		super.bindEvent();
		btn_search.setOnClickListener(this);
		grid_hot.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if(id!=-1 && list.get((int)id)!=null){
					startSearch(list.get((int)id).getTitle());
				}
			}
		});
	}

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_search:
			startSearch(edit_search.getText().toString());
			break;
		default:
			break;
		}
	}
	
	public void startSearch(String keys){
//		try {
//			keys = URLEncoder.encode(keys, "gb2312");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		String kurl = UrlUtils.PXING_SEARCH+keys;
		MSearchArticlePullListActivity.startMSearchArticlePullListActivity(getActivity(), kurl);
	}
}
