package com.open.pxing;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.google.gson.Gson;
import com.open.android.activity.common.CommonALLActivity;
import com.open.pxing.application.PXingApplication;
import com.open.pxing.bean.m.PatchBean;
import com.open.pxing.service.PXingMainPagerPushService;
import com.open.pxing.utils.DeviceUtils;
import com.open.pxing.utils.ServiceUtils;
import com.taobao.sophix.SophixManager;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.mistatistic.sdk.MiStatInterface;

public class SplashActivity extends Activity {
	private static final int SHOW_TIME_MIN = 3000;// 最小显示时间
	private long mStartTime;// 开始时间
	private static final int REQUEST_EXTERNAL_STORAGE_PERMISSION = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		mStartTime = System.currentTimeMillis();// 记录开始时间，
		SophixManager.getInstance().queryAndLoadNewPatch();
		// if (Build.VERSION.SDK_INT >= 23) {
		// requestExternalStoragePermission();
		// }
		MiPushClient.setAlias(SplashActivity.this, DeviceUtils.getDeviceId(this), null);
		Log.d("SplashActivity", "Alias===" + DeviceUtils.getDeviceId(this));
		MiPushClient.resumePush(SplashActivity.this, null);
		PXingApplication.msgDisplayListener = new PXingApplication.MsgDisplayListener() {
			@Override
			public void handle(final String msg) {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						updateConsole(msg);
					}
				});
			}
		};

		if (!ServiceUtils.isServiceExisted(this, PXingMainPagerPushService.class.getSimpleName())) {
			ServiceUtils.startPollingService(this, 5, PXingMainPagerPushService.class, PXingMainPagerPushService.ACTION);
		}

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

	/**
	 * 更新监控台的输出信息
	 * 
	 * @param content
	 *            更新内容
	 */
	private void updateConsole(String content) {
		try {
			Gson gson = new Gson();
			PatchBean bean = gson.fromJson(content, PatchBean.class);
			if (bean != null) {
				if (bean.getHandlePatchVersion() > 0) {
					alertNewPatch(content);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void alertNewPatch(String content) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setItems(new String[] { content + "检测到热更新包，重启生效" }, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				android.os.Process.killProcess(android.os.Process.myPid());
			}
		});
		builder.show();
	}

	/**
	 * 如果本地补丁放在了外部存储卡中, 6.0以上需要申请读外部存储卡权限才能够使用. 应用内部存储则不受影响
	 */

	// private void requestExternalStoragePermission() {
	// if (ContextCompat.checkSelfPermission(this,
	// Manifest.permission.READ_EXTERNAL_STORAGE)
	// != PackageManager.PERMISSION_GRANTED) {
	// ActivityCompat.requestPermissions(this, new
	// String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
	// REQUEST_EXTERNAL_STORAGE_PERMISSION);
	// }
	// }

	// @Override
	// public void onRequestPermissionsResult(int requestCode, String[]
	// permissions, int[] grantResults) {
	// switch (requestCode) {
	// case REQUEST_EXTERNAL_STORAGE_PERMISSION:
	// if (grantResults.length <= 0 || grantResults[0] !=
	// PackageManager.PERMISSION_GRANTED) {
	// updateConsole("local external storage patch is invalid as not read external storage permission");
	// }
	// break;
	// default:
	// }
	// }
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MiStatInterface.recordPageStart(SplashActivity.this, "splash page");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MiStatInterface.recordPageEnd();
	}

}
