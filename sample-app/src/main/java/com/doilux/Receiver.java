package com.doilux;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

@RestController
public class Receiver {

    private static final String QUEUE_NAME = "com.doilux.greeting";

    private static final String LINE_SEP = System.getProperty("line.separator");

    @RequestMapping("/receive")
    public String index() {

        Connection connection = null;
        Channel channel = null;

        String result;

        try {
            // Factory 生成
            ConnectionFactory factory = new ConnectionFactory();
            factory.setUri("amqp://admin:password@192.168.200.22:5672");

            // Connection & Channel 接続
            connection = factory.newConnection();
            channel = connection.createChannel();

            QueueingConsumer consumer = new QueueingConsumer(channel);
            channel.basicConsume(QUEUE_NAME, true, consumer);

            StringBuilder sb = new StringBuilder();

            while (true) {
                QueueingConsumer.Delivery delivery = consumer.nextDelivery(3000);
                if (delivery == null) {
                    break;
                }

                String message = "Receive Message : " + new String(delivery.getBody());
                sb.append(message);
                sb.append(LINE_SEP);
            }

            // Channel & Connection 切断
            channel.close();
            connection.close();

            result = sb.toString();

        } catch (TimeoutException | IOException | InterruptedException |
                KeyManagementException | NoSuchAlgorithmException | URISyntaxException e) {
            e.printStackTrace();
            result = "ng";
        } finally {

            if (channel != null) {
                try {
                    channel.close();
                } catch (Exception e) {
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                }
            }

        }

        return result;
    }
}
