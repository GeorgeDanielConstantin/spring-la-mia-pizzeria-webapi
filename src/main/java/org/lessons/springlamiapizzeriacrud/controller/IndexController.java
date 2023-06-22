package org.lessons.springlamiapizzeriacrud.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

public class IndexController {

    @RequestMapping("/")

    @GetMapping("/")
    public String home(Model model) {
        return "index";
    }
}

