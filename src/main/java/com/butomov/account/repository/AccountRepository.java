package com.butomov.account.repository;

import com.butomov.account.model.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {

    Account findByUserUserId(UUID userId);
}
