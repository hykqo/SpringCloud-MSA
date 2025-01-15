package org.example.firstservice;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/first-service")
@Slf4j
@RequiredArgsConstructor
public class FirsServiceController {

    private final Environment env;

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
    public String check(HttpServletRequest request){
        //HttpServletRequest로 서버포트 받아오기.
        log.info("Server port={}", request.getServerPort());

        //Environment로 서버포트 받아오기.
        return String.format("Hi, there. This is a message from First Service on PORT %s", env.getProperty("local.server.port"));
    }

}
