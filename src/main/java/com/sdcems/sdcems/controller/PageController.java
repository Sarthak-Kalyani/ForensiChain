package com.sdcems.sdcems.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String home() {
        return "login";
    }

    @GetMapping("/uploadPage")
    public String uploadPage() {
        return "upload";
    }

    @GetMapping("/verifyPage")
    public String verifyPage() {
        return "verify";
    }
}