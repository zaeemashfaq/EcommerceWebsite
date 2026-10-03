package dev.zaeem.userService.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Session extends BaseModel {
    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;
    @Column(length = 512)
    private String token;
    private Date expiringAt;
    @Enumerated(EnumType.STRING)
    private SessionStatus sessionStatus;
}
