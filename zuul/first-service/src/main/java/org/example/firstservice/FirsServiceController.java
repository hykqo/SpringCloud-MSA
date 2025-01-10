package org.example.firstservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/first-service")
@Slf4j
public class FirsServiceController {

    @GetMapping("/welcome")
    public String welcome(){
        return "Welcome to First Service";
    }

    @GetMapping("/message")
    public String message(@RequestHeader("first-request") String header){
      log.info(header);
      return "Welcome to First Service";
    }

    @GetMapping("/check")
    public String check(){
        return "Hi, there. This is a message from First Service";
    }

}
