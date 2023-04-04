package com.jzh.test;

import com.rabbitmq.client.*;

import java.nio.charset.StandardCharsets;

/**
 * rabbitmq consumer
 *
 * @author akyna
 * @date 04/03 003 11:38 PM
 */
public class RabbitMqConsumer {
    private final static String QUEUE_NAME = "TEST";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        Connection conn = factory.newConnection();
        Channel chan = conn.createChannel();
        chan.queueDeclare(QUEUE_NAME, false, false, false, null);
        // consumerTag 参数是消费者标签，delivery 参数是包含消息内容和元数据的 Delivery 对象。
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String msg = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println("get msg: " + msg);
        };
        CancelCallback cancelCallback = consumerTag -> {
            System.out.println("Consumer " + consumerTag + " has been cancelled");
        };
        // 队列名
        // 是否自动确认收到的消息
        // 当收到消息时要调用的回调函数，该回调函数必须接受消息作为参数
        // 当消费者取消订阅时要调用的回调函数，该回调函数必须接受消费者标签作为参数，该方法返回一个字符串，表示消费者标签，用于识别消费者。
        String consumerTag = chan.basicConsume(QUEUE_NAME, true, deliverCallback , cancelCallback);
        // 消费者标签(Consumer Tag)是在调用basicConsume方法时由客户端生成的一个唯一标识符，用于标识正在消费消息的特定消费者。
        // 它可以用于取消订阅，管理客户端库的内部状态或进行分配等操作。

        // 取消订阅
        // chan.basicCancel(consumerTag);
    }
}
