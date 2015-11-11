package com.elyes.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

import android.util.Log;

public class HelperHttp {

	public static HttpClient httpclient;

	public static DefaultHttpClient getThreadSafeClient() {
		if (httpclient != null)
			return (DefaultHttpClient) httpclient;
		HttpParams params = new BasicHttpParams();
		ConnManagerParams.setMaxTotalConnections(params, 100);
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);

		// Create and initialize scheme registry
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", PlainSocketFactory
				.getSocketFactory(), 2000));

		ClientConnectionManager cm = new ThreadSafeClientConnManager(params,
				schemeRegistry);
		httpclient = new DefaultHttpClient(cm, params);

		return (DefaultHttpClient) httpclient;
	}

	public static String getJSONResponseFromURL(String url) {
		
		String json_string = "";
		InputStream is = null;
		try {
			HttpGet httpget = new HttpGet(url);
			
			HttpResponse response = getThreadSafeClient().execute(httpget);

			HttpEntity entity = response.getEntity();
			is = entity.getContent();
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(is), 8192);
			String line = null;
			while ((line = reader.readLine()) != null) {
				json_string = json_string + line;
				System.out.println(line);
			}
			response.getEntity().consumeContent();
		} catch (Exception e) {
			Log.e("log_tag", "Error in http connection" + e.toString());
			return null;
		}
		return json_string;
	}

}
