package com.acme.notificacionapp.repository;

import com.acme.notificacionapp.domain.MessageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRequestRepository extends JpaRepository<MessageRequest, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<MessageRequest> findById(Long id);

    @Query(value = "select * FROM message_request mr " +
            " where mr.favorite_media='MAIL' and mr.message_state in ('PENDING','ERROR') AND MR.id IN (378,382) " +
            " order by mr.id for update skip locked limit :batch_size", nativeQuery = true)
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "5000")})
    List<MessageRequest> getBatchForUpdateById(@Param("batch_size") Long batch_size);

    @Query(value = "select * FROM message_request mr " +
            " where mr.favorite_media='MAIL' and mr.message_state in ('PENDING','ERROR') AND MR.id IN (378) " +
            " order by mr.id for update skip locked limit 1", nativeQuery = true)
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "5000")})
    MessageRequest getNextMessageForUpdate();

}
