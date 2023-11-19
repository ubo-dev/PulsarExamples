package org.example.producers;

import org.example.config.AppConfig;
import org.example.model.Order;
import org.example.utils.ClientUtils;
import org.example.utils.DataSourceUtils;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.impl.schema.JSONSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class OrdersDataSource {
    private static final Logger logger
            = LoggerFactory.getLogger(OrdersDataSource.class);
    public static void main(String[] args) throws IOException {
        Stream<Order> sourceStream = DataSourceUtils.loadDataFile(AppConfig.ORDERS_FILE_PATH)
                .map(DataSourceUtils::lineAsOrder);

        logger.info("Creating Pulsar Client ...");
        PulsarClient pulsarClient = ClientUtils.initPulsarClient(AppConfig.token);

        logger.info("Creating Orders Producer ...");
        Producer<Order> ordersProducer
                = pulsarClient.newProducer(JSONSchema.of(Order.class))
                .producerName("order-producers")
                .topic(AppConfig.ORDERS_TOPIC)
                .create();

        AtomicInteger counter = new AtomicInteger(1);
        for (Iterator<Order> it = sourceStream.iterator(); it.hasNext(); ) {
            Order order = it.next();

            ordersProducer.newMessage()
                    .value(order)
                    .eventTime(System.currentTimeMillis())
                    .send();

            logger.info("Total {} - Sent: {}", counter.getAndIncrement(), order);
        }

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("Sent '{}' orders.", counter.get());
            logger.info("Closing Resources...");
            try {
                ordersProducer.close();
                pulsarClient.close();
            } catch (PulsarClientException e) {
                e.printStackTrace();
            }
        }));
    }
}
