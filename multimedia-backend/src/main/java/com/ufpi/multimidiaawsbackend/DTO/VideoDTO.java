package com.ufpi.multimidiaawsbackend.DTO;

import com.ufpi.multimidiaawsbackend.Models.Enum.MIMETypes;

import java.io.Serializable;
import java.time.LocalDateTime;

public record VideoDTO(
        Long ownerId,
        LocalDateTime uploadDate,
        String description,
        String tags
) implements Serializable {}
