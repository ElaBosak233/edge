package dev.e23.edge.service;

import dev.e23.edge.model.response.UserResponse;
import dev.e23.edge.model.User;
import dev.e23.edge.model.request.UserLoginRequest;
import dev.e23.edge.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public UserService(BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
    }

    public List<UserResponse> getUsers() {
        List<UserResponse> users = new ArrayList<>();
        List<User> userData = userRepository.findAll();
        for (User user : userData) {
            users.add(new UserResponse(user));
        }
        return users;
    }

    public void createUser(User user) throws Exception {
        String password = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(password);
        userRepository.save(user);
    }

    public Boolean login(UserLoginRequest userLoginRequest) {
        User user = userRepository.findByUsername(userLoginRequest.getUsername());
        return bCryptPasswordEncoder.matches(userLoginRequest.getPassword(), user.getPassword());
    }

    public String updateUser() {
        return "User updated";
    }
}
