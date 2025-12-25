package com.example.userservice.security;

import com.example.userservice.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.IpAddressMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

@Configuration     //configration으로 관리될 수 있도록 어노테이션 등록
@EnableWebSecurity //security configration파일로 등록될 수 있도록 어노테이션 등록.
public class WebSecurity {

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserService userService;
    private Environment env;

    public static final String ALLOWED_IP_ADDRESS = "127.0.0.1";
    public static final String SUBNET = "/32";
    public static final IpAddressMatcher IP_ADDRESS_MATCHER = new IpAddressMatcher(ALLOWED_IP_ADDRESS + SUBNET);

    public WebSecurity(UserService userService, Environment env, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.env = env;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    //SecurityFilterChain 을 빈으로 등록해서 인증/인가 처리.
    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);

        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

        http.csrf( (csrf) -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/h2-console/**").permitAll()     //h2콘솔
                                .requestMatchers("/**").access(
                                        new WebExpressionAuthorizationManager(
                                                "hasIpAddress('127.0.0.1') or hasIpAddress('::1') or " +
                                                "hasIpAddress('192.168.20.68') or hasIpAddress('::1')"
                                        )
                                ).anyRequest().authenticated()
                )
//                .addFilterBefore(apiKeyAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class) //api-key 전용
                .authenticationManager(authenticationManager) //인증관련 매니저 지정.
                .addFilter(getAuthenticationFilter(authenticationManager)) //로그인 인증 필터
                .httpBasic(Customizer.withDefaults()) //Basic 인증 추가
                .headers((headers) -> headers //스프링 시큐리티에서는 X-Frame-Options를 지정하지 않으면 디폴트가 Deny로 설정되어 있음.
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)) //frame호출을 허용.
        ;
        return http.build();
    }

    /**
     *  OncePerRequestFilter : HTTP 요청마다 한 번만 실행되게 보장하는 필터
     *  보통은 UsernamePasswordAuthenticationFilter을 사용하지 못할때 선언하여 사용.(UsernamePasswordAuthenticationFilter을는 form방식에서 동작하는 filter)
     *  OncePerRequestFilter를 이용해서 api 토큰을 검증하고 통과한다면 인증객(Authentication)를 SecurityContext에 넣으면 됨.
     *  */
//    @Bean
    public OncePerRequestFilter apiKeyAuthenticationFilter() throws Exception {
        return new ApiKeyAuthenticationFilter(bCryptPasswordEncoder, env);
    }

    public AuthenticationFilter getAuthenticationFilter(AuthenticationManager authenticationManager) throws Exception {
        return new AuthenticationFilter(authenticationManager, userService, env);
    }
}
