package dev.cjsgk.community.controller;

import dev.cjsgk.community.auth.AuthenticationFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

// new
@Controller
@RequestMapping("/")
public class HomeController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
    private final AuthenticationFacade authFacade;

    public HomeController(
            @Autowired AuthenticationFacade authFacade
    ) {
        this.authFacade = authFacade;
    }
    // 도메인만 치고 들어왔을 때, 페이지를 일관되게 하기 위한 매핑
    @GetMapping
    public String root(){
        return "redirect:/home";
    }

    @GetMapping("home")
    public String home(){
        try {
            logger.info("connected user: {}",
                    authFacade.getUserName());
            logger.info(authFacade.getAuthentication().getClass().toString());
        } catch (NullPointerException e) {
            logger.info("no user logged in");
        }
        return "index";
    }
}