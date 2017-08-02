/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-8-2下午2:40:21
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.pxing.activity.m;

import java.io.File;
import java.net.URLEncoder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;
import android.widget.VideoView;

import com.open.pxing.R;
import com.open.pxing.utils.VideoDownloadUtils;

/**
 ***************************************************************************************************************************************************************************** 
 * 该实例中使用MediaPlayer完成播放，同时界面使用SurfaceView来实现
 * 
 * 这里我们实现MediaPlayer中很多状态变化时的监听器
 * 
 * 使用Mediaplayer时，也可以使用MediaController类，但是需要实现MediaController.mediaController接口
 * 实现一些控制方法。
 * 
 * 然后，设置controller.setMediaPlayer(),setAnchorView(),setEnabled(),show()就可以了，
 * 这里不再实现
 * 
 * @author :fengguangjing
 * @createTime:2017-8-2下午2:40:21
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class MVideoViewActivity extends Activity {
	private final String TAG = "MVideoViewActivity";
	private Button btn_play, btn_pause, btn_replay, btn_stop;
	private SeekBar seekBar;
	private VideoView vv_video;
	private boolean isPlaying;
	String dataPath = Environment.getExternalStorageDirectory().getPath() + "/com.open.pxing/video/v1.mp4";
	// private boolean readyToPlay = false;
	String url = "http://gslb.miaopai.com/stream/Ds94U6PGNVvS0PAaagceS31RvQs63CQw.mp4";
	String filename = "v1.mp4";

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_m_video);
		if(getIntent().getStringExtra("URL")!=null){
			url = getIntent().getStringExtra("URL");
			filename = URLEncoder.encode(url)+".mp4";
			dataPath = Environment.getExternalStorageDirectory().getPath() + "/com.open.pxing/video/"+filename;
		}
		
		seekBar = (SeekBar) findViewById(R.id.seekBar);
		vv_video = (VideoView) findViewById(R.id.vv_videoview);

		btn_play = (Button) findViewById(R.id.btn_play);
		btn_pause = (Button) findViewById(R.id.btn_pause);
		btn_replay = (Button) findViewById(R.id.btn_replay);
		btn_stop = (Button) findViewById(R.id.btn_stop);

		btn_play.setOnClickListener(click);
		btn_pause.setOnClickListener(click);
		btn_replay.setOnClickListener(click);
		btn_stop.setOnClickListener(click);

		// 为进度条添加进度更改事件
		seekBar.setOnSeekBarChangeListener(change);
		new Thread(connectNet).start();
	}

	private Handler connectHanlder = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			play(0);
		}
	};

	private Runnable connectNet = new Runnable() {
		@Override
		public void run() {
			Log.d("connectNet", "download start");
			VideoDownloadUtils.download(url, filename);
			connectHanlder.sendEmptyMessage(0);
		}
	};
	private OnSeekBarChangeListener change = new OnSeekBarChangeListener() {

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// 当进度条停止修改的时候触发
			// 取得当前进度条的刻度
			int progress = seekBar.getProgress();
			if (vv_video != null && vv_video.isPlaying()) {
				// 设置当前播放的位置
				vv_video.seekTo(progress);
			}
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {

		}

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

		}
	};
	private View.OnClickListener click = new View.OnClickListener() {

		@Override
		public void onClick(View v) {

			switch (v.getId()) {
			case R.id.btn_play:
				play(0);
				break;
			case R.id.btn_pause:
				pause();
				break;
			case R.id.btn_replay:
				replay();
				break;
			case R.id.btn_stop:
				stop();
				break;
			default:
				break;
			}
		}
	};

	protected void play(int msec) {
		Log.i(TAG, " 获取视频文件地址");
		File file = new File(dataPath);
		if (!file.exists()) {
			Toast.makeText(this, "视频文件路径错误", 0).show();
			return;
		}

		Log.i(TAG, "指定视频源路径");
		vv_video.setVideoPath(file.getAbsolutePath());
		Log.i(TAG, "开始播放");
		vv_video.start();

		// 按照初始位置播放
		vv_video.seekTo(msec);
		// 设置进度条的最大进度为视频流的最大播放时长
		seekBar.setMax(vv_video.getDuration());

		// 开始线程，更新进度条的刻度
		new Thread() {

			@Override
			public void run() {
				try {
					isPlaying = true;
					while (isPlaying) {
						// 如果正在播放，没0.5.毫秒更新一次进度条
						int current = vv_video.getCurrentPosition();
						seekBar.setProgress(current);

						sleep(500);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
		// 播放之后设置播放按钮不可用
		btn_play.setEnabled(false);

		vv_video.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				// 在播放完毕被回调
				btn_play.setEnabled(true);
			}
		});

		vv_video.setOnErrorListener(new OnErrorListener() {

			@Override
			public boolean onError(MediaPlayer mp, int what, int extra) {
				// 发生错误重新播放
				play(0);
				isPlaying = false;
				return false;
			}
		});
	}

	/**
	 * 重新开始播放
	 */
	protected void replay() {
		if (vv_video != null && vv_video.isPlaying()) {
			vv_video.seekTo(0);
			Toast.makeText(this, "重新播放", 0).show();
			btn_pause.setText("暂停");
			return;
		}
		isPlaying = false;
		play(0);

	}

	/**
	 * 暂停或继续
	 */
	protected void pause() {
		if (btn_pause.getText().toString().trim().equals("继续")) {
			btn_pause.setText("暂停");
			vv_video.start();
			Toast.makeText(this, "继续播放", 0).show();
			return;
		}
		if (vv_video != null && vv_video.isPlaying()) {
			vv_video.pause();
			btn_pause.setText("继续");
			Toast.makeText(this, "暂停播放", 0).show();
		}
	}

	/*
	 * 停止播放
	 */
	protected void stop() {
		if (vv_video != null && vv_video.isPlaying()) {
			vv_video.stopPlayback();
			btn_play.setEnabled(true);
			isPlaying = false;
		}
	}

	public static void startMVideoViewActivity(Context context,String url) {
		Intent intent = new Intent();
		intent.setClass(context, MVideoViewActivity.class);
		intent.putExtra("URL", url);
		context.startActivity(intent);
	}
}
