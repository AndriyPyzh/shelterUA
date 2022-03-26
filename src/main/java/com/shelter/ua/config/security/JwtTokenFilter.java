package com.shelter.ua.config.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.InvalidSignatureException;
import org.springframework.security.jwt.crypto.sign.MacSigner;
import org.springframework.security.jwt.crypto.sign.SignatureVerifier;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class JwtTokenFilter extends OncePerRequestFilter {

    private final String accessTokenKey;

    public JwtTokenFilter(String accessTokenKey) {
        this.accessTokenKey = accessTokenKey;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String token = getTokenFromHttpServletRequest(request);

        if (Strings.isNullOrEmpty(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            Map<String, Object> claims = getClaimsFromToken(token);

            String username = (String) claims.get("user_name");
            List<String> authorities = (List<String>) claims.get("authorities");

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (InvalidSignatureException e) {
            throw new IllegalStateException(String.format("Token %s cannot be trusted", token));
        }

        filterChain.doFilter(request, response);
    }

    private String getTokenFromHttpServletRequest(HttpServletRequest servletRequest) {
        return Optional
                .ofNullable(servletRequest.getHeader("Authorization"))
                .filter(authHeader -> authHeader.startsWith("Bearer "))
                .map(token -> token.substring(7))
                .orElse(null);
    }

    private Map<String, Object> getClaimsFromToken(String token) throws JsonProcessingException {
        SignatureVerifier verifier = new MacSigner(accessTokenKey);

        String claimsStr = JwtHelper.decodeAndVerify(token, verifier).getClaims();

        return new ObjectMapper().readValue(claimsStr, HashMap.class);
    }

}
