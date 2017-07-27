/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-6-7下午5:54:31
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.pxing.application;


import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

import android.app.Application;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.open.android.adapter.DefaultWebSocketAdapterFactory;
import com.open.android.adapter.ImageAdapter;
import com.open.android.adapter.WXHttpAdapter;
import com.open.android.module.WXEventModule;
import com.open.android.module.WeexModalUIModule;
import com.open.android.module.WeexModule;
import com.open.pxing.utils.AuthImageDownloader;
import com.open.pxing.utils.HttpClientImageDownloader;
import com.open.pxing.utils.UrlUtils;
import com.taobao.weex.InitConfig;
import com.taobao.weex.WXSDKEngine;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-6-7下午5:54:31
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class MMApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        HttpParams params = new BasicHttpParams();
        // Turn off stale checking. Our connections break all the time anyway,
        // and it's not worth it to pay the penalty of checking every time.
        HttpConnectionParams.setStaleCheckingEnabled(params, false);
        // Default connection and socket timeout of 10 seconds. Tweak to taste.
        HttpConnectionParams.setConnectionTimeout(params, 10 * 1000);
        HttpConnectionParams.setSoTimeout(params, 10 * 1000);
        HttpConnectionParams.setSocketBufferSize(params, 8192);

        // Don't handle redirects -- return them to the caller. Our code
        // often wants to re-POST after a redirect, which we must do ourselves.
        HttpClientParams.setRedirecting(params, false);
        // Set the specified user agent and register standard protocols.
        HttpProtocolParams.setUserAgent(params, UrlUtils.userAgent);
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        schemeRegistry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));

        ClientConnectionManager manager = new ThreadSafeClientConnManager(params, schemeRegistry);


//        ImageLoaderConfiguration configuration =
//                new ImageLoaderConfiguration
//                        .Builder(this)
////                        .defaultDisplayImageOptions(defaultOptions)
////                        .discCache(new UnlimitedDiscCache(cacheDir))
//                        .threadPoolSize(1)
//                        .memoryCache(new WeakMemoryCache())
//                        .imageDownloader(new HttpClientImageDownloader(new DefaultHttpClient(manager, params)))
//                        .build();
        
//        //创建默认的ImageLoader配置参数
//        ImageLoaderConfiguration configuration =   new ImageLoaderConfiguration.Builder(this).threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
//                .diskCacheFileNameGenerator(new Md5FileNameGenerator()).diskCacheSize(50 * 1024 * 1024) // 50 Mb
//                .tasksProcessingOrder(QueueProcessingType.LIFO).writeDebugLogs() // Remove for release app
//                .imageDownloader(new AuthImageDownloader(this))
//                .build();
        
        ImageLoaderConfiguration configuration =   new ImageLoaderConfiguration.Builder(this)
        		.threadPriority(Thread.NORM_PRIORITY - 2)
        		.denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(50 * 1024 * 1024) // 50 Mb
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs() // Remove for release app
                .memoryCache(new WeakMemoryCache())
                .imageDownloader(new HttpClientImageDownloader(new DefaultHttpClient(manager, params)))
                .build();
         
        //Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(configuration);
        
        InitConfig config=new InitConfig.Builder().setHttpAdapter(new WXHttpAdapter()).setImgAdapter(new ImageAdapter()).setWebSocketAdapterFactory(new DefaultWebSocketAdapterFactory()).build();
        WXSDKEngine.initialize(this,config);
        try {
			WXSDKEngine.registerModule("weexModule", WeexModule.class);
			WXSDKEngine.registerModule("weexModalUIModule", WeexModalUIModule.class);
			WXSDKEngine.registerModule("weexEventModule", WXEventModule.class);
			//WXSDKEngine.registerModule("weexJsoupModule", WeexJsoupModule.class);
//			WXSDKEngine.registerModule("actionSheet", WXActionSheetModule.class);
//			 // 注册 webview module
//			WXSDKEngine.registerModule("mywebview", WeeXWebViewModule.class);
//	        // 注册 webview 组件
//			WXSDKEngine.registerComponent("web", WeeXWeb.class);
//			
//			WXSDKEngine.registerComponent("myinput", MyInput.class);
//			WXSDKEngine.registerComponent("myrichtext",RichText.class);
//			WXSDKEngine.registerComponent(
//				        new SimpleComponentHolder(
//				          WeeXSlider.class,
//				          new WeeXSlider.Creator()
//				        ),
//				        true,
//				       "mypager"
//				      );
//			WXSDKEngine.registerComponent(
//			        new SimpleComponentHolder(
//			        		WeeXText.class,
//			                new WeeXText.Creator()
//			              ),
//			              false,
//			              "mystockview"
//			            );
//			WXSDKEngine.registerDomObject("mystockview", WeeXTextDomObject.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
