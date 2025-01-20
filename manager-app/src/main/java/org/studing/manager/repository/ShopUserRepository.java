package org.studing.manager.repository;

import org.springframework.data.repository.CrudRepository;
import org.studing.manager.entity.ShopUser;

import java.util.Optional;

public interface ShopUserRepository extends CrudRepository<ShopUser, Integer> {
    Optional<ShopUser> findByUsername(String username);
}
