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
			
			//post����Ĳ���
			String data = "username=" + username + "&password=" + password;
			//���һ�������
			OutputStream out = conn.getOutputStream();
			out.write(data.getBytes());
			out.flush();//�������
			out.close();//�ر���
			int responseCode = conn.getResponseCode();
			if (responseCode == 200) {
				InputStream is = conn.getInputStream();
				String state = getStringFromInputStream(is);
				return state;
			}else {
				Log.i(TAG, "����ʧ�ܣ�" + responseCode);
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
			//����
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
				Log.i(TAG, "����ʧ��");
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
	 * ����������һ���ַ�����Ϣ
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
		
		String html = baos.toString();	// �����е�����ת�����ַ���, ���õı�����: utf-8
		
//		String html = new String(baos.toByteArray(), "GBK");
		
		baos.close();
		return html;
	}
}
