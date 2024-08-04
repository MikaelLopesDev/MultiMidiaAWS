package com.ufpi.multimidiaawsbackend.Models;

import com.ufpi.multimidiaawsbackend.DTO.VideoDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "videos")
@Table(name = "videos")
public class Video extends AVContent {
    private int height;
    private int width;
    private int fps;
    private String audioCodec;
    private String videoCodec;
    private String thumbnailLocation;
    private String processingDetails;

    public Video(VideoDTO videoDTO) {
        this.setId(videoDTO.id());
        this.setFileName(videoDTO.fileName());
        this.setFileSize(videoDTO.fileSize());
        this.setUploadDate(videoDTO.uploadDate());
        this.setMIME(videoDTO.mimeType());
        this.setDescription(videoDTO.description());
        this.setHeight(videoDTO.height());
        this.setWidth(videoDTO.width());
        this.setFps(videoDTO.fps());
        this.setAudioCodec(videoDTO.audioCodec());
        this.setVideoCodec(videoDTO.videoCodec());
        this.setThumbnailLocation(videoDTO.thumbnailLocation());
        this.setProcessingDetails(videoDTO.processingDetails());
    }
}
