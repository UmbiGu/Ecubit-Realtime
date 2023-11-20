package it.ecubit.xmpp.services.service;


import it.ecubit.xmpp.services.exception.ExceptionGeneric;
import it.ecubit.xmpp.services.rest.EjabberdClient;
import it.ecubit.xmpp.services.rest.entity.GetOfflineCount;
import it.ecubit.xmpp.services.rest.entity.User;
import it.ecubit.xmpp.services.rest.entity.UserCheck;
import it.ecubit.xmpp.services.rest.entity.room.CreateRoom;
import it.ecubit.xmpp.services.rest.entity.room.GetRoomOccupants;
import it.ecubit.xmpp.services.rest.entity.room.RoomOccupants;
import it.ecubit.xmpp.services.rest.wrapperEntity.NumUserConnected;
import it.ecubit.xmpp.services.rest.wrapperEntity.ResponseOfflineCount;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class EjabberdApiService {

    @Value("${xmpp.ejabberd.api}")
    String host;
    public void registerUser(User user) throws IOException, ExceptionGeneric {
        EjabberdClient.getInstance(host).registerUser(user);
    }
    public List<User> getUsers() throws IOException {
		return EjabberdClient.getInstance(host).getRegisterUsers();
    }
    public NumUserConnected getConnectedUsersNumber() throws IOException {
		return EjabberdClient.getInstance(host).getConnectedUsersNumber();
    }

    public ResponseOfflineCount getOfflineCount(GetOfflineCount getOfflineCount) throws IOException{
		return EjabberdClient.getInstance(host).getOfflineCount(getOfflineCount);
    }

    public String createRoom(CreateRoom createRoom) throws IOException {
        return EjabberdClient.getInstance(host).createRoom(createRoom);
    }

    public List<RoomOccupants> getRoomOccupants(GetRoomOccupants getRoomOccupants) throws IOException {
        return EjabberdClient.getInstance(host).getRoomOccupants(getRoomOccupants);
    }

    public String accountCheck(UserCheck userCheck) throws IOException {
        return EjabberdClient.getInstance(host).accountCheck(userCheck);
    }
}
