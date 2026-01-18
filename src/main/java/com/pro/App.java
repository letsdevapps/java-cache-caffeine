package com.pro;

import com.pro.cache.UserCache;
import com.pro.repository.UserRepository;

public class App {
    public static void main( String[] args ) {
        System.out.println( "----- Java Cache Caffeine | Main -----" );
        
        UserRepository repository = new UserRepository();
        UserCache userCache = new UserCache(repository);

        System.out.println("Primeira chamada");
        System.out.println(userCache.get(1).getNome());

        System.out.println("Segunda chamada");
        System.out.println(userCache.get(1).getNome());
    }
}
