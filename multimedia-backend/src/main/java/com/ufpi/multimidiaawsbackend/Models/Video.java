package com.ufpi.multimidiaawsbackend.Models;

import com.ufpi.multimidiaawsbackend.DTO.VideoDTO;
import com.ufpi.multimidiaawsbackend.Models.Enum.GENRE;
import com.ufpi.multimidiaawsbackend.Models.Enum.MIMETypes;
import com.ufpi.multimidiaawsbackend.Utils.VideoUtils;
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
            MIMETypes.XMSVIDEO.name(),
            MIMETypes.MOV.name(),
            MIMETypes.QUICKTIME.name(),
            MIMETypes.WEBM.name()
    );

    public Video(VideoDTO videoDTO, User user, MultipartFile video) throws Exception {
        this.setOwner(user);
        this.setFileName(video.getOriginalFilename());
        this.setUploadDate(videoDTO.uploadDate());
        this.setDescription(videoDTO.description());
        this.setGenre(GENRE.valueOf(videoDTO.genre()));
        this.setTags(videoDTO.tags());
        this.setFileSize(video.getSize());
        this.setUploadDate(videoDTO.uploadDate());
        String mime = Objects.requireNonNull(video.getContentType()).toUpperCase().split("/")[1];
        mime = mime.contains("-")? mime.replace("-", "") : mime;
        this.setMIME(MIMETypes.valueOf(mime));
        this.setDescription(videoDTO.description());
        extractVideoAttributes(video);

    }

    private void extractVideoAttributes(MultipartFile video) throws Exception {
        File tempFile = createTempFile(video, ".mp4");
        this.setFps(VideoUtils.getVideoFPS(tempFile.getAbsolutePath()));
        this.setWidth(VideoUtils.getVideoWidth(tempFile.getAbsolutePath()));
        this.setHeight(VideoUtils.getVideoHeight(tempFile.getAbsolutePath()));
        this.setVideoCodec(VideoUtils.getVideoCodec(tempFile.getAbsolutePath()));
        this.setAudioCodec(VideoUtils.getAudioCodec(tempFile.getAbsolutePath()));
        this.setDuration(VideoUtils.getVideoDuration(tempFile.getAbsolutePath()));
        this.setBitRate(VideoUtils.getVideoBitrate(tempFile.getAbsolutePath()));
        String outputpath = "";
        VideoUtils.getThumbnail(tempFile.getAbsolutePath(), outputpath +".png");
    }

    private static File createTempFile(MultipartFile file, String suffix) throws IOException {
        File tempFile = File.createTempFile("tempfile", suffix);
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(file.getBytes());
        }
        return tempFile;
    }

    public static boolean verifyType(String type) {
        if(type.contains("-")){
            type = type.replace("-", "");
        }
        return VALID_VIDEO_TYPES.contains(type.toUpperCase());
    }
}
