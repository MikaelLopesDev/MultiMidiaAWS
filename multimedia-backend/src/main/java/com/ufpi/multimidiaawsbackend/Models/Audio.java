package com.ufpi.multimidiaawsbackend.Models;

import com.ufpi.multimidiaawsbackend.DTO.AudioDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "audios")
@Table(name = "audios")
public class Audio extends AVContent {
    private int samplingRate;
    private int channels;

    public Audio(AudioDTO audioDTO) {
        this.setId(audioDTO.id());
        this.setFileName(audioDTO.fileName());
        this.setFileSize(audioDTO.fileSize());
        this.setUploadDate(audioDTO.uploadDate());
        this.setMIME(audioDTO.mimeType());
        this.setDescription(audioDTO.description());
        this.setTags(audioDTO.tags());
        this.setDuration(audioDTO.duration());
        this.setSamplingRate(audioDTO.samplingRate());
        this.setChannels(audioDTO.channels());
    }
}
