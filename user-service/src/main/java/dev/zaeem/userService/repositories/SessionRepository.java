package dev.zaeem.userService.repositories;

import dev.zaeem.userService.models.Session;
import dev.zaeem.userService.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    <S extends Session> S save (S entity);
    Optional<Session> findByUser(User user);
    Optional<Session> findById(Long id);
    Optional<Session> findByToken(String token);
    void deleteById(Long id);
}
