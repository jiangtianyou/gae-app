package com.app.modules.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("a/home")
public class HomeController {
    @RequestMapping("")
    public String login() {
        return "login";
    }
}
