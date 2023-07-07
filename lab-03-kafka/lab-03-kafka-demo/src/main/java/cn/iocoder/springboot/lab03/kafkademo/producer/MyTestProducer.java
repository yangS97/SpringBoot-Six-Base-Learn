package cn.iocoder.springboot.lab03.kafkademo.producer;

import cn.iocoder.springboot.lab03.kafkademo.message.Demo01Message;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import javax.annotation.Resource;

/**
 * @author YS
 * @date 2023/7/6 17:25
 * @Description
 */
@Component
public class MyTestProducer {
    @Resource
    private KafkaTemplate<Object, Object> kafkaTemplate;

    public ListenableFuture<SendResult<Object, Object>> asyncSend(Integer id) {
        Demo01Message demo01Message = new Demo01Message();
        demo01Message.setId(id);

        ListenableFuture<SendResult<Object, Object>> sendResultFuture = kafkaTemplate.send(Demo01Message.TOPIC, demo01Message);
        return sendResultFuture;
    }

}
