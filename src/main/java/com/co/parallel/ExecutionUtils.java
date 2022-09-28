package com.co.parallel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ExecutionUtils {

    private static final Logger log = LoggerFactory.getLogger(ExecutionUtils.class);

    private static boolean trace = false;

    private static int maxThreads = 30;

    // This function runs a collection of lambda functions in parallel.
    public static List<Object> runParallelBase(List<IParallelExecuteFunction> listToRun) {
        int numThreads = listToRun.size();
        if (trace) {
            log.debug("Enter runParallel");
        }
        // Results will be placed in a thread safe list.
        List<Object> results = Collections.synchronizedList(new ArrayList<Object>());
        for ( int i = 0; i < numThreads; ++i) {
            results.add(null);
        }

        // Indicates which indexes in the list failed.
        List<Exception> errors = Collections.synchronizedList(new ArrayList<Exception>());

        // Count down latch to indicate when the threads are all finished.
        CountDownLatch countDownLatch = new CountDownLatch(numThreads);
        for ( int i = 0; i < numThreads; ++i) {
            final int index = i;
            // Run the lambda function in a thread.
            new Thread(() -> {
                Object result = null;
                // Try/catch in the execute function and log the error. The exception must be caught,
                // otherwise, the countDownLatch will not be decremented.
                try {
                    if (trace) {
                        log.debug("Start Thread " + index);
                    }
                    log.debug("listToRun.get(index) {} ", listToRun.size());
                    result = listToRun.get(index).execute();
                    if (trace) {
                        log.debug("End Thread " + index);
                    }
                } catch(Exception e) {
                    e.printStackTrace();
                    errors.add(e);
                }
                // Record the result at the same index as the lambda function in listToRun.
                results.set(index, result);
                // Thread is finished. Decrement the countDownLatch.
                countDownLatch.countDown();
            }).start();
        }

        // Wait for all threads to finish.
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (trace) {
            log.debug("Finish runParallel");
        }
        // If any errors occurred, throw an exception.
        if (errors.size() > 0) {
            throw new RuntimeException(errors.get(0)) ;
        }
        // The results are returned in the same order as the associated lambda functions in listToRun.
        return results;
    }

    // Puts a restriction on the number of threads that can be generated to maxThreads.
    // This way, some weird input cannot bring the server to its knees with threads.
    public static List<Object> runParallel(List<IParallelExecuteFunction> listToRun) {
        List<Object> results = new ArrayList<Object>();
        for (int i = 0; i < listToRun.size(); i += maxThreads) {
            List<IParallelExecuteFunction> tListToRun = listToRun.subList(i, Math.min(i + maxThreads, listToRun.size()));
            results.addAll(runParallelBase(tListToRun));
        }

        return results;
    }

    public static List<Object> runParallel(List<IParallelExecuteFunction> listToRun, int maxThreads) {
        List<Object> results = new ArrayList<Object>();
        for (int i = 0; i < listToRun.size(); i += maxThreads) {
            List<IParallelExecuteFunction> tListToRun = listToRun.subList(i, Math.min(i + maxThreads, listToRun.size()));
            results.addAll(runParallelBase(tListToRun));
        }

        return results;
    }

    public static List<Object> runParallel(IParallelExecuteFunction... listToRun) {
        List<IParallelExecuteFunction> tListToRun = new ArrayList<IParallelExecuteFunction>();
        for (IParallelExecuteFunction item: listToRun) {
            tListToRun.add(item);
        }
        return runParallelBase(tListToRun);
    }

}

/*
 *
 	Sample Usage:
 *
 *
      List<IParallelExecuteFunction> listToRun = new ArrayList<IParallelExecuteFunction>();
      listToRun.add(() -> {
      	try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				// Must return some type of results.
      	return null;
      });
      listToRun.add(() -> {
      	try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
      	return null;
      });
      listToRun.add(() -> {
      	try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
      	return null;
      });
      listToRun.add(() -> {
      	try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
      	return null;
      });
      listToRun.add(() -> {
      	try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
      	return null;
      });
      log.debug("start run parallel");
      List<Object> pResults = ExecutionUtils.runParallel(listToRun);
      log.debug("end run parallel size()=" + pResults.size());
*/
