package dev.zaeem.NotificationService.services;

import dev.zaeem.NotificationService.dtos.SendNotificationRequestDto;
import dev.zaeem.NotificationService.exceptions.NotFoundException;
import dev.zaeem.NotificationService.models.*;
import dev.zaeem.NotificationService.repositories.MockUserRepository;
import dev.zaeem.NotificationService.repositories.NotificationRepository;
import dev.zaeem.NotificationService.twilio.SmsController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Scanner;

@Service
public class NotificationService {
    private NotificationRepository notificationRepository;
    private MockUserRepository mockUserRepository;
    private SmsController smsController;

    public NotificationService(NotificationRepository notificationRepository,
                               MockUserRepository mockUserRepository,
                               SmsController smsController){
        this.notificationRepository = notificationRepository;
        this.mockUserRepository  = mockUserRepository;
        this.smsController = smsController;
    }

    public ResponseEntity<String> sendNotification(SendNotificationRequestDto requestDto)
            throws NotFoundException {
        Notification notification = Notification.from(requestDto);
        notificationRepository.save(notification);
        Channel channel = notification.getNotificationChannel();
        Optional<MockUser> optionalUser = mockUserRepository.findById(notification.getUserId());
        if(optionalUser.isEmpty()){
            throw new NotFoundException("User with id "+ notification.getUserId()+" does not exist!");
        }
        MockUser user = optionalUser.get();
        Message message = notification.getMessage();
        if(channel.equals(Channel.SMS)){
            return sendNotificationBySms(message,user.getMobileNumber());
        }
        else if(channel.equals(Channel.EMAIL)){
            sendNotificationByEmail(message,user.getEmailId());
            return new ResponseEntity<String>(
                    "Email service coming soon!", HttpStatus.NOT_FOUND);
        }
        else{
            sendNotificationByPush(message,user.getDeviceId());
            return new ResponseEntity<String>(
                    "Push notification service coming soon!", HttpStatus.NOT_FOUND);
        }
    }
    private ResponseEntity<String> sendNotificationBySms(Message message, String toNumber){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter twilio phone number");
        String fromNumber = sc.nextLine();
        String messageContent = message.getMessageTitle() + "\n" + message.getMessageContent();
        return smsController.sendSMS(fromNumber,toNumber,messageContent);
    }
    private void sendNotificationByEmail(Message message, String emailId){}
    private void sendNotificationByPush(Message message,String deviceId){}
}
