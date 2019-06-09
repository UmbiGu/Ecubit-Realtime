package it.almaviva.nerds.services.controller;

import de.gultsch.ejabberd.api.EjabberdApi;
import de.gultsch.ejabberd.api.hla.Ejabberd;
import it.almaviva.nerds.services.model.Broadcast;
import it.almaviva.nerds.services.model.Multicast;
import it.almaviva.nerds.services.model.User;
import it.almaviva.nerds.services.repository.UserRepository;
import it.almaviva.nerds.services.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
public class UserRestServiceController {

    @Autowired
    private UserRepository userRepository;

    @Value("${nerds.host}")
    private String host;

    @Value("${nerds.ejabberd.api}")
    private String ejabberApi;

    @Value("${nerds.ejabberd.multicast}")
    private String multicast;

    @CrossOrigin
    @GetMapping(path = "/users/attesta/{gruppo}/{name}") // Map ONLY GET Requests
    public @ResponseBody
    String attesta(@PathVariable String gruppo, @PathVariable String name) {

        try {

            Ejabberd client = new Ejabberd(new EjabberdApi(ejabberApi));
            boolean result = client.addUserToSharedRosterGroup(
                    name, host,
                    gruppo, host);

            Collection<String> group = client.getSharedRosterGroupMembers(host, gruppo);
            String stanza = Util.Message.multicastStanza(
                    "ATTESTA_ACTION",
                    gruppo, group);
            boolean response = client.sendStanza("admin@nerds.almaviva.it", multicast, stanza);

            if (result)
                return "0";
            else
                return "1";

        } catch (Exception e) {
            return "Exception" + e.getMessage();
        }
    }

    @CrossOrigin
    @GetMapping(path = "/users/deattesta/{gruppo}/{name}") // Map ONLY GET Requests
    public @ResponseBody
    String deattesta(@PathVariable String gruppo, @PathVariable String name) {

        try {

            Ejabberd client = new Ejabberd(new EjabberdApi("http://nerds.almaviva.it/api/"));
            boolean result = client.removeUserFromSharedRosterGroup(
                    name, host,
                    gruppo, host);
            Collection<String> group = client.getSharedRosterGroupMembers(host, gruppo);
            String stanza = Util.Message.multicastStanza(
                    "DEATTESTA_ACTION",
                    gruppo, group);
            boolean response = client.sendStanza("admin@nerds.almaviva.it", multicast, stanza);

            if (result)
                return "0";
            else
                return "1";

        } catch (Exception e) {
            return "Exception" + e.getMessage();
        }
    }

    @CrossOrigin
    @PostMapping(path = "/users/multicast") // Map ONLY GET Requests
    public @ResponseBody
    String sendMulticast(@RequestBody Multicast message) {
        try {

            String stanza = Util.Message.multicastStanza(message);
            Ejabberd client = new Ejabberd(new EjabberdApi(ejabberApi));
            boolean response = client.sendStanza(message.getFrom(), multicast, stanza);

            if (response)
                return "0";
            else
                return "1";

        } catch (Exception e) {
            return "Exception" + e.getMessage();
        }
    }

    @CrossOrigin
    @PostMapping(path = "/users/broadcast") // Map ONLY GET Requests
    public @ResponseBody
    String sendBroadcast(@RequestBody Broadcast message) {
        try {
            Ejabberd client = new Ejabberd(new EjabberdApi(ejabberApi));
            Collection<String> users = client.getRegisteredUsers(host);
            String stanza = Util.Message.multicastStanzaAddress(host,
                    message.getSubject(),
                    message.getBody(), users);
            boolean response = client.sendStanza(message.getFrom(), multicast , stanza);

            if (response)
                return "0";
            else
                return "1";

        } catch (Exception e) {
            return "Exception" + e.getMessage();
        }
    }

    @PostMapping(path = "/users/add") // Map ONLY GET Requests
    public @ResponseBody
    Long addNewUser(@RequestBody User utente) {
        userRepository.save(utente);
        return utente.getId();
    }

    @GetMapping(path = "/users/all")
    public @ResponseBody
    Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping(path = "/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable Integer id) throws UserException {
        Optional<User> user = userRepository.findById(id);

        if (!user.isPresent()) {
            throw new UserException("User does not exist");
        }
        return new ResponseEntity<>(user.get(), HttpStatus.OK);
    }

    @GetMapping(path = "/users/name/{name}")
    public ResponseEntity<User> getUserByName(@PathVariable String name) throws UserException {
        Iterable<User> userList = userRepository.findAll();
        for (User user : userList) {
            if (user.getUsername().equals(name)) {
                return new ResponseEntity<>(user, HttpStatus.OK);
            }
        }
        throw new UserException("User does not exist");
    }

    @DeleteMapping(path = "/users/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Integer id) throws UserException {
        Optional<User> user = userRepository.findById(id);

        if (!user.isPresent()) {
            throw new UserException("User does not exist");
        }
        userRepository.delete(user.get());
        return new ResponseEntity<>(user.get(), HttpStatus.OK);
    }
}
