package dev.e23.edge.model.request;

import lombok.Data;

@Data
public class UserLoginRequest {
    private String username;
    private String password;
}
