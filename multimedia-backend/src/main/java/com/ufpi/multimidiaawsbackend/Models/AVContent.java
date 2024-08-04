package com.ufpi.multimidiaawsbackend.Models;

import com.ufpi.multimidiaawsbackend.Models.Enum.GENRE;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class AVContent extends Media{
    private int duration;
    private int bitRate;
    private GENRE genre;
}
