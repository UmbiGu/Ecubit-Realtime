package it.ecubit.xmpp.services.controller;

import it.ecubit.xmpp.services.exception.BadRequestException;
import it.ecubit.xmpp.services.rest.entity.*;
import it.ecubit.xmpp.services.rest.entity.room.CreateRoom;
import it.ecubit.xmpp.services.rest.entity.room.GetRoomOccupants;
import it.ecubit.xmpp.services.rest.entity.room.RoomOccupants;
import it.ecubit.xmpp.services.rest.wrapperEntity.*;
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

    // Registrazione di un utente
    @CrossOrigin
    @PostMapping("/registerUser")
    public User registerUser(@RequestBody User user) throws BadRequestException, IOException {
        ejabberdApiService.registerUser(user);
        return userService.addUser(user);
    }

    // Lista degli utenti registrati
    @CrossOrigin
    @GetMapping("/getUsers")
    public List<User> getUsers() throws IOException {
        return ejabberdApiService.getUsers();
    }

    // Numero degli utenti connessi
    @CrossOrigin
    @GetMapping("/getConnectedUsersNumbers")
    public NumUserConnected getConnectedUsersNumber() throws IOException {
        return ejabberdApiService.getConnectedUsersNumber();
    }

    // Numero dei messaggi pendenti per quell'utente
    @CrossOrigin
    @PostMapping("/getOfflineCount")
    public ResponseOfflineCount getOfflineCount(@RequestBody GetOfflineCount getOfflineCount) throws BadRequestException, IOException{
        return ejabberdApiService.getOfflineCount(getOfflineCount);
    }

    // Creazione stanza
    @CrossOrigin
    @PostMapping("/createRoom")
    public String createRoom(@RequestBody CreateRoom createRoom) throws BadRequestException, IOException{
        return ejabberdApiService.createRoom(createRoom);
    }

    // Lista occupanti stanza
    @CrossOrigin
    @PostMapping("/getRoomOccupants")
    public List<RoomOccupants> getRoomOccupants(@RequestBody GetRoomOccupants getRoomOccupants) throws BadRequestException, IOException{
        return ejabberdApiService.getRoomOccupants(getRoomOccupants);
    }

    // Controllo dati account
    @CrossOrigin
    @PostMapping("/check_account")
    public String accountCheck(@RequestBody UserCheck userCheck) throws BadRequestException, IOException{
        return ejabberdApiService.accountCheck(userCheck);
    }

    // Controllo password
    @CrossOrigin
    @PostMapping("/check_password")
    public String passwordCheck(@RequestBody User user) throws BadRequestException, IOException{
        return ejabberdApiService.passwordCheck(user);
    }

    // Unban utente
    @CrossOrigin
    @PostMapping("/unban_ip")
    public UnbanWrap unbanIp(@RequestBody UnbanIp unbanIp) throws BadRequestException, IOException{
        return ejabberdApiService.unbanIp(unbanIp);
    }

    // Utenti connessi
    @CrossOrigin
    @GetMapping("/getConnectedUsers")
    public List<UserInfo> getConnectedUsers() throws BadRequestException, IOException{
        return ejabberdApiService.getConnectedUsers();
    }

    // Ban Utente
    @CrossOrigin
    @PostMapping("/ban_user")
    public String banUser(@RequestBody BanUser banUser) throws BadRequestException, IOException{
        return ejabberdApiService.banUser(banUser);
    }

    // Timeout account utente
    @CrossOrigin
    @PostMapping("/delete_old_users")
    public String deleteOldUsers(@RequestBody DeleteOldUsers deleteOldUsers) throws BadRequestException, IOException{
        return ejabberdApiService.deleteOldUsers(deleteOldUsers);
    }

    // Ultima attività utente
    @CrossOrigin
    @PostMapping("/get_last")
    public GetLastActivity getLastActivity(@RequestBody GetLast getLast) throws BadRequestException, IOException{
        return ejabberdApiService.getLastActivity(getLast);
    }

    //Unregister User
    @CrossOrigin
    @PostMapping("/unregister")
    public String unregisterUser(@RequestBody Unregister unregister) throws BadRequestException, IOException{
        return ejabberdApiService.unregisterUser(unregister);
    }

    //Change Password User
    @CrossOrigin
    @PostMapping("/change_password")
    public String changePasswordUser(@RequestBody ChangePasswordUser changePasswordUser) throws BadRequestException, IOException{
        return ejabberdApiService.changePasswordUser(changePasswordUser);
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

