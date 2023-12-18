package dev.zaeem.productservice.security.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class JwtObject {
    private long userId;
    private String userName;
    private Set<Role> roles;
    private SessionStatus status;

    //Just did for test cases
    public static JwtObject from(){
        return null;
    }
    public static boolean checkIfSessionActive(JwtObject authTokenObj){
        return authTokenObj.status.equals(SessionStatus.ACTIVE);
    }
}
