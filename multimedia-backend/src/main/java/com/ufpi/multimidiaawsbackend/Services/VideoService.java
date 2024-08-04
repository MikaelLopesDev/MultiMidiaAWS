package com.ufpi.multimidiaawsbackend.Services;

import com.ufpi.multimidiaawsbackend.DTO.VideoDTO;
import com.ufpi.multimidiaawsbackend.DTO.VideoDTO;
import com.ufpi.multimidiaawsbackend.Exceptions.VideoNotFoundException;
import com.ufpi.multimidiaawsbackend.Models.Video;
import com.ufpi.multimidiaawsbackend.Models.Video;
import com.ufpi.multimidiaawsbackend.Repositories.VideoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VideoService {
    private final VideoRepository repository;
    public VideoService(VideoRepository repository) {
        this.repository = repository;
    }
    
    public List<Video> getAllVideos() {
        return this.repository.findAll();
    }
    
    public Video findVideoById(Long id){
        return repository.findVideoById(id).orElseThrow(VideoNotFoundException::new);
    }
    
    public Video createVideo(VideoDTO videoDTO){
        Video newVideo = new Video(videoDTO);
        this.repository.save(newVideo);
        return newVideo;
    }

    public Video updateVideo(Long id, VideoDTO videoDTO) {
        Optional<Video> videoOptional = this.repository.findById(id);
        if (videoOptional.isPresent()) {
            Video video = videoOptional.get();
            video.setId(videoDTO.id());
            video.setFileName(videoDTO.fileName());
            video.setFileSize(videoDTO.fileSize());
            video.setUploadDate(videoDTO.uploadDate());
            video.setMIME(videoDTO.mimeType());
            video.setDescription(videoDTO.description());
            video.setHeight(videoDTO.height());
            video.setWidth(videoDTO.width());
            video.setFps(videoDTO.fps());
            video.setAudioCodec(videoDTO.audioCodec());
            video.setVideoCodec(videoDTO.videoCodec());
            video.setThumbnailLocation(videoDTO.thumbnailLocation());
            video.setProcessingDetails(videoDTO.processingDetails());
            return this.repository.save(video);
        } else {
            return null;
        }
    }
    
    public void deleteVideoById(Long id) {
        Optional<Video> videoOptional = this.repository.findById(id);
        if (videoOptional.isPresent()) {
            this.repository.deleteById(id);
        }
    }
}