package org.example.utils;

import org.apache.pulsar.client.api.ClientBuilder;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.impl.auth.AuthenticationToken;

import java.util.Optional;

public class ClientUtils {
    public static PulsarClient initPulsarClient(Optional<String> authToken) throws PulsarClientException {
        ClientBuilder builder = PulsarClient
                .builder()
                .serviceUrl("pulsar://localhost:6650");

        authToken.ifPresent(token -> builder.authentication(new AuthenticationToken(token)));
        return builder.build();
    }
}
