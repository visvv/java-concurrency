package com.vv.concurrency;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.IntStream;

/**
 * Hello world!
 *
 */
public class CustomExecutorCompletionServiceNonBlocking
{
    public static void main( String[] args ) throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        List<Callable<String>> taskList = new ArrayList<>();

        IntStream.range(0,10).forEach((i) -> {
            final String message = "Message : " + i;
            // Higher sleep time only for even tasks.
            final int sleepTime = (i % 2 == 0) ? 2000 : 0;
            taskList.add(()-> {
                Thread.sleep(sleepTime);
                return message  + " - " + Thread.currentThread().getName();
            });
        });

        CompletionService<String> completionService = new ExecutorCompletionService<>(executorService);
        for(Callable<String> task : taskList) {
            completionService.submit(task);
        }

        for(int i =0; i < 10 ; i++) {
            System.out.println(completionService.take().get()); // blocking.
        }
        // Other thread execution results where not blocked by the running threads
        // Output will appear as soon the task completes its execution.
        // So all even task results will appear first.
        executorService.shutdown();
    }
}
