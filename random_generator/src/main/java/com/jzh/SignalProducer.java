package com.jzh;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.Random;

/**
 * 信号发生器
 *
 * @author akyna
 * @date 04/04 004 5:13 PM
 */
public class SignalProducer {
    // 间隔时间 100 ms
    private final static int interval = 100;

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        Connection conn = factory.newConnection();
        Channel chan = conn.createChannel();
        chan.queueDeclare(MyQueues.SIGNAL_QUEUE_NAME, false, false, false, null);
        Random random = new Random();
        double mean = 50.0; // 均值
        double stddev = 10.0; // 标准差
        while (true) {
            double randomValue = random.nextGaussian() * stddev + mean;
            System.out.println("产生随机信号: " + randomValue);
            chan.basicPublish("", MyQueues.SIGNAL_QUEUE_NAME, null, String.valueOf(randomValue).getBytes());
            Thread.sleep(interval);
        }
    }
}
