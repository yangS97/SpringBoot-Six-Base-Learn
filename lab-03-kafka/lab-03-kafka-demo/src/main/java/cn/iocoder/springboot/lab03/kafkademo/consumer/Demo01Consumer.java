package cn.iocoder.springboot.lab03.kafkademo.consumer;

import cn.iocoder.springboot.lab03.kafkademo.message.Demo01Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class Demo01Consumer {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * @KafkaListener注解常用的属性有以下几个：
     * topics：指定订阅的 topic 名称，支持订阅多个 topic。
     * groupId：指定消费组的 groupId，用于标识消费者。
     * id：指定消费者的 id，用于标识消费者。
     * containerFactory：指定 KafkaListenerContainerFactory 的 Bean 名称，用于指定使用的 KafkaListenerContainerFactory。
     * errorHandler：指定 KafkaListenerErrorHandler 的 Bean 名称，用于指定使用的 KafkaListenerErrorHandler。
     * @param message
     */
    @KafkaListener(topics = Demo01Message.TOPIC,
            groupId = "demo01-consumer-group-" + Demo01Message.TOPIC)
    public void onMessage(Demo01Message message) {
        logger.info("[onMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), message);
    }

}
