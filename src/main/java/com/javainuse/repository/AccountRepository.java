package com.javainuse.repository;

import com.javainuse.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    default Account getForUpdateById(Integer id) {
        return findById(id).orElse(null);
    }

    @Lock(LockModeType.PESSIMISTIC_READ)
    Optional<Account> findById(Integer customerId);

}
