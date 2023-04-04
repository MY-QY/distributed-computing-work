package com.jzh;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * 信号分析器
 *
 * @author akyna
 * @date 04/04 004 5:14 PM
 */
public class SignalAnalyser {
    private final static Integer N = 50;

    public static void main(String[] args) throws Exception {
        ArrayList<Double> signals = new ArrayList<>();
        ConnectionFactory factory = new ConnectionFactory();
        Connection conn = factory.newConnection();
        Channel chan = conn.createChannel();
        chan.queueDeclare(MyQueues.SIGNAL_QUEUE_NAME, false, false, false, null);
        chan.queueDeclare(MyQueues.RES_QUEUE_NAME, false, false, false, null);
        DeliverCallback deliverCallback = (tag, delivery) -> {
            Double signal = Double.valueOf(new String(delivery.getBody(), StandardCharsets.UTF_8));
            signals.add(signal);
            if (signals.size() == N) {
                // 分析信号
                // 最小值 最大值 总和 均值 方差
                double min = Double.MAX_VALUE, max = Double.MIN_VALUE, sum = 0, avg, var = 0;
                for (double v : signals) {
                    sum += v;
                    if (v < min) min = v;
                    if (v > max) max = v;
                }
                avg = sum / N;
                for (double v : signals) {
                    var += (v - avg) * (v - avg);
                }
                var /= N;
                ResOfSignalAnalysis res = new ResOfSignalAnalysis(min, max, avg, var);
                System.out.println("生成信号分析结果: " + res);
                // 清空分析过的信号
                signals.clear();
                // 序列化
                try (
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        ObjectOutputStream oos = new ObjectOutputStream(bos);
                ) {
                    oos.writeObject(res);
                    oos.flush();
                    // 发送消息
                    chan.basicPublish("", MyQueues.RES_QUEUE_NAME, null, bos.toByteArray());
                }
            }
        };
        chan.basicConsume(MyQueues.SIGNAL_QUEUE_NAME, true, deliverCallback, tag -> {
        });
    }
}
