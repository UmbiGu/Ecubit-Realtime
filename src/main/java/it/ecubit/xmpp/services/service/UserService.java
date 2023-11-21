package it.ecubit.xmpp.services.service;

import it.ecubit.xmpp.services.repository.UserRepo;
import it.ecubit.xmpp.services.rest.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired(required = true)
    UserRepo userRepo;

    public User addUser(User user){
       return userRepo.save(user);
    }
}
