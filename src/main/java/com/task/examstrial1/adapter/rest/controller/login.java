package com.task.examstrial1.adapter.rest.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import java.security.Principal;

@Controller
@RequestMapping()
public class login {
    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

    @GetMapping("/")
    public String landing(Principal principal, Model model){
        String name = principal.getName();
        model.addAttribute("name",name);

        return "home";

    }




}
