package com.ufpi.multimidiaawsbackend.Exceptions;

public class LoginIncorrectDataException extends RuntimeException{
    public LoginIncorrectDataException() {
        super("Email ou senha incorretos");
    }
}