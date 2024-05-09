package dev.e23.edge.model.request;

import lombok.Data;

@Data
public class UserRegisterRequest {
    private String username;
    private String nickname;
    private String email;
    private String password;
}
