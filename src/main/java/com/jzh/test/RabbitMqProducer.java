package com.jzh.test;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;

/**
 * rabbitmq producer
 * @author akyna
 * @date 04/03 003 11:22 PM
 */
public class RabbitMqProducer {
    private final static String QUEUE_NAME = "TEST";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        try(
                Connection conn = factory.newConnection();
                Channel chan = conn.createChannel();
        ) {
            // queue_name 要声明的队列的名称。
            // durable 指定队列是否需要持久化。如果是，则声明的队列将会在节点重新启动或者发生其他的中断时仍然存在。该参数的值被保存在 Erlang 的 Mnesia 数据库中，用来标示该队列是否应该被存储到磁盘上。
            // exclusive 指定队列是否为当前连接的独占队列。如果是，则声明的队列将会被当前连接独占，同时也会在当前连接关闭时自动删除该队列。
            // autoDelete 指定在队列中所有消费者都断开连接时，是否自动删除队列。
            // arguments k-v map 其它参数。
            chan.queueDeclare(QUEUE_NAME, false, false, false, null);
            String msg = "你好";
            // exchange 指定将要发送消息的交换器名称（或使用空字符串表示默认交换器）。交换器是消息发布的路由中心，用于将消息路由到一个或多个队列。
            // routingKey 指定将消息发布到哪个队列中。当交换器收到消息时，它根据路由键（Routing Key）将消息路由到与此匹配的一个或多个队列。
            // mandatory （可选）当消息无法路由到与之匹配的任何队列时，将消息返回给生产者。
            // immediate （可选）指定消息是否要立即发送。
            // AMQP.BasicProperties AMQP 消息属性，可以设置消息的各种属性，如消息的优先级、持久性、消息ID、类型等。
            // body 要发送的消息体
            chan.basicPublish("", QUEUE_NAME, null, msg.getBytes(StandardCharsets.UTF_8));
        }
    }

}
