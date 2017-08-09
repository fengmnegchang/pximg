/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-8-9上午10:11:09
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.pxing.activity.m;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.open.android.activity.CommonFragmentActivity;
import com.open.pxing.R;
import com.open.pxing.auth.Util;
import com.open.pxing.utils.UrlUtils;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-8-9上午10:11:09
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class MQQAuthLoginActivity extends CommonFragmentActivity{
	public static String mAppid = "101421230";
	public static Tencent mTencent;
	private static boolean isServerSideLogin = false;
	private UserInfo mInfo;
	private static Intent mPrizeIntent = null;
	private TextView mUserInfo;
	private ImageView mUserLogo;
	private Button mNewLoginButton;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_m_qq_auth);
		init();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.enrz.activity.CommonFragmentActivity#initValue()
	 */
	@Override
	protected void initValue() {
		// TODO Auto-generated method stub
//		super.initValue();
		if (getIntent().getStringExtra("URL") != null) {
			url = getIntent().getStringExtra("URL");
		} else {
			url = UrlUtils.PXING_SEARCH;
		}
		if (mTencent == null) {
	        mTencent = Tencent.createInstance(mAppid, this);
	    }
		 // 获取有奖分享的intent信息
        if (null != getIntent()) {
            mPrizeIntent = getIntent();
        }
        mNewLoginButton = (Button) findViewById(R.id.new_login_btn);
		mUserInfo = (TextView) findViewById(R.id.user_nickname);
		mUserLogo = (ImageView) findViewById(R.id.user_logo);
		mNewLoginButton.setOnClickListener(this);
		updateLoginButton();
	}
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onResume()
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		 // 有奖分享处理
        handlePrizeShare();
	}

	/* (non-Javadoc)
	 * @see com.open.pxing.activity.m.MCommonTitleBarActivity#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.new_login_btn:
			onClickLogin();
			break;
		default:
			break;
		}
		super.onClick(v);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.android.activity.CommonFragmentActivity#addfragment()
	 */
	@Override
	public void addfragment() {
		// TODO Auto-generated method stub
	}

	public static void startMQQAuthLoginActivity(Context context, String url) {
		Intent intent = new Intent();
		intent.putExtra("URL", url);
		intent.setClass(context, MQQAuthLoginActivity.class);
		context.startActivity(intent);
	}
	
	
	private void onClickLogin() {
		if (!mTencent.isSessionValid()) {
			mTencent.login(MQQAuthLoginActivity.this, "all", loginListener);
            isServerSideLogin = false;
			Log.d("SDKQQAgentPref", "FirstLaunch_SDK:" + SystemClock.elapsedRealtime());
		} else {
            if (isServerSideLogin) { // Server-Side 模式的登陆, 先退出，再进行SSO登陆
                mTencent.logout(MQQAuthLoginActivity.this);
                mTencent.login(MQQAuthLoginActivity.this, "all", loginListener);
                isServerSideLogin = false;
                Log.d("SDKQQAgentPref", "FirstLaunch_SDK:" + SystemClock.elapsedRealtime());
                return;
            }
		    mTencent.logout(MQQAuthLoginActivity.this);
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
        boolean hasPrize = mTencent.checkPrizeByIntent(MQQAuthLoginActivity.this, mPrizeIntent);
        if (hasPrize) {
            Util.showConfirmCancelDialog(MQQAuthLoginActivity.this, "有奖品领取", "请使用QQ登录后，领取奖品！", prizeShareConfirmListener);
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
                            mTencent.queryUnexchangePrize(MQQAuthLoginActivity.this, mPrizeIntent.getExtras(),
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
            Util.toastMessage(MQQAuthLoginActivity.this, "onError: " + e.errorDetail);
            Util.dismissDialog();
        }

        @Override
        public void onCancel() {
            Util.toastMessage(MQQAuthLoginActivity.this, "onCancel: ");
            Util.dismissDialog();
        }

        @Override
        public void onComplete(Object response) {
            Util.showConfirmCancelDialog(MQQAuthLoginActivity.this, "兑换奖品", response.toString(),
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
                            mTencent.exchangePrize(MQQAuthLoginActivity.this, params, prizeExchangeListener);
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
            Util.toastMessage(MQQAuthLoginActivity.this, "onError: " + e.errorDetail);
            Util.dismissDialog();
        }

        @Override
        public void onCancel() {
            Util.toastMessage(MQQAuthLoginActivity.this, "onCancel: ");
            Util.dismissDialog();
        }

        @Override
        public void onComplete(Object response) {
            Util.showResultDialog(MQQAuthLoginActivity.this, response.toString(), "兑换信息");
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
                Util.showResultDialog(MQQAuthLoginActivity.this, "返回为空", "登录失败");
                return;
            }
            JSONObject jsonResponse = (JSONObject) response;
            if (null != jsonResponse && jsonResponse.length() == 0) {
                Util.showResultDialog(MQQAuthLoginActivity.this, "返回为空", "登录失败");
                return;
            }
			Util.showResultDialog(MQQAuthLoginActivity.this, response.toString(), "登录成功");
            // 有奖分享处理
            handlePrizeShare();
			doComplete((JSONObject)response);
		}

		protected void doComplete(JSONObject values) {

		}

		@Override
		public void onError(UiError e) {
			Util.toastMessage(MQQAuthLoginActivity.this, "onError: " + e.errorDetail);
			Util.dismissDialog();
		}

		@Override
		public void onCancel() {
			Util.toastMessage(MQQAuthLoginActivity.this, "onCancel: ");
			Util.dismissDialog();
            if (isServerSideLogin) {
                isServerSideLogin = false;
            }
		}
	}
    
    private void updateLoginButton() {
		if (mTencent != null && mTencent.isSessionValid()) {
            if (isServerSideLogin) {
                mNewLoginButton.setTextColor(Color.BLUE);
                mNewLoginButton.setText("登录");
//                mServerSideLoginBtn.setTextColor(Color.RED);
//                mServerSideLoginBtn.setText("退出Server-Side账号");
            } else {
                mNewLoginButton.setTextColor(Color.RED);
                mNewLoginButton.setText("退出帐号");
//                mServerSideLoginBtn.setTextColor(Color.BLUE);
//                mServerSideLoginBtn.setText("Server-Side登陆");
            }
		} else {
			mNewLoginButton.setTextColor(Color.BLUE);
			mNewLoginButton.setText("登录");
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
			mInfo = new UserInfo(MQQAuthLoginActivity.this, mTencent.getQQToken());
			mInfo.getUserInfo(listener);

		} else {
			mUserInfo.setText("");
			mUserInfo.setVisibility(android.view.View.GONE);
			mUserLogo.setVisibility(android.view.View.GONE);
		}
	}
    
    Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 0) {
				JSONObject response = (JSONObject) msg.obj;
				if (response.has("nickname")) {
					try {
						mUserInfo.setVisibility(android.view.View.VISIBLE);
						mUserInfo.setText(response.getString("nickname"));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}else if(msg.what == 1){
				Bitmap bitmap = (Bitmap)msg.obj;
				mUserLogo.setImageBitmap(bitmap);
				mUserLogo.setVisibility(android.view.View.VISIBLE);
			}
		}

	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    Log.d(TAG, "-->onActivityResult " + requestCode  + " resultCode=" + resultCode);
	    if (requestCode == Constants.REQUEST_LOGIN ||
	    	requestCode == Constants.REQUEST_APPBAR) {
	    	Tencent.onActivityResultData(requestCode,resultCode,data,loginListener);
	    }

	    super.onActivityResult(requestCode, resultCode, data);
	}
}