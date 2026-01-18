package com.pro.cache;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.stats.CacheStats;
import com.pro.model.User;
import com.pro.repository.UserRepository;

/*
Usado em:
	Alta concorrência
	APIs reativas
	Sistemas não bloqueantes
*/

public class UserCacheAsync {

    private final AsyncLoadingCache<Integer, User> cache;

    public UserCacheAsync(UserRepository repository) {
        this.cache = Caffeine.newBuilder()
                .maximumSize(1_000)
                .expireAfterWrite(5, TimeUnit.MINUTES)
                .recordStats()
                .buildAsync(repository::buscarPorId);
    }

    public CompletableFuture<User> get(Integer userId) {
        return cache.get(userId);
    }

    public void invalidate(Integer userId) {
        cache.synchronous().invalidate(userId);
    }

    public void invalidateAll() {
        cache.synchronous().invalidateAll();
    }

    public CacheStats stats() {
        return cache.synchronous().stats();
    }

    public void put(Integer userId, User user) {
        cache.synchronous().put(userId, user);
    }
}