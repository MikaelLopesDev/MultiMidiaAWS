package com.ufpi.multimidiaawsbackend.Exceptions;

public class AudioNotFoundException extends RuntimeException {
    public AudioNotFoundException() {
        super("Audio não encontrado");
    }
}
