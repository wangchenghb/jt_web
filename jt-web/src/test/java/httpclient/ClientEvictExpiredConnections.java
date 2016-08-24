package httpclient;

import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;



public class ClientEvictExpiredConnections {
	public static void main(String[] args) {
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		
		//设置最大连接数
		cm.setMaxTotal(200);
		
		//设置每个主机地址的并发数
		cm.setDefaultMaxPerRoute(20);
		
		new IdleConnectionEvictor(cm).start();
	}
	
	public static class IdleConnectionEvictor extends Thread{
		private final HttpClientConnectionManager connMgr;
		
		private volatile boolean shutdown;

		public IdleConnectionEvictor(HttpClientConnectionManager connMgr) {
			super();
			this.connMgr = connMgr;
		}
		
		@Override
		public void run() {
			try {
				while(!shutdown){
					synchronized(this){
						wait(5000);
						//关闭失效的连接
						connMgr.closeExpiredConnections();
					}
				}
			} catch (InterruptedException e) {
				//结束
			}
		}
		
		public void shutdown(){
			shutdown = true;
			synchronized(this){
				notifyAll();
			}
		}
	}
}
