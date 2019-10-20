/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.m7summativeguessthenumber.controllers;

import com.sg.m7summativeguessthenumber.data.GuessTheNumberDaoException;
import com.sg.m7summativeguessthenumber.service.InvalidDataException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 *
 * @author daler
 */
@ControllerAdvice
@RestController
public class GuessTheNumberControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(GuessTheNumberDaoException.class)
    public ResponseEntity<String> handleGuessTheNumberDaoException(GuessTheNumberDaoException ex, 
            WebRequest request) {
        return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<String> handleInvalidDataException(InvalidDataException ex,
            WebRequest request) {
        return new ResponseEntity(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
