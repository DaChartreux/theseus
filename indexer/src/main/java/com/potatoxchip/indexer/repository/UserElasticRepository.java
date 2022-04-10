package com.potatoxchip.indexer.repository;

import com.potatoxchip.indexer.model.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface UserElasticRepository extends ElasticsearchRepository<User, Long> {

}
