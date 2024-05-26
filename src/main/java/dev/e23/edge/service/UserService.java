package dev.e23.edge.service;

import dev.e23.edge.model.request.UserRegisterRequest;
import dev.e23.edge.model.response.UserResponse;
import dev.e23.edge.model.User;
import dev.e23.edge.model.request.UserLoginRequest;
import dev.e23.edge.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service // 告诉 Spring 这是一个服务组件
public class UserService {

    /*
    定义需要被注入的 BCryptPasswordEncoder，UserRepository，即用户数据访问组件
    注意这里仅仅是定义，具体的实例化看下面的构造函数
     */
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;

    /*
    构造函数，用于实例化 UserService
    @Autowired 注解告诉 Spring 这个构造函数需要自动注入 BCryptPasswordEncoder，UserRepository
     */
    @Autowired
    public UserService(BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
    }

    public List<UserResponse> getUsers() {
        List<UserResponse> userResponses = new ArrayList<>(); // 创建一个 ArrayList 用于存放用户的基本信息
        List<User> users = userRepository.findAll(); // 调用 UserRepository 的 findAll() 方法，返回所有的用户
        for (User user : users) { // 遍历所有的用户
            userResponses.add(new UserResponse(user)); // 将用户的基本信息添加到 ArrayList 中
        }
        return userResponses;
    }

    // 注意，按照道理来讲，这是一个管理员方法，不应该暴露给普通用户，普通用户创建用户应该被引导至 register() 方法
    public void createUser(User user) throws Exception {
        String password = bCryptPasswordEncoder.encode(user.getPassword()); // 对用户的密码进行加密
        user.setPassword(password); // 将加密后的密码设置到用户对象中
        userRepository.save(user); // 调用 UserRepository 的 save() 方法，创建一个新的用户
    }

    public UserResponse login(UserLoginRequest userLoginRequest) {
        User user = userRepository.findByUsername(userLoginRequest.getUsername()); // 调用 UserRepository 的 findByUsername() 方法，返回指定用户名的用户
        if (user != null && bCryptPasswordEncoder.matches(userLoginRequest.getPassword(), user.getPassword())) { // 判断用户是否存在且密码是否正确
            return new UserResponse(user); // 若正确，返回用户的基本信息
        }
        return null;
    }

    public void register(UserRegisterRequest userRegisterRequest) {
        User user = new User(
                userRegisterRequest.getUsername(),
                userRegisterRequest.getNickname(),
                userRegisterRequest.getEmail(),
                userRegisterRequest.getPassword()
        ); // 创建一个新的用户对象
        String password = bCryptPasswordEncoder.encode(user.getPassword()); // 对用户的密码进行加密
        user.setPassword(password); // 将加密后的密码设置到用户对象中
        userRepository.save(user); // 调用 UserRepository 的 save() 方法，创建一个新的用户
    }
}
