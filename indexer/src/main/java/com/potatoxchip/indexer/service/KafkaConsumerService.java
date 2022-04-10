package com.potatoxchip.indexer.service;

import com.potatoxchip.indexer.model.Payload;
import com.potatoxchip.indexer.model.User;
import com.potatoxchip.indexer.repository.UserElasticRepository;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    private final UserElasticRepository userElasticRepository;
    private final ElasticsearchRestTemplate elasticsearchTemplate;


    @Autowired
    public KafkaConsumerService(UserElasticRepository userElasticRepository,
                                ElasticsearchRestTemplate elasticsearchTemplate) {
        this.userElasticRepository = userElasticRepository;
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    @KafkaListener(topics = "debezium01.theseus.users", groupId = "theseus.consumer.group")
    public void consume(Payload payload) {

        Payload.Operation operation = payload.getOperation();

        switch (operation) {
            case c -> createNewUserIndex(payload);
            case u -> updateUserIndex(payload);
            case d -> deleteUserIndex(payload);
        }
    }

    private void createNewUserIndex(Payload payload) {
        System.out.println("Creating index for" + payload.getAfter());
        userElasticRepository.save(payload.getAfter());
    }

    private void updateUserIndex(Payload payload) {
        System.out.println("Updating index for");
        User beforeUser = payload.getBefore();
        User afterUser = payload.getAfter();

        Query searchQuery = new NativeSearchQueryBuilder().withQuery(QueryBuilders.matchQuery("id",
                beforeUser.getId())).build();

        SearchHit<User> userSearchHit = elasticsearchTemplate.searchOne(searchQuery, User.class, IndexCoordinates.of(
                "user"));

        if (userSearchHit == null) {
            return;
        }

        User user = userSearchHit.getContent();

        user.setUserName(afterUser.getUserName());
        user.setEmail(afterUser.getEmail());
        user.setHandleName(afterUser.getHandleName());
        user.setId(afterUser.getId());

        userElasticRepository.save(user);
    }

    private void deleteUserIndex(Payload payload) {
        User beforeUser = payload.getBefore();

        userElasticRepository.deleteById(beforeUser.getId());
    }
}
