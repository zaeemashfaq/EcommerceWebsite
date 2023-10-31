package dev.zaeem.NotificationService.controller;

import dev.zaeem.NotificationService.dtos.SendNotificationRequestDto;
import dev.zaeem.NotificationService.exceptions.NotFoundException;
import dev.zaeem.NotificationService.services.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    private NotificationService notificationService;

    public NotificationController(NotificationService notificationService){
        this.notificationService = notificationService;
    }
    @PostMapping("/send")
    public ResponseEntity<String> sendNotification(@RequestBody SendNotificationRequestDto requestDto)
            throws NotFoundException {
        return notificationService.sendNotification(requestDto);
    }
}
