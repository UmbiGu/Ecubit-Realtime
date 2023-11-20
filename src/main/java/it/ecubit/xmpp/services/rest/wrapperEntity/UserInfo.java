package it.ecubit.xmpp.services.rest.wrapperEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    String jid;
    String connection;
    String ip;
    int port;
    int priority;
    String node;
    int uptime;
    String status;
    String resource;
    String statustext;

}

