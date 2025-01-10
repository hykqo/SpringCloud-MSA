package org.example.secondservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/second-service")
@RestController
@Slf4j
public class SecondServiceController {


    @GetMapping("/welcome")
    public String welcome(){
        return "Welcome to the second-service";
    }

    @GetMapping("/message")
    public String message(@RequestHeader("second-request") String header){
        log.info(header);
        return "Welcome to the second-service";
    }

    @GetMapping("/check")
    public String check(){
        return "Hi, there. This is a message from Second Service";
    }
}
