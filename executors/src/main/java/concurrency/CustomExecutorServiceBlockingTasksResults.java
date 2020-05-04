package com.vv.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.IntStream;

/**
 * Hello world!
 *
 */
public class CustomExecutorServiceBlockingTasksResults
{
    public static void main( String[] args ) throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        List<Callable<String>> taskList = new ArrayList<>();

        IntStream.range(0,10).forEach((i) -> {
            final String message = "Message : " + i;
            // Higher sleep time only for the even tasks.
            final int sleepTime = (i % 2 == 0) ? 2000 : 0;
            taskList.add(()-> {
                Thread.sleep(sleepTime);
                return message  + " - " + Thread.currentThread().getName();
            });
        });

        List<Future<String>> futureList = executorService.invokeAll( taskList);

        for(Future<String> future : futureList) {
            System.out.println(future.get()); // blocking.
        }
        // Other thread execution results where blocked the running threads
        // Results will appear the same order as the corresponding tasks has been invoked
        executorService.shutdown();
    }
}
