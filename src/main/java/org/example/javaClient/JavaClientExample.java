package org.example.javaClient;

import org.apache.pulsar.client.api.*;

import java.util.stream.IntStream;

public class JavaClientExample {

    private static final String SERVICE_URL = "pulsar://localhost:6650";
    private static final String TOPIC_NAME = "test-topic";
    private static final String SUBSCRIPTION_NAME = "test-sub";


    public void useJavaClient() throws PulsarClientException {


        // You can instantiate a PulsarClient object using just a URL for the target Pulsar cluster like this:
        PulsarClient client = PulsarClient.builder()
                .serviceUrl("pulsar://localhost:6650")
                .build();

        // Once you've instantiated a PulsarClient object, you can create a Producer for a specific Pulsar topic.
        Producer<byte[]> producer = client.newProducer()
                .topic("my-topic")
                .create();
        producer.send("My message".getBytes());

        // By default, producers produce messages that consist of byte arrays.
        // You can produce different types by specifying a message schema.
        Producer<String> stringProducer = client.newProducer(Schema.STRING)
                .topic("my-topic")
                .create();
        stringProducer.send("My string message");

        MessageListener messageListener = (consumer, msg) -> {
            try {
                System.out.println("Message recieved: " + new String(msg.getData()));
                consumer.acknowledge(msg);
            } catch (Exception e) {
                consumer.negativeAcknowledge(msg);
            }
        };

        Consumer stringConsumer = client.newConsumer()
                .topic("my-topic")
                .subscriptionName("my-subscription")
                .messageListener(messageListener)
                .subscribe();



        // Make sure that you close your producers, consumers, and clients when you do not need them.

        producer.close();
        stringProducer.close();
        client.close();


        // Close operations can also be asynchronous:
        /*
        producer.closeAsync()
                .thenRun(() -> System.out.println("Producer closed"))
                .exceptionally((ex) -> {
                    System.err.println("Failed to close producer: " + ex);
                    return null;
                });

        stringProducer.closeAsync()
                .thenRun(() -> System.out.println("StringProducer closed"))
                .exceptionally((ex) -> {
                    System.err.println("Failed to close producer: " + ex);
                    return null;
                });

        client.closeAsync()
                .thenRun(() -> System.out.println("Client closed"))
                .exceptionally((ex) -> {
                    System.err.println("Failed to close producer: " + ex);
                    return null;
                });

        MessageListener messageListener = (consumer, msg) -> {
            try {
                System.out.println("Message recieved: " + new String(msg.getData()));
                consumer.acknowledge(msg);
            } catch (Exception e) {
                consumer.negativeAcknowledge(msg);
            }
        };

        Consumer consumer = client.newConsumer()
                .topic("my-topic")
                .subscriptionName("my-subscription")
                .messageListener(messageListener)
                .subscribe();
         */

    }
}
