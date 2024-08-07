package com.ufpi.multimidiaawsbackend.Models;

import com.ufpi.multimidiaawsbackend.DTO.AudioDTO;
import com.ufpi.multimidiaawsbackend.Models.Enum.GENRE;
import com.ufpi.multimidiaawsbackend.Models.Enum.MIMETypes;
import com.ufpi.multimidiaawsbackend.Utils.AudioUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "audios")
@Table(name = "audios")
public class Audio extends AVContent {
    private float samplingRate;
    private int channels;

    private static final Set<String> VALID_AUDIO_TYPES = Set.of(
            MIMETypes.MP3.name(),
            MIMETypes.WAV.name(),
            MIMETypes.WAVE.name(),
            MIMETypes.OGG.name(),
            MIMETypes.MPEG.name(),
            MIMETypes.FLAC.name(),
            MIMETypes.XFLAC.name()
    );

    public Audio(AudioDTO audioDTO, User user, MultipartFile audio) throws Exception {
        this.setOwner(user);
        this.setFileName(audio.getOriginalFilename());
        this.setFileSize(audio.getSize());
        this.setUploadDate(audioDTO.uploadDate());
        String mime = Objects.requireNonNull(audio.getContentType()).toUpperCase().split("/")[1];
        mime = mime.contains("-")? mime.replace("-", "") : mime;
        this.setMIME(MIMETypes.valueOf(mime));
        this.setDescription(audioDTO.description());
        this.setGenre(GENRE.valueOf(audioDTO.genre()));
        this.setTags(audioDTO.tags());

        this.setDuration(AudioUtils.getDuration(audio, mime));
        this.setSamplingRate(AudioUtils.getSamplingRate(audio, mime));
        this.setChannels(AudioUtils.getChannels(audio, mime));
        this.setBitRate(AudioUtils.getBitRate(audio, mime));
    }


    public static boolean verifyType(String type) {
        if(type.contains("-")){
            type = type.replace("-", "");
        }
        return VALID_AUDIO_TYPES.contains(type.toUpperCase());
    }
}
