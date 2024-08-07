package com.ufpi.multimidiaawsbackend.Services;

import com.ufpi.multimidiaawsbackend.DTO.AudioDTO;
import com.ufpi.multimidiaawsbackend.Exceptions.AudioNotFoundException;
import com.ufpi.multimidiaawsbackend.Exceptions.InvalidFileException;
import com.ufpi.multimidiaawsbackend.Exceptions.NotAnAudioException;
import com.ufpi.multimidiaawsbackend.Models.Audio;
import com.ufpi.multimidiaawsbackend.Models.User;
import com.ufpi.multimidiaawsbackend.Repositories.AudioRepository;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AudioService {
    private final UserService userService;
    private final AudioRepository repository;
    public AudioService(UserService userService, AudioRepository repository) {
        this.userService = userService;
        this.repository = repository;
    }
    
    public List<Audio> getAllAudios() {
        return this.repository.findAll();
    }
    
    public Audio findAudioById(Long id){
        return repository.findAudioById(id).orElseThrow(AudioNotFoundException::new);
    }

    public List<Audio> getOwnerAudios(Long ownerId){
        userService.findUserById(ownerId);
        return getAllAudios().stream().filter(audio -> audio.getOwner().getId().equals(ownerId)).collect(Collectors.toList());
    }
    
    @SneakyThrows
    public Audio createAudio(AudioDTO audioDTO, MultipartFile file){
        validateAudio(file);
        User owner = userService.findUserById(audioDTO.ownerId());
        Audio newAudio = new Audio(audioDTO, owner, file);
        this.repository.save(newAudio);
        //TODO: add to s3
        return newAudio;
    }

    private void validateAudio(MultipartFile file){
        String fileType = Objects.requireNonNull(file.getContentType()).toUpperCase().split("/")[1];
        if(fileType == null){
            throw new InvalidFileException();
        }
        if(!Audio.verifyType(fileType)){
            throw new NotAnAudioException();
        }
    }

    public Audio updateAudio(Long id, AudioDTO audioDTO) {
        Optional<Audio> AudioOptional = this.repository.findById(id);
        if (AudioOptional.isPresent()) {
            //TODO: Alterar o nome do arquivo
            Audio audio = AudioOptional.get();
            audio.setOwner(userService.findUserById(audioDTO.ownerId()));
            audio.setDescription(audioDTO.description());
            audio.setTags(audioDTO.tags());
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