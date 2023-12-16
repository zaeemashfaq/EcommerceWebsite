package dev.zaeem.userService.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize(as = User.class)
public class User extends BaseModel{
    private String userName;
    @Column(length = 256)
    private String encPassword;
    @ManyToMany
    @JsonIgnore
    private Set<Role> roles = new HashSet<>();
}
