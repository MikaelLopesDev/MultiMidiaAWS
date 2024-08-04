package com.ufpi.multimidiaawsbackend.Controllers;

import com.ufpi.multimidiaawsbackend.DTO.AudioDTO;
import com.ufpi.multimidiaawsbackend.Models.Audio;
import com.ufpi.multimidiaawsbackend.Services.AudioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<Audio> createAudio(@RequestBody AudioDTO audioDTO){
        Audio newAudio = audioService.createAudio(audioDTO);
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
