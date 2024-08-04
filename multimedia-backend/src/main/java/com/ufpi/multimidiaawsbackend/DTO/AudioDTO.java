package com.ufpi.multimidiaawsbackend.DTO;

import com.ufpi.multimidiaawsbackend.Models.Enum.MIMETypes;

import java.io.Serializable;
import java.time.LocalDateTime;

public record AudioDTO(
        Long id,
        String fileName,
        int fileSize,
        LocalDateTime uploadDate,
        MIMETypes mimeType,
        String description,
        int duration,
        String tags,
        int samplingRate,
        int channels
) implements Serializable {}
