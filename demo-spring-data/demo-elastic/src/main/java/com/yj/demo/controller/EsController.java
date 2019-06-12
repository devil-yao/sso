package com.yj.demo.controller;

import com.yj.demo.entity.EsEntity;
import com.yj.demo.repositories.EsRespository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Optional;

@RestController
public class EsController {

    @Resource
    private EsRespository esRespository;

    @GetMapping("/save")
    public void test() throws IOException {
        EsEntity esEntity = new EsEntity();
        esEntity.setDesc("desc");
        esEntity.setName("name");
        esEntity.setId(1);
        esRespository.save(esEntity);
    }

    @GetMapping("/id/{id}")
    public EsEntity get(@PathVariable("id") Integer id){
        Optional<EsEntity> optional = esRespository.findById(id);
        return optional.isPresent()?optional.get():null;
    }
}
