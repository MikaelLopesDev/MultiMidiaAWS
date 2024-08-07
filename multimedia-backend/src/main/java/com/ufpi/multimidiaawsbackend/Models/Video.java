package com.ufpi.multimidiaawsbackend.Models;

import com.ufpi.multimidiaawsbackend.DTO.VideoDTO;
import com.ufpi.multimidiaawsbackend.Models.Enum.MIMETypes;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "videos")
@Table(name = "videos")
public class Video extends AVContent {
    private int height;
    private int width;
    private int fps;
    private String audioCodec;
    private String videoCodec;
    private String thumbnailLocation;
    private String processingDetails;
    private static final Set<String> VALID_VIDEO_TYPES = Set.of(
            MIMETypes.MP4.name(),
            MIMETypes.AVI.name(),
            MIMETypes.MOV.name(),
            MIMETypes.WEBM.name()
    );

    public Video(VideoDTO videoDTO, User user, MultipartFile video) throws IOException {
        this.setOwner(user);
        this.setFileName(video.getOriginalFilename());
        this.setUploadDate(videoDTO.uploadDate());
        this.setDescription(videoDTO.description());
        this.setTags(videoDTO.tags());
        this.setFileSize(video.getSize());
        this.setUploadDate(videoDTO.uploadDate());
        String mime = Objects.requireNonNull(video.getContentType()).toUpperCase().split("/")[1];
        this.setMIME(MIMETypes.valueOf(mime));
        this.setDescription(videoDTO.description());
        extractVideoAttributes(video);

    }

    private void extractVideoAttributes(MultipartFile video) throws IOException {
        File tempFile = convertToFile(video);

        try (FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(tempFile)) {
            grabber.start();

            this.width = grabber.getImageWidth();
            this.height = grabber.getImageHeight();
            this.fps = (int) grabber.getFrameRate();
            this.videoCodec = grabber.getVideoCodecName();
            this.audioCodec = grabber.getAudioCodecName();
            //TODO: Pegar a thumbnail
            //this.setThumbnailLocation(videoDTO.thumbnailLocation());
            /*
            TODO: Pegar detalhes de processo Versões de qualidade de vídeo geradas: Detalhes sobre as versões criadas com
             diferentes resoluções (por exemplo, 1080p, 720p, 480p) e suas respectivas
             localizações.
             this.setProcessingDetails(videoDTO.processingDetails());
            */
            grabber.stop();
        } finally {
            tempFile.delete();
        }
    }

    private File convertToFile(MultipartFile file) throws IOException {
        File convFile = File.createTempFile("temp", file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convFile);
             InputStream is = file.getInputStream()) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
            }
        }
        return convFile;
    }

    public static boolean verifyType(String type) {
        return VALID_VIDEO_TYPES.contains(type.toUpperCase());
    }
}
