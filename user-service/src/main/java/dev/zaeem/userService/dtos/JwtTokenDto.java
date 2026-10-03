package dev.zaeem.userService.dtos;

import dev.zaeem.userService.models.Role;
import dev.zaeem.userService.models.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import dev.zaeem.userService.models.SessionStatus;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class JwtTokenDto {
    private long userId;
    private String userName;
    private Set<Role> roles;
    private SessionStatus status;

    public static JwtTokenDto from(SessionStatus sessionStatus){
        JwtTokenDto jwtTokenDto = new JwtTokenDto();
        jwtTokenDto.setStatus(sessionStatus);
        return  jwtTokenDto;
    }
    public static JwtTokenDto from(User user){
        JwtTokenDto jwtTokenDto = new JwtTokenDto();
        jwtTokenDto.setUserId(user.getId());
        jwtTokenDto.setUserName(user.getUserName());
        jwtTokenDto.setRoles(user.getRoles());
        return jwtTokenDto;
    }
    public static JwtTokenDto from(User user,SessionStatus sessionStatus){
        JwtTokenDto jwtTokenDto = JwtTokenDto.from(user);
        jwtTokenDto.setStatus(sessionStatus);
        return  jwtTokenDto;
    }
}
