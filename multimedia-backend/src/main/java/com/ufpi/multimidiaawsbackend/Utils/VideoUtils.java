package com.ufpi.multimidiaawsbackend.Utils;

import org.bytedeco.ffmpeg.avcodec.AVPacket;
import org.bytedeco.ffmpeg.avutil.AVFrame;
import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.ffmpeg.global.avformat;
import org.bytedeco.ffmpeg.avcodec.AVCodecContext;
import org.bytedeco.ffmpeg.avcodec.AVCodecParameters;
import org.bytedeco.ffmpeg.avformat.AVFormatContext;
import org.bytedeco.ffmpeg.avutil.AVDictionary;
import org.bytedeco.ffmpeg.global.avutil;
import org.bytedeco.ffmpeg.global.swscale;
import org.bytedeco.ffmpeg.swscale.SwsContext;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.opencv.global.opencv_core;
import org.bytedeco.opencv.global.opencv_imgcodecs;
import org.bytedeco.opencv.opencv_core.Mat;
import static org.bytedeco.ffmpeg.global.avutil.AVMEDIA_TYPE_VIDEO;
import static org.bytedeco.ffmpeg.global.avutil.AV_PIX_FMT_BGR24;

public class VideoUtils {

    public static int getVideoWidth(String filename) throws Exception {
        return getVideoInfo(filename, InfoType.WIDTH);
    }

    public static int getVideoHeight(String filename) throws Exception {
        return getVideoInfo(filename, InfoType.HEIGHT);
    }

    public static int getVideoFPS(String filename) throws Exception {
        return getVideoInfo(filename, InfoType.FPS);
    }

    public static String getVideoCodec(String filename) throws Exception {
        return getVideoCodecOrAudioCodec(filename, InfoType.VIDEO_CODEC);
    }

    public static String getAudioCodec(String filename) throws Exception {
        return getVideoCodecOrAudioCodec(filename, InfoType.AUDIO_CODEC);
    }

    private static int getVideoInfo(String filename, InfoType infoType) throws Exception {
        AVFormatContext formatContext = avformat.avformat_alloc_context();
        if (avformat.avformat_open_input(formatContext, filename, null, null) != 0) {
            throw new Exception("Erro ao abrir o arquivo de vídeo.");
        }

        if (avformat.avformat_find_stream_info(formatContext, (AVDictionary) null) < 0) {
            throw new Exception("Não foi possível encontrar as informações do stream.");
        }

        int videoStreamIndex = findVideoStreamIndex(formatContext);
        if (videoStreamIndex == -1) {
            throw new Exception("Nenhum stream de vídeo encontrado.");
        }

        AVCodecParameters codecParams = formatContext.streams(videoStreamIndex).codecpar();
        AVCodecContext codecContext = avcodec.avcodec_alloc_context3(avcodec.avcodec_find_decoder(codecParams.codec_id()));
        avcodec.avcodec_parameters_to_context(codecContext, codecParams);

        if (avcodec.avcodec_open2(codecContext, avcodec.avcodec_find_decoder(codecParams.codec_id()), (AVDictionary) null) < 0) {
            throw new Exception("Não foi possível abrir o codec.");
        }

        int result = switch (infoType) {
            case WIDTH -> codecContext.width();
            case HEIGHT -> codecContext.height();
            case FPS -> codecContext.framerate().num() / codecContext.framerate().den();
            default -> throw new IllegalStateException("Unexpected value: " + infoType);
        };

        avcodec.avcodec_free_context(codecContext);
        avformat.avformat_close_input(formatContext);
        return result;
    }

    public static double getVideoDuration(String filename) throws Exception {
        AVFormatContext formatContext = avformat.avformat_alloc_context();
        if (avformat.avformat_open_input(formatContext, filename, null, null) != 0) {
            throw new Exception("Erro ao abrir o arquivo de vídeo.");
        }

        if (avformat.avformat_find_stream_info(formatContext, (AVDictionary) null) < 0) {
            throw new Exception("Não foi possível encontrar as informações do stream.");
        }

        long duration = formatContext.duration();
        avformat.avformat_close_input(formatContext);

        return duration / (double) avutil.AV_TIME_BASE;
    }

    private static String getVideoCodecOrAudioCodec(String filename, InfoType infoType) throws Exception {
        AVFormatContext formatContext = avformat.avformat_alloc_context();
        if (avformat.avformat_open_input(formatContext, filename, null, null) != 0) {
            throw new Exception("Erro ao abrir o arquivo.");
        }

        if (avformat.avformat_find_stream_info(formatContext, (AVDictionary) null) < 0) {
            throw new Exception("Não foi possível encontrar as informações do stream.");
        }

        int streamIndex;
        switch (infoType) {
            case VIDEO_CODEC -> streamIndex = findVideoStreamIndex(formatContext);
            case AUDIO_CODEC -> streamIndex = findAudioStreamIndex(formatContext);
            default -> throw new IllegalArgumentException("InfoType inválido.");
        }

        if (streamIndex == -1) {
            throw new Exception("Nenhum stream encontrado para o tipo especificado.");
        }

        AVCodecParameters codecParams = formatContext.streams(streamIndex).codecpar();
        String codecName = avcodec.avcodec_get_name(codecParams.codec_id()).getString();

        avformat.avformat_close_input(formatContext);
        return codecName;
    }

    private static int findVideoStreamIndex(AVFormatContext formatContext) {
        for (int i = 0; i < formatContext.nb_streams(); i++) {
            if (formatContext.streams(i).codecpar().codec_type() == AVMEDIA_TYPE_VIDEO) {
                return i;
            }
        }
        return -1;
    }

    private static int findAudioStreamIndex(AVFormatContext formatContext) {
        for (int i = 0; i < formatContext.nb_streams(); i++) {
            if (formatContext.streams(i).codecpar().codec_type() == avutil.AVMEDIA_TYPE_AUDIO) {
                return i;
            }
        }
        return -1;
    }

    public static int getVideoBitrate(String filename) throws Exception {
        AVFormatContext formatContext = avformat.avformat_alloc_context();
        if (avformat.avformat_open_input(formatContext, filename, null, null) != 0) {
            throw new Exception("Erro ao abrir o arquivo de vídeo.");
        }

        if (avformat.avformat_find_stream_info(formatContext, (AVDictionary) null) < 0) {
            throw new Exception("Não foi possível encontrar as informações do stream.");
        }

        long bitrate = formatContext.bit_rate(); // Bitrate em bits por segundo
        avformat.avformat_close_input(formatContext);

        return (int) bitrate;
    }

    public static void getThumbnail(String filename, String outputImagePath) throws Exception {
        AVFormatContext formatContext = avformat.avformat_alloc_context();
        if (avformat.avformat_open_input(formatContext, filename, null, null) != 0) {
            throw new Exception("Erro ao abrir o arquivo de vídeo.");
        }

        if (avformat.avformat_find_stream_info(formatContext, (AVDictionary) null) < 0) {
            throw new Exception("Não foi possível encontrar as informações do stream.");
        }

        int videoStreamIndex = findVideoStreamIndex(formatContext);
        if (videoStreamIndex == -1) {
            throw new Exception("Nenhum stream de vídeo encontrado.");
        }

        AVCodecParameters codecParams = formatContext.streams(videoStreamIndex).codecpar();
        AVCodecContext codecContext = avcodec.avcodec_alloc_context3(avcodec.avcodec_find_decoder(codecParams.codec_id()));
        avcodec.avcodec_parameters_to_context(codecContext, codecParams);

        if (avcodec.avcodec_open2(codecContext, avcodec.avcodec_find_decoder(codecParams.codec_id()), (AVDictionary) null) < 0) {
            throw new Exception("Não foi possível abrir o codec.");
        }

        AVFrame frame = avutil.av_frame_alloc();
        AVPacket packet = avcodec.av_packet_alloc();

        while (avformat.av_read_frame(formatContext, packet) >= 0) {
            if (packet.stream_index() == videoStreamIndex) {
                int response = avcodec.avcodec_send_packet(codecContext, packet);
                if (response < 0) {
                    break;
                }

                response = avcodec.avcodec_receive_frame(codecContext, frame);
                if (response == 0) {
                    saveFrameAsImage(frame, codecContext, outputImagePath);
                    break;
                }
            }
            avcodec.av_packet_unref(packet);
        }

        avutil.av_frame_free(frame);
        avcodec.av_packet_free(packet);
        avcodec.avcodec_free_context(codecContext);
        avformat.avformat_close_input(formatContext);
    }

    private static void saveFrameAsImage(AVFrame frame, AVCodecContext codecContext, String outputImagePath) throws Exception {
        SwsContext swsContext = swscale.sws_getContext(
                codecContext.width(), codecContext.height(), codecContext.pix_fmt(),
                codecContext.width(), codecContext.height(), AV_PIX_FMT_BGR24,
                swscale.SWS_BILINEAR, null, null, (double[]) null);

        AVFrame rgbFrame = avutil.av_frame_alloc();
        BytePointer data = new BytePointer(avutil.av_malloc(avutil.av_image_get_buffer_size(AV_PIX_FMT_BGR24, codecContext.width(), codecContext.height(), 1)));
        avutil.av_image_fill_arrays(rgbFrame.data(), rgbFrame.linesize(), data, AV_PIX_FMT_BGR24, codecContext.width(), codecContext.height(), 1);

        swscale.sws_scale(swsContext, frame.data(), frame.linesize(), 0, codecContext.height(), rgbFrame.data(), rgbFrame.linesize());

        Mat img = new Mat(codecContext.height(), codecContext.width(), opencv_core.CV_8UC3, rgbFrame.data(0).position(0));
        opencv_imgcodecs.imwrite(outputImagePath, img);

        avutil.av_frame_free(rgbFrame);
        swscale.sws_freeContext(swsContext);
    }

    private enum InfoType {
        WIDTH, HEIGHT, FPS, VIDEO_CODEC, AUDIO_CODEC
    }
}
