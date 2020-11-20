package com.reqhu.testtask2.repository;

import com.reqhu.testtask2.domain.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, String> {

    @EntityGraph(attributePaths = {"roles"})
    @Query("SELECT u FROM User u WHERE u.login=:login")
    Optional<User> findWithRoles(@Param("login") String login);

}
