package com.ufpi.multimidiaawsbackend.Controllers;

import com.ufpi.multimidiaawsbackend.DTO.AudioDTO;
import com.ufpi.multimidiaawsbackend.Models.Audio;
import com.ufpi.multimidiaawsbackend.Services.AudioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/audios")
@CrossOrigin(origins = "*")
public class AudioController {
    private final com.ufpi.multimidiaawsbackend.Services.AudioService audioService;

    public AudioController(AudioService service) {
        this.audioService = service;
    }

    @GetMapping
    public ResponseEntity<List<Audio>> getAllAudios(){
        List<Audio> audios = this.audioService.getAllAudios();
        return new ResponseEntity<>(audios, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Audio> getAudioById(@PathVariable("id") Long id){
        Audio audio = this.audioService.findAudioById(id);
        return new ResponseEntity<>(audio, HttpStatus.OK);
    }

    @GetMapping("from/{ownerId}")
    public ResponseEntity<List<Audio>> getUserAudios(@PathVariable("ownerId") Long ownerId){
        List<Audio> images = this.audioService.getOwnerAudios(ownerId);
        return new ResponseEntity<>(images, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Audio> createAudio(@RequestParam MultipartFile file, @RequestParam Long ownerId, @RequestParam LocalDateTime uploadDate, @RequestParam String description, @RequestParam String genre, @RequestParam String tags){
        AudioDTO audioDTO = new AudioDTO(ownerId, uploadDate, description, genre, tags);
        Audio newAudio = audioService.createAudio(audioDTO, file);
        return new ResponseEntity<>(newAudio, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Audio> updateAudio(@PathVariable("id") Long id, @RequestBody AudioDTO audioDTO) {
        Audio updatedAudio = audioService.updateAudio(id, audioDTO);
        if (updatedAudio != null) {
            return new ResponseEntity<>(updatedAudio, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAudioById(@PathVariable("id") Long id) {
        this.audioService.deleteAudioById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
