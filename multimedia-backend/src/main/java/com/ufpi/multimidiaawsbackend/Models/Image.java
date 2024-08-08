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
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.MemoryCacheImageInputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.image.BufferedImage;
import java.io.*;
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

    public Image(ImageDTO imageDTO, User user, MultipartFile image) throws IOException, ImageProcessingException, TranscoderException, ParserConfigurationException, SAXException {
        this.setOwner(user);
        this.setFileName(image.getOriginalFilename());
        this.setUploadDate(imageDTO.uploadDate());
        this.setDescription(imageDTO.description());
        this.setTags(imageDTO.tags());
        this.setFileSize(image.getSize());
        String mime = Objects.requireNonNull(image.getContentType()).toUpperCase().split("/")[1];
        mime = mime.contains("SVG")? mime.split("\\+")[0] : mime;
        this.setMIME(MIMETypes.valueOf(mime));
        if (image.getOriginalFilename().endsWith(".svg")) {
            setImageBaseMetaDataFromSVG(image);
            setExif(extractSvgMetadata(image));
        } else {
            setImageBaseMetaData(image);
            setExifData(image);
        }

    }

    private void setImageBaseMetaData(MultipartFile image) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(image.getInputStream());
        if (bufferedImage != null) {
            this.setColorDepth(calculateColorDepth(bufferedImage));
            this.setHeight(bufferedImage.getHeight());
            this.setWidth(bufferedImage.getWidth());
        }
    }

    private void setImageBaseMetaDataFromSVG(MultipartFile image) throws IOException, TranscoderException {
        InputStream inputStream = image.getInputStream();
        PNGTranscoder transcoder = new PNGTranscoder();
        TranscoderInput input = new TranscoderInput(inputStream);
        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        TranscoderOutput output = new TranscoderOutput(pngOutputStream);
        transcoder.transcode(input, output);
        ImageInputStream imageInputStream = new MemoryCacheImageInputStream(new ByteArrayInputStream(pngOutputStream.toByteArray()));
        BufferedImage bufferedImage = ImageIO.read(imageInputStream);
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

    public String extractSvgMetadata(MultipartFile svgFile) throws IOException, ParserConfigurationException, SAXException {
        StringBuilder metadataStringBuilder = new StringBuilder();

        InputStream inputStream = svgFile.getInputStream();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(inputStream);

        NodeList titleList = document.getElementsByTagName("title");
        if (titleList.getLength() > 0) {
            String title = titleList.item(0).getTextContent();
            metadataStringBuilder.append("Title: ").append(title).append("\n");
        }

        NodeList descList = document.getElementsByTagName("desc");
        if (descList.getLength() > 0) {
            String description = descList.item(0).getTextContent();
            metadataStringBuilder.append("Description: ").append(description).append("\n");
        }

        NodeList metadataList = document.getElementsByTagName("metadata");
        if (metadataList.getLength() > 0) {
            String metadata = metadataList.item(0).getTextContent();
            metadataStringBuilder.append("Metadata: ").append(metadata).append("\n");
        }

        return metadataStringBuilder.toString();
    }

    public static boolean verifyType(String type) {
        if(type.contains("SVG")){
            type = type.split("\\+")[0];
        }
        return VALID_IMAGE_TYPES.contains(type.toUpperCase());
    }
}
