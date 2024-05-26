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

/*
告诉 Spring 这是一个 RESTful 风格的控制器
返回的数据都是 JSON 格式的，而非模板渲染后的 HTML
 */
@RestController
/*
映射请求路径，这里是 /api/users
到时候访问的时候就是 http://localhost:8080/api/users 开头的
 */
@RequestMapping("/api/users")
public class UserController {

    /*
    定义需要被注入的服务，这里是 UserService（即用户服务）
    注意这里仅仅是定义，具体的实例化看下面的构造函数
     */
    private final UserService userService;

    /*
    构造函数，用于实例化 UserController
    @Autowired 注解告诉 Spring 这个构造函数需要自动注入 UserService
    这样子 getUsers()，createUser() 等等方法才能正常使用 UserService
     */
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> getUsers() {
        List<UserResponse> users = userService.getUsers(); // 这里调用了 UserService 的 getUsers() 方法，返回所有的用户的基本信息
        return ResponseEntity.ok(Map.of(
                "code", HttpStatus.OK.value(),
                "data", users
        ));
    }

    @PostMapping("/")
    public ResponseEntity<Map<String, Object>> createUser(@RequestBody User user) {
        try {
            userService.createUser(user); // 这里调用了 UserService 的 createUser() 方法，创建一个新的用户
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

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody UserLoginRequest userLoginRequest) {
        UserResponse userResponse = userService.login(userLoginRequest); // 这里调用了 UserService 的 login() 方法，尝试登录，登录成功就会返回这个 user 的 UserResponse
        if (userResponse != null) {
            /*
            若登陆成功，则生成一个新的 JWT Token 并返回
             */
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
            userService.register(userRegisterRequest); // 这里调用了 UserService 的 register() 方法，注册一个新的用户
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
