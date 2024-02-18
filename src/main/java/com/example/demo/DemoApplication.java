package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.atomic.AtomicReference;

@SpringBootApplication
@EnableScheduling
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	static Map<String, String> kvStore = new HashMap<>();
	static Queue<String> queue = new LinkedList<>();
	static AtomicReference<String> cachedString = new AtomicReference<>();
	static synchronized void putValueInMapAndPrint(String key, String value){
		System.out.println(Thread.currentThread().getName() + " this is accessing this block");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        kvStore.put(key, value);
	}

	static synchronized void setCachedString(String key){
		cachedString.set(key);
	}

	static String getCachedString(){
		return cachedString.get();
	}

	static synchronized void insertIntoQueue(String key){
		System.out.println(Thread.currentThread().getName());
//		add null check for cachestring
		if (getCachedString() == null){
			System.out.println("addding element in first " + key);
			setCachedString(key);
			queue.add(key);
		}
		if (getCachedString().equals(key)){
			System.out.println(key + "it is equal" + getCachedString());
			return;
		}
		else {
			System.out.println("not equal");
			System.out.println("adding element " + key);
			queue.add(key);
		}
	}
}
