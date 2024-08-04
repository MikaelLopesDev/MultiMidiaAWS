package com.ufpi.multimidiaawsbackend.Services;

import com.ufpi.multimidiaawsbackend.DTO.AudioDTO;
import com.ufpi.multimidiaawsbackend.DTO.AudioDTO;
import com.ufpi.multimidiaawsbackend.Exceptions.AudioNotFoundException;
import com.ufpi.multimidiaawsbackend.Models.Audio;
import com.ufpi.multimidiaawsbackend.Models.Audio;
import com.ufpi.multimidiaawsbackend.Repositories.AudioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AudioService {
    private final AudioRepository repository;
    public AudioService(AudioRepository repository) {
        this.repository = repository;
    }
    
    public List<Audio> getAllAudios() {
        return this.repository.findAll();
    }
    
    public Audio findAudioById(Long id){
        return repository.findAudioById(id).orElseThrow(AudioNotFoundException::new);
    }
    
    public Audio createAudio(AudioDTO audioDTO){
        Audio newAudio = new Audio(audioDTO);
        this.repository.save(newAudio);
        return newAudio;
    }

    public Audio updateAudio(Long id, AudioDTO audioDTO) {
        Optional<Audio> AudioOptional = this.repository.findById(id);
        if (AudioOptional.isPresent()) {
            Audio audio = AudioOptional.get();
            audio.setId(audioDTO.id());
            audio.setFileName(audioDTO.fileName());
            audio.setFileSize(audioDTO.fileSize());
            audio.setUploadDate(audioDTO.uploadDate());
            audio.setMIME(audioDTO.mimeType());
            audio.setDescription(audioDTO.description());
            audio.setTags(audioDTO.tags());
            audio.setDuration(audioDTO.duration());
            audio.setSamplingRate(audioDTO.samplingRate());
            audio.setChannels(audioDTO.channels());
            return this.repository.save(audio);
        } else {
            return null;
        }
    }
    
    public void deleteAudioById(Long id) {
        Optional<Audio> audioOptional = this.repository.findById(id);
        if (audioOptional.isPresent()) {
            this.repository.deleteById(id);
        }
    }
}