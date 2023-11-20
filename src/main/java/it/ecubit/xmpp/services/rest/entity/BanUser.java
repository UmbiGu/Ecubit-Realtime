package it.ecubit.xmpp.services.rest.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BanUser {
    String user;
    String host;
    String reason;
}
