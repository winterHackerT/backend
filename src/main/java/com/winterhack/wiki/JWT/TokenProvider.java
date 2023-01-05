package com.winterhack.wiki.JWT;

import java.security.Key;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;

@Component
public class TokenProvider implements InitializingBean {

  private final Logger logger = LoggerFactory.getLogger(TokenProvider.class);
  
  private static final String AUTHORITES_KEY = "wiki";

  private final String secret;
  private final long tokenValidityInMilliseconds;
  private Key key;

  public TokenProvider(
    @Value("${jwt.secret}") String secret,
    @Value("${jwt.token-validity-in-seconds}") long tokenValidityInMilliseconds
  ) {
    this.secret = secret;
    this.tokenValidityInMilliseconds = tokenValidityInMilliseconds;
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    byte[] keyBytes = Base64.getDecoder().decode(secret);
    this.key = Keys.hmacShaKeyFor(keyBytes);
  }

  public String createToken(Authentication authentication) {
    String authorities = authentication
      .getAuthorities()
      .stream()
      .map(GrantedAuthority::getAuthority)
      .collect(Collectors.joining(","));

    long now = (new Date()).getTime();
    Date validity = new Date(now + this.tokenValidityInMilliseconds);

    return Jwts
      .builder()
      .setSubject(authentication.getName())
      .claim(AUTHORITES_KEY, authorities)
      .signWith(key, SignatureAlgorithm.HS512)
      .setExpiration(validity)
      .compact();
  }

  public Authentication getAuthentication(String token) {
    Claims claims = Jwts
      .parserBuilder()
      .setSigningKey(key)
      .build()
      .parseClaimsJws(token)
      .getBody();

    Collection<? extends GrantedAuthority> authorities = Arrays
      .stream(claims.get(AUTHORITES_KEY).toString().split(","))
      .map((x) -> new SimpleGrantedAuthority(x == "" || x == null ? "NULL" : x))
      .collect(Collectors.toList());

    User principal = new User(claims.getSubject(), "", authorities);

    return new UsernamePasswordAuthenticationToken(
      principal,
      token,
      authorities
    );
  }

  public boolean validateToken(String token) {
    try {
      Jwts
        .parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(token);

      return true;

    } catch (SecurityException | MalformedJwtException error) {
      logger.warn("Wrong JWT Signature");

    } catch (ExpiredJwtException error) {
      logger.warn("Expired JWT Token");

    } catch (UnsupportedJwtException error) {
      logger.warn("Unsupported JWT Token");

    } catch (IllegalArgumentException error) {
      logger.warn("Wrong JWT");
    }

    return false;
  }

}
