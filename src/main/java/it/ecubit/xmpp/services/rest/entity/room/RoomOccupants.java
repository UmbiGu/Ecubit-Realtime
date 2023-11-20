package it.ecubit.xmpp.services.rest.entity.room;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomOccupants {

	String jid;
	String nick;
	String role;
}
