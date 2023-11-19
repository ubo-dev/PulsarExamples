package org.example.consumers;

import org.apache.pulsar.client.api.MessageListener;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.example.config.AppConfig;
import org.example.utils.ClientUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataConsumer {

    private static final Logger logger = LoggerFactory.getLogger(DataConsumer.class);
    public static void main(String[] args) throws PulsarClientException {

        PulsarClient pulsarClient = ClientUtils.initPulsarClient(AppConfig.token);

        //consumeUser(pulsarClient);
        //consumeItem(pulsarClient);
        consumeOrder(pulsarClient);
    }


    public static void consumeUser(PulsarClient pulsarClient) throws PulsarClientException {
        MessageListener messageListener = (consumer, msg) -> {
            try {
                logger.info("User received: {}",new String(msg.getData()));
                consumer.acknowledge(msg);
            } catch (Exception e) {
                consumer.negativeAcknowledge(msg);
            }
        };
        try {
            pulsarClient.newConsumer()
                    .topic(AppConfig.USERS_TOPIC)
                    .subscriptionName("my-subscription")
                    .messageListener(messageListener)
                    .subscribe();

        } catch (PulsarClientException e) {
            throw new RuntimeException(e);
        }

    }

    public static void consumeItem(PulsarClient pulsarClient) throws PulsarClientException {
        MessageListener messageListener = (consumer, msg) -> {
            try {
                logger.info("Item received: {}",new String(msg.getData()));
                consumer.acknowledge(msg);
            } catch (Exception e) {
                consumer.negativeAcknowledge(msg);
            }
        };
        try {
            pulsarClient.newConsumer()
                    .topic(AppConfig.ITEMS_TOPIC)
                    .subscriptionName("my-subscription")
                    .messageListener(messageListener)
                    .subscribe();

        } catch (PulsarClientException e) {
            throw new RuntimeException(e);
        }

    }

    public static void consumeOrder(PulsarClient pulsarClient) throws PulsarClientException {
        MessageListener messageListener = (consumer, msg) -> {
            try {
                logger.info("Order received: {}", new String(msg.getData()));
                consumer.acknowledge(msg);
            } catch (Exception e) {
                consumer.negativeAcknowledge(msg);
            }
        };
        try {
            pulsarClient.newConsumer()
                    .topic(AppConfig.ORDERS_TOPIC)
                    .subscriptionName("my-subscription")
                    .messageListener(messageListener)
                    .subscribe();

        } catch (PulsarClientException e) {
            throw new RuntimeException(e);
        }

    }
}
