package dev.zaeem.NotificationService.dtos;

import dev.zaeem.NotificationService.models.Channel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendNotificationRequestDto {
    private long requestingServiceId;
    private long requestingServiceNotificationId;
    private long userId;
    private String messageTitle;
    private String messageContent;
    private Channel notificationChannel;
}
