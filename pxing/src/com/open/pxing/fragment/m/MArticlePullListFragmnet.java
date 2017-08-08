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

import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.open.android.bean.db.OpenDBBean;
import com.open.android.db.service.OpenDBService;
import com.open.android.fragment.common.CommonPullToRefreshListFragment;
import com.open.android.utils.NetWorkUtils;
import com.open.pxing.activity.m.MImagePullListActivity;
import com.open.pxing.adapter.m.MArticleListAdapter;
import com.open.pxing.bean.m.MArticleBean;
import com.open.pxing.json.m.MArticleJson;
import com.open.pxing.jsoup.m.MArticleJsoupService;
import com.open.pxing.utils.DBMySqlUtils;
import com.xiaomi.mistatistic.sdk.MiStatInterface;

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
public class MArticlePullListFragmnet extends CommonPullToRefreshListFragment<MArticleBean, MArticleJson> {
	public MArticleListAdapter mMArticleListAdapter;

	public static MArticlePullListFragmnet newInstance(String url, boolean isVisibleToUser) {
		MArticlePullListFragmnet fragment = new MArticlePullListFragmnet();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onPause()
	 */
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MiStatInterface.recordPageEnd();
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onResume()
	 */
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MiStatInterface.recordPageStart(getActivity(), "marticle page");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.android.fragment.BaseV4Fragment#initValues()
	 */
	@Override
	public void initValues() {
		// TODO Auto-generated method stub
		mMArticleListAdapter = new MArticleListAdapter(getActivity(), list);
		mPullToRefreshListView.setAdapter(mMArticleListAdapter);
		mPullToRefreshListView.setMode(Mode.BOTH);
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
		String href = url;
		if(pageNo>1){
			if(url.endsWith("/")){
				href = url+"page/"+pageNo;
			}else{
				href = url+"/page/"+pageNo;
			}
		}
		String typename = "MArticleJsoupService-parseList-"+pageNo;
		if(NetWorkUtils.isNetworkAvailable(getActivity())){
			mMArticleJson.setList(MArticleJsoupService.parseList(href, pageNo));
			try {
				//数据存储
				Gson gson = new Gson();
				OpenDBBean  openbean = new OpenDBBean();
	    	    openbean.setUrl(url);
	    	    openbean.setTypename(typename);
			    openbean.setTitle(gson.toJson(mMArticleJson));
			    OpenDBService.insert(getActivity(), openbean);
			    
			    //jdbc
//			    DBMySqlUtils.query();
			    DBMySqlUtils.query(openbean);
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
		if(result==null){
			return;
		}
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
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
		mMArticleListAdapter.notifyDataSetChanged();
		// Call onRefreshComplete when the list has been refreshed.
		mPullToRefreshListView.onRefreshComplete();
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
 
//	/* (non-Javadoc)
//	 * @see com.open.android.fragment.BaseV4Fragment#volleyJson(java.lang.String)
//	 */
//	@Override
//	public void volleyJson(String href) {
//		// TODO Auto-generated method stub
//		System.out.println(href);
//		final Map<String, String> headers  = new HashMap<String, String>();
////		headers.put("Cookie", "yka_gid=45d53573-9ff3-c9a5-1ef9-2133b1f52365; UM_distinctid=15bd6a1663777-0924f97c4d1129-35414878-1aeaa0-15bd6a166381be; a=rYWRb0VDLuA6; analyse_author_id=808b3dabacb64e72be216db6393e80cf; not.use.page.proxy=http%3A%2F%2Fwww.yoka.com%2Fdna%2Fm%2Fa6; __SessionHandler=8897c5ca7f3fbae9661563e18fb96904; KM.PASSPORT.MEMBER=uid%3D8687946%26guid%3D18534ef9e1169780ecf03e9107fb1a88%26id%3D%E5%BE%A1%E5%AE%88uodaztspr%26nickName%3D%E5%BE%A1%E5%AE%88uodaztspr%26nick%3D%26third_source%3D1%26visitDate%3D1496211949%26pwd%3Dd41d8cd98f00b204e9800998ecf8427e%26sign2%3D7d2f1f5200957df4b3e9d23664ff9fc2%26sighbbs%3D87B2F99A1B36D348BCCD5332B4E5531C%26avatar_url%3Dhttp%3A%2F%2Fucenter.yoka.com%2Fdata%2Favatar%2F008%2F68%2F79%2F46_avatar_small.jpg%26expire_time%3D604800%26is_validate%3D0%26open_id%3D%26qq_nick%3D%26real_name%3D%26third%3D1%26sign%3D77f91d969b8b66b811d546a9b236207c; KM.PASSPORT.MEMBER.LastLogin=login_time%3D1496211949%26register_time%3D%26reg_source%3D%26login_source%3D; KM.PASSPORT.MEMBER.TRACK=uid%3D8687946%26nickName%3D%E5%BE%A1%E5%AE%88uodaztspr; KM.PASSPORT.MEMBERGUID=18534ef9e1169780ecf03e9107fb1a88; yokaATC=yoka,535,2743,2715,1496213180016,www.yoka.com%2Fdna%2Fm%2F; ADVS=35289fbbea9bd9; ASL=17318,ppzkk,74e218de74e218de74e218de74e218de74e218de; yka_ph=%7B%20%27value%27%3A%20%2700000000000000010000101100011%27%2C%27lastdate%27%3A%20%271496246400000%27%7D; yka_srchost=www.yoka.com/dna; __clickidc=149155608220710046; ADVC=34fd9c408bfbc0; yka_tid=19cc9c8b-e231-a8aa-723f-decf1001b468; Hm_lvt_a641a94f2a28291909af4213f237173a=1495611999,1495782766,1496198049,1496284030; Hm_lpvt_a641a94f2a28291909af4213f237173a=1496284423");  
//		headers.put("User-Agent", UrlUtils.userAgent);
//		//Accept-Language:zh-CN,zh;q=0.8
//		headers.put("charset", "gb2312");
//		RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
//		StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, href,
//				new Listener<String>() {
//					@Override
//					public void onResponse(String response) {
//						MArticleJson mMArticleJson = new MArticleJson();
//						mMArticleJson.setList(MArticleJsoupService.parseList(response, pageNo));
//						onCallback(mMArticleJson);
//					}
//				}, this)
// 	{
//			
////			/* (non-Javadoc)
////			 * @see com.android.volley.toolbox.StringRequest#getHeaders()
////			 */
////			@Override
////			public Map<String, String> getHeaders() throws AuthFailureError {
////				// TODO Auto-generated method stub
////				return headers;
////			}
// 
//		
////		 protected Response<String>  parseNetworkResponse(NetworkResponse response)  
////	        {  
////			 
////			 String parsed = null;
////		        try {
////		            parsed = new String(response.data, "gb2312");
////		        } catch (UnsupportedEncodingException e) {
////		            try {
////						parsed = new String(response.data,"gb2312");
////					} catch (UnsupportedEncodingException e1) {
////						e1.printStackTrace();
////					}
////		        }
////		        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
////	        }
// 		}
//		;
//		requestQueue.add(jsonObjectRequest);
//	}
//
//	/* (non-Javadoc)
//	 * @see com.open.android.fragment.BaseV4Fragment#onErrorResponse(com.android.volley.VolleyError)
//	 */
//	@Override
//	public void onErrorResponse(VolleyError error) {
//		// TODO Auto-generated method stub
//		super.onErrorResponse(error);
//		System.out.println(error);
//	}
//	
	/* (non-Javadoc)
	 * @see com.open.android.fragment.common.CommonPullToRefreshListFragment#onItemClick(android.widget.AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
//		super.onItemClick(parent, view, position, id);
		if(id!=-1 && list.get((int)id)!=null){
			//保存收藏
			MArticleBean bean = list.get((int) id);
			String href = "";
			if (bean.getHref().contains("_")) {
				href = bean.getHref().split("_")[0] + ".html";
			} else {
				href = bean.getHref();
			}
			OpenDBBean openbean = new OpenDBBean();
			openbean.setImgsrc(bean.getDataimg());
			openbean.setUrl(href);
			openbean.setType(1);
			openbean.setTitle(bean.getAlt());
			openbean.setTypename(1+"");
			OpenDBService.insert(getActivity(), openbean);
//			if(href.contains("mnvid")){
//				MVideoViewActivity.startMVideoViewActivity(getActivity(), bean.getHref());
//			}else{
//				MImagePullListActivity.startMImagePullListActivity(getActivity(),
//						bean.getHref());
//			}
			MImagePullListActivity.startMImagePullListActivity(getActivity(),
					bean.getHref());
			MiStatInterface.recordCountEvent("美图", "查看详图");
		}
	}

}
