package com.ufpi.multimidiaawsbackend.Repositories;


import com.ufpi.multimidiaawsbackend.Models.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VideoRepository extends JpaRepository<Video, Long> {
    Optional<Video> findVideoById(Long id);
}