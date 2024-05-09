package dev.e23.edge.model.response;

import dev.e23.edge.model.User;
import lombok.Data;

@Data
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
