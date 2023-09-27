package com.omerfurkan.studentforum.exceptions;

import java.time.ZonedDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class StuNetExceptionHandler {

    @ExceptionHandler(value = {ResourceAlreadyExistsException.class, ConflictException.class})
    public ResponseEntity<StuNetError> genericHandler(ResourceAlreadyExistsException e) {
        StuNetError error = new StuNetError();
        error.setMessage(e.getMessage())
            .setStatus(HttpStatus.CONFLICT.value())
            .setTimestamp(ZonedDateTime.now());

        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    public ResponseEntity<StuNetError> genericHandler(ResourceNotFoundException e) {
        StuNetError error = new StuNetError();
        error.setMessage(e.getMessage()).setTimestamp(ZonedDateTime.now()).setStatus(HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {BadRequestException.class})
    public ResponseEntity<StuNetError> genericHandler(BadRequestException e) {
        StuNetError error = new StuNetError();
        error.setMessage(e.getMessage()).setTimestamp(ZonedDateTime.now()).setStatus(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {InternalServerErrorException.class})
    public ResponseEntity<StuNetError> genericHandler(InternalServerErrorException e) {
        StuNetError error = new StuNetError();
        error.setMessage(e.getMessage()).setTimestamp(ZonedDateTime.now()).setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {PreconditionFailedException.class})
    public ResponseEntity<StuNetError> genericHandler(PreconditionFailedException e) {
        StuNetError error = new StuNetError();
        error.setMessage(e.getMessage()).setTimestamp(ZonedDateTime.now()).setStatus(HttpStatus.PRECONDITION_FAILED.value());
        return new ResponseEntity<>(error, HttpStatus.PRECONDITION_FAILED);
    }
}
