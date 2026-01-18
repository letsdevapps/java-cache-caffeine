package com.pro.cache;

import java.util.concurrent.TimeUnit;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.github.benmanes.caffeine.cache.stats.CacheStats;
import com.pro.model.User;
import com.pro.repository.UserRepository;

public class UserCache {

	private final LoadingCache<Integer, User> cache;

	public UserCache(UserRepository repository) {
		this.cache = Caffeine.newBuilder()
				.maximumSize(1_000)
				.expireAfterWrite(5, TimeUnit.MINUTES) // TTL definido
				.recordStats() // registrar metricas
				.build(repository::buscarPorId);
	}

	public User get(Integer userId) {
		return cache.get(userId);
	}

	public void invalidate(Integer userId) {
		cache.invalidate(userId);
	}

	public void invalidateAll() {
		cache.invalidateAll();
	}

	public CacheStats stats() {
		return cache.stats();
	}
}