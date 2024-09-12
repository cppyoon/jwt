
package com.chyoon.jwt.repository;

import com.chyoon.jwt.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserJPARepository extends JpaRepository<Users, Long> {

    Optional<Users> findByUserId(String userId);

    boolean existsByUserId(String userId);

}