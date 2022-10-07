package com.azikosolutions.springbootsns.listeners;

import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.PublishRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {

    @Value("${topicARN}")
    private String TOPIC_ARN;

    private final AmazonSNSClient snsClient;

    @Autowired
    public KafkaListeners(AmazonSNSClient snsClient) {
        this.snsClient = snsClient;
    }

    @KafkaListener(topics = "kafka.usecase.students", groupId = "groupId")
    void listener(String data) {
        PublishRequest publishRequest =
                new PublishRequest(TOPIC_ARN, "Notification sent to " + data);
        snsClient.publish(publishRequest);
    }



}
