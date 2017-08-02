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

import java.io.IOException;

import com.open.pxing.R;
import com.open.pxing.utils.VideoDownloadUtils;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.LinearLayout;

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
public class MMediaPlayerActivity extends Activity implements OnCompletionListener, OnErrorListener, OnInfoListener, OnPreparedListener, OnSeekCompleteListener, OnVideoSizeChangedListener,
		SurfaceHolder.Callback {
	private Display currDisplay;
	private SurfaceView surfaceView;
	private SurfaceHolder holder;
	private MediaPlayer player;
	private int vWidth, vHeight;
	String dataPath = Environment.getExternalStorageDirectory().getPath() + "/com.open.pxing/video/v1.mp4";
	// private boolean readyToPlay = false;
	String url ="http://bsyqncdn.miaopai.com/stream/JUcrYOTtrE14088zhyf5G~JXl2nZ2ebX_32.mp4?ssig=35cb4d83d832d8f7180d112e2bb71a2d&time_stamp=1501659887661";
	String filename="v1.mp4";

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_m_media_player);
		surfaceView = (SurfaceView) this.findViewById(R.id.video_surface);
		// 给SurfaceView添加CallBack监听
		holder = surfaceView.getHolder();
		holder.addCallback(MMediaPlayerActivity.this);
		// 为了可以播放视频或者使用Camera预览，我们需要指定其Buffer类型
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		// 下面开始实例化MediaPlayer对象
		player = new MediaPlayer();
		player.setOnCompletionListener(MMediaPlayerActivity.this);
		player.setOnErrorListener(MMediaPlayerActivity.this);
		player.setOnInfoListener(MMediaPlayerActivity.this);
		player.setOnPreparedListener(MMediaPlayerActivity.this);
		player.setOnSeekCompleteListener(MMediaPlayerActivity.this);
		player.setOnVideoSizeChangedListener(MMediaPlayerActivity.this);
		Log.v("Begin:::", "surfaceDestroyed called");
		// 然后指定需要播放文件的路径，初始化MediaPlayer
		try {
			player.setDataSource(dataPath);
			Log.v("Next:::", "surfaceDestroyed called");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 然后，我们取得当前Display对象
		currDisplay = MMediaPlayerActivity.this.getWindowManager().getDefaultDisplay();
//	    new Thread(connectNet).start();
	}
	private Handler connectHanlder = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// 给SurfaceView添加CallBack监听
			holder = surfaceView.getHolder();
			holder.addCallback(MMediaPlayerActivity.this);
			// 为了可以播放视频或者使用Camera预览，我们需要指定其Buffer类型
			holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
			// 下面开始实例化MediaPlayer对象
			player = new MediaPlayer();
			player.setOnCompletionListener(MMediaPlayerActivity.this);
			player.setOnErrorListener(MMediaPlayerActivity.this);
			player.setOnInfoListener(MMediaPlayerActivity.this);
			player.setOnPreparedListener(MMediaPlayerActivity.this);
			player.setOnSeekCompleteListener(MMediaPlayerActivity.this);
			player.setOnVideoSizeChangedListener(MMediaPlayerActivity.this);
			Log.v("Begin:::", "surfaceDestroyed called");
			// 然后指定需要播放文件的路径，初始化MediaPlayer
			try {
				player.setDataSource(dataPath);
				Log.v("Next:::", "surfaceDestroyed called");
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 然后，我们取得当前Display对象
			currDisplay = MMediaPlayerActivity.this.getWindowManager().getDefaultDisplay();
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

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// 当Surface尺寸等参数改变时触发
		Log.v("Surface Change:::", "surfaceChanged called");
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// 当SurfaceView中的Surface被创建的时候被调用
		// 在这里我们指定MediaPlayer在当前的Surface中进行播放
		player.setDisplay(holder);
		// 在指定了MediaPlayer播放的容器后，我们就可以使用prepare或者prepareAsync来准备播放了
		player.prepareAsync();

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {

		Log.v("Surface Destory:::", "surfaceDestroyed called");
	}

	@Override
	public void onVideoSizeChanged(MediaPlayer arg0, int arg1, int arg2) {
		// 当video大小改变时触发
		// 这个方法在设置player的source后至少触发一次
		Log.v("Video Size Change", "onVideoSizeChanged called");

	}

	@Override
	public void onSeekComplete(MediaPlayer arg0) {
		// seek操作完成时触发
		Log.v("Seek Completion", "onSeekComplete called");

	}

	@Override
	public void onPrepared(MediaPlayer player) {
		// 当prepare完成后，该方法触发，在这里我们播放视频

		// 首先取得video的宽和高
		vWidth = player.getVideoWidth();
		vHeight = player.getVideoHeight();

		if (vWidth > currDisplay.getWidth() || vHeight > currDisplay.getHeight()) {
			// 如果video的宽或者高超出了当前屏幕的大小，则要进行缩放
			float wRatio = (float) vWidth / (float) currDisplay.getWidth();
			float hRatio = (float) vHeight / (float) currDisplay.getHeight();

			// 选择大的一个进行缩放
			float ratio = Math.max(wRatio, hRatio);

			vWidth = (int) Math.ceil((float) vWidth / ratio);
			vHeight = (int) Math.ceil((float) vHeight / ratio);

			// 设置surfaceView的布局参数
			surfaceView.setLayoutParams(new LinearLayout.LayoutParams(vWidth, vHeight));

			// 然后开始播放视频

			player.start();
		}
	}

	@Override
	public boolean onInfo(MediaPlayer player, int whatInfo, int extra) {
		// 当一些特定信息出现或者警告时触发
		switch (whatInfo) {
		case MediaPlayer.MEDIA_INFO_BAD_INTERLEAVING:
			break;
		case MediaPlayer.MEDIA_INFO_METADATA_UPDATE:
			break;
		case MediaPlayer.MEDIA_INFO_VIDEO_TRACK_LAGGING:
			break;
		case MediaPlayer.MEDIA_INFO_NOT_SEEKABLE:
			break;
		}
		return false;
	}

	@Override
	public boolean onError(MediaPlayer player, int whatError, int extra) {
		Log.v("Play Error:::", "onError called");
		switch (whatError) {
		case MediaPlayer.MEDIA_ERROR_SERVER_DIED:
			Log.v("Play Error:::", "MEDIA_ERROR_SERVER_DIED");
			break;
		case MediaPlayer.MEDIA_ERROR_UNKNOWN:
			Log.v("Play Error:::", "MEDIA_ERROR_UNKNOWN");
			break;
		default:
			break;
		}
		return false;
	}

	@Override
	public void onCompletion(MediaPlayer player) {
		// 当MediaPlayer播放完成后触发
		Log.v("Play Over:::", "onComletion called");
		this.finish();
	}
}
