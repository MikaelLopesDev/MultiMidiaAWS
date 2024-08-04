package com.ufpi.multimidiaawsbackend.Infra;

import com.ufpi.multimidiaawsbackend.DTO.ExceptionDTO;
import com.ufpi.multimidiaawsbackend.Exceptions.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {
    //TODO: Adicionar exceptions faltantes

    @ExceptionHandler(PasswordRulesException.class)
    public ResponseEntity InvalidPassword(PasswordRulesException exception){
        ExceptionDTO exceptionDTO = new ExceptionDTO(exception.getMessage(), "400");
        return ResponseEntity.badRequest().body(exceptionDTO);
    }

    @ExceptionHandler(InvalidEmailException.class)
    public ResponseEntity InvalidEmail(InvalidEmailException exception){
        ExceptionDTO exceptionDTO = new ExceptionDTO(exception.getMessage(), "400");
        return ResponseEntity.badRequest().body(exceptionDTO);
    }

    @ExceptionHandler(LoginIncorrectDataException.class)
    public ResponseEntity LoginDataIncorrect(LoginIncorrectDataException exception){
        ExceptionDTO exceptionDTO = new ExceptionDTO(exception.getMessage(), "404");
        return ResponseEntity.status(404).body(exceptionDTO);
    }

    @ExceptionHandler(FieldAlreadyInUseException.class)
    public ResponseEntity ThreatDuplicationEntry(FieldAlreadyInUseException exception){
        ExceptionDTO exceptionDTO = new ExceptionDTO(exception.getMessage(), "400");
        return ResponseEntity.badRequest().body(exceptionDTO);
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity UserNotFound(UserNotFoundException exception){
        ExceptionDTO exceptionDTO = new ExceptionDTO(exception.getMessage(), "404");
        return ResponseEntity.badRequest().body(exceptionDTO);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity threatGeneralException(Exception exception){
        ExceptionDTO exceptionDTO = new ExceptionDTO(exception.getMessage(), "500");
        return ResponseEntity.internalServerError().body(exceptionDTO);
    }
}
