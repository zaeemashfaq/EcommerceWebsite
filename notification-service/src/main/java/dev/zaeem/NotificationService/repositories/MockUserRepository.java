package dev.zaeem.NotificationService.repositories;

import dev.zaeem.NotificationService.models.MockUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MockUserRepository extends JpaRepository<MockUser, Long> {
    <S extends MockUser> S save (S Entity);
    Optional<MockUser> findById(long id);
}
