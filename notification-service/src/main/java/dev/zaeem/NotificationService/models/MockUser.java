package dev.zaeem.NotificationService.models;

import dev.zaeem.NotificationService.dtos.CreateUserDto;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class MockUser extends BaseModel{
    private String userName;
    private String mobileNumber;
    private String emailId;
    private String deviceId;
    public static MockUser from(CreateUserDto userDto){
        MockUser user = new MockUser();
        user.setUserName(userDto.getUserName());
        user.setDeviceId(userDto.getDeviceId());
        user.setEmailId(userDto.getEmailId());
        user.setMobileNumber(userDto.getMobileNumber());
        return user;
    }
}
