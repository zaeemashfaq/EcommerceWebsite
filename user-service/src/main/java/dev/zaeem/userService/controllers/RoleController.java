package dev.zaeem.userService.controllers;

import dev.zaeem.userService.dtos.CreateRoleDto;
import dev.zaeem.userService.dtos.JwtTokenDto;
import dev.zaeem.userService.exceptions.UnauthenticatedUserException;
import dev.zaeem.userService.exceptions.UnauthorizedUserException;
import dev.zaeem.userService.models.Role;
import dev.zaeem.userService.models.SessionStatus;
import dev.zaeem.userService.services.AuthenticationService;
import dev.zaeem.userService.services.RoleService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/roles")
public class RoleController {
    private RoleService roleService;
    private AuthenticationService authenticationService;
    public RoleController(RoleService roleService, AuthenticationService authenticationService){
        this.roleService = roleService;
        this.authenticationService = authenticationService;
    }
    @PostMapping
    public ResponseEntity<Role> createRole(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
            @RequestBody CreateRoleDto roleDto)
            throws UnauthenticatedUserException,
            UnauthorizedUserException {
        JwtTokenDto jwtTokenDto = authenticationService.validate(token);
        if(!jwtTokenDto.getStatus().equals(SessionStatus.ACTIVE)){
            throw new UnauthenticatedUserException("User not authenticated. Please login.");
        }
        boolean isAdmin = checkIfAdmin(jwtTokenDto);
        if(isAdmin){
            Role role = roleService.createRole(roleDto.getRole());
            return new ResponseEntity<>(role, HttpStatus.OK);
        }
        else{
            throw new UnauthorizedUserException("User not authorized to create roles!");
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
