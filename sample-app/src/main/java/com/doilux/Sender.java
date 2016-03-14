package com.doilux;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

@RestController
public class Sender {

    private int num = 0;

    private static final String QUEUE_NAME = "com.doilux.greeting";

    @RequestMapping("/send")
    public String index() {

        Connection connection = null;
        Channel channel = null;

        String result;

        try {
            num++;

            // Factory 生成
            ConnectionFactory factory = new ConnectionFactory();
            factory.setUri("amqp://admin:password@192.168.200.22:5672");

            // Conenction & Channel 接続
            connection = factory.newConnection();
            channel = connection.createChannel();

            // com.doilux.greeting キューに Hello World メッセージを送信
            String message = "Hello World! [" + num + "]";
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());

            // Channel & Connection 切断
            channel.close();
            connection.close();

            result = "Send Message : " + message;

        } catch (TimeoutException | IOException | KeyManagementException | NoSuchAlgorithmException | URISyntaxException e) {
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
