package com.filemanagement.security;

import com.filemanagement.model.AuthUser;
import com.filemanagement.utils.Constants;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenProvider extends AbstractTokenProvider {

    public String generateToken(UserDetails userDetails) {
        claims.put(Constants.CLAIM_KEY_USERNAME, userDetails.getUsername());
        final Date createdDate = new Date();
        claims.put(Constants.CLAIM_KEY_CREATED, createdDate);
        return doGenerateToken(claims, accessTokenExpiration);
    }

    public String generateRefreshToken(UserDetails userDetails) {
        claims.put(Constants.CLAIM_KEY_USERNAME, userDetails.getUsername());
        final Date createdDate = new Date();
        claims.put(Constants.CLAIM_KEY_CREATED, createdDate);
        return doGenerateToken(claims, refreshTokenExpiration);
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        AuthUser user = (AuthUser) userDetails;
        final String username = getUsernameFromToken(token);
        final Date created = getCreatedDateFromToken(token);
        return (username.equals(user.getUsername()) && !isTokenExpired(token)
                && !isCreatedBeforeLastPasswordReset(created, user.getLastPasswordResetDate()));
    }
}