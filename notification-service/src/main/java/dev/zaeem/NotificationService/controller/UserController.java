package dev.zaeem.NotificationService.controller;

import dev.zaeem.NotificationService.dtos.CreateUserDto;
import dev.zaeem.NotificationService.models.MockUser;
import dev.zaeem.NotificationService.services.MockUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private MockUserService mockUserService;
    public UserController(MockUserService mockUserService){
        this.mockUserService = mockUserService;
    }
    @PostMapping("/user")
    ResponseEntity<String> createUser(@RequestBody CreateUserDto userDto){
        return mockUserService.createUser(userDto);
    }
}
