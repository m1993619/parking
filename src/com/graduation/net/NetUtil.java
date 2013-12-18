package com.graduation.net;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * Created by meng on 13-12-12.
 */
public class NetUtil
{

	

	public static String getData(String path) throws IOException
	{
		String result = null;
		HttpGet httpGet = new HttpGet(path);
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse httpResponse = httpClient.execute(httpGet);

		if (httpResponse.getStatusLine().getStatusCode() == 200)
		{
			HttpEntity httpEntity = httpResponse.getEntity();
			result = EntityUtils.toString(httpEntity, "UTF-8");
			result.replaceAll("\r", "");
			// System.out.println("try result:"+ result);
		}
		else
		{
			httpGet.abort();
			result = "get a error!" + httpResponse.getStatusLine().getStatusCode();

		}
		return result;

	}

}
