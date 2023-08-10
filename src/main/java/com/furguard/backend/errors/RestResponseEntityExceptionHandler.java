package com.furguard.backend.errors;

import com.furguard.backend.entities.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@ResponseStatus
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<ResponseMessage> AlreadyExistException(AlreadyExistException exception){
        ResponseMessage message = new ResponseMessage(409, HttpStatus.CONFLICT, exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ResponseMessage> BadRequestException(BadRequestException exception){
        ResponseMessage message = new ResponseMessage(400, HttpStatus.BAD_REQUEST, exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    @ExceptionHandler(InvalidUserException.class)
    public ResponseEntity<ResponseMessage> InvalidUserException(InvalidUserException exception){
        ResponseMessage message = new ResponseMessage(403, HttpStatus.FORBIDDEN, exception.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(message);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ResponseMessage> NotFoundException(NotFoundException exception){
        ResponseMessage message = new ResponseMessage(404, HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }
}
