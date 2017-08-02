/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-6-21上午11:12:09
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.pxing.fragment.pc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.open.android.bean.db.OpenDBBean;
import com.open.android.db.service.OpenDBService;
import com.open.android.fragment.BaseV4Fragment;
import com.open.android.utils.NetWorkUtils;
import com.open.pxing.R;
import com.open.pxing.adapter.pc.PCFocusViewPagerAdapter;
import com.open.pxing.bean.m.MArticleBean;
import com.open.pxing.json.m.MArticleJson;
import com.open.pxing.jsoup.m.MArticleJsoupService;
import com.open.pxing.jsoup.pc.PCNavJsoupService;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-6-21上午11:12:09
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class PCFocusViewPagerFragment extends BaseV4Fragment<MArticleJson, PCFocusViewPagerFragment>implements OnPageChangeListener{
	public ViewPager viewpager;
	public List<MArticleBean> list = new ArrayList<MArticleBean>();
	public PCFocusViewPagerAdapter mPCFocusViewPagerAdapter;
	public ImageView[] dots;
	public int currentIndex;
	public int size;
	public LinearLayout layout_dot;
	
	public static PCFocusViewPagerFragment newInstance(String url, boolean isVisibleToUser) {
		PCFocusViewPagerFragment fragment = new PCFocusViewPagerFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}
	

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_common_viewpager, container, false);
		viewpager = (ViewPager) view.findViewById(R.id.viewpager);
		layout_dot = (LinearLayout) view.findViewById(R.id.layout_dot);
		return view;
	}
	
	/* (non-Javadoc)
	 * @see com.open.enrz.fragment.BaseV4Fragment#initValues()
	 */
	@Override
	public void initValues() {
		// TODO Auto-generated method stub
		super.initValues();
		mPCFocusViewPagerAdapter = new PCFocusViewPagerAdapter(getActivity(),list);
		viewpager.setAdapter(mPCFocusViewPagerAdapter);
	}
	
	/* (non-Javadoc)
	 * @see com.open.enrz.fragment.BaseV4Fragment#bindEvent()
	 */
	@Override
	public void bindEvent() {
		// TODO Auto-generated method stub
		super.bindEvent();
		viewpager.setOnPageChangeListener(this);
	}


	/* (non-Javadoc)
	 * @see com.open.enrz.fragment.BaseV4Fragment#call()
	 */
	@Override
	public MArticleJson call() throws Exception {
		// TODO Auto-generated method stub
		MArticleJson mMArticleJson = new MArticleJson();
		String typename = "PCNavJsoupService-parsePagerList-"+pageNo;
		if(NetWorkUtils.isNetworkAvailable(getActivity())){
			//读取asserts文件
			try {  
				//Return an AssetManager instance for your application's package  
//	            InputStream is = getActivity().getAssets().open("page");  
//	            int size = is.available();  
//	            // Read the entire asset into a local byte buffer.  
//	            byte[] buffer = new byte[size];  
//	            is.read(buffer);  
//	            is.close();  
//	            // Convert the buffer into a string.  
//	            String text = new String(buffer, "UTF-8");  
				
				URL urll= new URL(url);  
	            HttpURLConnection conn=(HttpURLConnection)urll.openConnection();  
	            //取得inputStream，并进行读取  
	            InputStream input=conn.getInputStream();  
	            BufferedReader in=new BufferedReader(new InputStreamReader(input));  
	            String line=null;  
	            StringBuffer sb=new StringBuffer();  
	            while((line=in.readLine())!=null){  
	                sb.append(line);  
	            }  
	            System.out.println(sb.toString());  
	            
	            String  text = sb.toString().replace("document.writeln(\"", "").replace("\");","").replace("\\","");
	            // Finally stick the string into the text view.  
	            mMArticleJson.setList(PCNavJsoupService.parsePagerList(text,pageNo));
	        } catch (IOException e) {  
	            // Should never happen!  
	            throw new RuntimeException(e);  
	        }  
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


	/* (non-Javadoc)
	 * @see com.open.enrz.fragment.BaseV4Fragment#onCallback(java.lang.Object)
	 */
	@Override
	public void onCallback(MArticleJson result) {
		// TODO Auto-generated method stub
		list.clear();
		list.addAll(result.getList());
		mPCFocusViewPagerAdapter.notifyDataSetChanged();
		
		 size = result.getList().size();
		 if(size>0){
			 dots = new ImageView[size];
				for (int i = 0; i < size; i++) {
					ImageView img = new ImageView(getActivity());
					img.setImageResource(R.drawable.dot);
					img.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT));
					img.setPadding(15, 15, 15, 15);
					img.setClickable(true);
					dots[i] = img;
					dots[i].setEnabled(true);
					dots[i].setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							int position = (Integer) v.getTag();
							setCurView(position);
							setCurDot(position);
						}
					});
					dots[i].setTag(i);

					layout_dot.addView(dots[i]);
				}

				currentIndex = 0;
				dots[currentIndex].setEnabled(false);
				viewpager.setCurrentItem(0);
		 }
			
	}
	
	/**
	 * 设置当前的引导页
	 */
	public void setCurView(int position) {
		if (position < 0 || position >= size) {
			return;
		}

		viewpager.setCurrentItem(position);
	}

	/**
	 * 这只当前引导小点的选中
	 */
	public void setCurDot(int positon) {
		if (positon < 0 || positon > size - 1
				|| currentIndex == positon) {
			return;
		}

		dots[positon].setEnabled(false);
		dots[currentIndex].setEnabled(true);

		currentIndex = positon;
	}
	
	/* (non-Javadoc)
	 * @see android.support.v4.view.ViewPager.OnPageChangeListener#onPageScrollStateChanged(int)
	 */
	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see android.support.v4.view.ViewPager.OnPageChangeListener#onPageScrolled(int, float, int)
	 */
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see android.support.v4.view.ViewPager.OnPageChangeListener#onPageSelected(int)
	 */
	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		setCurDot(arg0);
	}
	
	/* (non-Javadoc)
	 * @see com.open.enrz.fragment.BaseV4Fragment#handlerMessage(android.os.Message)
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