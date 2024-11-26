package com.lt.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liutong
 * @date 2024年11月26日 15:39
 */
@RestController
public class TestController {
    @RequestMapping("/")
    public String test(){
        return "hello world";
    }
}
