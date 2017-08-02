/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-8-2下午2:48:32
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.pxing.utils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import android.os.Environment;
import android.util.Log;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2017-8-2下午2:48:32
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class VideoDownloadUtils {
	static String PATH = Environment.getExternalStorageDirectory() + "/com.open.pxing/video/";
	public static void download(String url2,String filename) {
		try {
			URL url = null;
			try {
				url = new URL(url2);
			} catch (MalformedURLException e) {
				Log.e("VideoDownloadUtils", e.getMessage(), e);
			}
			HttpURLConnection conn = null;

			if (url2.startsWith("https")) {
				trustAllHosts();
				HttpsURLConnection https;

				https = (HttpsURLConnection) url.openConnection();
				https.setHostnameVerifier(DO_NOT_VERIFY);

				// https.setRequestProperty("Accept",
				// "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
				// https.setRequestProperty("Upgrade-Insecure-Requests", "1");
				// https.setRequestProperty("Host", "img.pximg.com");
				// https.setRequestProperty("User-Agent",
				// "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.76 Mobile Safari/537.36");
				// https.setRequestProperty("User-Agent",
				// "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
				// https.setRequestProperty("Accept-Encoding",
				// "gzip, deflate, sdch");
				// https.setRequestProperty("Accept-Language",
				// "zh-CN,zh;q=0.8");
				// https.setRequestProperty("Cache-Control", "max-age=0");
				// https.setRequestProperty("Connection", "keep-alive");
				https.setRequestProperty("referer", "img.pximg.com");
				// https.setRequestProperty("Access-Control-Allow-Origin", "*");
				// https.setRequestProperty("Origin", "img.pximg.com");
				conn = https;
				conn.connect();
			} else {
				conn = (HttpURLConnection) url.openConnection();
			}

			conn.setReadTimeout(5 * 1000);
			conn.setConnectTimeout(5 * 1000);
			conn.setRequestMethod("GET");

			conn.getResponseCode();
			InputStream inStream;
			// if (inStream == null) {
			inStream = conn.getInputStream();
			// }
			// This is a try with resources, Java 7+ only
			// If you use Java 6 or less, use a finally block instead
			// try (Scanner scanner = new Scanner(inStream)) {
			// scanner.useDelimiter("\\Z");
			// System.out.print(scanner.next());
			// }
			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				ByteArrayOutputStream outStream = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = inStream.read(buffer)) != -1) {
					outStream.write(buffer, 0, len);
				}
				outStream.close();
				inStream.close();
				// return outStream.toByteArray();

				// BufferedInputStream bis = null;
				// ByteArrayOutputStream out =null;
				// try {
				// bis = new BufferedInputStream(inStream,1024);
				// out = new ByteArrayOutputStream();
				// int len=0;
				// byte[] buffer = new byte[1024];
				// while((len = bis.read(buffer)) != -1){
				// out.write(buffer, 0, len);
				// }
				// out.close();
				// bis.close();
				// } catch (MalformedURLException e1) {
				// e1.printStackTrace();
				// } catch (IOException e) {
				// e.printStackTrace();
				// }
				//
				File dirFile = new File(PATH);
				if (!dirFile.exists()) {
					dirFile.mkdir();
				}
				File myCaptureFile = new File(PATH + filename);
				if(myCaptureFile.exists()){
					myCaptureFile.deleteOnExit();
				}
				myCaptureFile.createNewFile();
				BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
				bos.write(outStream.toByteArray());
				bos.flush();
				bos.close();
				
				Log.d("VideoDownloadUtils", "create file success");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	   // always verify the host - dont check for certificate
final static HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
   @Override
   public boolean verify(String hostname, SSLSession session) {
       return true;
   }
};

	/**
* Trust every server - dont check for any certificate
*/
private static void trustAllHosts() {
   // Create a trust manager that does not validate certificate chains
   TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
       @Override
       public void checkClientTrusted(
               java.security.cert.X509Certificate[] x509Certificates,
               String s) throws java.security.cert.CertificateException {
       }

       @Override
       public void checkServerTrusted(
               java.security.cert.X509Certificate[] x509Certificates,
               String s) throws java.security.cert.CertificateException {
       }

       @Override
       public java.security.cert.X509Certificate[] getAcceptedIssuers() {
           return new java.security.cert.X509Certificate[]{};
       }
   }};

   // Install the all-trusting trust manager
   try {
       SSLContext sc = SSLContext.getInstance("TLS");
       sc.init(null, trustAllCerts, new java.security.SecureRandom());
       HttpsURLConnection
               .setDefaultSSLSocketFactory(sc.getSocketFactory());
   } catch (Exception e) {
       e.printStackTrace();
   }
}
}
