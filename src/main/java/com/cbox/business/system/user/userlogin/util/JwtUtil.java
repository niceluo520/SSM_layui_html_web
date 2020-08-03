package com.cbox.business.system.user.userlogin.util;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * @ClassName: JwtUtil
 * @Function:
 * 
 * @author cbox
 * @date 2020年3月10日 上午10:02:37
 * @version 1.0
 */
@Service
public class JwtUtil {

    @Value("${jwt.config.key:jdi$#sd}")
    private String key;// token加密的秘钥，可以通过配置文件配置。

    @Value("${jwt.config.ttl:3600000}")
    private long ttl;// token的有效时间，可以通过配置文件配置。单位：毫秒。

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getTtl() {
        return ttl;
    }

    public void setTtl(long ttl) {
        this.ttl = ttl;
    }

    /**
     * 生成JWT
     *
     * @param id
     * @param subject
     * @return
     */
    public String createJWT(String id, String subject, String roles) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        JwtBuilder builder = Jwts.builder().setId(id).setSubject(subject).setIssuedAt(now).signWith(SignatureAlgorithm.HS256, key).claim("roles", roles);
        if (ttl > 0) {
            builder.setExpiration(new Date(nowMillis + ttl));
        }
        return builder.compact();
    }

    /**
     * 解析JWT
     * 
     * @param jwtStr
     * @return
     */
    public Claims parseJWT(String jwtStr) {
        return Jwts.parser().setSigningKey(key).parseClaimsJws(jwtStr).getBody();
    }
}
