package httpclient;

import java.io.IOException;
import java.net.URI;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

/**
 * HttpClient连接池
 * @author tarena
 *
 */
public class HttpConnectManager {
	public static void main(String[] args) throws IOException, Exception {
		// httpclient 连接池
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();

		// 设置最大连接数
		cm.setMaxTotal(200);

		// 设置每个主机地址的并发数
		cm.setDefaultMaxPerRoute(20);
		doget(cm);
	}

	private static void doget(PoolingHttpClientConnectionManager cm) throws Exception, IOException {
		CloseableHttpClient httpclient = HttpClients.custom().setConnectionManager(cm).build();

		// 定义请求的参数
		URI uri = new URIBuilder("http://manage.jt.com/item/cat/list").setParameter("id", "0").build();

		// 创建http get对象
		HttpGet get = new HttpGet(uri);

		CloseableHttpResponse response = null;
		try {
			// 执行请求
			response = httpclient.execute(get);
			// 根据状态码判断是否成功
			if (response.getStatusLine().getStatusCode() == 200) {
				String page = EntityUtils.toString(response.getEntity());
				System.out.println(page);
			}
		} finally {
			if (response != null) {
				response.close();
			}
			// 此处不能关闭httpClient,如果关闭，连接池就会销毁
		}
	}
}
