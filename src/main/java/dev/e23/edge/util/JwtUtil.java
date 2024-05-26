package dev.e23.edge.util;

import java.util.Calendar;
import java.util.Map;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;

public class JwtUtil {
    @Value("${jwt.secret}")
    private static final String SecretKey = "fake_secret_key"; // 从配置文件中读取密钥

    public static String getToken(Map<String,String> map){
        JWTCreator.Builder builder = JWT.create();
        map.forEach(builder::withClaim); // 遍历传入的 Payload 数据，并添加到 Builder 中
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.MINUTE,90); // 设置过期时间为 90 分钟
        builder.withExpiresAt(instance.getTime());
        return builder.sign(Algorithm.HMAC256(SecretKey)); // 使用 HMAC256 算法生成签名，作为 Token 返回
    }

    // 验证 Token 是否合法以及是否过期
    public static void verify(String token){
        // 创建一个 JWTVerifier 实例，使用相同的密钥构建
        JWT.require(Algorithm.HMAC256(SecretKey)).build().verify(token);
    }

    // 从 Token 中解析数据
    public static DecodedJWT getToken(String token){
        return JWT.require(Algorithm.HMAC256(SecretKey)).build().verify(token);
    }
}
