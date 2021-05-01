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

    default MessageRequest getForUpdateById(Integer id) {
        return findById(id).orElse(null);
    }

    @Lock(LockModeType.PESSIMISTIC_READ)
    Optional<MessageRequest> findById(Integer customerId);

    @Query(value = "select * from message_request where  order by id limit :batch_size for update", nativeQuery = true)
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "5000")})
    List<MessageRequest> getBatchForUpdateById(@Param("batch_size") Long batch_size);

//    @Lock(LockModeType.PESSIMISTIC_WRITE)
//    Stock findById(String id)

}
