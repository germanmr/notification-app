package com.acme.notificationapp.repository;

import com.acme.notificationapp.domain.Publication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublicationWriteRepository extends JpaRepository<Publication, Long> {
}
