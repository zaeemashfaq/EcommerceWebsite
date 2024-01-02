package dev.zaeem.userService.controllers;

import dev.zaeem.userService.dtos.AddRoleDto;
import dev.zaeem.userService.dtos.JwtTokenDto;
import dev.zaeem.userService.dtos.UserDto;
import dev.zaeem.userService.exceptions.UnauthenticatedUserException;
import dev.zaeem.userService.exceptions.UnauthorizedUserException;
import dev.zaeem.userService.models.Role;
import dev.zaeem.userService.models.SessionStatus;
import dev.zaeem.userService.services.AuthenticationService;
import dev.zaeem.userService.services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;
    private AuthenticationService authenticationService;

    public UserController(UserService userService, AuthenticationService authenticationService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/{id}")
    public ResponseEntity<UserDto> getUserDetails(@PathVariable("id") long id) {
        return new ResponseEntity<UserDto>(userService.getUserDetails(id), HttpStatus.OK);
    }

    @PostMapping("/roles/{id}")
    public ResponseEntity<UserDto> setUserRoles(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
            @PathVariable("id") long id,
            @RequestBody AddRoleDto roleDto)
            throws UnauthenticatedUserException,
            UnauthorizedUserException {
        JwtTokenDto jwtTokenDto = authenticationService.validate(token);
        if (!jwtTokenDto.getStatus().equals(SessionStatus.ACTIVE)) {
            throw new UnauthenticatedUserException("User not authenticated. Please login.");
        }
        boolean isAdmin = checkIfAdmin(jwtTokenDto);
        if (isAdmin) {
            return new ResponseEntity<UserDto>(userService.setUserRoles(id, roleDto.getRole()), HttpStatus.OK);
        }
        else {
            throw new UnauthorizedUserException("User not authorized to set roles!");
        }
    }
    boolean checkIfAdmin(JwtTokenDto jwtTokenDto){
        boolean isAdmin = false;
        Set<Role> roles = jwtTokenDto.getRoles();
        for(Role role1:roles){
            if(role1.getRole().equals("Admin")){
                isAdmin = true;
                break;
            }
        }
        return isAdmin;
    }
}
