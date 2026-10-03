package dev.zaeem.NotificationService.services;
import dev.zaeem.NotificationService.dtos.CreateUserDto;
import dev.zaeem.NotificationService.models.MockUser;
import dev.zaeem.NotificationService.repositories.MockUserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class MockUserService {
    private MockUserRepository userRepository;
    public MockUserService(MockUserRepository userRepository){
        this.userRepository = userRepository;
    }
    public ResponseEntity<String> createUser(CreateUserDto userDto){
        MockUser user = MockUser.from(userDto);
        userRepository.save(user);
        ResponseEntity response = new ResponseEntity<String>("User created successfully", HttpStatus.OK);
        return response;
    }
}
