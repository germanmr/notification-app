package com.acme.notificationapp.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Profile(Profiles.LOCAL)
@Configuration
@EnableKafka
public class KafkaProducerConfig {

    @Value("${instance.id}")
    private String APPLICATION_INSTANCE_ID;

    @Bean(value = "kafkaTemplate")
    @Primary
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(stringProducerFactory(), true);
    }

    @Bean(value = "stringProducerFactory")
    @Primary
    public ProducerFactory<String, String> stringProducerFactory() {
        Map<String, Object> config = new ConcurrentHashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
//        config.put(ProducerConfig.LINGER_MS_CONFIG, 100);
        config.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
//        config.put(ProducerConfig.ACKS_CONFIG, "all");
        DefaultKafkaProducerFactory<String, String> defaultKafkaProducerFactory = new DefaultKafkaProducerFactory<>(
                config);

        defaultKafkaProducerFactory.setTransactionIdPrefix(APPLICATION_INSTANCE_ID + "-transaction-");
        return defaultKafkaProducerFactory;
    }

//    @Bean(name = "chainedStringKafkaTransactionManager")
//    @Primary
//    public ChainedKafkaTransactionManager<String, String> chainedTransactionManager(
//            JpaTransactionManager jpaTransactionManager, DataSourceTransactionManager dsTransactionManager) {
//        return new ChainedKafkaTransactionManager<>(kafkaStringTransactionManager(),
//                jpaTransactionManager, dsTransactionManager);
//    }
//
//    @Bean(value = "stringKafkaTransactionManager")
//    public KafkaTransactionManager<String, String> kafkaStringTransactionManager() {
//        KafkaTransactionManager<String, String> ktm = new KafkaTransactionManager<>(
//                stringProducerFactory());
//        ktm.setNestedTransactionAllowed(true);
//        ktm.setTransactionSynchronization(AbstractPlatformTransactionManager.SYNCHRONIZATION_ALWAYS);
//        return ktm;
//    }
//    @Bean(value = "kafkaTransactionManager")
//    public KafkaTransactionManager<String, String> kafkaTransactionManager() {
//        KafkaTransactionManager<String, String> ktm = new KafkaTransactionManager<>(
//                stringProducerFactory());
//        ktm.setNestedTransactionAllowed(true);
//        ktm.setTransactionSynchronization(AbstractPlatformTransactionManager.SYNCHRONIZATION_ALWAYS);
//        return ktm;
//    }
//
//    @Bean
//    public DataSourceTransactionManager dsTransactionManager(@Qualifier("datasource") DataSource ds) {
//        return new DataSourceTransactionManager(ds);
//    }
//
//    @Bean
//    public JpaTransactionManager jpaTransactionManager(EntityManagerFactory entityManagerFactory) {
//        JpaTransactionManager transactionManager = new JpaTransactionManager();
//        transactionManager.setEntityManagerFactory(entityManagerFactory);
//        return transactionManager;
//    }
//
//    @Bean
//    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
//        return jpaTransactionManager(entityManagerFactory);
//    }

}