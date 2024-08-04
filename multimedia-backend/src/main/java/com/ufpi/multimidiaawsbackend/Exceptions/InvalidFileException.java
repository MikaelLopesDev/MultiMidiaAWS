package com.ufpi.multimidiaawsbackend.Exceptions;

public class InvalidFileException extends RuntimeException {
    public InvalidFileException() {
        super("Arquivo inválido.");
    }
}
