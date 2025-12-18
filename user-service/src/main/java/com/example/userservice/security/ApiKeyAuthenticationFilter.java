package com.example.userservice.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Slf4j
public class ApiKeyAuthenticationFilter extends OncePerRequestFilter {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final Environment env;

    public ApiKeyAuthenticationFilter(BCryptPasswordEncoder bCryptPasswordEncoder, Environment env) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.env = env;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("CustomAuthenticationFilter.doFilterInternal");

        // 이미 인증된 사용자가 있으면, API Key 검증 스킵
        Authentication existingAuth = SecurityContextHolder.getContext().getAuthentication();
        if (existingAuth != null && existingAuth.isAuthenticated()) {
            filterChain.doFilter(request, response);
            return;
        }

        // header 추출
        String authorization = request.getHeader("Authorization");
        String apiKey = request.getHeader("apiKey");
        if(apiKey == null){
            if(authorization != null && authorization.startsWith("Bearer ")) {
                apiKey = authorization.substring(7);
            }
        }

        //basic api-key 있을 경우 맞지 않으면 실패.
        String apiRawPasswd = env.getProperty("spring.security.basic.key"); //$2a$10$atrV7lklwrGOFpELZ3fIveDW7z2oSr71p6MMvn8RcDvDWgAyRN.z.
        boolean basicBool = bCryptPasswordEncoder.matches(apiRawPasswd, apiKey);
        if(!basicBool) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        /**
         * api 통신시 api-key 인증 통과할 경우 여기서 인증 로그인 처리 하면 됨.
         * */

        filterChain.doFilter(request, response);
    }
}


