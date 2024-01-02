package dev.zaeem.userService.services;

import dev.zaeem.userService.dtos.UserDto;
import dev.zaeem.userService.models.Role;
import dev.zaeem.userService.models.User;
import dev.zaeem.userService.repositories.RoleRepository;
import dev.zaeem.userService.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }
    @Override
    public UserDto getUserDetails(long id) {
        Optional<User> optionalUser =  userRepository.findById(id);
        if(optionalUser.isEmpty()){
            return null;
        }
        User user = optionalUser.get();
        return UserDto.from(user);
    }

    @Override
    public UserDto setUserRoles(long id, String role) {
        Optional<User> optionalUser =  userRepository.findById(id);
        if(optionalUser.isEmpty()){
            return null;
        }
        User user = optionalUser.get();
        Role role1 = roleRepository.findByRole(role);
        user.add(role1);
        User savedUser = userRepository.save(user);
        return UserDto.from(savedUser);
    }
}
