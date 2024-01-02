package dev.zaeem.userService.services;

import dev.zaeem.userService.dtos.UserDto;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserDto getUserDetails(long id);
//    void loginUser(User user);
    UserDto setUserRoles(long id, String role);
}
