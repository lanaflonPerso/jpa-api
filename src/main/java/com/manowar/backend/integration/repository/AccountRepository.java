package com.manowar.backend.integration.repository;

import com.manowar.backend.model.Account;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Created by jacky on 05/06/2017.
 */

@RepositoryRestResource(collectionResourceRel = "account", path = "account")
public interface AccountRepository extends PagingAndSortingRepository<Account,Long> {
    List<Account> findAccountByEmail(@Param("email") String email);
    List<Account> findAccountByUsername(@Param("username") String username);
    List<Account> findAccountByUsernameAndPassword(@Param("username") String username,
                                                    @Param("password") String password);
}
