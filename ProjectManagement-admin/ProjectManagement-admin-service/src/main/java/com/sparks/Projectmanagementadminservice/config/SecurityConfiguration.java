package com.sparks.Projectmanagementadminservice.config;

import com.alibaba.fastjson.JSONObject;
import com.sparks.Projectmanagementadmincommon.web.ResultDto;
import com.sparks.Projectmanagementadminservice.service.AuthorizeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * @Author: Sparks
 * @Date: 2024/8/29 23:33
 * @Version 1.0
 * @TODO:
 */
@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfiguration {

    private AuthorizeService authorizeService;
    private DataSource dataSource;

    @Autowired
    public SecurityConfiguration(AuthorizeService authorizeService, DataSource dataSource) {
        this.authorizeService = authorizeService;
        this.dataSource = dataSource;
    }

    @Autowired
    public void setAuthorizeService(AuthorizeService authorizeService) {
        this.authorizeService = authorizeService;

    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,PersistentTokenRepository tokenRepository) throws Exception {
        http
                .authorizeHttpRequests((authorize) -> authorize
//                         .requestMatchers("/public/**").permitAll() // 公共资源允许所有用户访问
//                         .requestMatchers("/auth/**").hasAnyRole("USER", "ADMIN") // 管理员资源需要 ADMIN 角色
//                            .requestMatchers("/auth/**").permitAll() // 公共资源允许所有用户访问
                            .anyRequest().authenticated() // 其他所有请求都需要认证
                )
                .formLogin((form) -> form
                        .loginPage("/")
                        .loginProcessingUrl("/auth/login")
                        // 登录成功后的处理
                        .successHandler(this::onAuthenticationSuccess)
                        //.successForwardUrl("/index")
                        // 登录失败后的处理
                        .failureHandler(this::onAuthenticationFailure)
                )
                .logout((logout) -> logout
                        .logoutUrl("/auth/logout")
                        // 退出登录成功后的处理
                        .logoutSuccessHandler(this::onLogoutSuccess)
                )
                .rememberMe(rememberMe -> rememberMe
                        .rememberMeParameter("rememberMe")
                        .tokenRepository(tokenRepository)
                        .tokenValiditySeconds(3600 * 24 * 5)
                )
                .exceptionHandling((exception) -> exception
                                //未登录时访问其他url，返回 JSON 格式的错误信息。
                                 .authenticationEntryPoint(this::onAuthenticationFailureAll)
                )
                .csrf(AbstractHttpConfigurer::disable
                        //使用 Cookie 存储 CSRF Token。withHttpOnlyFalse() 表示 CSRF Token 不是 HttpOnly，以便前端可以读取。
                        //.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                )
                .cors(cors -> cors.configurationSource(this.corsConfigurationSource()));
        return http.build();
    }




    @Bean
    public PersistentTokenRepository tokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
//        jdbcTokenRepository.setCreateTableOnStartup(true); //第一次没有建表时设置成true，建表后设置成false
        jdbcTokenRepository.setCreateTableOnStartup(false);
        return jdbcTokenRepository;
    }




    /**
     * 配置跨域请求的来源
     * 由于前端请求来自不同的源，后端需要配置跨域策略以允许这些请求
     * 这里采用放行所有来源的方式，以支持跨域请求
     *
     * @return CorsConfigurationSource 实例，用于处理跨域请求配置
     */
    private CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
//        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * 配置身份验证管理器，用于处理用户认证逻辑(自定义)
     * 该方法通过AuthenticationManagerBuilder来配置认证的相关细节
     */
    @Bean
    public AuthenticationManager authenticationManagerBean(HttpSecurity httpSecurity) throws Exception {
            AuthenticationManagerBuilder authenticationManagerBuilder = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
            authenticationManagerBuilder.userDetailsService(authorizeService).passwordEncoder(passwordEncoder());
            return authenticationManagerBuilder.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }



    private void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException {
        // 设置响应的内容类型
        httpServletResponse.setContentType("application/json; charset=utf-8");
        httpServletResponse.setCharacterEncoding("utf-8");
//        log.info("---------Success>>>>request: "+request.getRequestURI());
        httpServletResponse.getWriter().write(JSONObject.toJSONString(ResultDto.success("登录成功")));
    }
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {
        // 设置响应的内容类型
        httpServletResponse.setContentType("application/json; charset=utf-8");
        httpServletResponse.setCharacterEncoding("utf-8");
        if(!e.getMessage().isEmpty()){
            httpServletResponse.getWriter().write(JSONObject.toJSONString(ResultDto.fail(e.getMessage())));
            return;
        }
        httpServletResponse.getWriter().write(JSONObject.toJSONString(ResultDto.fail("登录失败")));
    }
    private void onLogoutSuccess(HttpServletRequest request, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException {
        // 设置响应的内容类型
        httpServletResponse.setContentType("application/json; charset=utf-8");
        httpServletResponse.setCharacterEncoding("utf-8");
//        log.info("---------LogoutSuccess>>>>request: "+request.getRequestURI());
        httpServletResponse.getWriter().write(JSONObject.toJSONString(ResultDto.success("退出成功")));
    }

    private void onAuthenticationFailureAll(HttpServletRequest request, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {
        // 设置响应的内容类型
        httpServletResponse.setContentType("application/json; charset=utf-8");
        httpServletResponse.setCharacterEncoding("utf-8");
//        log.info("---------FailureAll>>>>request: "+request.getRequestURI());
        httpServletResponse.getWriter().write(JSONObject.toJSONString(ResultDto.success("请先登录！！")));

    }

}
