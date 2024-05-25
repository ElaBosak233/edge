package dev.e23.edge.initializer;

import dev.e23.edge.model.User;
import dev.e23.edge.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
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
