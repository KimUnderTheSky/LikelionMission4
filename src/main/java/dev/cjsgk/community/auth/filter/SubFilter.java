package dev.cjsgk.community.auth.filter;

import dev.cjsgk.community.auth.LikelionSsoConsts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@Component
public class SubFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(SubFilter.class);

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;


        Cookie[] cookies = httpRequest.getCookies(); // 쿠키를 받아온다.
        if (cookies != null) { // null값 확인
            for (Cookie cookie: cookies){
                if (cookie.getName().equals(LikelionSsoConsts.LIKELION_LOGIN_COOKIE)){ //쿠키 이름 상수화
                    logger.info("Login Token Found, {}", cookie.getValue()); // 토큰 찾기
                    chain.doFilter(request, response); // 토큰 찾으면 필터실행
                    return;
                }
            }
        }

        logger.info("Login Token Missing");
        chain.doFilter(request, response);

    }
}