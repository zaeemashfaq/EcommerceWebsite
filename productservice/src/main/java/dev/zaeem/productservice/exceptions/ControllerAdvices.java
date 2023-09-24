package dev.zaeem.productservice.exceptions;

import dev.zaeem.productservice.dtos.ExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

//ControllerAdvice sits in the middle of Dispatcher and Controller
@ControllerAdvice
public class ControllerAdvices {
    @ExceptionHandler(NotFoundException.class)
    private ResponseEntity<ExceptionDto> handleNotFoundException(NotFoundException notFoundException){
        System.out.println("Not found exception occured!");
        return new ResponseEntity(
                new ExceptionDto(HttpStatus.NOT_FOUND,notFoundException.getMessage()),HttpStatus.NOT_FOUND
        );
    }
}
