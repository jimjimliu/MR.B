package com.kachiingapp.kachiing.api.controller.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * http����
 *
 * @author nzs
 *
 */
public class HttpUtils {
	/**
	 * @description : post�ӿ� ���ؽ���ַ���
	 * @params : [url, param]
	 * @param url
	 *            ����ӿ�
	 * @param param
	 *            ��Ҫ��json�ַ���
	 * @return :java.lang.String
	 */
	public static String sendPost(String urlStr, String param) {
		String retStr = "";
		try {
			URL url = new URL(urlStr);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("POST");
			urlConnection.setDoOutput(true);
			urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

			OutputStream outputStream = urlConnection.getOutputStream();
			outputStream.write(param.getBytes("UTF-8"));
			outputStream.flush();

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					urlConnection.getInputStream(), "UTF-8"));
			StringBuilder response = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				response.append(line);
				response.append("\n");
			}

			outputStream.close();
			reader.close();
			urlConnection.disconnect();
			retStr = response.toString();
			System.out.println(retStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retStr;
	}

	public static String sendGet(String urlStr) {
		String retStr = "";
		try {
			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setDoOutput(true);

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "UTF-8"));
			StringBuilder response = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				response.append(line);
				response.append("\n");
			}
			reader.close();
			retStr = response.toString();
			System.out.println(retStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retStr;
	}

}
