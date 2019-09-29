package org.cay.play.springredis.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
@Slf4j
public class MyController {
    @GetMapping("/h1")
    public String h1(HttpSession session) {
        session.setAttribute("h1", "haha");
        return "H1";
    }

    @GetMapping("/h2")
    public String h2(HttpSession session) {
        log.debug((String) session.getAttribute("h1"));
        return "H2";
    }


}
