package com.ufpi.multimidiaawsbackend.DTO;

import java.io.Serializable;
import java.time.LocalDateTime;

public record UserDTO(
        Long id,
        String fullName,
        String username,
        String password,
        String email,
        String profileImage,
        String description,
        LocalDateTime creationDate
) implements Serializable {}