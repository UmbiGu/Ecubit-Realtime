package it.ecubit.xmpp.services.util;

import it.ecubit.xmpp.services.model.message.Multicast;
import org.springframework.beans.factory.annotation.Value;

import java.util.Collection;

public class Util {

    public static class Message {

        @Value("${xmpp.host}")
        static private String host;

        public static String multicastStanza(
                String subject,
                String body,
                Collection<String> users) {

            String addresses = "";
            for (String user : users) {
                addresses += "<address type=\"to\" jid=\"" + user + "\" />";
            }
            String stanza = "<message>" +
                    "<addresses xmlns=\"http://jabber.org/protocol/address\">" +
                    addresses +
                    "</addresses>" +
                    "<subject>" + subject + "</subject>" +
                    "<body>" + body + "</body>" +
                    "</message>";

            return stanza;
        }

        public static String multicastStanzaAddress(
                String host,
                String subject,
                String body,
                Collection<String> users) {

            String addresses = "";
            for (String user : users) {
                addresses += "<address type=\"to\" jid=\"" + user + "@" + host  + "\" />";
            }
            String stanza = "<message>" +
                    "<addresses xmlns=\"http://jabber.org/protocol/address\">" +
                    addresses +
                    "</addresses>" +
                    "<subject>" + subject + "</subject>" +
                    "<body>" + body + "</body>" +
                    "</message>";

            return stanza;
        }


        public static String multicastStanza(Multicast stanza) {
            return Util.Message.multicastStanza(
                    stanza.getSubject(),
                    stanza.getBody(),
                    stanza.getTo());
        }
    }




}
