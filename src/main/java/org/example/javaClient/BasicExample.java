package org.example.javaClient;

import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.Schema;

public class BasicExample {

    public static void main(String[] args) throws PulsarClientException {

        PulsarClient client = PulsarClient.builder()
                .serviceUrl("http://127.0.0.1:8080")
                .build();

        // create producer that will send values as Strings, default is byte[]
        Producer<String> producer = client.newProducer(Schema.STRING)
                .topic("hellotopic")
                .create();

        // create a new message, send it, and block until it is acknowledged
        producer.newMessage()
                .key("mykey")
                .value("myvalue")
                .sendAsync();       // .sendAsync() wont block until it is acknowledged

        //producer.close();
        //client.close();

        PulsarClient consumerClient = PulsarClient.builder()
                .serviceUrl("http://127.0.0.1:8080")
                .build();


        // create a consumer that will receive values as Strings
        Consumer consumer = consumerClient.newConsumer(Schema.STRING)
                .topic("mytopic")
                .subscriptionName("my-subscription")
                .subscribe();

        while(true) {

            // block and wait until a single message is available
            Message<String> message = consumer.receive();

            // do something with messsage
            handleMessage(message);

            // acknowledge the message so that it can be deleted by the message broker
            consumer.acknowledge(message);

            return;
        }
    }

    private static void handleMessage(Message<String> message) {

        System.out.println("Received message is: " + new String(message.getData()));
    }

}
