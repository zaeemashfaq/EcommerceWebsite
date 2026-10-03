package dev.zaeem.userService.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UnauthorizedUserException extends Exception{
    public UnauthorizedUserException(String message){
        super(message);
    }
}
