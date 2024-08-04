package com.ufpi.multimidiaawsbackend.Models;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.ufpi.multimidiaawsbackend.DTO.ImageDTO;
import com.ufpi.multimidiaawsbackend.Models.Enum.MIMETypes;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "images")
@Table(name = "images")
public class Image extends Media implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int height;
    private int width;
    private int colorDepth;
    private String exif;

    private static final Set<String> VALID_IMAGE_TYPES = Set.of(
            MIMETypes.JPEG.name(),
            MIMETypes.PNG.name(),
            MIMETypes.GIF.name(),
            MIMETypes.SVG.name(),
            MIMETypes.WEBP.name()
    );

    public Image(ImageDTO imageDTO, User user, MultipartFile image) throws IOException, ImageProcessingException {
        this.setOwner(user);
        this.setFileName(image.getOriginalFilename());
        this.setUploadDate(imageDTO.uploadDate());
        this.setDescription(imageDTO.description());
        this.setTags(imageDTO.tags());
        this.setFileSize(image.getSize());
        String mime = Objects.requireNonNull(image.getContentType()).toUpperCase().split("/")[1];
        this.setMIME(MIMETypes.valueOf(mime));
        setImageBaseMetaData(image);
        setExifData(image);

    }

    private void setImageBaseMetaData(MultipartFile image) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(image.getInputStream());
        if (bufferedImage != null) {
            this.setColorDepth(calculateColorDepth(bufferedImage));
            this.setHeight(bufferedImage.getHeight());
            this.setWidth(bufferedImage.getWidth());
        }
    }

    private int calculateColorDepth(BufferedImage image) {
        int[] bitMasks = image.getColorModel().getComponentSize();
        int colorDepth = 0;
        for (int bitMask : bitMasks) {
            colorDepth += bitMask;
        }
        return colorDepth;
    }

    private void setExifData(MultipartFile image) throws IOException, ImageProcessingException {
        Metadata metadata = ImageMetadataReader.readMetadata(image.getInputStream());
        ExifIFD0Directory exifIFD0Directory = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
        ExifSubIFDDirectory exifSubIFDDirectory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
        if (exifIFD0Directory != null) {
            this.setExif("Make: " + exifIFD0Directory.getString(ExifIFD0Directory.TAG_MAKE) +
                    ", Model: " + exifIFD0Directory.getString(ExifIFD0Directory.TAG_MODEL));
        }
        if (exifSubIFDDirectory != null) {
            this.setExif(this.getExif() + ", DateTime: " + exifSubIFDDirectory.getString(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL));
        }
    }

    public static boolean verifyType(String type) {
        return VALID_IMAGE_TYPES.contains(type.toUpperCase());
    }
}
