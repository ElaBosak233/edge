package dev.e23.edge.model.request;

import lombok.Data;

@Data // Lombok 注解，自动生成 getter()，setter()，toString() 等方法
/*
用户登录请求类，用于接收用户登录请求（即 Controller 层的参数）
 */
public class UserLoginRequest {
    private String username;
    private String password;
}
