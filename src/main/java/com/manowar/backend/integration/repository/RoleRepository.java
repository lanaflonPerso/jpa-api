package com.manowar.backend.integration.repository;

import com.manowar.backend.model.Role;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Created by jacky on 05/06/2017.
 */
@RepositoryRestResource(collectionResourceRel = "role", path = "role")
public interface RoleRepository extends PagingAndSortingRepository<Role,Long>{
        List<Role> findByUsername(@Param("username") String username);
}
