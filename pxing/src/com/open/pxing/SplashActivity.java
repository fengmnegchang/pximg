package com.open.pxing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.open.android.activity.common.CommonALLActivity;

public class SplashActivity extends Activity {
	private static final int SHOW_TIME_MIN = 3000;// 最小显示时间
	private long mStartTime;// 开始时间

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		mStartTime = System.currentTimeMillis();// 记录开始时间，

		new Thread() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see java.lang.Thread#run()
			 */
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(3000);
					mHandler.sendEmptyMessage(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				super.run();
			}

		}.start();
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1000:// 如果城市列表加载完毕，就发送此消息
				long loadingTime = System.currentTimeMillis() - mStartTime;// 计算一下总共花费的时间
				if (loadingTime < SHOW_TIME_MIN) {// 如果比最小显示时间还短，就延时进入MainActivity，否则直接进入
					mHandler.postDelayed(goToMainActivity, SHOW_TIME_MIN - loadingTime);
				} else {
					mHandler.post(goToMainActivity);
				}
				break;
			default:
				break;
			}
		}
	};
	// 进入下一个Activity
	Runnable goToMainActivity = new Runnable() {
		@Override
		public void run() {
			SplashActivity.this.startActivity(new Intent(SplashActivity.this, CommonALLActivity.class));
			finish();
		}
	};
}
