package com.filemanagement.security;

import com.filemanagement.utils.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.filemanagement.utils.StringUtils.nonNull;


public abstract class AbstractTokenProvider {

    public Map<String, Object> claims = new HashMap<>();

    @Value("${jwt.token.secret}")
    public String secret;

    @Value("${jwt.expire.sec}")
    public Long accessTokenExpiration;

    @Value("${jwt.refreshTokenExpire.sec}")
    public Long refreshTokenExpiration;

    public Claims getClaimsFromToken(String token) {
        if (nonNull(token)) {
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        }
        return null;
    }

    public String getUsernameFromToken(String token) {
        if (nonNull(token)) {
            final Claims claims = getClaimsFromToken(token);
            return claims.getSubject();
        }
        return null;
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
        if (nonNull(token)) {
            final Claims claims = getClaimsFromToken(token);
            return new Date((Long) claims.get(Constants.CLAIM_KEY_CREATED));
        }
        return null;
    }

    public Date getExpirationDateFromToken(String token) {
        if (nonNull(token)) {
            final Claims claims = getClaimsFromToken(token);
            return claims.getExpiration();
        }
        return null;
    }

    public Boolean isTokenExpired(String token) {
        if (nonNull(token)) {
            final Date expiration = getExpirationDateFromToken(token);
            return expiration.before(new Date());
        }
        return null;
    }

    public Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (nonNull(lastPasswordReset) && created.before(lastPasswordReset));
    }
}
