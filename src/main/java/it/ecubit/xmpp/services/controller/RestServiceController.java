package it.ecubit.xmpp.services.controller;

import it.ecubit.xmpp.services.util.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ImportResource;
import org.springframework.integration.xmpp.XmppHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ImportResource("/xmppOutbound.xml")
public class RestServiceController {

    @Autowired
    private ApplicationContext appContext;

    @GetMapping("/version")
    public String version() {
        return "{ version : \"" + Config.appName + " " + Config.appVersionName + "\" }";
    }

    // XMPP
    @CrossOrigin
    @GetMapping("/message")
    public void message() {
        MessageChannel toUserChannel = appContext.getBean("xmppOutbound", MessageChannel.class);
        Message<String> message = MessageBuilder.withPayload("AAAA")
                .setHeader(XmppHeaders.TO, "admin@localhost")
                .build();
        toUserChannel.send(message);
    }
}
