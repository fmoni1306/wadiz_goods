package com.wadiz_goods.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
// 로깅
@Slf4j
public class HomeController {

    @RequestMapping("/")
    public String home() {
        return "home";
    }
}
