package es.amplia.utils.rabbitmq.topics;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static es.amplia.utils.rabbitmq.MessageUtils.getMessage;
import static es.amplia.utils.rabbitmq.MessageUtils.getRouting;

public class EmitLogTopic {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmitLogTopic.class);
    private static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");

        String routingKey = getRouting(args);
        String message = getMessage(args);

        channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes());
        LOGGER.debug("Sent [{}] '{}' message", routingKey, message);

        channel.close();
        connection.close();
    }
}
