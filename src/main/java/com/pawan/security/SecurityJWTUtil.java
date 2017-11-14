package com.pawan.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.mobile.device.Device;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.pawan.security.dao.JwtUser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@Component
public class SecurityJWTUtil {

	private String secret = "pawan123";

	private Long expiration = 3000L;

	public String getUsernameFromToken(String token) {
		String username;
		try {
			final Claims claims = getClaimsFromToken(token);
			username = claims.getSubject();
		} catch (Exception e) {
			username = null;
		}
		return username;
	}

	private Claims getClaimsFromToken(String token) {
		Claims claims;
		try {
			claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			claims = null;
		}
		return claims;
	}

	public Date getCreatedDateFromToken(String token) {
		Date created;
		try {
			final Claims claims = getClaimsFromToken(token);
			created = new Date((Long) claims.get("created"));
		} catch (Exception e) {
			created = null;
		}
		return created;
	}

	public Date getExpirationDateFromToken(String token) {
		Date expiration;
		try {
			final Claims claims = getClaimsFromToken(token);
			expiration = claims.getExpiration();
		} catch (Exception e) {
			expiration = null;
		}
		return expiration;
	}

	private Date generateExpirationDate() {
		return new Date(System.currentTimeMillis() + expiration * 1000);
	}

	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
		return (lastPasswordReset != null && created.before(lastPasswordReset));
	}
	
	
	 public String generateToken(UserDetails userDetails, Device device) {
	        Map<String, Object> claims = new HashMap<>();
	        claims.put("sub", userDetails.getUsername());
	        claims.put("audience", "unknown");
	        claims.put("created", new Date());
	        return generateToken(claims);
	    }

	    String generateToken(Map<String, Object> claims) {
	        return Jwts.builder()
	                .setClaims(claims)
	                .setExpiration(generateExpirationDate())
	                .signWith(SignatureAlgorithm.HS512, secret)
	                .compact();
	    }

	    public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
	        final Date created = getCreatedDateFromToken(token);
	        return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset)
	                && (!isTokenExpired(token));
	    }

	    public String refreshToken(String token) {
	        String refreshedToken;
	        try {
	            final Claims claims = getClaimsFromToken(token);
	            claims.put("created", new Date());
	            refreshedToken = generateToken(claims);
	        } catch (Exception e) {
	            refreshedToken = null;
	        }
	        return refreshedToken;
	    }
	    
	    
	    public Boolean validateToken(String token, UserDetails userDetails) {
	        JwtUser user = (JwtUser) userDetails;
	        final String username = getUsernameFromToken(token);
	        final Date created = getCreatedDateFromToken(token);
	        return (
	                username.equals(user.getUsername())
	                        && !isTokenExpired(token)
	                        && !isCreatedBeforeLastPasswordReset(created, user.getLastPasswordResetDate()));
	}


}
