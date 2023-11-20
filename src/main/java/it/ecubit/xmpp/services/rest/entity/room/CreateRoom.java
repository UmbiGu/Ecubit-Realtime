package it.ecubit.xmpp.services.rest.entity.room;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRoom {
    String name;
    String service;
    String host;
}
