package dev.zaeem.userService.repositories;

import dev.zaeem.userService.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, Long> {
    <S extends Role> S save (S entity);
    public List<Role> findAllByIdIn(List<Long> roleIds);
    public Role findByRole(String name);
}
