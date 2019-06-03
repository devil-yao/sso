package org.yj.demo.app.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient(name = "zuul-demo")
public interface ConfigFeign {

    @ResponseBody
    @GetMapping("/app")
    void get();

}
