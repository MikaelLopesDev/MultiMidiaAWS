package com.ufpi.multimidiaawsbackend.Services;

import com.ufpi.multimidiaawsbackend.DTO.ImageDTO;
import com.ufpi.multimidiaawsbackend.Exceptions.ImageNotFoundException;
import com.ufpi.multimidiaawsbackend.Exceptions.InvalidEmailException;
import com.ufpi.multimidiaawsbackend.Exceptions.InvalidFileException;
import com.ufpi.multimidiaawsbackend.Exceptions.NotAnImageException;
import com.ufpi.multimidiaawsbackend.Models.Enum.MIMETypes;
import com.ufpi.multimidiaawsbackend.Models.Image;
import com.ufpi.multimidiaawsbackend.Models.User;
import com.ufpi.multimidiaawsbackend.Repositories.ImageRepository;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ImageService {
    private final ImageRepository repository;
    private final UserService userService;

    public ImageService(ImageRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }
    
    public List<Image> getAllImages() {
        return this.repository.findAll();
    }
    
    public Image findImageById(Long id){
        return repository.findImageById(id).orElseThrow(ImageNotFoundException::new);
    }
   
    public List<Image> getOwnerImages(Long ownerId){     
        userService.findUserById(ownerId);
        return getAllImages().stream().filter(image -> image.getOwner().getId().equals(ownerId)).collect(Collectors.toList());
    }

    @SneakyThrows
    public Image createImage(ImageDTO imageDTO, MultipartFile file){
        validateImage(file);
        User owner = userService.findUserById(imageDTO.ownerId());
        Image newImage = new Image(imageDTO, owner, file);
        this.repository.save(newImage);
        //TODO: add to s3
        return newImage;
    }

    private void validateImage(MultipartFile file){
        String fileType = Objects.requireNonNull(file.getContentType()).toUpperCase().split("/")[1];
        if(fileType == null){
            throw new InvalidFileException();
        }
        if(!Image.verifyType(fileType)){
            throw new NotAnImageException();
        }
    }

    public Image updateImage(Long id, ImageDTO imageDTO) {
        //TODO: Editar na aws
        Optional<Image> imageOptional = this.repository.findById(id);
        if (imageOptional.isPresent()) {
            //TODO: Alterar o nome do arquivo
            Image image = imageOptional.get();
            image.setOwner(userService.findUserById(imageDTO.ownerId()));
            image.setDescription(imageDTO.description());
            image.setTags(imageDTO.tags());
            return this.repository.save(image);
        } else {
            return null;
        }
    }
    
    public void deleteImageById(Long id) {
        //TODO: Remover da aws
        Optional<Image> imageOptional = this.repository.findById(id);
        if (imageOptional.isPresent()) {
            this.repository.deleteById(id);
        }
    }
}
