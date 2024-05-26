package dev.e23.edge.model.response;

import dev.e23.edge.model.User;
import lombok.Data;

@Data // Lombok 注解，自动生成 getter()，setter()，toString() 等方法
/*
用户响应类，用于返回用户信息，通常在传输数据时使用（即 Controller 层返回值）
进入数据库的数据是 User 类型，而不是 UserResponse 类型
 */
public class UserResponse {
    private int id;
    private String username;
    private String nickname;
    private String email;

    public UserResponse(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
    }
}
