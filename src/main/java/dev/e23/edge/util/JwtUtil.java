package dev.e23.edge.util;

import java.util.Calendar;
import java.util.Map;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JwtUtil {
    private static String TOKEN = "token!Q@W3e4r"; // 定义密钥

    /**
     * 生成 JWT 令牌
     * @param map 传入的 Payload 数据
     * @return 返回生成的令牌
     */
    public static String getToken(Map<String,String> map){
        JWTCreator.Builder builder = JWT.create();

        // 遍历传入的 Payload 数据，并添加到 Builder 中
        map.forEach(builder::withClaim);

        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.MINUTE,90);

        // 设置过期时间为 7 秒后
        builder.withExpiresAt(instance.getTime());

        // 使用 HMAC256 签名算法进行签名，并返回令牌字符串
        return builder.sign(Algorithm.HMAC256(TOKEN));
    }

    /**
     * 验证 JWT 令牌
     * @param token 要验证的令牌字符串
     */
    public static void verify(String token){
        // 创建一个 JWTVerifier 实例，使用相同的密钥构建
        JWT.require(Algorithm.HMAC256(TOKEN)).build().verify(token);
    }

    /**
     * 获取令牌中的 Payload 数据
     * @param token 要解析的令牌字符串
     * @return 解码后的令牌对象（DecodedJWT）
     */
    public static DecodedJWT getToken(String token){
        // 创建一个 JWTVerifier 实例，使用相同的密钥构建，并对令牌进行验证和解码
        return JWT.require(Algorithm.HMAC256(TOKEN)).build().verify(token);
    }
}
