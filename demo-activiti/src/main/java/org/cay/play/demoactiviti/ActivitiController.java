package org.cay.play.demoactiviti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ActivitiController {

    @Autowired
    ActivitiTest activitiTest;

    @RequestMapping("/hahaha")
    public void hahaha(@RequestParam String key) {
        activitiTest.startProcess(key);
    }

}
