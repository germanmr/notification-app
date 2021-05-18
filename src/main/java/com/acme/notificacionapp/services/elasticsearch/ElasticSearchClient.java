package com.acme.notificacionapp.services.elasticsearch;

import java.util.concurrent.Future;

public interface ElasticSearchClient {

    /**
     * Load bulk data to ElasticSearch
     *
     * @return
     */
    Future<Boolean> loadData();
}
