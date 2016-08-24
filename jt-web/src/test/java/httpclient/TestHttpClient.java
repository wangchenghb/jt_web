package httpclient;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;


/**
 * httpclent 入门
 * 
 * @author tarena
 *
 */
public class TestHttpClient {
	/*
	 * get请求
	 */
	@Test
	public void get() throws Exception {
		// 创建Httpclient对象
		CloseableHttpClient httpclient = HttpClients.createDefault();

		// 创建http Get请求
//		HttpGet get = new HttpGet("http://manage.jt.com/page/index");
//		HttpGet get = new HttpGet("http://item.jd.com/2292568.html#product-detail");
//		HttpGet get = new HttpGet("http://cd.jd.com/yanbao/v3?skuId=2292568&cat=9987,653,655&area=1_72_2799_0&brandId=12669&callback=yanbao_jsonp_callback");
		HttpGet get = new HttpGet("http://list.jd.com/list.html?cat=9987,653,655");

		CloseableHttpResponse response = null;
		try {
			response = httpclient.execute(get);
			String page = EntityUtils.toString(response.getEntity(), "GBK");
			
			Document doc = Jsoup.parse(page);
			
			String count = doc.select(".fp-text").text();
			
			int parseInt = Integer.parseInt(StringUtils.substringAfter(count, "/"));
			// 获取请求状态码
			int statusCode = response.getStatusLine().getStatusCode();
			
			System.out.println("CC"+parseInt);
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				response.close();
			}
			httpclient.close();
		}
	}

	/*
	 * post
	 */
	@Test
	public void post() throws Exception {
		// 创建Http请求对象
		CloseableHttpClient httpclient = HttpClients.createDefault();

		// 创建post请求
		HttpPost post = new HttpPost("http://manage.jt.com/page/index");
		CloseableHttpResponse response = null;
		try {
			response = httpclient.execute(post);

			String page = EntityUtils.toString(response.getEntity(), "UTF-8");

			System.out.println(page);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				response.close();
			}
			httpclient.close();
		}
	}

	/*
	 * get请求带参数
	 */
	@Test
	public void getParam() throws Exception {
		// 创建httpclient 对象
		CloseableHttpClient httpclient = HttpClients.createDefault();
		// 定义请求的参数
		URI uri = new URIBuilder("http://manage.jt.com/item/cat/list").setParameter("id", "0").build();

		// 创建get请求
		HttpGet get = new HttpGet(uri);

		CloseableHttpResponse response = null;
		try {
			// 执行请求
			response = httpclient.execute(get);
			if (response.getStatusLine().getStatusCode() == 200) {
				String page = EntityUtils.toString(response.getEntity(), "UTF-8");
				System.out.println(page);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				response.close();
			}
			httpclient.close();
		}
	}

	/*
	 * post请求带参数
	 */
	@Test
	public void postParam() throws Exception {
		// 创建httpclient 对象
		CloseableHttpClient httpclient = HttpClients.createDefault();

		// 创建post请求
		HttpPost post = new HttpPost("http://manage.jt.com/item/cat/list");

		// 设置参数
		ArrayList<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("id", "0"));

		// 构造一个form表单式的实体
		UrlEncodedFormEntity formentity = new UrlEncodedFormEntity(parameters, "UTF-8");

		// 将表单实体设置到httppost对象中
		post.setEntity(formentity);

		CloseableHttpResponse response = null;

		try {
			// 执行请求
			response = httpclient.execute(post);
			//根据状态码判断是否成功
			if (response.getStatusLine().getStatusCode() == 200) {
				String page = EntityUtils.toString(response.getEntity(), "UTF-8");
				System.out.println(page);
			}
		} finally {
			if (response != null) {
				response.close();
			}
			httpclient.close();
		}
	}
}
