package dev.zaeem.NotificationService.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserDto {
    private String userName;
    private String mobileNumber;
    private String emailId;
    private String deviceId;
}
