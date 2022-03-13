package fpt.com.fresher.recruitmentmanager.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("/")
    public String hello() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "common/Home";
    }
}
