package org.example.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    // JWT 密钥（从配置文件读取）
    private static String SECRET_KEY;

    // Token 有效期（默认24小时）
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24;

    // Token 签发者
    private static final String ISSUER = "shuye-system";

    // 算法实例
    private static Algorithm algorithm;

    /**
     * 设置密钥（从配置文件注入）
     */
    @Value("${jwt.secret:default-jwt-secret-key-for-development}")
    public void setSecretKey(String secretKey) {
        SECRET_KEY = secretKey;
        algorithm = Algorithm.HMAC256(SECRET_KEY);
    }

    /**
     * 生成 JWT Token（String类型 userId）
     */
    public static String generateToken(String userId) {
        return generateToken(userId, EXPIRATION_TIME);
    }

    /**
     * 生成 JWT Token（Long类型 userId）
     */
    public static String generateToken(Long userId) {
        return generateToken(String.valueOf(userId));
    }

    /**
     * 核心 Token 生成方法
     */
    private static String generateToken(String userId, long expirationTime) {
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + expirationTime);

        return JWT.create()
                .withIssuer(ISSUER)
                .withSubject(userId)
                .withIssuedAt(now)
                .withExpiresAt(expireDate)
                .sign(algorithm);
    }

    /**
     * 验证并解析 Token
     */
    public static DecodedJWT verifyToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build();
            return verifier.verify(token);
        } catch (JWTVerificationException e) {
            throw new RuntimeException("Token验证失败: " + e.getMessage());
        }
    }

    /**
     * 验证 Token 是否有效
     */
    public static boolean isValidToken(String token) {
        try {
            verifyToken(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 从 Token 中获取用户ID（String类型）
     */
    public static String getUserIdFromToken(String token) {
        DecodedJWT decodedJWT = verifyToken(token);
        return decodedJWT.getSubject();
    }

    /**
     * 从 Token 中获取用户ID（Long类型）
     */
    public static Long getUserIdLongFromToken(String token) {
        try {
            String userIdStr = getUserIdFromToken(token);
            return Long.parseLong(userIdStr);
        } catch (NumberFormatException e) {
            throw new RuntimeException("用户ID格式错误: " + e.getMessage());
        }
    }

    /**
     * 检查 Token 是否过期
     */
    public static boolean isTokenExpired(String token) {
        try {
            DecodedJWT decodedJWT = verifyToken(token);
            return decodedJWT.getExpiresAt().before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * 获取 Token 过期时间
     */
    public static Date getExpirationDate(String token) {
        try {
            DecodedJWT decodedJWT = verifyToken(token);
            return decodedJWT.getExpiresAt();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取 Token 签发时间
     */
    public static Date getIssuedAtDate(String token) {
        try {
            DecodedJWT decodedJWT = verifyToken(token);
            return decodedJWT.getIssuedAt();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 刷新 Token（重新生成）
     */
    public static String refreshToken(String oldToken) {
        try {
            String userId = getUserIdFromToken(oldToken);
            return generateToken(userId);
        } catch (Exception e) {
            throw new RuntimeException("刷新Token失败: " + e.getMessage());
        }
    }

    /**
     * 验证用户ID与Token是否匹配
     */
    public static boolean isTokenForUser(String token, Long userId) {
        try {
            Long tokenUserId = getUserIdLongFromToken(token);
            return tokenUserId.equals(userId);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 验证用户ID与Token是否匹配（String版本）
     */
    public static boolean isTokenForUser(String token, String userId) {
        try {
            String tokenUserId = getUserIdFromToken(token);
            return tokenUserId.equals(userId);
        } catch (Exception e) {
            return false;
        }
    }
}