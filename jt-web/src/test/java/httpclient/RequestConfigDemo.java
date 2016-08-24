package httpclient;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/*
 * 设置请求参数
 */
public class RequestConfigDemo {
	public static void main(String[] args) throws IOException {
		//创建Httplient对象
		CloseableHttpClient httpclient = HttpClients.createDefault();
		
		//创建http get请求
		HttpGet get = new HttpGet("http://www.baidu.com");

		//构建请求配置信息
		RequestConfig config = RequestConfig.custom().setConnectTimeout(1000) //创建连接的最长时间
		.setConnectionRequestTimeout(500) //从连接池中获取到连接的最长时间
		.setSocketTimeout(10*1000)//数据传输的最长时间
		.setStaleConnectionCheckEnabled(true).build();//提交请求前测试连接是否用
		
		//设置请求配置信息
		get.setConfig(config);
		
		CloseableHttpResponse response =null;
		try {
			//执行请求
			response = httpclient.execute(get);
			
			//判断返回状态是否为200
			if(response.getStatusLine().getStatusCode()==200){
				String page = EntityUtils.toString(response.getEntity(),"UTF-8");
				System.out.println(page);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(response != null){
				response.close();
			}
			httpclient.close();
		}
	}
}
