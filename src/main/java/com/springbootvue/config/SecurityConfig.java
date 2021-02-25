package com.springbootvue.config;

import com.springbootvue.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserService userService;

    @Autowired
    public SecurityConfig(BCryptPasswordEncoder bCryptPasswordEncoder, UserService userService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userService = userService;
    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserService userService) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService);
        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder);
        return authenticationProvider;
    }

    //사용자 세부 서비스를 설정하기 위한 오버라이딩
    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        //실제 인증을 진행할 Provider
        //auth.authenticationProvider(authenticationProvider(userServiceImpl));
    }

    //스프링 시큐리티의 필터 연결을 설정하기 위한 오버라이딩
    @Override
    public void configure(WebSecurity web) {
        //이미지,자바스크립트,css 디렉토리 보안 설정
        web.ignoring().antMatchers("/**");
    }

    //인터셉터로 요청을 안전하게 보호하는 방법을 설정하기 위한 오버라이딩
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //HTTP 관련 보안 설정 **가장 중요
        http
                .authorizeRequests() //시큐리티 처리에 HttpServletRequest를 이용한다는 것
                .antMatchers("/guest/setUserInfo").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().ignoringAntMatchers("/guest/setUserInfo")
                .and()
                .formLogin()
                .loginPage("/guest/login")
                .defaultSuccessUrl("/")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .deleteCookies("JSESSIONID")
                .and()
                .exceptionHandling()
                .accessDeniedPage("/access-denied");
    }
}
