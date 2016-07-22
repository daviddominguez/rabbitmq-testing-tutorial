package es.amplia.utils.rabbitmq.workQueues;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static es.amplia.utils.rabbitmq.MessageUtils.getMessage;

public class NewTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(NewTask.class);
    private static final String QUEUE_NAME = "worker";

    public static void main(String[] args) throws IOException, TimeoutException {
        String message = getMessage(args);

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
        LOGGER.debug("Sent '{}' message", message);
    }
}
