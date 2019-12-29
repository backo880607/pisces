package com.pisces.platform.web.token;

import com.pisces.platform.core.utils.AppUtils;
import com.pisces.platform.web.config.WebProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class JwtTokenHelper {
    private static final String CLAIM_KEY_ROLES = "roles";
    private static final String CLAIM_KEY_CREATED = "created";
    private static WebProperties audience;

    private static WebProperties getAudience() {
        if (audience == null) {
            synchronized (JwtTokenHelper.class) {
                if (audience == null) {
                    audience = AppUtils.getBean(WebProperties.class);
                }
            }
        }
        return audience;
    }

    public static String getUsernameFromToken(String token) {
        String username;
        try {
            username = getClaimsFromToken(token).getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    public static Date getCreatedDateFromToken(String token) {
        Date created;
        try {
            final Claims claims = getClaimsFromToken(token);
            created = new Date((Long) claims.get(CLAIM_KEY_CREATED));
        } catch (Exception e) {
            created = null;
        }
        return created;
    }

    public static Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    @SuppressWarnings("unchecked")
    public static Collection<SimpleGrantedAuthority> getAuthorities(String token) {
        Collection<SimpleGrantedAuthority> authorities;
        try {
            final Claims claims = getClaimsFromToken(token);
            List<String> scopes = claims.get(CLAIM_KEY_ROLES, List.class);
            authorities = scopes.stream()
                    .map(authority -> new SimpleGrantedAuthority(authority))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            authorities = null;
        }
        return authorities;
    }

    private static Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(getAudience().getBase64Secret()).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    private static Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + getAudience().getExpiresSecond() * 1000);
    }

    public static Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public static String generateToken(User userDetails) {
        Claims claims = Jwts.claims().setSubject(userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED, new Date());
        claims.put(CLAIM_KEY_ROLES, userDetails.getAuthorities().stream().map(s -> s.toString()).collect(Collectors.toList()));
        return generateToken(claims);
    }

    public static String generateToken(String username, Collection<? extends GrantedAuthority> authorities) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put(CLAIM_KEY_CREATED, new Date());
        claims.put(CLAIM_KEY_ROLES, authorities.stream().map(s -> s.toString()).collect(Collectors.toList()));
        return generateToken(claims);
    }

    public static String generateToken(Claims claims) {
        // 生成签名密钥
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(getAudience().getBase64Secret());
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());

        return Jwts.builder().setHeaderParam("typ", "JWT")
                .setClaims(claims)
                .setIssuer(getAudience().getClientId())  //发行者
                .setAudience(getAudience().getName())  //接受者
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, signingKey)
                .compact();
    }

    public static Boolean canTokenBeRefreshed(String token) {
        return !isTokenExpired(token);
    }

    public static String refreshToken(String token) {
        String refreshedToken;
        try {
            final Claims claims = getClaimsFromToken(token);
            claims.put(CLAIM_KEY_CREATED, new Date());
            refreshedToken = generateToken(claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    public static Boolean validateToken(String token, UserDetails userDetails) {
        User user = (User) userDetails;
        final String username = getUsernameFromToken(token);
        return (username.equals(user.getUsername()) && isTokenExpired(token) == false);
    }
}

