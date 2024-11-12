package com.lt;

import com.lt.config.LoginCodeEnum;
import com.lt.config.LoginProperties;
import com.wf.captcha.base.Captcha;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 验证码生成
 * @author liutong
 * @date 2024年03月19日 9:58
 */
@RestController
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
    @GetMapping("/")
    public String creat(){
        // 默认为arithmetic
        LoginProperties loginProperties = new LoginProperties();
        Captcha captcha = loginProperties.getCaptcha(LoginCodeEnum.arithmetic);
        Captcha captcha1 = loginProperties.getCaptcha(LoginCodeEnum.chinese);
        Captcha captcha2 = loginProperties.getCaptcha(LoginCodeEnum.chinese_gif);
        System.out.println(captcha.text());
        System.out.println(captcha1.text());
        System.out.println(captcha2.text());
        System.out.println(captcha.toBase64());
        System.out.println(captcha1.toBase64());
        System.out.println(captcha2.toBase64());
        return captcha.toBase64();
    }
}
