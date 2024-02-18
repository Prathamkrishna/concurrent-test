package com.example.demo;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

import static com.example.demo.DemoApplication.*;

// this class is "fetching" value from external source - here I have just put thread to sleep for 2 seconds
@Service
public class ExtSourceService {

    @Scheduled(fixedDelay = 1000, initialDelay = 5000)
    public void getValue() throws NullPointerException, NoSuchElementException {
        String key;
        try {
            if (queue.element()==null)return;
        }catch (NoSuchElementException n){
            System.out.println("error");
        }
        key=queue.poll();
        if (key == null) return;
        setCachedString(key);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        kvStore.put(key, key+key);
    }
}
