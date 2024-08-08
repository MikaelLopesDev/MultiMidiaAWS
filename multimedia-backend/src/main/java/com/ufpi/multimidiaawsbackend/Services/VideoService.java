package com.ufpi.multimidiaawsbackend.Services;

import com.ufpi.multimidiaawsbackend.DTO.VideoDTO;
import com.ufpi.multimidiaawsbackend.Exceptions.InvalidFileException;
import com.ufpi.multimidiaawsbackend.Exceptions.NotAVideoException;
import com.ufpi.multimidiaawsbackend.Exceptions.VideoNotFoundException;
import com.ufpi.multimidiaawsbackend.Models.*;
import com.ufpi.multimidiaawsbackend.Models.Video;
import com.ufpi.multimidiaawsbackend.Repositories.VideoRepository;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VideoService {
    private final VideoRepository repository;
    private final UserService userService;
    public VideoService(VideoRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }
    
    public List<Video> getAllVideos() {
        return this.repository.findAll();
    }
    
    public Video findVideoById(Long id){
        return repository.findVideoById(id).orElseThrow(VideoNotFoundException::new);
    }

    public List<Video> getOwnerVideos(Long ownerId){
        userService.findUserById(ownerId);
        return getAllVideos().stream().filter(video -> video.getOwner().getId().equals(ownerId)).collect(Collectors.toList());
    }
    
    @SneakyThrows
    public Video createVideo(VideoDTO videoDTO, MultipartFile file){
        validateVideo(file);
        User owner = userService.findUserById(videoDTO.ownerId());
        Video newVideo = new Video(videoDTO, owner, file);
        this.repository.save(newVideo);
        return newVideo;
    }

    private void validateVideo(MultipartFile file){
        String fileType = Objects.requireNonNull(file.getContentType()).toUpperCase().split("/")[1];
        if(fileType == null){
            throw new InvalidFileException();
        }
        if(!Video.verifyType(fileType)){
            throw new NotAVideoException();
        }
    }

    public Video updateVideo(Long id, VideoDTO videoDTO) {
        Optional<Video> videoOptional = this.repository.findById(id);
        if (videoOptional.isPresent()) {
            //TODO: Alterar o nome do arquivo
            Video video = videoOptional.get();
            video.setOwner(userService.findUserById(videoDTO.ownerId()));
            video.setDescription(videoDTO.description());
            video.setTags(videoDTO.tags());
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