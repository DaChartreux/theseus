package com.potatoxchip.api.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public record UserService(UserRepository userRepository) {

    @Autowired
    public UserService {
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User createUser(User user) {

        List<User> uu = userRepository.findByEmailOrHandleName(user.getEmail(), user.getHandleName());
        System.out.println(uu);

        if (uu.size() > 0) {
            throw new IllegalStateException("Email or Handle Name already used");
        }

        return userRepository.save(user);
    }
}
