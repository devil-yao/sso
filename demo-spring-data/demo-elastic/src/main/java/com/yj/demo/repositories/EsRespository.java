package com.yj.demo.repositories;

import com.yj.demo.entity.EsEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EsRespository extends ElasticsearchCrudRepository<EsEntity,Integer> {
}
