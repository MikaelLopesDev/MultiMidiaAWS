package com.ufpi.multimidiaawsbackend.Exceptions;

public class ImageNotFoundException extends RuntimeException {
    public ImageNotFoundException() {
        super("Imagem não encontrada");
    }
}
