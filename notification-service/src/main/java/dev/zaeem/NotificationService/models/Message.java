package dev.zaeem.NotificationService.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Message extends BaseModel {
    private String messageTitle;
    private String messageContent;
    public static Message from(Notification notification){
        Message message = new Message();
        message.setMessageTitle(notification.getMessage().getMessageTitle());
        message.setMessageContent(notification.getMessage().getMessageContent());
        return message;
    }
}
