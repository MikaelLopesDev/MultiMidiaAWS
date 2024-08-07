package com.ufpi.multimidiaawsbackend.Exceptions;

public class NotAnAudioException extends RuntimeException {
    public NotAnAudioException() {
        super("Arquivo inválido ou arquivo não é um áudio. Os tipos permitidos são: MP3, WAV, OGG, FLAC");
    }
}
