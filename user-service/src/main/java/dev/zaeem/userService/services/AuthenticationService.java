package dev.zaeem.userService.services;

import dev.zaeem.userService.dtos.JwtTokenDto;
import dev.zaeem.userService.dtos.UserDto;
import dev.zaeem.userService.models.Role;
import dev.zaeem.userService.models.Session;
import dev.zaeem.userService.models.SessionStatus;
import dev.zaeem.userService.models.User;
import dev.zaeem.userService.repositories.RoleRepository;
import dev.zaeem.userService.repositories.SessionRepository;
import dev.zaeem.userService.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMapAdapter;

import javax.crypto.SecretKey;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;


@Service
public class AuthenticationService {
    private UserRepository userRepository;
    private SessionRepository sessionRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RoleRepository roleRepository;

    public AuthenticationService(UserRepository userRepository, SessionRepository sessionRepository,
                                 BCryptPasswordEncoder bCryptPasswordEncoder,
                                 RoleRepository roleRepository){
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleRepository = roleRepository;
    }
//    API for Login
    public Session login(String userName, String password){
        Optional<User> userOptional = userRepository.findByUserName(userName);
        if(userOptional.isEmpty()){
            return null;
        }
        User user = userOptional.get();
        if(!bCryptPasswordEncoder.matches(password,user.getEncPassword())){
            return null;
        }
        MacAlgorithm alg = Jwts.SIG.HS256;
        String secretKey = "JWT_SECRET_KEY_JWT_SECRET_KEY_JWT_SECRET_KEY";
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());


// Create the compact JWS:
        Date expiryAt = Date.from(LocalDate.now().plusDays(3)
                .atStartOfDay(ZoneId.systemDefault()).toInstant());
        HashMap<String, Object> jsonForJwt = new HashMap<>();
        jsonForJwt.put("id",user.getId());
        jsonForJwt.put("userName",user.getUserName());
        jsonForJwt.put("roles",user.getRoles());
        jsonForJwt.put("createdAt",new Date());
        jsonForJwt.put("expiryAt", expiryAt);
//        jsonForJwt.put("expiryAt", new Date(LocalDate.now().plusDays(3).toEpochDay()));

        String jws = Jwts.builder().claims(jsonForJwt).signWith(key, alg).compact();

//        String token = RandomStringUtils.randomAlphanumeric(30);
//        System.out.println(token);
        Session session = new Session();
        session.setUser(user);
        session.setToken(jws);
        session.setExpiringAt(expiryAt);
        session.setSessionStatus(SessionStatus.ACTIVE);
        sessionRepository.save(session);
        return session;
    }

//    API for Logging out
    public User logout(String token, long id){
        Optional<Session> sessionOptional = sessionRepository.findByToken(token);
        if(sessionOptional.isEmpty()){
            return null;
        }
        Session session = sessionOptional.get();
        User user = session.getUser();
        session.setSessionStatus(SessionStatus.ENDED);
        sessionRepository.save(session);
        return user;
    }

//    API for Signing Up
    public User signup(String userName, String encPassword){
        User user = new User();
        user.setUserName(userName);
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByRole("NormalUser"));
        user.setRoles(roles);
        user.setEncPassword(bCryptPasswordEncoder.encode(encPassword));
        userRepository.save(user);
        return user;
    }

//    API for Token Validation
    public JwtTokenDto validate(String token){
        Optional<Session> sessionOptional = sessionRepository.findByToken(token);
        if(sessionOptional.isEmpty()){
            System.out.println("Empty session object");
            return JwtTokenDto.from(SessionStatus.INVALID_TOKEN);
        }
        Session session = sessionOptional.get();
        User user = session.getUser();
        if(session.getSessionStatus().equals(SessionStatus.ENDED)){
            System.out.println("Ended session");
            return JwtTokenDto.from(user,session.getSessionStatus());
        }
//        token = session.getToken();
        String secretKey = "JWT_SECRET_KEY_JWT_SECRET_KEY_JWT_SECRET_KEY";
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());
//        Jws<Claims> claimsJws = Jwts.parser().build().parseSignedClaims(token);
        Jws<Claims> claimsJws = Jwts.parser()
                .setSigningKey(key) // Set the signing key for verification
                .build()
                .parseClaimsJws(token);
        /*long userId = claimsJws.getPayload().get("id",Long.class);
        String userName = claimsJws.getPayload().get("userName",String.class);*/
        Date expiryAt = claimsJws.getPayload().get("expiryAt",Date.class);
        /*Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty()){
            System.out.println("Invalid user");
            return SessionStatus.INVALID_USER;
        }
        User user = userOptional.get();
        if(user.getId()!=userId){
            System.out.println("User id's don't match");
            return SessionStatus.INVALID_USER;
        }*/
        if(expiryAt.before(new Date())){
            System.out.println("Time expired");
            session.setSessionStatus(SessionStatus.ENDED);
            sessionRepository.save(session);
        }
        return JwtTokenDto.from(user,session.getSessionStatus());
    }
}
