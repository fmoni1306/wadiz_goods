package com.wadiz_goods.cofig;

import com.wadiz_goods.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private MemberService memberService;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //static 디렉터리의 하위 파일 목록은 인증을 무시하기 위한 설정 (=항상 통과)
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/lib/**");
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/**").permitAll()
                .and()
                .formLogin()// 로그인 설정
                .loginPage("/account/signin")// 커스텀 login 페이지를 사용
                .defaultSuccessUrl("/") // 로그인 성공 시 이동할 페이지
                .permitAll()
                .usernameParameter("username")
                .and()
                .logout()// 로그아웃 설정
                .logoutRequestMatcher(new AntPathRequestMatcher("/account/signout"))
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true) // 세션 초기화
                .and()
                .exceptionHandling();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 로그인 처리를 하기위한 AuthenticationManagerBuilder 를 설정
        auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());
    }

}
