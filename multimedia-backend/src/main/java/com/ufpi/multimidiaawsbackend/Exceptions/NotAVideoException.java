package com.ufpi.multimidiaawsbackend.Exceptions;

public class NotAVideoException extends RuntimeException {
    public NotAVideoException() {
        super("Arquivo inválido ou arquivo não é um vídeo. Os tipos permitidos são: MP4, AVI, MOV, WEBM");
    }
}
