package org.yj.demo.app.controller;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/login")
public class LoginController {

    @RequestMapping
    public void login(HttpServletRequest request){
        request.getParameterMap().forEach((key,value) -> {
            System.out.println(key+"="+ ToStringBuilder.reflectionToString(value, ToStringStyle.SHORT_PREFIX_STYLE));
        });
    }

}
