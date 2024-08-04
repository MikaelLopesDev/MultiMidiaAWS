package com.ufpi.multimidiaawsbackend.Controllers;

import com.ufpi.multimidiaawsbackend.DTO.ImageDTO;
import com.ufpi.multimidiaawsbackend.Models.Image;
import com.ufpi.multimidiaawsbackend.Services.ImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/images")
@CrossOrigin(origins = "*")
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService service) {
        this.imageService = service;
    }
    @GetMapping
    public ResponseEntity<List<Image>> getAllImages(){
        List<Image> images = this.imageService.getAllImages();
        return new ResponseEntity<>(images, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Image> getImageById(@PathVariable("id") Long id){
        Image image = this.imageService.findImageById(id);
        return new ResponseEntity<>(image, HttpStatus.OK);
    }

  @GetMapping("from/{ownerId}")
  public ResponseEntity<List<Image>> getUserImages(@PathVariable("ownerId") Long ownerId){
    List<Image> images = this.imageService.getOwnerImages(ownerId);
    return new ResponseEntity<>(images, HttpStatus.OK);
  }

    @PostMapping
    public ResponseEntity<Image> createImage(@RequestParam MultipartFile file, @RequestParam Long ownerId, @RequestParam LocalDateTime uploadDate, @RequestParam String description, @RequestParam String tags){
        ImageDTO imageDTO = new ImageDTO(ownerId, uploadDate, description, tags);
        Image newImage = imageService.createImage(imageDTO, file);
        return new ResponseEntity<>(newImage, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Image> updateImage(@PathVariable("id") Long id, @RequestBody ImageDTO imageDTO) {
        Image updatedImage = imageService.updateImage(id, imageDTO);
        if (updatedImage != null) {
            return new ResponseEntity<>(updatedImage, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImageById(@PathVariable("id") Long id) {
        this.imageService.deleteImageById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
