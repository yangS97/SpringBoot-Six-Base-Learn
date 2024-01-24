package cn.iocoder.springboot.lab29.asynctask;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author YS
 * @date 2023/7/24 14:42
 * @Description
 */
public class CompleteFutureTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        // 创建异步执行任务:
        CompletableFuture<Double> cf = CompletableFuture.supplyAsync(CompleteFutureTest::fetchPrice);

        // 如果执行成功:
        cf.thenAccept((result) -> System.out.println(result));

        // 如果执行异常:
        cf.exceptionally((e) -> {
            e.printStackTrace();
            return null;
        });

        // 主线程不要立刻结束，否则CompletableFuture默认使用的线程池会立刻关闭:
        Thread.sleep(2000);
    }

    static Double fetchPrice() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        if (Math.random() < 0.3) {
            throw new RuntimeException("fetch price failed!");
        }
        return 5 + Math.random() * 20;
    }
}
