package com.shen.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

/**
 * Jwt工具类
 *
 * @author Shen
 * @date 2022/12/18 9:03
 */
public class JwtUtil {
    //有效期为
    public static final long JWT_TTL = 60 * 60 * 1000L; //一个小时

    //设置秘钥明文
    public static final String JWT_KEY = "shen";

    /**
     * 使用UUID生成token
     *
     * @return token
     */
    public static String getUUID(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }


    public static String createJWT(String id, String subject, Long ttlMills){
        JwtBuilder builder = getJwtBuilder(subject, ttlMills, id); //设置过期时间
        return builder.compact();
    }

    /**
     * 生成jwt，手动设置过期时间
     *
     * @param subject token中要存放的数据(json格式)
     * @param ttlMills token超时时间
     * @return
     */
    public static String createJWT(String subject, Long ttlMills){
        JwtBuilder builder = getJwtBuilder(subject, ttlMills, getUUID()); //设置过期时间
        return builder.compact();
    }

    /**
     * 生成jwt,使用默认过期时间
     *
     * @param subject token中要存放的数据(json格式)
     * @return
     */
    public static String createJWT(String subject){
        JwtBuilder builder = getJwtBuilder(subject, null, getUUID()); //设置过期时间
        return builder.compact();
    }


    private static JwtBuilder getJwtBuilder(String subject, Long ttlMills, String uuid){
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;  //签名的加密算法
        SecretKey secretKey = generalKey();
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        //如果没有提供ttl，则使用默认ttl
        if(ttlMills == null){
            ttlMills = JwtUtil.JWT_TTL;
        }
        long expireMills = nowMillis + ttlMills;
        Date expireDate = new Date(expireMills);
        return Jwts.builder()
                .setId(uuid)                                //唯一的ID
                .setSubject(subject)                        //主体，可以是json数据
                .setIssuer("shen")                          //签发者
                .setIssuedAt(now)                           //签发时间
                .signWith(signatureAlgorithm, secretKey)    //使用HS256对称加密算法加密签名，第二个参数为秘钥
                .setExpiration(expireDate);                 //设置过期时间
    }

    /**
     * 生成加密后的秘钥secretKey
     *
     * @return
     */
    public static SecretKey generalKey(){
        byte[] encodedKey = Base64.getDecoder().decode(JwtUtil.JWT_KEY);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    /**
     * 解析
     *
     * @param jwt
     * @return
     * @throws Exception
     */
    public static Claims parseJWT(String jwt) throws Exception{
        SecretKey secretKey = generalKey();
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody();
    }


    public static void main(String[] args) throws Exception {
//        String data = JSON.toJSONString(new User(1, "shc123"));
//        String jwt = createJWT(data);
//        System.out.println(jwt);
//        eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI0ODA4OGE4MmYyNzM0ODQyYjEwMGY3YmRlNzVkYmFjZSIsInN1YiI6IntcInBhc3N3b3JkXCI6XCJzaGMxMjNcIixcInVpZFwiOjF9IiwiaXNzIjoic2hlbiIsImlhdCI6MTY3MTMyNjk5MSwiZXhwIjoxNjcxMzMwNTkxfQ.Sm4xek6QkVCgiornsBkCe9nSaqCP3bJNW6tU-rt1UrY
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI0ODA4OGE4MmYyNzM0ODQyYjEwMGY3YmRlNzVkYmFjZSIsInN1YiI6IntcInBhc3N3b3JkXCI6XCJzaGMxMjNcIixcInVpZFwiOjF9IiwiaXNzIjoic2hlbiIsImlhdCI6MTY3MTMyNjk5MSwiZXhwIjoxNjcxMzMwNTkxfQ.Sm4xek6QkVCgiornsBkCe9nSaqCP3bJNW6tU-rt1UrY";
        Claims claims = parseJWT(token);
        System.out.println(claims);
    }
}
