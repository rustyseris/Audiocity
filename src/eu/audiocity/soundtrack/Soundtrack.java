package eu.audiocity.soundtrack;

import javafx.concurrent.Task;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.List;

public abstract class Soundtrack {
    private static final int BITS_PER_SAMPLE = 8;

    public abstract String getName();
    public abstract int getSampleRate();
    public abstract int getSamplesCount();
    public abstract Duration getDuration();
    public abstract List<Channel> getChannels();

    public AudioInputStream getAudioStream() {
        InputStream inputStream = new SoundtrackStream(this);
        AudioFormat format = new AudioFormat(
                this.getSampleRate(),
                BITS_PER_SAMPLE,
                this.getChannels().size(),
                false,
                true
        );
        return new AudioInputStream(inputStream, format, this.getSamplesCount() * this.getChannels().size());
    }

    public abstract Soundtrack copy();

    public static abstract class Builder extends Task<Soundtrack> {
        public void startTask() {
            Thread thread = new Thread(this);
            thread.setDaemon(true);
            thread.start();
        }
    }

    private class SoundtrackStream extends InputStream {
        private Soundtrack soundtrack;
        private int sampleIndex = 0;
        private int channelIndex = 0;

        SoundtrackStream(Soundtrack soundtrack) {
            this.soundtrack = soundtrack;
        }

        @Override
        public int read() {
            List<Channel> channels = this.soundtrack.getChannels();
            int value = -1;
            if(this.channelIndex < channels.size() && this.sampleIndex < this.soundtrack.getSamplesCount()) {
                Sample sample = channels.get(this.channelIndex).getSamples().get(this.sampleIndex);
                value = (int) ((sample.get() * 127 + 128));
                this.channelIndex++;
            } else if(this.sampleIndex < this.soundtrack.getSamplesCount()) {
                this.sampleIndex++;
                this.channelIndex = 0;
            }
            return value;
        }
    }
}
