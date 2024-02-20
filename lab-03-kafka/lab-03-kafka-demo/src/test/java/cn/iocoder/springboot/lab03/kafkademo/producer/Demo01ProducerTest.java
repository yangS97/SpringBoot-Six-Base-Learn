package cn.iocoder.springboot.lab03.kafkademo.producer;

import cn.iocoder.springboot.lab03.kafkademo.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.support.SendResult;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class Demo01ProducerTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Demo01Producer producer;

    /**
     * SendResult 是发送结果的封装类，包含了ProducerRecord 和 RecordMetadata 两个对象。
     * ProducerRecord 是发送的消息对象，RecordMetadata 是发送成功后的元数据信息。
     * ProducerRecord 包含了 topic、key、value 等信息。
     * RecordMetadata 包含了 topic、partition、offset、timestamp 等信息。
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void testSyncSend() throws ExecutionException, InterruptedException {
        int id = (int) (System.currentTimeMillis() / 1000);
        SendResult result = producer.syncSend(id);
        logger.info("[testSyncSend][发送编号：[{}] 发送结果：[{}]]", id, result);

        // 阻塞等待，保证消费
        new CountDownLatch(1).await();
    }

    @Test
    public void testASyncSend() throws InterruptedException {
        int id = (int) (System.currentTimeMillis() / 1000);
        producer.asyncSend(id).addCallback(new ListenableFutureCallback<SendResult<Object, Object>>() {

            @Override
            public void onFailure(Throwable e) {
                logger.info("[testASyncSend][发送编号：[{}] 发送异常]]", id, e);
            }

            @Override
            public void onSuccess(SendResult<Object, Object> result) {
                logger.info("[testASyncSend][发送编号：[{}] 发送成功，结果为：[{}]]", id, result);
            }

        });

        // 阻塞等待，保证消费
        new CountDownLatch(1).await();
    }
//
//    @Test
//    public void testOnewaySend() throws InterruptedException {
//        int id = (int) (System.currentTimeMillis() / 1000);
//        producer.onewaySend(id);
//        logger.info("[testOnewaySend][发送编号：[{}] 发送完成]", id);
//
//        // 阻塞等待，保证消费
//        new CountDownLatch(1).await();
//    }

//    @Test
//    public void testSyncSendMore() throws ExecutionException, InterruptedException {
//        for (int i = 0; i < 1000; i++) {
//            int id = (int) (System.currentTimeMillis() / 1000);
//            SendResult result = producer.syncSend(id);
//            logger.info("[testSyncSend][发送编号：[{}] 发送结果：[{}]]", id, result);
//            Thread.sleep(10);
//        }
//
//        // 阻塞等待，保证消费
//        new CountDownLatch(1).await();
//    }
//
//    @Test
//    public void block() throws InterruptedException {
//        // 阻塞等待，保证消费
//        new CountDownLatch(1).await();
//    }

}
