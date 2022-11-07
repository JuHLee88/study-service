package com.example.studyservice.config;

import com.example.studyservice.home.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserService userService;
    /**
     * 규칙 설정
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        System.out.println("configure!!!");
        http
                .authorizeRequests()
                    .antMatchers( "http://127.0.0.1:9001/study-service/login", "/resources/**").permitAll() // 로그인 권한은 누구나, resources파일도 모든권한
                    // USER, ADMIN 접근 허용
                    .antMatchers("/userAccess").hasRole("USER")
                    .antMatchers("/userAccess").hasRole("ADMIN")
                    .and()
                .formLogin()
                    .loginPage("http://127.0.0.1:9001/study-service/login")
//                    .loginProcessingUrl("http://127.0.0.1:9001/study-service/access")
                    .defaultSuccessUrl("http://127.0.0.1:9001/study-service/dashboard")
//                    .defaultSuccessUrl("http://127.0.0.1:8000/study-service/dashboard")
                    .failureUrl("http://127.0.0.1:9001/study-service/access_denied") // 인증에 실패했을 때 보여주는 화면 url, 로그인 form으로 파라미터값 error=true로 보낸다.
                    .and()
                .logout()
                    .logoutUrl("http://127.0.0.1:9001/study-service/logout")
                    .logoutSuccessUrl("http://127.0.0.1:9001/study-service/login")
                    .and()
                .csrf().disable();		//로그인 창
    }

    /**
     * 로그인 인증 처리 메소드
     * @param auth
     * @throws Exception
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
    }
}
