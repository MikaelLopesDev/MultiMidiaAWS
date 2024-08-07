package com.ufpi.multimidiaawsbackend.Utils;

import com.jcraft.jorbis.JOrbisException;
import com.jcraft.jorbis.VorbisFile;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;
import com.ufpi.multimidiaawsbackend.Exceptions.NotAnAudioException;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import org.springframework.web.multipart.MultipartFile;
import org.jaudiotagger.audio.AudioFile;



import javax.sound.sampled.*;
import java.io.*;

public class AudioUtils {

    public static double getDuration(MultipartFile file, String mimeType) throws Exception {
        return switch (mimeType.toUpperCase()) {
            case "MP3", "MPEG" -> getMp3Duration(file);
            case "WAV", "WAVE" -> getWavDuration(file);
            case "OGG" -> getOggDuration(file);
            case "FLAC", "XFLAC" -> getFlacDuration(file);
            default -> throw new NotAnAudioException();
        };
    }

    public static float getSamplingRate(MultipartFile file, String mimeType) throws Exception {
        return switch (mimeType.toUpperCase()) {
            case "MP3", "MPEG" -> getMp3SamplingRate(file);
            case "WAV", "WAVE" -> getWavSamplingRate(file);
            case "OGG" -> (int) getOggSamplingRate(file);
            case "FLAC", "XFLAC" -> getFlacSamplingRate(file);
            default -> throw new NotAnAudioException();
        };
    }

    public static int getChannels(MultipartFile file, String mimeType) throws Exception {
        return switch (mimeType.toUpperCase()) {
            case "MP3", "MPEG" -> getMp3Channels(file);
            case "WAV", "WAVE" -> getWavChannels(file);
            case "OGG" -> getOggChannels(file);
            case "FLAC", "XFLAC" -> getFlacChannels(file);
            default -> throw new NotAnAudioException();
        };
    }

    public static int getBitRate(MultipartFile file, String mimeType) throws Exception {
        return switch (mimeType.toUpperCase()) {
            case "MP3", "MPEG" -> getMp3Bitrate(file);
            case "WAV", "WAVE" -> getWavBitrate(file);
            case "OGG" -> getOggBitrate(file);
            case "FLAC", "XFLAC" -> getFlacBitrate(file);
            default -> throw new NotAnAudioException();
        };
    }

//---------------------------- MP3 -------------------------------------------------------
    private static long getMp3Duration(MultipartFile file) throws IOException, UnsupportedTagException, InvalidDataException {
        File tempFile = createTempFile(file, ".mp3");
        Mp3File mp3File = new Mp3File(tempFile.getAbsolutePath());
        long duration = mp3File.getLengthInSeconds();
        tempFile.delete();
        return duration;
    }

    private static int getMp3SamplingRate(MultipartFile file) throws IOException, UnsupportedTagException, InvalidDataException {
        File tempFile = createTempFile(file, ".mp3");
        Mp3File mp3File = new Mp3File(tempFile.getAbsolutePath());
        int sample = mp3File.getSampleRate();
        tempFile.delete();
        return sample;
    }

    private static int getMp3Channels(MultipartFile file) throws IOException, UnsupportedTagException, InvalidDataException {
        File tempFile = createTempFile(file, ".mp3");
        Mp3File mp3File = new Mp3File(tempFile.getAbsolutePath());
        int channels;
        if (mp3File.getChannelMode().equals("Joint stereo") || mp3File.getChannelMode().equals("Stereo")) {
            channels = 2;
        } else {
            channels = 1;
        }
        tempFile.delete();
        return channels;
    }

    private static int getMp3Bitrate(MultipartFile file) throws IOException, UnsupportedTagException, InvalidDataException {
        File tempFile = createTempFile(file, ".mp3");
        Mp3File mp3File = new Mp3File(tempFile.getAbsolutePath());
        int bitrate = mp3File.getBitrate();
        tempFile.delete();
        return bitrate;
    }


//---------------------------- WAV -------------------------------------------------------
    private static long getWavDuration(MultipartFile file) throws UnsupportedAudioFileException, IOException {
        File tempFile = createTempFile(file, ".wav");
        AudioFileFormat fileFormat = AudioSystem.getAudioFileFormat(tempFile);
        AudioFormat format = fileFormat.getFormat();
        long frames = fileFormat.getFrameLength();
        tempFile.delete();
        return (long) ((frames + 0.0) / format.getFrameRate());
    }

    private static int getWavSamplingRate(MultipartFile file) throws UnsupportedAudioFileException, IOException {
        File tempFile = createTempFile(file, ".wav");
        AudioFileFormat fileFormat = AudioSystem.getAudioFileFormat(tempFile);
        AudioFormat format = fileFormat.getFormat();
        tempFile.delete();
        return (int) format.getSampleRate();
    }

    private static int getWavChannels(MultipartFile file) throws UnsupportedAudioFileException, IOException {
        File tempFile = createTempFile(file, ".wav");
        AudioFileFormat fileFormat = AudioSystem.getAudioFileFormat(tempFile);
        AudioFormat format = fileFormat.getFormat();
        tempFile.delete();
        return format.getChannels();
    }

    private static int getWavBitrate(MultipartFile file) throws UnsupportedAudioFileException, IOException {
        File tempFile = createTempFile(file, ".wav");
        AudioFileFormat fileFormat = AudioSystem.getAudioFileFormat(tempFile);
        AudioFormat format = fileFormat.getFormat();
        int bitrate = (int) (format.getSampleRate() * format.getFrameSize() * 8);
        tempFile.delete();
        return bitrate;
    }
//---------------------------- OGG -------------------------------------------------------
    public static double getOggDuration(MultipartFile file) throws IOException, JOrbisException {
        File tempFile = createTempFile(file, ".ogg");
        VorbisFile vorbisFile = new VorbisFile(tempFile.getAbsolutePath());
        tempFile.delete();
        return vorbisFile.time_total(0);
    }

    private static float getOggSamplingRate(MultipartFile file) throws IOException {
        File tempFile = createTempFile(file, ".ogg");
        org.gagravarr.vorbis.VorbisFile vorbisFile = new org.gagravarr.vorbis.VorbisFile(tempFile);
        tempFile.delete();
        return vorbisFile.getInfo().getSampleRate();
    }

    private static int getOggChannels(MultipartFile file) throws IOException {
        File tempFile = createTempFile(file, ".ogg");
        org.gagravarr.vorbis.VorbisFile vorbisFile = new org.gagravarr.vorbis.VorbisFile(tempFile);
        tempFile.delete();
        return vorbisFile.getInfo().getChannels();
    }

    private static int getOggBitrate(MultipartFile file) throws IOException, JOrbisException {
        File tempFile = createTempFile(file, ".ogg");
        VorbisFile vorbisFile = new VorbisFile(tempFile.getAbsolutePath());
        tempFile.delete();
        return vorbisFile.bitrate(0);
    }

//---------------------------- FLAC -------------------------------------------------------
    private static double getFlacDuration(MultipartFile file) throws IOException, CannotReadException, TagException, InvalidAudioFrameException, ReadOnlyFileException {
        File tempFile = createTempFile(file, ".flac");
        File audioFile = new File(tempFile.getAbsolutePath());
        AudioFile audioFileObj = AudioFileIO.read(audioFile);
        tempFile.delete();
        return audioFileObj.getAudioHeader().getTrackLength();
    }

    private static float getFlacSamplingRate(MultipartFile file) throws IOException, CannotReadException, TagException, InvalidAudioFrameException, ReadOnlyFileException {
        File tempFile = createTempFile(file, ".flac");
        File audioFile = new File(tempFile.getAbsolutePath());
        AudioFile audioFileObj = AudioFileIO.read(audioFile);
        tempFile.delete();
        return audioFileObj.getAudioHeader().getSampleRateAsNumber();
    }

    private static int getFlacChannels(MultipartFile file) throws IOException, CannotReadException, TagException, InvalidAudioFrameException, ReadOnlyFileException {
        File tempFile = createTempFile(file, ".flac");
        File audioFile = new File(tempFile.getAbsolutePath());
        AudioFile audioFileObj = AudioFileIO.read(audioFile);
        tempFile.delete();
        return Integer.parseInt(audioFileObj.getAudioHeader().getChannels());
    }

    private static int getFlacBitrate(MultipartFile file) throws IOException, CannotReadException, TagException, InvalidAudioFrameException, ReadOnlyFileException {
        File tempFile = createTempFile(file, ".flac");
        File audioFile = new File(tempFile.getAbsolutePath());
        AudioFile audioFileObj = AudioFileIO.read(audioFile);
        tempFile.delete();
        return Integer.parseInt(audioFileObj.getAudioHeader().getBitRate());
    }

//---------------------------- OUTROS -------------------------------------------------------
    private static File createTempFile(MultipartFile file, String suffix) throws IOException {
        File tempFile = File.createTempFile("tempfile", suffix);
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(file.getBytes());
        }
        return tempFile;
    }
}
