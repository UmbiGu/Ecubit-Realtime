package it.ecubit.xmpp.services.controller;

import it.ecubit.xmpp.services.exception.ExceptionGeneric;
import it.ecubit.xmpp.services.rest.entity.GetOfflineCount;
import it.ecubit.xmpp.services.rest.entity.User;
import it.ecubit.xmpp.services.rest.entity.UserCheck;
import it.ecubit.xmpp.services.rest.entity.room.CreateRoom;
import it.ecubit.xmpp.services.rest.entity.room.GetRoomOccupants;
import it.ecubit.xmpp.services.rest.entity.room.RoomOccupants;
import it.ecubit.xmpp.services.rest.wrapperEntity.NumUserConnected;
import it.ecubit.xmpp.services.rest.wrapperEntity.ResponseOfflineCount;
import it.ecubit.xmpp.services.service.EjabberdApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import retrofit2.http.Body;
import retrofit2.http.GET;

import java.io.IOException;
import java.util.List;

@RestController
public class UserRestServiceController {

    @Autowired
    EjabberdApiService ejabberdApiService;
    @Value("${xmpp.host}")
    private String host;

    @Value("${xmpp.ejabberd.api}")
    private String ejabberApi;

    @Value("${xmpp.ejabberd.multicast}")
    private String multicast;

    @CrossOrigin
    @PostMapping("/registerUser") // Map ONLY GET Requests
    public User registerUser(@RequestBody User user) throws ExceptionGeneric, IOException {
        ejabberdApiService.registerUser(user);
        return user;
    }

    @CrossOrigin
    @GetMapping("/getUsers")
    public List<User> getUsers() throws IOException {
        List<User> list = ejabberdApiService.getUsers();
        return list;
    }

    @CrossOrigin
    @GetMapping("/getConnectedUsersNumbers")
    public NumUserConnected getConnectedUsersNumber() throws IOException {
        NumUserConnected numUserConnected = ejabberdApiService.getConnectedUsersNumber();
        return numUserConnected;
    }

    @CrossOrigin
    @PostMapping("/getOfflineCount")
    public ResponseOfflineCount getOfflineCount(@RequestBody GetOfflineCount getOfflineCount) throws ExceptionGeneric, IOException{
        return ejabberdApiService.getOfflineCount(getOfflineCount);
    }

    @CrossOrigin
    @PostMapping("/createRoom")
    public String createRoom(@RequestBody CreateRoom createRoom) throws ExceptionGeneric, IOException{
        return ejabberdApiService.createRoom(createRoom);
    }

    @CrossOrigin
    @PostMapping("/getRoomOccupants")
    public List<RoomOccupants> getRoomOccupants(@RequestBody GetRoomOccupants getRoomOccupants) throws ExceptionGeneric, IOException{
        return ejabberdApiService.getRoomOccupants(getRoomOccupants);
    }

    @CrossOrigin
    @PostMapping("/check_account")
    public String accountCheck(@RequestBody UserCheck userCheck) throws ExceptionGeneric, IOException{
        return ejabberdApiService.accountCheck(userCheck);
    }

    @CrossOrigin
    @PostMapping("/check_password")
    public String passwordCheck(@RequestBody User user) throws ExceptionGeneric, IOException{
        return ejabberdApiService.passwordCheck(user);
    }


	
//    @CrossOrigin
//    @PostMapping(path = "/users/broadcast") // Map ONLY GET Requests
//    public @ResponseBody
//    String sendBroadcast(@RequestBody Broadcast message) {
//        try {
//            Ejabberd client = new Ejabberd(new EjabberdApi(ejabberApi));
//            Collection<String> users = client.getRegisteredUsers(host);
//            String stanza = Util.Message.multicastStanzaAddress(host,
//                    message.getSubject(),
//                    message.getBody(), users);
//            boolean response = client.sendStanza(message.getFrom(), multicast , stanza);
//
//            if (response)
//                return "0";
//            else
//                return "1";
//
//        } catch (Exception e) {
//            return "Exception" + e.getMessage();
//        }
//    }

//@CrossOrigin
//@PostMapping(path = "/users/multicast") // Map ONLY GET Requests
//public @ResponseBody
//String sendMulticast(@RequestBody Multicast message) {
//    try {
//
//        String stanza = Util.Message.multicastStanza(message);
//        Ejabberd client = new Ejabberd(new EjabberdApi(ejabberApi));
//        boolean response = client.sendStanza(message.getFrom(), multicast, stanza);
//
//        if (response)
//            return "0";
//        else
//            return "1";
//
//    } catch (Exception e) {
//        return "Exception" + e.getMessage();
//    }
}

