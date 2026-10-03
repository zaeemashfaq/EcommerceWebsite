package dev.zaeem.NotificationService.services;

import dev.zaeem.NotificationService.dtos.MessageDto;
import dev.zaeem.NotificationService.dtos.SendNotificationRequestDto;
import dev.zaeem.NotificationService.exceptions.EmptyNotificationListException;
import dev.zaeem.NotificationService.exceptions.NotFoundException;
import dev.zaeem.NotificationService.models.*;
import dev.zaeem.NotificationService.repositories.MockUserRepository;
import dev.zaeem.NotificationService.repositories.NotificationRepository;
import dev.zaeem.NotificationService.twilio.SmsController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

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
        System.out.println("RequestDto userId: "+requestDto.getUserId());
        Notification notification = Notification.from(requestDto);
        notificationRepository.save(notification);
        System.out.println("Notification user id: "+notification.getUserId());
        Channel channel = notification.getNotificationChannel();
        Optional<MockUser> optionalUser = mockUserRepository.findById(notification.getUserId());
        if(optionalUser.isEmpty()){
            throw new NotFoundException("User with id "+ notification.getUserId()+" does not exist!");
        }
        MockUser user = optionalUser.get();
        System.out.println("User's mobile number: "+user.getMobileNumber());
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
        System.out.println("Enter twilio phone number: ");
        String fromNumber = sc.nextLine();
        System.out.println(fromNumber);
        String messageContent = message.getMessageTitle() + "\n" + message.getMessageContent();
        return smsController.sendSMS(fromNumber,toNumber,messageContent);
    }
    private void sendNotificationByEmail(Message message, String emailId){}
    private void sendNotificationByPush(Message message,String deviceId){}
    public ResponseEntity<List<MessageDto>> fetchNotificationHistoryByUser(long userId)
            throws EmptyNotificationListException {
        Optional<List<Notification>> notificationsOptional = notificationRepository.findByUserId(userId);
        if(notificationsOptional.isEmpty()){
            throw  new EmptyNotificationListException(
                    "There are no notifications here yet! Please check later.");
        }
        List<Notification> notifications = notificationsOptional.get();
        Collections.sort(notifications);
        List<MessageDto> messages = new ArrayList<>();
        for (Notification notification : notifications){
            MessageDto message = MessageDto.from(notification);
            messages.add(message);
        }
        ResponseEntity<List<MessageDto>> response = new ResponseEntity<>(messages,HttpStatus.OK);
        return response;
    }
}
