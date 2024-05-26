package dev.e23.edge.initializer;

import dev.e23.edge.model.User;
import dev.e23.edge.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
/*
CommandLineRunner 接口是 Spring Boot 提供的一个接口，用于在应用启动后执行一些初始化操作
这里我们实现了这个接口，用于在应用启动后初始化一个用户数据（如果数据库中没有用户数据的话）
 */
public class UserInitializer implements CommandLineRunner {

    private final UserService userService;

    private static final User admin = new User("admin", "admin", "admin@admin.com", "123456");

    @Autowired
    public UserInitializer(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        if (this.userService.getUsers().isEmpty()) {
            userService.createUser(UserInitializer.admin);
        }
    }
}
