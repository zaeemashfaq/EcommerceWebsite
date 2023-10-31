package dev.zaeem.NotificationService.models;

import com.fasterxml.jackson.databind.ser.Serializers;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Message extends BaseModel {
    private String messageTitle;
    private String messageContent;
}
