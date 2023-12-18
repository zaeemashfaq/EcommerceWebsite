package dev.zaeem.productservice.security.models;

import dev.zaeem.productservice.models.BaseModel;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class Role {
    private long id;
    private String role;

    public static boolean checkIfSeller(JwtObject jwtTokenDto){
        boolean isSeller = false;
        Set<Role> roles = jwtTokenDto.getRoles();
        for(Role role1:roles){
            if(role1.getRole().equals("Seller")){
                isSeller = true;
                break;
            }
        }
        return isSeller;
    }
    public static boolean checkIfAdmin(JwtObject jwtTokenDto){
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
