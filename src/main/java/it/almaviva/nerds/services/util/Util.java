package it.almaviva.nerds.services.util;

import it.almaviva.nerds.services.model.Multicast;
import org.springframework.beans.factory.annotation.Value;

import java.util.Collection;

public class Util {

    public static class Message {

        @Value("${nerds.host}")
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
