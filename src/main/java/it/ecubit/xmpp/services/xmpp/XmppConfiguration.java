package it.ecubit.xmpp.services.xmpp;

import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.stringprep.XmppStringprepException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.xmpp.config.XmppConnectionFactoryBean;

@Configuration
public class XmppConfiguration {


    @Value("${xmpp.host}")
    private String host;

    @Bean("xmppConnection")
    public XmppConnectionFactoryBean xmppConnectionFactoryBean() {

        try {

            XMPPTCPConnectionConfiguration connectionConfiguration = XMPPTCPConnectionConfiguration.builder()
                    .setXmppDomain(JidCreate.domainBareFrom(host))
                    .setHost(host)
                    .setPort(5222)
                    .setResource("xmppServices")
                    .setUsernameAndPassword("admin", "admin")
                    .setSecurityMode(SecurityMode.disabled)
                    .setSendPresence(true)
                    .setDebuggerEnabled(true)
                    .build();

            XmppConnectionFactoryBean connectionFactoryBean = new XmppConnectionFactoryBean();
            connectionFactoryBean.setConnectionConfiguration(connectionConfiguration);
            connectionFactoryBean.setSubscriptionMode(null);
            return connectionFactoryBean;

        } catch (XmppStringprepException e) {
            e.printStackTrace();
        }

        return null;
    }
}
