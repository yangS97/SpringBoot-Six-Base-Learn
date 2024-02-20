package cn.iocoder.springboot.lab03.kafkademo.producer;

import cn.iocoder.springboot.lab03.kafkademo.message.Demo01Message;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;

/**
 * 封装了两个发送消息的方法: syncSend() 和 asyncSend(), 分别用于同步发送消息和异步发送消息。
 * 同步方法的实现逻辑: 创建 Demo01Message 消息，然后调用 kafkaTemplate.send() 方法发送消息，最后调用 get() 方法等待发送结果。
 * 异步方法的实现逻辑: 创建 Demo01Message 消息，然后调用 kafkaTemplate.send() 方法发送消息，返回 ListenableFuture 对象。
 */
@Component
public class Demo01Producer {

    @Resource
    private KafkaTemplate<Object, Object> kafkaTemplate;

    public SendResult syncSend(Integer id) throws ExecutionException, InterruptedException {
        // 创建 Demo01Message 消息
        Demo01Message message = new Demo01Message();
        message.setId(id);
        // 同步发送消息
        return kafkaTemplate.send(Demo01Message.TOPIC, message).get();
    }

    public ListenableFuture<SendResult<Object, Object>> asyncSend(Integer id) {
        // 创建 Demo01Message 消息
        Demo01Message message = new Demo01Message();
        message.setId(id);
        // 异步发送消息
        return kafkaTemplate.send(Demo01Message.TOPIC, message);
    }

}
