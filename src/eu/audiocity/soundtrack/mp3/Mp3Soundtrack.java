package eu.audiocity.soundtrack.mp3;

import eu.audiocity.soundtrack.Channel;
import eu.audiocity.soundtrack.Sample;
import eu.audiocity.soundtrack.Soundtrack;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Mp3Soundtrack extends Soundtrack {
    File file;

    public Mp3Soundtrack(File file) throws Exception {
        this.file = file;
        AudioInputStream in= AudioSystem.getAudioInputStream(file);
        AudioInputStream din;
        AudioFormat baseFormat = in.getFormat();
        AudioFormat decodedFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                baseFormat.getSampleRate(),
                16,
                baseFormat.getChannels(),
                baseFormat.getChannels() * 2,
                baseFormat.getSampleRate(),
                false);
        din = AudioSystem.getAudioInputStream(decodedFormat, in);

        byte[] bytes = new byte[decodedFormat.getChannels() * decodedFormat.getFrameSize()];
        int byteRead = din.read(bytes);

        List<List<Sample>> channels = new ArrayList<>(baseFormat.getChannels());
        for (int i = 0; i < baseFormat.getChannels(); i++){
            channels.add(new ArrayList<>());
        }

        while(byteRead > 0) {
            byteRead = din.read(bytes);
            ByteBuffer buffer = ByteBuffer.wrap(bytes);
            ShortBuffer shortBuffer = buffer.asShortBuffer();
        }
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public int getSampleRate() {
        return 0;
    }

    @Override
    public int getSamplesCount() {
        return 0;
    }

    @Override
    public Duration getDuration() {
        return null;
    }

    @Override
    public List<Channel> getChannels() {
        return null;
    }

    @Override
    public Soundtrack copy() {
        return null;
    }
}
