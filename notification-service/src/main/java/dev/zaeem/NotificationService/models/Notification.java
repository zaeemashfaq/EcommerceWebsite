package dev.zaeem.NotificationService.models;

import dev.zaeem.NotificationService.dtos.SendNotificationRequestDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Notification extends BaseModel{
    private long userId;
    private Channel notificationChannel;
    private long requestingServiceId;
    private long requestingServiceNotificationId;
    private DeliveryStatus deliveryStatus;
    @OneToOne(cascade = {CascadeType.PERSIST})
    private Message message;
    public static Notification from(SendNotificationRequestDto requestDto){
        Notification notification = new Notification();
        notification.setRequestingServiceId(requestDto.getRequestingServiceId());
        notification.setRequestingServiceNotificationId(requestDto.getRequestingServiceNotificationId());
        notification.setNotificationChannel(requestDto.getNotificationChannel());
        notification.setDeliveryStatus(DeliveryStatus.PENDING);
        Message message = new Message();
        message.setMessageTitle(requestDto.getMessageTitle());
        message.setMessageContent(requestDto.getMessageContent());
        notification.setMessage(message);
        notification.setUserId(requestDto.getUserId());
        return notification;
    }
}
