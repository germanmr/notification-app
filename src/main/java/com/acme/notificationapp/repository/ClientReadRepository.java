package com.acme.notificationapp.repository;

import com.acme.notificationapp.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientReadRepository extends JpaRepository<Client, Long> {
}
