package com.pro.cache;

import java.util.concurrent.TimeUnit;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.pro.model.User;
import com.pro.repository.UserRepository;

public class UserCache {

	private final LoadingCache<Integer, User> cache;

	public UserCache(UserRepository repository) {
		this.cache = Caffeine.newBuilder()
                .maximumSize(1_000)
                .expireAfterWrite(5, TimeUnit.MINUTES)
                .build(repository::buscarPorId);
	}

	public User get(Integer userId) {
		return cache.get(userId);
	}
}