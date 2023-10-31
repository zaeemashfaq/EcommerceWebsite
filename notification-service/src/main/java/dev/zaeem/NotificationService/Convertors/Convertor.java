package dev.zaeem.NotificationService.Convertors;

import dev.zaeem.NotificationService.dtos.SendNotificationRequestDto;
import dev.zaeem.NotificationService.models.DeliveryStatus;
import dev.zaeem.NotificationService.models.Message;
import dev.zaeem.NotificationService.models.Notifcation;

public class Convertor {
    public Notifcation convertSendNotificationRequestDtoToNotification(
            SendNotificationRequestDto requestDto){
        Notifcation notifcation = new Notifcation();
        notifcation.setRequestingServiceId(requestDto.getRequestingServiceId());
        notifcation.setRequestingServiceNotificationId(requestDto.getRequestingServiceNotificationId());
        notifcation.setNotificationChannel(requestDto.getNotificationChannel());
        notifcation.setDeliveryStatus(DeliveryStatus.PENDING);
        Message message = new Message();
        message.setMessageTitle(requestDto.getMessageTitle());
        message.setMessageContent(requestDto.getMessageContent());
        notifcation.setMessage(message);
        notifcation.setUserId(requestDto.getUserId());
    }
}
