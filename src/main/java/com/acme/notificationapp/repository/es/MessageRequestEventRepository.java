package com.acme.notificationapp.repository.es;

import com.acme.notificationapp.domain.MessageRequestEvent;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface MessageRequestEventRepository extends ElasticsearchRepository<MessageRequestEvent, String> {
}