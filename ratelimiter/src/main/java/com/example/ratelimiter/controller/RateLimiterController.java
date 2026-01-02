package com.example.ratelimiter.controller;

// import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ratelimiter.Dto.RateLimiterRequestDto;
import com.example.ratelimiter.Dto.RateLimiterResponseDto;
import com.example.ratelimiter.Service.RateLimiterService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
public class RateLimiterController {

            RateLimiterService rateLimiterService;

            RateLimiterController(RateLimiterService rateLimiterService) {
                this.rateLimiterService = rateLimiterService;
            }

            @GetMapping("/")
            public String getMethodName(){
                return new String("Hello+ !");
            }
            
            @PostMapping("/rateLimiter")
            public RateLimiterResponseDto rateLimiter(@RequestBody RateLimiterRequestDto requestDto) {
                long t1 = System.nanoTime();
                System.out.println("Request Received at controller at: " + t1);
                RateLimiterResponseDto responseDto = rateLimiterService.rateLimiter(requestDto);
                long t2 = System.nanoTime();
                System.out.println("Request Completed at controller at: " + t2);
                System.out.println("Total time taken by controller: " + (t2 - t1));
                return responseDto;

            }

}
