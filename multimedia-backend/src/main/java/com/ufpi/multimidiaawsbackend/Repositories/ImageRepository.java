package com.ufpi.multimidiaawsbackend.Repositories;


import com.ufpi.multimidiaawsbackend.Models.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> findImageById(Long id);
}