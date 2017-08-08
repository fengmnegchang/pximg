/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-6-9上午11:38:33
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.pxing.fragment.app;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.open.pxing.R;
import com.open.pxing.activity.app.MCollectionGridFragmentActivity;
import com.open.pxing.activity.app.MHistoryListGridFragmentActivity;
import com.open.pxing.adapter.m.MSlideMenuAdapter;
import com.open.pxing.auth.Util;
import com.open.pxing.bean.m.MSlideMenuBean;
import com.open.pxing.fragment.m.MLeftMenuPullListFragmnet;
import com.open.pxing.json.m.MSlideMenuJson;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-6-9上午11:38:33
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class MAppLeftMenuPullListFragmnet extends MLeftMenuPullListFragmnet{
	public View headview;
	public static String mAppid = "222222";
	public static Tencent mTencent;
	private static boolean isServerSideLogin = false;
	private UserInfo mInfo;
	private static Intent mPrizeIntent = null;
	
	public static MAppLeftMenuPullListFragmnet newInstance(String url, boolean isVisibleToUser) {
		MAppLeftMenuPullListFragmnet fragment = new MAppLeftMenuPullListFragmnet();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_common_pulllistview, container, false);
		mPullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.pull_refresh_list);
		headview = LayoutInflater.from(getActivity()).inflate(R.layout.layout_m_app_menu_head, null);
		return view;
	}
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onViewCreated(android.view.View, android.os.Bundle)
	 */
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if (mTencent == null) {
	        mTencent = Tencent.createInstance(mAppid, getActivity());
	    }
		 // 获取有奖分享的intent信息
        if (null != getActivity().getIntent()) {
            mPrizeIntent = getActivity().getIntent();
        }
		super.onViewCreated(view, savedInstanceState);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.android.fragment.BaseV4Fragment#initValues()
	 */
	@Override
	public void initValues() {
		// TODO Auto-generated method stub
		mPullToRefreshListView.getRefreshableView().addHeaderView(headview);
		mMSlideMenuAdapter = new MSlideMenuAdapter(getActivity(), list);
		mPullToRefreshListView.setAdapter(mMSlideMenuAdapter);
		mPullToRefreshListView.setMode(Mode.PULL_FROM_START);
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onActivityResult(int, int, android.content.Intent)
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		 Log.d("MAppMainSlideMenuActivity", "-->onActivityResult " + requestCode  + " resultCode=" + resultCode);
	    if (requestCode == Constants.REQUEST_LOGIN ||
	    	requestCode == Constants.REQUEST_APPBAR) {
	    	Tencent.onActivityResultData(requestCode,resultCode,data,loginListener);
	    }
		super.onActivityResult(requestCode, resultCode, data);
	}

	 
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.android.fragment.common.CommonPullToRefreshListFragment#call()
	 */
	@Override
	public MSlideMenuJson call() throws Exception {
		// TODO Auto-generated method stub
		MSlideMenuJson mMSlideMenuJson = new MSlideMenuJson();
//		mMSlideMenuJson.setList(MLeftMenuJsoupService.parseList(url, pageNo));
		List<MSlideMenuBean> list = new ArrayList<MSlideMenuBean>();
		MSlideMenuBean bean = new MSlideMenuBean();
		bean.setTitle("我的收藏");
		bean.setResid(getResources().getIdentifier("icon_my_favorite", "drawable", getActivity().getPackageName()));
		list.add(bean);
		
		bean = new MSlideMenuBean();
		bean.setTitle("浏览历史");
		bean.setResid(getResources().getIdentifier("icon_browsing_history", "drawable", getActivity().getPackageName()));
		list.add(bean);
		
		
		bean = new MSlideMenuBean();
		bean.setTitle("清空缓存");
		bean.setResid(getResources().getIdentifier("icon_clear_cache", "drawable", getActivity().getPackageName()));
		list.add(bean);
		
		
		bean = new MSlideMenuBean();
		bean.setTitle("qq授权登陆");
		bean.setResid(getResources().getIdentifier("icon_about_us", "drawable", getActivity().getPackageName()));
		list.add(bean);
		mMSlideMenuJson.setList(list);
		return mMSlideMenuJson;
	}
	
	/* (non-Javadoc)
	 * @see com.open.mm.fragment.m.MLeftMenuPullListFragmnet#onItemClick(android.widget.AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
//		super.onItemClick(parent, view, position, id);
		if(id!=-1&&list.get((int)id)!=null){
			String title = list.get((int)id).getTitle();
			if(title.equals("我的收藏")){
				MCollectionGridFragmentActivity.startMCollectionGridFragmentActivity(getActivity(), title);
			}else if(title.equals("浏览历史")){
				MHistoryListGridFragmentActivity.startMHistoryListGridFragmentActivity(getActivity(), title);
			}else if(title.equals("qq授权登陆")){
				onClickLogin();
			}
		}
	}
	
	
	private void onClickLogin() {
		if (!mTencent.isSessionValid()) {
			mTencent.login(getActivity(), "all", loginListener);
            isServerSideLogin = false;
			Log.d("SDKQQAgentPref", "FirstLaunch_SDK:" + SystemClock.elapsedRealtime());
		} else {
            if (isServerSideLogin) { // Server-Side 模式的登陆, 先退出，再进行SSO登陆
                mTencent.logout(getActivity());
                mTencent.login(getActivity(), "all", loginListener);
                isServerSideLogin = false;
                Log.d("SDKQQAgentPref", "FirstLaunch_SDK:" + SystemClock.elapsedRealtime());
                return;
            }
		    mTencent.logout(getActivity());
			updateUserInfo();
			updateLoginButton();
		}
	}
	
	IUiListener loginListener = new BaseUiListener() {
        @Override
        protected void doComplete(org.json.JSONObject values) {
        	Log.d("SDKQQAgentPref", "AuthorSwitch_SDK:" + SystemClock.elapsedRealtime());
            initOpenidAndToken(values);
            updateUserInfo();
            updateLoginButton();
        }
    };
    public static void initOpenidAndToken(JSONObject jsonObject) {
        try {
            String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
            String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
            String openId = jsonObject.getString(Constants.PARAM_OPEN_ID);
            if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires)
                    && !TextUtils.isEmpty(openId)) {
                mTencent.setAccessToken(token, expires);
                mTencent.setOpenId(openId);
            }
        } catch(Exception e) {
        }
    }
    /**
     * 有奖分享处理，未接入有奖分享可以不考虑
     */
    private void handlePrizeShare() {
        // -----------------------------------
        // 下面的注释请勿删除，编译lite版的时候需要删除, 注意//[不要有空格。
        //[liteexludestartmeta]
        if (null == mPrizeIntent || null == mTencent) {
            return;
        }
        // 有奖分享处理
        boolean hasPrize = mTencent.checkPrizeByIntent(getActivity(), mPrizeIntent);
        if (hasPrize) {
            Util.showConfirmCancelDialog(getActivity(), "有奖品领取", "请使用QQ登录后，领取奖品！", prizeShareConfirmListener);
        }
        //[liteexludeendmeta]
    }
    
    // -----------------------------------
    // 下面的注释请勿删除，编译lite版的时候需要删除, 注意//[不要有空格。
    //[liteexludestart_flag_one]
    private DialogInterface.OnClickListener prizeShareConfirmListener = new DialogInterface.OnClickListener() {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    boolean isLogin = mTencent.isSessionValid();
                    if (isLogin) {
                        // 本地已经有保存openid和accesstoken的情况下，先调用mTencent.setAccesstoken().
                        // 也可在奖品列表页，主动调用此接口获取未领取的奖品
                        if (null != mPrizeIntent) {
                            mTencent.queryUnexchangePrize(getActivity(), mPrizeIntent.getExtras(),
                                    prizeQueryUnexchangeListener);
                        }
                    } else {
                        // 未登陆提示用户使用QQ号登陆
                        onClickLogin();
                    }
                    break;
                default:
                    break;
            }
        }
    };
    
    private IUiListener prizeQueryUnexchangeListener = new IUiListener() {

        @Override
        public void onError(UiError e) {
            Util.toastMessage(getActivity(), "onError: " + e.errorDetail);
            Util.dismissDialog();
        }

        @Override
        public void onCancel() {
            Util.toastMessage(getActivity(), "onCancel: ");
            Util.dismissDialog();
        }

        @Override
        public void onComplete(Object response) {
            Util.showConfirmCancelDialog(getActivity(), "兑换奖品", response.toString(),
                    new PrizeClickExchangeListener(response.toString()));
            // 兑换奖品后，mPrizeIntent 置为空
            mPrizeIntent = null;
        }
    };
    
    private class PrizeClickExchangeListener implements DialogInterface.OnClickListener {
        String response = "";

        PrizeClickExchangeListener(String strResponse) {
            response = strResponse;
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    if (null != mTencent) {
                        Bundle params = new Bundle();
                        ArrayList<String> shareIdList = handlePrizeResponse(response);
                        if (null != shareIdList) {
                            ArrayList<String> list = new ArrayList<String>();
                            // 后台测试环境目前只支持一个shareid的兑换，正式环境会支持多个shareid兑换。
                            list.add(shareIdList.get(0));
                            params.putStringArrayList("shareid_list", list);
                            mTencent.exchangePrize(getActivity(), params, prizeExchangeListener);
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    };
    
    private IUiListener prizeExchangeListener = new IUiListener() {

        @Override
        public void onError(UiError e) {
            Util.toastMessage(getActivity(), "onError: " + e.errorDetail);
            Util.dismissDialog();
        }

        @Override
        public void onCancel() {
            Util.toastMessage(getActivity(), "onCancel: ");
            Util.dismissDialog();
        }

        @Override
        public void onComplete(Object response) {
            Util.showResultDialog(getActivity(), response.toString(), "兑换信息");
        }
    };
    
    private ArrayList<String> handlePrizeResponse(String response) {
        ArrayList<String> shareIdList = new ArrayList<String>();
        if (TextUtils.isEmpty(response)) {
            return null;
        }
        try {
            JSONObject obj = new JSONObject(response);
            int code = obj.getInt("ret");
            int subCode = obj.getInt("subCode");
            if (code == 0 && subCode == 0) {
                JSONObject data = obj.getJSONObject("data");
                JSONArray prizeList = data.getJSONArray("prizeList");
                int size = prizeList.length();
                JSONObject prize = null;
                for (int i = 0; i < size; i++) {
                    prize = prizeList.getJSONObject(i);
                    if (null != prize) {
                        shareIdList.add(prize.getString("shareId"));
                    }
                }
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
        return shareIdList;
    }
    //[liteexludeend_flag_one]
    
    private class BaseUiListener implements IUiListener {

		@Override
		public void onComplete(Object response) {
            if (null == response) {
                Util.showResultDialog(getActivity(), "返回为空", "登录失败");
                return;
            }
            JSONObject jsonResponse = (JSONObject) response;
            if (null != jsonResponse && jsonResponse.length() == 0) {
                Util.showResultDialog(getActivity(), "返回为空", "登录失败");
                return;
            }
			Util.showResultDialog(getActivity(), response.toString(), "登录成功");
            // 有奖分享处理
            handlePrizeShare();
			doComplete((JSONObject)response);
		}

		protected void doComplete(JSONObject values) {

		}

		@Override
		public void onError(UiError e) {
			Util.toastMessage(getActivity(), "onError: " + e.errorDetail);
			Util.dismissDialog();
		}

		@Override
		public void onCancel() {
			Util.toastMessage(getActivity(), "onCancel: ");
			Util.dismissDialog();
            if (isServerSideLogin) {
                isServerSideLogin = false;
            }
		}
	}
    
    private void updateLoginButton() {
		if (mTencent != null && mTencent.isSessionValid()) {
            if (isServerSideLogin) {
//                mNewLoginButton.setTextColor(Color.BLUE);
//                mNewLoginButton.setText("登录");
//                mServerSideLoginBtn.setTextColor(Color.RED);
//                mServerSideLoginBtn.setText("退出Server-Side账号");
            } else {
//                mNewLoginButton.setTextColor(Color.RED);
//                mNewLoginButton.setText("退出帐号");
//                mServerSideLoginBtn.setTextColor(Color.BLUE);
//                mServerSideLoginBtn.setText("Server-Side登陆");
            }
		} else {
//			mNewLoginButton.setTextColor(Color.BLUE);
//			mNewLoginButton.setText("登录");
//            mServerSideLoginBtn.setTextColor(Color.BLUE);
//            mServerSideLoginBtn.setText("Server-Side登陆");
		}
	}
    
    
    private void updateUserInfo() {
		if (mTencent != null && mTencent.isSessionValid()) {
			IUiListener listener = new IUiListener() {

				@Override
				public void onError(UiError e) {

				}

				@Override
				public void onComplete(final Object response) {
					Message msg = new Message();
					msg.obj = response;
					msg.what = 0;
					mHandler.sendMessage(msg);
					new Thread(){

						@Override
						public void run() {
							JSONObject json = (JSONObject)response;
							if(json.has("figureurl")){
								Bitmap bitmap = null;
								try {
									bitmap = Util.getbitmap(json.getString("figureurl_qq_2"));
								} catch (JSONException e) {

								}
								Message msg = new Message();
								msg.obj = bitmap;
								msg.what = 1;
								mHandler.sendMessage(msg);
							}
						}

					}.start();
				}

				@Override
				public void onCancel() {

				}
			};
			mInfo = new UserInfo(getActivity(), mTencent.getQQToken());
			mInfo.getUserInfo(listener);

		} else {
//			mUserInfo.setText("");
//			mUserInfo.setVisibility(android.view.View.GONE);
//			mUserLogo.setVisibility(android.view.View.GONE);
		}
	}
    
    Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 0) {
				JSONObject response = (JSONObject) msg.obj;
				if (response.has("nickname")) {
					try {
//						mUserInfo.setVisibility(android.view.View.VISIBLE);
//						mUserInfo.setText(response.getString("nickname"));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}else if(msg.what == 1){
//				Bitmap bitmap = (Bitmap)msg.obj;
//				mUserLogo.setImageBitmap(bitmap);
//				mUserLogo.setVisibility(android.view.View.VISIBLE);
			}
		}

	};

}
