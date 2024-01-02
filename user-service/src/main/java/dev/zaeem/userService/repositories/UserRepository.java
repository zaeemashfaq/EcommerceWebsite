package dev.zaeem.userService.repositories;

import dev.zaeem.userService.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public <S extends User> S save(S entity);
    public Optional<User> findById(Long id);
    public Optional<User> findByUserNameAndEncPassword(String userName, String encPassword);
    public Optional<User> findByUserName(String userName);
}
