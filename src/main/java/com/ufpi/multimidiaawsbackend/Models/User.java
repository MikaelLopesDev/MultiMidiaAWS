package com.ufpi.multimidiaawsbackend.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ufpi.multimidiaawsbackend.DTO.UserDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private String username;
    @JsonIgnore
    private String password;
    private String email;
    private String profileImage;
    private String description;
    private LocalDateTime creationDate;

    public User(UserDTO userDTO) {
        this.setFullName(userDTO.fullName());
        this.setUsername(userDTO.username());
        this.setPassword(userDTO.password());
        this.setEmail(userDTO.email());
        this.setProfileImage(userDTO.profileImage());
        this.setDescription(userDTO.description());
        this.setCreationDate(userDTO.creationDate());
    }
}

