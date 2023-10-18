package com.filemanagement.security;

import com.filemanagement.utils.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractTokenProvider {

    public Map<String, Object> claims = new HashMap<>();

    @Value("${jwt.token.secret}")
    public String secret;

    @Value("${jwt.expire.sec}")
    public Long accessTokenExpiration;

    @Value("${jwt.refreshTokenExpire.sec}")
    public Long refreshTokenExpiration;

    public Claims getClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    public String getUsernameFromToken(String token) {
        final Claims claims = getClaimsFromToken(token);
        return claims.getSubject();
    }

    public String doGenerateToken(Map<String, Object> claims, long expiration) {
        final Date createdDate = (Date) claims.get(Constants.CLAIM_KEY_CREATED);
        final Date expirationDate = new Date(createdDate.getTime() + expiration * 1000);
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public Date getCreatedDateFromToken(String token) {
        final Claims claims = getClaimsFromToken(token);
        return new Date((Long) claims.get(Constants.CLAIM_KEY_CREATED));
    }

    public Date getExpirationDateFromToken(String token) {
        final Claims claims = getClaimsFromToken(token);
        return claims.getExpiration();
    }

    public Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }
}
