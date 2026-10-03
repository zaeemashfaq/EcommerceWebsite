package dev.zaeem.userService.dtos;

import dev.zaeem.userService.models.Role;
import dev.zaeem.userService.models.User;
import dev.zaeem.userService.models.Session;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class UserDto {
    private String userName;
    private Set<Role> roles = new HashSet<>();
    public static UserDto from(User user){
        UserDto userDto = new UserDto();
        userDto.setUserName(user.getUserName());
        userDto.setRoles(user.getRoles());
        return userDto;
    }
    public static UserDto from(Session session){
        User user = session.getUser();
        return UserDto.from(user);
    }
}
