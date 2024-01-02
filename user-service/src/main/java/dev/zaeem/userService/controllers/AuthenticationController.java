package dev.zaeem.userService.controllers;

import dev.zaeem.userService.dtos.*;
import dev.zaeem.userService.models.Session;
import dev.zaeem.userService.models.User;
import dev.zaeem.userService.services.AuthenticationService;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMapAdapter;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private AuthenticationService authenticationService;
    @Autowired
    public AuthenticationController(AuthenticationService authenticationService){
        this.authenticationService = authenticationService;
    }
    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginRequestDto requestDto){
        Session session = authenticationService.login(requestDto.getUserName(),requestDto.getPassword());

        UserDto userDto = UserDto.from(session);
        MultiValueMapAdapter<String,String> headers = new MultiValueMapAdapter<>(new HashMap<>());
        headers.add(HttpHeaders.SET_COOKIE,"auth-token:"+session.getToken());
        ResponseEntity<UserDto> response = new ResponseEntity<>(userDto,headers, HttpStatus.OK);
        return response;
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody LogoutRequestDto requestDto){
        User user = authenticationService.logout(requestDto.getToken(), requestDto.getId());
        String message;
        if(user==null){
            message = "Sorry. Invalid session credentials.";
        }
        else{
            message = user.getUserName()+" logged out successfully.";
        }
        ResponseEntity<String> response = new ResponseEntity<>(message,HttpStatus.OK);
        return response;
    }
    @PostMapping("/signup")
    public ResponseEntity<UserDto> signup(@RequestBody SignUpRequestDto requestDto){
        User user = authenticationService.signup(requestDto.getUserName(),requestDto.getPassword());
        UserDto userDto = UserDto.from(user);
        ResponseEntity<UserDto> response = new ResponseEntity<>(userDto,HttpStatus.OK);
        return response;
    }
    @PostMapping("/validate")
//    public ResponseEntity<SessionStatus> validate(@RequestBody ValidateRequestDto requestDto){
//    public ResponseEntity<SessionStatus> validate(@RequestHeader("Cookie")Map<String,String> cookie,
    public ResponseEntity<JwtTokenDto> validate(@Nullable @RequestHeader(name = HttpHeaders.AUTHORIZATION) String authToken,
                                                  @RequestBody ValidateRequestDto requestDto){

        System.out.println(authToken);
        JwtTokenDto jwtTokenDto = authenticationService.validate(requestDto.getToken());
//        SessionStatus status = authenticationService.validate(request.getCookies());
        ResponseEntity<JwtTokenDto> response = new ResponseEntity<>(jwtTokenDto,HttpStatus.OK);
        return response;
    }
}
