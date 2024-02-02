package cn.iocoder.springboot.lab04.rabbitmqdemo.producer;

import cn.iocoder.springboot.lab04.rabbitmqdemo.message.Demo14Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class Demo14Producer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public String syncSend(Integer id) {
        // 创建 Demo01Message 消息
        Demo14Message message = new Demo14Message();
        message.setId(id);
        // 创建 CorrelationData 对象  --> 这是reply回复对象, 用于消息的确认, 里面设置了一个UUID, 用于标识回复消息的唯一性
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        // 同步发送消息，并接收结果  convertSendAndReceive(), 设置了消息的回复对象(correlationData), 用于消息的确认
        return (String) rabbitTemplate.convertSendAndReceive(Demo14Message.EXCHANGE, Demo14Message.ROUTING_KEY, message,
                correlationData);
    }

}
