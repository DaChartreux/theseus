package com.potatoxchip.api.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public record UserService(UserRepository userRepository, UserElasticRepository userElasticRepository) {

    @Autowired
    public UserService {
    }

    public List<UserModel> getUsers() {
        Iterable<UserModel> userIterable = userElasticRepository.findAll();


        List<UserModel> userList = new ArrayList<>();
        userIterable.forEach(userList::add);

        return userList;
    }

    public User createUser(User user) {
        List<User> userList = userRepository.findByEmailOrHandleName(user.getEmail(), user.getHandleName());

        if (userList.size() > 0) {
            throw new IllegalStateException("Email or Handle Name already used");
        }

        return userRepository.save(user);
    }

    public User updateUser(Long userId, User user) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isEmpty()) {
            throw new IllegalStateException("Invalid ID");
        }

        User existingUser = optionalUser.get();

        if (user.getEmail() != null) {
            Optional<User> userByEmail = userRepository.findByEmail(user.getEmail());

            if (userByEmail.isPresent() && !Objects.equals(userByEmail.get().getId(), existingUser.getId())) {
                throw new IllegalStateException("Email ID already used");
            }

            existingUser.setEmail(user.getEmail());
        }

        if (user.getHandleName() != null) {
            Optional<User> userByHandleName = userRepository.findByHandleName(user.getHandleName());

            if (userByHandleName.isPresent() && !Objects.equals(userByHandleName.get().getId(), existingUser.getId())) {
                throw new IllegalStateException("Handle name already used");
            }

            existingUser.setHandleName(user.getHandleName());
        }

        if (user.getUserName() != null) {
            existingUser.setUserName(user.getUserName());
        }

        userRepository.save(existingUser);

        return existingUser;
    }

    public User getUser(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isEmpty()) {
            throw new IllegalStateException("Invalid ID");
        }

        return optionalUser.get();
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
