package com.acme.notificationapp.services.elasticsearch;

import com.acme.notificationapp.domain.MessageRequestEvent;
import com.acme.notificationapp.repository.es.MessageRequestEventRepository;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MessageRequestEventRepositoryService {

    private final MessageRequestEventRepository repository;

    private static final String INDEX = "message_request_event";
    private final ElasticsearchOperations elasticsearchOperations;

    @Autowired
    public MessageRequestEventRepositoryService(MessageRequestEventRepository repository,
                                                ElasticsearchOperations elasticsearchOperations) {
        this.repository = repository;
        this.elasticsearchOperations = elasticsearchOperations;
    }

    // TODO add tests
    public void createMessageRequestIndexBulk(final List<MessageRequestEvent> messageRequests) {
        repository.saveAll(messageRequests);
    }

    public MessageRequestEvent index(final MessageRequestEvent messageRequestEvent) {
        return repository.save(messageRequestEvent);
    }

    public List<MessageRequestEvent> findEventsByMessageRequest(UUID messageRequestId) {
        QueryBuilder queryBuilder = QueryBuilders.matchQuery("id", messageRequestId.toString());

        Query searchQuery = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .build();
        SearchHits<MessageRequestEvent> hits =
                elasticsearchOperations
                        .search(searchQuery,
                                MessageRequestEvent.class,
                                IndexCoordinates.of(INDEX));
        return hits.toList().stream().map(h -> h.getContent()).collect(Collectors.toList());
    }

    public void deleteIndex() {
        elasticsearchOperations.indexOps(IndexCoordinates.of(INDEX)).delete();
    }
}

