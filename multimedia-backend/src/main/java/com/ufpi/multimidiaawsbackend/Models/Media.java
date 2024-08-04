package com.ufpi.multimidiaawsbackend.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ufpi.multimidiaawsbackend.Models.Enum.MIMETypes;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@MappedSuperclass
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fileName;
    private long fileSize;
    private LocalDateTime uploadDate;
    @Enumerated(EnumType.STRING)
    private MIMETypes MIME;
    private String Description;
    private String tags;
    @ManyToOne
    @JsonBackReference
    @NonNull
    private User owner;

    @JsonIgnore
    public List<String> getTagsAsList(){
        return Arrays.stream(toString().split(",")).toList();
    }
}
