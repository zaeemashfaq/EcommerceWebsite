package dev.zaeem.NotificationService.dtos;

import org.springframework.http.HttpStatus;

public class ExceptionDto {
    private HttpStatus errorCode;
    private String message;
    public ExceptionDto(HttpStatus status, String message){
        this.errorCode = status;
        this.message = message;
    }
}
