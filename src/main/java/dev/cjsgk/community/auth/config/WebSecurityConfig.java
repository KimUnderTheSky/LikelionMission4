package dev.cjsgk.community.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;


// new
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final AuthenticationSuccessHandler customSuccessHandler;

    public WebSecurityConfig(
            UserDetailsService userDetailsService,
            AuthenticationSuccessHandler customSuccessHandler
    ) {
        this.userDetailsService = userDetailsService;
        this.customSuccessHandler = customSuccessHandler;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests()
                    .antMatchers( // 이 경로에 맞는 요청들은 모두 허용한다.
                            "/home/**",
    //                            "/user",
                            "/user/signup/**",
                            "/",
                            "/css/**",
                            "/images/**",
                            "/js/**"
                    )
                    .permitAll()
                    .anyRequest()// 그외의 요청들은
                    .authenticated() // 인증정보가 있어야한다.
                    .and()
                .formLogin()//로그인 페이지 설정
                    .loginPage("/user/login") // 성공시
                    .defaultSuccessUrl("/home") // 홈으로
    //                    .successHandler(customSuccessHandler)
                    .permitAll()// 접근허가해줘라
                    .and()
                .logout()
                    .logoutUrl("/user/logout")// 로그아웃 요청
                    .logoutSuccessUrl("/home")//성공시 어디로 갈지
                    .deleteCookies("JSEESIONID")//삭제하과 싶은 쿠키
                    .invalidateHttpSession(true) // HttpSession초기화
                    .permitAll();
    }

}