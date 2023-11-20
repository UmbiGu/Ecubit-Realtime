package it.ecubit.xmpp.services.rest.entity.room;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetRoomOccupants {
    String name;
    String service;
}
