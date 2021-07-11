package com.wadiz_goods.controller;

import com.wadiz_goods.controller.form.MemberForm;
import com.wadiz_goods.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class AccountController {

    private final MemberService memberService;

    @GetMapping("account/signup")
    public String signUp(Model model) {
        model.addAttribute("form", new MemberForm());
        return "account/signup";
    }

    @PostMapping("account/signup")
    public String signIn(@Valid MemberForm form, BindingResult result) {
        if (result.hasErrors()) {
            return "account/signup";
        }
        memberService.join(form);

        return "redirect:/";
    }

    @GetMapping("account/signin")
    public String signIn(Model model) {
        model.addAttribute("form", new MemberForm());
        return "account/signin";
    }
}
