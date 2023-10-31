package dev.zaeem.NotificationService.repositories;

import dev.zaeem.NotificationService.models.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    <S extends Notification> S save (S entity);
    Optional<List<Notification>> findByUserId(Long id);
}
