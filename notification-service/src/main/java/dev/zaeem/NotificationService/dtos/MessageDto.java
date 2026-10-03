package dev.zaeem.NotificationService.dtos;

import dev.zaeem.NotificationService.models.Notification;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class MessageDto {
    private String messageTitle;
    private String messageContent;
    private Instant instant;

    public static MessageDto from(Notification notification){
        MessageDto messageDto = new MessageDto();
        messageDto.setMessageTitle(notification.getMessage().getMessageTitle());
        messageDto.setMessageContent(notification.getMessage().getMessageContent());
        messageDto.setInstant(notification.getInstant());
        return messageDto;
    }
}
