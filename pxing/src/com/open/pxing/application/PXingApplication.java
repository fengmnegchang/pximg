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




import java.io.File;

import android.app.Application;
import android.os.Environment;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.util.ByteConstants;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.open.pxing.utils.ElnImageDownloaderFetcher;

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
public class PXingApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DiskCacheConfig diskCacheConfig = DiskCacheConfig.newBuilder(this)
                .setBaseDirectoryPath(new File(Environment.getExternalStorageDirectory() +"/"+ getPackageName()+"/"))
                .setBaseDirectoryName("image_cache")
                .setMaxCacheSize(50 * ByteConstants.MB)
                .setMaxCacheSizeOnLowDiskSpace(10 * ByteConstants.MB)
                .setMaxCacheSizeOnVeryLowDiskSpace(2 * ByteConstants.MB)
                .build();
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
                .setNetworkFetcher(new ElnImageDownloaderFetcher())
                .setMainDiskCacheConfig(diskCacheConfig).build();
        Fresco.initialize(this, config);
       
//        ImageLoaderConfiguration configuration =
//                new ImageLoaderConfiguration
//                        .Builder(this)
////                        .defaultDisplayImageOptions(defaultOptions)
////                        .discCache(new UnlimitedDiscCache(cacheDir))
//                        .threadPoolSize(1)
//                        .memoryCache(new WeakMemoryCache())
//                        .imageDownloader(new HttpClientImageDownloader(new DefaultHttpClient(manager, params)))
////                        .imageDownloader(new AuthImageDownloader(this))
//                        .build();
        
//        //创建默认的ImageLoader配置参数
//        ImageLoaderConfiguration configuration =   new ImageLoaderConfiguration.Builder(this).threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
//                .diskCacheFileNameGenerator(new Md5FileNameGenerator()).diskCacheSize(50 * 1024 * 1024) // 50 Mb
//                .tasksProcessingOrder(QueueProcessingType.LIFO).writeDebugLogs() // Remove for release app
//                .imageDownloader(new AuthImageDownloader(this))
//                .build();
        
//        ImageLoaderConfiguration configuration =   new ImageLoaderConfiguration.Builder(this)
//        		.threadPriority(Thread.NORM_PRIORITY - 2)
//        		.denyCacheImageMultipleSizesInMemory()
//                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
//                .diskCacheSize(50 * 1024 * 1024) // 50 Mb
//                .tasksProcessingOrder(QueueProcessingType.LIFO)
//                .writeDebugLogs() // Remove for release app
//                .memoryCache(new WeakMemoryCache())
//                .imageDownloader(new AuthImageDownloader(this))
//                .build();
//         
//        //Initialize ImageLoader with configuration.
//        ImageLoader.getInstance().init(configuration);
//        
//        InitConfig config=new InitConfig.Builder().setHttpAdapter(new WXHttpAdapter()).setImgAdapter(new ImageAdapter()).setWebSocketAdapterFactory(new DefaultWebSocketAdapterFactory()).build();
//        WXSDKEngine.initialize(this,config);
//        try {
//			WXSDKEngine.registerModule("weexModule", WeexModule.class);
//			WXSDKEngine.registerModule("weexModalUIModule", WeexModalUIModule.class);
//			WXSDKEngine.registerModule("weexEventModule", WXEventModule.class);
//			//WXSDKEngine.registerModule("weexJsoupModule", WeexJsoupModule.class);
////			WXSDKEngine.registerModule("actionSheet", WXActionSheetModule.class);
////			 // 注册 webview module
////			WXSDKEngine.registerModule("mywebview", WeeXWebViewModule.class);
////	        // 注册 webview 组件
////			WXSDKEngine.registerComponent("web", WeeXWeb.class);
////			
////			WXSDKEngine.registerComponent("myinput", MyInput.class);
////			WXSDKEngine.registerComponent("myrichtext",RichText.class);
////			WXSDKEngine.registerComponent(
////				        new SimpleComponentHolder(
////				          WeeXSlider.class,
////				          new WeeXSlider.Creator()
////				        ),
////				        true,
////				       "mypager"
////				      );
////			WXSDKEngine.registerComponent(
////			        new SimpleComponentHolder(
////			        		WeeXText.class,
////			                new WeeXText.Creator()
////			              ),
////			              false,
////			              "mystockview"
////			            );
////			WXSDKEngine.registerDomObject("mystockview", WeeXTextDomObject.class);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
    }
}
