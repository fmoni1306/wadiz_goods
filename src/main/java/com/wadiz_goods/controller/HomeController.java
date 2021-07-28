package com.wadiz_goods.controller;

import com.wadiz_goods.domain.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
// 로깅
@Slf4j
public class HomeController {

    @RequestMapping("/")
    public String home(Authentication authentication) {
        System.out.println("home");
        return "home";
    }
}
