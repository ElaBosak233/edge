package dev.e23.edge.controller;

import dev.e23.edge.model.User;
import dev.e23.edge.model.request.UserRegisterRequest;
import dev.e23.edge.model.response.UserResponse;
import dev.e23.edge.model.request.UserLoginRequest;
import dev.e23.edge.service.UserService;
import dev.e23.edge.util.JwtUtil;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
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
                "code", HttpStatus.OK.value(),
                "data", users
        ));
    }

    @PostMapping("/")
    public ResponseEntity<Map<String, Object>> createUser(@RequestBody User user) {
        try {
            userService.createUser(user);
            return ResponseEntity.ok(Map.of(
                    "code", HttpStatus.OK.value(),
                    "msg", "user created successfully"
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "code", HttpStatus.BAD_REQUEST.value(),
                    "msg", e.getMessage()
            ));
        }
    }

    @PutMapping("/")
    public ResponseEntity<Map<String, Object>> updateUser() {
        return ResponseEntity.ok(Map.of(
                "code", HttpStatus.OK.value(),
                "msg", userService.updateUser()
        ));
    }

    @DeleteMapping("/")
    public String deleteUser() {
        return "User deleted";
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody UserLoginRequest userLoginRequest) {
        UserResponse userResponse = userService.login(userLoginRequest);
        if (userResponse != null) {
            Map<String, String> payload = new HashMap<>();
            payload.put("id", String.valueOf(userResponse.getId()));
            payload.put("username", userResponse.getUsername());
            String jwtToken = JwtUtil.getToken(payload);
            return ResponseEntity.ok(Map.of(
                    "code", HttpStatus.OK.value(),
                    "msg", "login successful",
                    "data", userResponse,
                    "token", jwtToken
            ));
        }
        return ResponseEntity.badRequest().body(Map.of(
                "code", HttpStatus.BAD_REQUEST.value(),
                "msg", "login failed"
        ));
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody UserRegisterRequest userRegisterRequest) {
        try {
            userService.register(userRegisterRequest);
            return ResponseEntity.ok(Map.of(
                    "code", HttpStatus.OK.value(),
                    "msg", "register successful"
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "code", HttpStatus.BAD_REQUEST.value(),
                    "msg", e.getMessage()
            ));
        }
    }
}
