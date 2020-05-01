package com.vv.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NewExecutorService {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        for(int i = 0; i < 10; i++) {
            final String msg = "Msg - " + i;
            executorService.submit(() -> {
                System.out.println(msg + " " + Thread.currentThread().getName());
            });
        }
        executorService.shutdown();
    }
}
