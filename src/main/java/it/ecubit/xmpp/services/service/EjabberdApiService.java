package it.ecubit.xmpp.services.service;


import it.ecubit.xmpp.services.exception.ExceptionGeneric;
import it.ecubit.xmpp.services.rest.EjabberdClient;
import it.ecubit.xmpp.services.rest.entity.GetOfflineCount;
import it.ecubit.xmpp.services.rest.entity.User;
import it.ecubit.xmpp.services.rest.entity.room.CreateRoom;
import it.ecubit.xmpp.services.rest.wrapperEntity.NumUserConnected;
import it.ecubit.xmpp.services.rest.wrapperEntity.ResponseOfflineCount;
import it.ecubit.xmpp.services.rest.wrapperEntity.UserInfo;
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
       List<User> list = EjabberdClient.getInstance(host).getRegisterUsers();
        return list;
    }
    public NumUserConnected getConnectedUsersNumber() throws IOException {
        NumUserConnected numUserConnected = EjabberdClient.getInstance(host).getConnectedUsersNumber();
        return numUserConnected;
    }
    public List<UserInfo> getUserInfo() throws IOException {
        List<UserInfo> userInfoList = EjabberdClient.getInstance(host).connectedUsers();
        return userInfoList;
    }
    public ResponseOfflineCount getOfflineCount(GetOfflineCount user) throws IOException {
        ResponseOfflineCount responseOfflineCount = EjabberdClient.getInstance(host).getOfflineCount(user);
        return responseOfflineCount;
    }
    public String createRoom(CreateRoom room) throws IOException {
        String response = EjabberdClient.getInstance(host).createRoom(room);
        return response;
    }
}
