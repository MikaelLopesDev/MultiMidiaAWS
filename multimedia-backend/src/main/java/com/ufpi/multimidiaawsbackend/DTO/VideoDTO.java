package com.ufpi.multimidiaawsbackend.DTO;

import com.ufpi.multimidiaawsbackend.Models.Enum.MIMETypes;

import java.io.Serializable;
import java.time.LocalDateTime;

public record VideoDTO(
        Long id,
        String fileName,
        int fileSize,
        LocalDateTime uploadDate,
        MIMETypes mimeType,
        String description,
        String duration,
        String tags,
        int height,
        int width,
        int fps,
        String audioCodec,
        String videoCodec,
        String thumbnailLocation,
        String processingDetails
) implements Serializable {}
