package org.yj.demo.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yj.demo.app.bean.BeanProperties;
import org.yj.demo.app.feign.ConfigFeign;

import javax.annotation.Resource;
import java.util.Random;

@RestController
public class ConfigController {

    @Resource
    private BeanProperties beanProperties;
    @Resource
    private ConfigFeign configFeign;

    @GetMapping("/")
    public String name(){
        System.out.println(Thread.currentThread().getId());
        Random random = new Random();
        if (random.nextInt(100) > 50){
            return beanProperties.getName();
        }else {
//            configFeign.get();
        }
        return null;
    }

}
