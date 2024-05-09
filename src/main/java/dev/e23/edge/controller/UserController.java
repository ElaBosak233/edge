package dev.e23.edge.controller;

import dev.e23.edge.model.User;
import dev.e23.edge.model.request.UserRegisterRequest;
import dev.e23.edge.model.response.UserResponse;
import dev.e23.edge.model.request.UserLoginRequest;
import dev.e23.edge.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> getUsers() {
        List<UserResponse> users = userService.getUsers();
        return ResponseEntity.ok(Map.of(
                "code", 200,
                "data", users
        ));
    }

    @PostMapping("/")
    public ResponseEntity<Map<String, Object>> createUser(@RequestBody User user) {
        try {
            userService.createUser(user);
            return ResponseEntity.ok(Map.of(
                    "code", 200,
                    "msg", "user created successfully"
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "code", 400,
                    "msg", e.getMessage()
            ));
        }
    }

    @PutMapping("/")
    public String updateUser() {
        return "User updated";
    }

    @DeleteMapping("/")
    public String deleteUser() {
        return "User deleted";
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody UserLoginRequest userLoginRequest) {
        Boolean success = userService.login(userLoginRequest);
        if (success) {
            return ResponseEntity.ok(Map.of(
                    "code", 200,
                    "msg", "login successful"
            ));
        }
        return ResponseEntity.badRequest().body(Map.of(
                "code", 400,
                "msg", "login failed"
        ));
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody UserRegisterRequest userRegisterRequest) {
        return ResponseEntity.ok(Map.of(
                "code", 200,
                "msg", "11"
        ));
    }
}
