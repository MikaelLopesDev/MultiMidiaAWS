package com.ufpi.multimidiaawsbackend.Exceptions;

public class NotAnImageException extends RuntimeException {
    public NotAnImageException() {
        super("Arquivo inválido ou arquivo não é uma imagem. Os tipos permitidos são: JPEG, PNG, GIF, SVG, WEBP");
    }
}
