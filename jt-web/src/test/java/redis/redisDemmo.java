package redis;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class redisDemmo {

	/*
	 * jedis第一个示例
	 * 
	 * @author tarena
	 *
	 */
	@Test
	public void TestRedis() {
		Jedis jedis = new Jedis("192.168.233.30", 6379);
		// String str = jedis.get("num");
		// System.out.println(str);

		// jedis.mset("test1","1","test2","2");
		List<String> mget = jedis.mget("test1", "test2");
		for (String str : mget) {
			System.out.println(str);
		}
	}

	/*
	 * 连接jedisPool创建jedis连接
	 */
	@Test
	public void TestJedisPool() {
		// 构建连接池配置信息
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();

		// 设置最大连接数
		jedisPoolConfig.setMaxTotal(200);

		// 构建连接池
		JedisPool jedisPool = new JedisPool(jedisPoolConfig, "192.168.233.30", 6379);

		// 从连接池中获取连接
		Jedis jedis = jedisPool.getResource();

		// 读取数据
		System.out.println(jedis.get("num"));

		// 将连接还回到连接池中
		jedisPool.returnResource(jedis);

		// 释放连接池
		jedisPool.close();

	}
	
	/*
	 * 分片ShardJedisPool
	 * 多个节点的透明访问
	 */
	@Test
	public void ShardJedisPoolDemo(){
		//构建连接池配置信息
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		//设置连接池最大连接数
		jedisPoolConfig.setMaxTotal(200);
		//定义集群信息
		ArrayList<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
		shards.add(new JedisShardInfo("192.168.233.30",6379));
		shards.add(new JedisShardInfo("192.168.233.30",6380));
		//定义集群连接池
		ShardedJedisPool shardedJedisPool = new ShardedJedisPool(jedisPoolConfig,shards);
		ShardedJedis jedis = null;
		
		//从连接池中获取jedis分片对象
		try {
			jedis = shardedJedisPool.getResource();
			for(int i=0;i<100;i++){
				jedis.set("test"+i, ""+i);
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}finally{
			if(null != jedis){
				jedis.close();
			}
		}
		shardedJedisPool.close();
		
		
	}

}
