package com.chyoon.jwt.common.config;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class JwtProvider {

    private String salt = "MEN1ZE8xQ29tbXVuaWNhdGlvTjJDb21wYW5ZM2tSNFRlNU90dGFlNmFiY2RlZmc=";

    private Key secretKey;

    private final long tokenValidTime = 3600 * 1000;// 1시간

    private final UserDetailsService userDetailsService;

    @PostConstruct
    protected void init() {
        secretKey = Keys.hmacShaKeyFor(salt.getBytes(StandardCharsets.UTF_8));
    }

    public long getTokenExpiredTime(){
        return tokenValidTime;
    }

    // 토큰 생성
//    public String createToken(String userName, String userAuth, long tokenTime) {
//        Claims claims = Jwts.claims().setSubject(userName);
//        claims.put("user_auth", userAuth);
//        Date now = new Date();
//        return Jwts.builder()
//                .setClaims(claims)
//                .setIssuedAt(now)
//                .setExpiration(new Date(now.getTime() + tokenTime))
//                .signWith(secretKey, SignatureAlgorithm.HS256)
//                .compact();
//    }

    public String createAccessToken(String userId) {
        Date now = new Date();

        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidTime))
                .signWith(this.secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    // 권한정보 획득
    // Spring Security 인증과정에서 권한확인을 위한 기능
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserId(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰에 담겨있는 유저 account 획득
    public String getUserId(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().getSubject();
    }

    // Authorization Header를 통해 인증을 한다.
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        // Bearer 검증
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }

    // 토큰 검증
    public boolean validateToken(HttpServletRequest request, String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
        } catch (ExpiredJwtException e) {// 위 코드 예외 걸면 알아서 만료 예외 발생시키는 걸로 알고있음(만료일 경우)
            request.setAttribute("exception", "EXPIRED_TOKEN");
            return false;
        } catch (Exception e) {
            request.setAttribute("exception", "INVALID_TOKEN");
            return false;
        }

        return true;
    }
}