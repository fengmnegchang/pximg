/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-6-8下午2:14:30
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.pxing.fragment.m;

import java.util.List;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.Spannable;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.open.android.bean.db.OpenDBBean;
import com.open.android.db.service.OpenDBService;
import com.open.android.fragment.BaseV4Fragment;
import com.open.android.utils.NetWorkUtils;
import com.open.android.widget.OpenClickableSpan;
import com.open.pxing.R;
import com.open.pxing.bean.m.MArticleBean;
import com.open.pxing.json.m.MArticleJson;
import com.open.pxing.jsoup.m.MArticleJsoupService;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-6-8下午2:14:30
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class MImageHeadFragmnet extends BaseV4Fragment<MArticleJson, MImageHeadFragmnet> {
	public TextView text_title,text_camLiDes,text_meta,text_tag;
	
	public static MImageHeadFragmnet newInstance(String url, boolean isVisibleToUser) {
		MImageHeadFragmnet fragment = new MImageHeadFragmnet();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_m_image_head, container, false);
		text_title = (TextView) view.findViewById(R.id.text_title);
		text_camLiDes = (TextView) view.findViewById(R.id.text_camLiDes);
		text_meta = (TextView) view.findViewById(R.id.text_meta);
		text_tag = (TextView) view.findViewById(R.id.text_tag);
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
		String typename = "MArticleJsoupService-parseImageList-"+pageNo;
		if(NetWorkUtils.isNetworkAvailable(getActivity())){
			mMArticleJson.setList(MArticleJsoupService.parseImageList(url, pageNo));
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.android.fragment.common.CommonPullToRefreshListFragment#onCallback
	 * (com.open.android.json.CommonJson)
	 */
	@Override
	public void onCallback(MArticleJson result) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		if(result==null){
			return;
		}
		try {
			MArticleBean bean = result.getList().get(0); 
			if(bean!=null){
				text_title.setText(bean.getAlt());
				text_camLiDes.setText(bean.getPostmeta());
				
				Spanned spanned = Html.fromHtml(bean.getMeta());
				text_meta.setText(spanned);
				text_meta.setMovementMethod(LinkMovementMethod.getInstance());  
				URLSpan[] obj = spanned.getSpans(0, spanned.length(), URLSpan.class);
				for (int i = 0; i < obj.length; i++) {
					int start = spanned.getSpanStart(obj[i]);
		            int end = spanned.getSpanEnd(obj[i]);
					((Spannable) spanned).removeSpan(obj[i]);
					OpenClickableSpan clickableSpan = new OpenClickableSpan(getContext(),obj[i].getURL());   
					((Spannable) spanned).setSpan(clickableSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				}
				text_meta.setText(spanned);
				
				
				spanned = Html.fromHtml(bean.getTag());
				text_tag.setText(spanned);
				text_tag.setMovementMethod(LinkMovementMethod.getInstance());  
				obj = spanned.getSpans(0, spanned.length(), URLSpan.class);
				for (int i = 0; i < obj.length; i++) {
					int start = spanned.getSpanStart(obj[i]);
		            int end = spanned.getSpanEnd(obj[i]);
					((Spannable) spanned).removeSpan(obj[i]);
					OpenClickableSpan clickableSpan = new OpenClickableSpan(getContext(),obj[i].getURL());   
					((Spannable) spanned).setSpan(clickableSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				}
				text_tag.setText(spanned);
			}
		} catch (Exception e) {
			e.printStackTrace();
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
