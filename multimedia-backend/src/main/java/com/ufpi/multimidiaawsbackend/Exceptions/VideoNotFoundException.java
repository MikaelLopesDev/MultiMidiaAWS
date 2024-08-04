package com.ufpi.multimidiaawsbackend.Exceptions;

public class VideoNotFoundException extends RuntimeException {
    public VideoNotFoundException() {
        super("Video não encontrado");
    }
}
