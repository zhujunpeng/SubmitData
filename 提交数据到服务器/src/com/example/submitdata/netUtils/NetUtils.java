package com.example.submitdata.netUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;

import android.util.Log;

public class NetUtils {
	private static final String TAG = "NetUtils";
	
	public static String LoginForPost(String username,String password){
		try {
			URL mURL = new URL("http://192.168.1.3:8080/Serviceitheima28/servlet/LoginServlet");
			HttpURLConnection conn = (HttpURLConnection) mURL.openConnection();
			
			conn.setRequestMethod("POST");
			conn.setConnectTimeout(10000);
			conn.setReadTimeout(5000);
			conn.setDoInput(true);
			
			//post请求的参数
			String data = "username=" + username + "&password=" + password;
			//获得一个输出流
			OutputStream out = conn.getOutputStream();
			out.write(data.getBytes());
			out.flush();//清除缓存
			out.close();//关闭流
			int responseCode = conn.getResponseCode();
			if (responseCode == 200) {
				InputStream is = conn.getInputStream();
				String state = getStringFromInputStream(is);
				return state;
			}else {
				Log.i(TAG, "访问失败：" + responseCode);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static String LoginForGet(String username,String password){
		HttpURLConnection conn = null;
		try {
			//编码
			String data = "username=" + URLDecoder.decode(username) + "&password=" + URLDecoder.decode(password);
			URL mURL = new URL("http://192.168.1.3:8080/Serviceitheima28/servlet/LoginServlet?" + data);
			conn = (HttpURLConnection) mURL.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(1000);
			conn.setReadTimeout(5000);
			int responseCode = conn.getResponseCode();
			if (responseCode == 200) {
				InputStream is = conn.getInputStream();
				String state = getStringFromInputStream(is);
				return state;
			}else {
				Log.i(TAG, "访问失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if (conn != null) {
				conn.disconnect();
			}
		}
		return null;
	}
	
	/**
	 * 根据流返回一个字符串信息
	 * @param is
	 * @return
	 * @throws IOException 
	 */
	private static String getStringFromInputStream(InputStream is) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = -1;
		
		while((len = is.read(buffer)) != -1) {
			baos.write(buffer, 0, len);
		}
		is.close();
		
		String html = baos.toString();	// 把流中的数据转换成字符串, 采用的编码是: utf-8
		
//		String html = new String(baos.toByteArray(), "GBK");
		
		baos.close();
		return html;
	}
}
