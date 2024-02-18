package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static com.example.demo.DemoApplication.insertIntoQueue;
import static com.example.demo.DemoApplication.kvStore;

@RestController
public class TestController {
    @GetMapping("/{key}")
    public String getValue(@PathVariable String key) throws ExecutionException, InterruptedException {
        return CompletableFuture.supplyAsync(()->{
            insertIntoQueue(key);
            while (kvStore.get(key)==null){
//                System.out.println("still waiting");
//                perhaps can insert a timeout for x seconds so that the client is not simply waiting for response
            }
            return kvStore.get(key);
        }).get();
    }
}
