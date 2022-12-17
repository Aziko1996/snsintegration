package com.azikosolutions.springbootsns.controller;

import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.SubscribeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController {

    @Value("${topicARN}")
    private String TOPIC_ARN;

    private final AmazonSNSClient snsClient;

    @Autowired
    public NotificationController(AmazonSNSClient snsClient) {
        this.snsClient = snsClient;
    }

    @GetMapping("/addSubscription/{email}")
    public String addSubscription(@PathVariable String email) {
        SubscribeRequest request = new SubscribeRequest(TOPIC_ARN, "email", email);
        snsClient.subscribe(request);
        return "Subscription request is pending. To confirm the subscription, check your email : " + email;
    }

    @GetMapping("/sendNotification")
    public String publishMessageToTopic(){
        PublishRequest publishRequest=new PublishRequest(TOPIC_ARN, buildEmailBody(),"Notification: Tashkent Network connectivity issue");
        snsClient.publish(publishRequest);
        return "Notification send successfully !!";
    }

    private String buildEmailBody() {
        return "Dear Employee ,\n" +
                "\n" +
                "\n" +
                "Connection down Tashkent."+"\n"+
                "All the servers in Tashkent Data center are not accessible. We are working on it ! \n" +
                "Notification will be sent out as soon as the issue is resolved. For any questions regarding this message please feel free to contact IT Service Support team";
    }

}
