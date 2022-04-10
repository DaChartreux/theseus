package com.potatoxchip.api.user;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserElasticRepository extends ElasticsearchRepository<UserModel, Long> {
}
