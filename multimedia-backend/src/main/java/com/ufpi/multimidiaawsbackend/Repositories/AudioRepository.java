package com.ufpi.multimidiaawsbackend.Repositories;


import com.ufpi.multimidiaawsbackend.Models.Audio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AudioRepository extends JpaRepository<Audio, Long> {
    Optional<Audio> findAudioById(Long id);
}