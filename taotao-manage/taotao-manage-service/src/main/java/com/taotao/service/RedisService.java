package com.taotao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

@Service
public class RedisService {

	@Autowired
	private ShardedJedisPool jedisPool;

	/**
	 * set 操作
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public String set(final String key, final String value) {
		return this.set(key, value, null);
	}

	/**
	 * set 操作 ，并设置 生存时间
	 * 
	 * @param key
	 * @param value
	 * @param seconds
	 * @return
	 */
	public String set(final String key, final String value, final Integer seconds) {
		return this.execute(new Function<String, ShardedJedis>() {
			@Override
			public String calback(ShardedJedis e) {
				String str = e.set(key, value);
				if (seconds != null) {
					e.expire(key, seconds);
				}
				return str;
			}
		});
	}

	/**
	 * get 操作
	 * 
	 * @param key
	 * @return
	 */
	public String get(final String key) {
		return this.execute(new Function<String, ShardedJedis>() {
			@Override
			public String calback(ShardedJedis e) {
				return e.get(key);
			}
		});
	}

	/**
	 * 删除 操作
	 * 
	 * @param key
	 * @return
	 */
	public Long del(final String key) {
		return this.execute(new Function<Long, ShardedJedis>() {
			@Override
			public Long calback(ShardedJedis e) {
				return e.del(key);
			}
		});
	}

	/**
	 * 设置生存时间
	 * 
	 * @param key
	 * @param seconds
	 *            生存时间
	 * @return
	 */
	public Long expire(final String key, final int seconds) {
		return this.execute(new Function<Long, ShardedJedis>() {
			@Override
			public Long calback(ShardedJedis e) {
				return e.expire(key, seconds);
			}
		});
	}

	private <T> T execute(Function<T, ShardedJedis> fun) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = jedisPool.getResource();
			return fun.calback(shardedJedis);
		} finally {
			if (shardedJedis != null) {
				// 关闭，检测连接是否有效，有效则放回到连接池中，无效则重置状态
				shardedJedis.close();
			}
		}
	}

}
