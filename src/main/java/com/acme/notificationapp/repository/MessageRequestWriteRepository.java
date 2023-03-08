package com.acme.notificationapp.repository;

import com.acme.notificationapp.domain.MessageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRequestWriteRepository extends JpaRepository<MessageRequest, Long> {
}
