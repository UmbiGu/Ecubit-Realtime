package it.almaviva.nerds.services.xmpp;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

@Component("xmppConsumer")
public class XmppConsumer {

    public void get(Message<String> message) {

        MessageHeaders headers = message.getHeaders();

        System.out.println("From: " + headers.get("xmpp_from"));
        System.out.println("To: " + headers.get("xmpp_to"));
        System.out.println("Timestamp: " + headers.getTimestamp());
        System.out.println("Payload: " + message.getPayload());
    }
}