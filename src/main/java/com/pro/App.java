package com.pro;

import java.util.concurrent.CompletableFuture;

import com.github.benmanes.caffeine.cache.stats.CacheStats;
import com.pro.cache.UserCache;
import com.pro.cache.UserCacheAsync;
import com.pro.model.User;
import com.pro.repository.UserRepository;

public class App {
	public static void main(String[] args) {
		System.out.println("----- Java Cache Caffeine | Main -----");

		UserRepository repository = new UserRepository();
		UserCache userCache = new UserCache(repository);

		System.out.println("Primeira chamada");
		System.out.println(userCache.get(1).getNome());

		System.out.println("Segunda chamada");
		System.out.println(userCache.get(1).getNome());

		System.out.println("----- Java Cache Caffeine | Metricas -----");

		// Regra Pratica
		// Hit rate < 60% → cache mal configurado
		// Hit rate > 80% → cache saudável

		CacheStats stats = userCache.stats();

		System.out.println("Hits: " + stats.hitCount());
		System.out.println("Misses: " + stats.missCount());
		System.out.println("Hit rate: " + stats.hitRate());
		System.out.println("Load time (ms): " + stats.totalLoadTime() / 1_000_000);
		
		System.out.println("----- Java Cache Caffeine | Assincrono -----");
		
//		UserRepository repository = new UserRepository();
		UserCacheAsync asyncCache = new UserCacheAsync(repository);

		asyncCache.get(1)
			.thenAccept(user -> System.out.println(user.getNome()))
			.join() // coloquei este join apenas para bloquear até completar, se nao no proximo passo ele invalida o cache e a gente não ve o resultado
			;

		asyncCache.invalidate(1);

		System.out.println("Hit rate: " + asyncCache.stats().hitRate());

		// Invalidação dentro do callback (quando fizer sentido claro)

		asyncCache.get(1)
	        .thenAccept(user -> {
	            System.out.println(user.getNome());
	            asyncCache.invalidate(1);
	        });

		// Usar CompletableFuture combinando lógica
		
		asyncCache.get(1)
        	.thenCompose((User user) -> {
        		System.out.println(user.getNome());
        		asyncCache.invalidate(1);
        		return CompletableFuture.completedFuture(user);
        	});
	}
}
