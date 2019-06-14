package com.yj.demo.controller;

import com.google.gson.Gson;
import com.yj.demo.entity.EsEntity;
import com.yj.demo.repositories.EsRespository;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.indices.DeleteIndex;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
public class EsController {

    @Resource
    private EsRespository esRespository;

    @Resource
    private ElasticsearchTemplate elasticsearchTemplate;

    @Resource
    private JestClient jestClient;

    @GetMapping("/save")
    public void test() {
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

    @GetMapping("/delete")
    public void deleteAll() throws IOException {
        DeleteIndex index = new DeleteIndex.Builder("es-entity").build();
        jestClient.execute(index);
    }
    @GetMapping("/get")
    public List<JestResult> get() throws IOException {
        EsEntity esEntity = new EsEntity();
        esEntity.setShowName("desc");
        Gson gson = new Gson();

        String paras = new SearchSourceBuilder().query( QueryBuilders.fuzzyQuery("showName","desc")).toString();
        Search search = new Search.Builder( gson.toJson(esEntity)).build();
        SearchResult result = jestClient.execute(search);
        Search search2 = new Search.Builder( paras).build();
        SearchResult result2 = jestClient.execute(search2);
        return Arrays.asList(result,result2);
    }
}
