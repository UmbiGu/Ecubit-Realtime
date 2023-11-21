package it.ecubit.xmpp.services.controller;

import it.ecubit.xmpp.services.exception.ExceptionGeneric;
import it.ecubit.xmpp.services.rest.entity.GetOfflineCount;
import it.ecubit.xmpp.services.rest.entity.User;
import it.ecubit.xmpp.services.rest.entity.room.CreateRoom;
import it.ecubit.xmpp.services.rest.wrapperEntity.NumUserConnected;
import it.ecubit.xmpp.services.rest.wrapperEntity.ResponseOfflineCount;
import it.ecubit.xmpp.services.rest.wrapperEntity.UserInfo;
import it.ecubit.xmpp.services.service.EjabberdApiService;
import it.ecubit.xmpp.services.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class UserRestApiController {

    @Autowired
    EjabberdApiService ejabberdApiService;
    @Autowired(required = true)
    UserService userService;
    @Value("${xmpp.host}")
    private String host;

    @Value("${xmpp.ejabberd.api}")
    private String ejabberApi;

    @Value("${xmpp.ejabberd.multicast}")
    private String multicast;
//Registrazione di un utente
    @CrossOrigin
    @PostMapping("/registerUser") // Map ONLY GET Requests
    public User registerUser(@RequestBody User user) throws ExceptionGeneric, IOException {
    ejabberdApiService.registerUser(user);

    return userService.addUser(user);
    }
//Lista degli utenti registrati
    @CrossOrigin
    @GetMapping("/getUsers")
    public List<User> getUsers() throws IOException {
        List<User> list = ejabberdApiService.getUsers();
        return list;
    }
//Numero degli utenti connessi
    @CrossOrigin
    @GetMapping("/getConnectedUsersNumbers")
    public NumUserConnected getConnectedUsersNumber() throws IOException {
        NumUserConnected numUserConnected = ejabberdApiService.getConnectedUsersNumber();
        return numUserConnected;
    }
//Lista degli utenti connessi
    @CrossOrigin
    @GetMapping("/getUserInfo")
    public List<UserInfo> getUserInfo() throws IOException {
        List<UserInfo> userInfoList = ejabberdApiService.getUserInfo();
        return userInfoList;
    }
    //Numero dei messaggi pendenti per quell'utente
    @CrossOrigin
    @PostMapping("/getOfflineCount")
    public ResponseOfflineCount getOfflineCount(@RequestBody GetOfflineCount user) throws IOException {
        ResponseOfflineCount responseOfflineCount = ejabberdApiService.getOfflineCount(user);
        return responseOfflineCount;
    }
    @CrossOrigin
    @PostMapping("/createRoom")
    public String createRoom(@RequestBody CreateRoom room) throws IOException {
        String response = ejabberdApiService.createRoom(room);
        return response;

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