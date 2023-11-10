package dev.zaeem.NotificationService.controller;

import dev.zaeem.NotificationService.dtos.MessageDto;
import dev.zaeem.NotificationService.dtos.SendNotificationRequestDto;
import dev.zaeem.NotificationService.exceptions.EmptyNotificationListException;
import dev.zaeem.NotificationService.exceptions.NotFoundException;
import dev.zaeem.NotificationService.models.Message;
import dev.zaeem.NotificationService.services.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @PostMapping("/fetch/user/{id}")
    public ResponseEntity<List<MessageDto>> fetchNotificationHistoryByUser(
            @PathVariable long id) throws EmptyNotificationListException {
        return notificationService.fetchNotificationHistoryByUser(id);
    }
}
