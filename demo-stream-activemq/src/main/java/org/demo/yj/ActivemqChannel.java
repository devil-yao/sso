package org.demo.yj;

import org.springframework.cloud.stream.binder.*;
import org.springframework.cloud.stream.provisioning.ConsumerDestination;
import org.springframework.cloud.stream.provisioning.ProducerDestination;
import org.springframework.integration.core.MessageProducer;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

public class ActivemqChannel extends AbstractMessageChannelBinder<ConsumerProperties,ProducerProperties,ActivemqProvisioningProvider> {

    public ActivemqChannel(String[] headersToEmbed, ActivemqProvisioningProvider provisioningProvider) {
        super(headersToEmbed, provisioningProvider);
    }

    @Override
    protected MessageHandler createProducerMessageHandler(ProducerDestination destination, ProducerProperties producerProperties, MessageChannel errorChannel) throws Exception {
        return null;
    }

    @Override
    protected MessageProducer createConsumerEndpoint(ConsumerDestination destination, String group, ConsumerProperties properties) throws Exception {
        return null;
    }
}
