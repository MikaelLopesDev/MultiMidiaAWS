package com.ufpi.multimidiaawsbackend.Controllers;

import com.ufpi.multimidiaawsbackend.DTO.VideoDTO;
import com.ufpi.multimidiaawsbackend.Models.Video;
import com.ufpi.multimidiaawsbackend.Services.VideoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/videos")
@CrossOrigin(origins = "*")
public class VideoController {

    private final VideoService videoService;

    public VideoController(VideoService service) {
        this.videoService = service;
    }
    @GetMapping
    public ResponseEntity<List<Video>> getAllVideos(){
        List<Video> videos = this.videoService.getAllVideos();
        return new ResponseEntity<>(videos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Video> getVideoById(@PathVariable("id") Long id){
        Video video = this.videoService.findVideoById(id);
        return new ResponseEntity<>(video, HttpStatus.OK);
    }

    @GetMapping("from/{ownerId}")
    public ResponseEntity<List<Video>> getUserVideos(@PathVariable("ownerId") Long ownerId){
        List<Video> videos = this.videoService.getOwnerVideos(ownerId);
        return new ResponseEntity<>(videos, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Video> createVideo(@RequestParam MultipartFile file, @RequestParam Long ownerId, @RequestParam LocalDateTime uploadDate, @RequestParam String description, @RequestParam String genre, @RequestParam String tags){
        VideoDTO videoDTO = new VideoDTO(ownerId, uploadDate, description, genre, tags);
        Video newVideo = videoService.createVideo(videoDTO, file);
        return new ResponseEntity<>(newVideo, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Video> updateVideo(@PathVariable("id") Long id, @RequestBody VideoDTO videoDTO) {
        Video updatedVideo = videoService.updateVideo(id, videoDTO);
        if (updatedVideo != null) {
            return new ResponseEntity<>(updatedVideo, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVideoById(@PathVariable("id") Long id) {
        this.videoService.deleteVideoById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
