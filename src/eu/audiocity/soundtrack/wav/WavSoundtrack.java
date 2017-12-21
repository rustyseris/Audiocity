package eu.audiocity.soundtrack.wav;

import eu.audiocity.soundtrack.Channel;
import eu.audiocity.soundtrack.Sample;
import eu.audiocity.soundtrack.Soundtrack;
import javafx.concurrent.Task;

import javax.sound.sampled.AudioInputStream;
import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class WavSoundtrack extends Soundtrack {
    private File file;
    private List<Channel> channels;
    private int sampleRate;

    @Override
    public String getName() {
        return this.file.getName();
    }

    @Override
    public int getSampleRate() {
        return this.sampleRate;
    }

    @Override
    public Duration getDuration() {
        return null;
    }

    @Override
    public List<Channel> getChannels() {
        return this.channels;
    }

    @Override
    public AudioInputStream getAudioStream() {
        return null;
    }

    public static class Builder extends Task<Soundtrack> {
        private File file;
        private static final int CHUNK_SIZE = 4096;

        public Builder(File file) {
            this.file = file;
        }

        public void start() {
            Thread executor = new Thread(this);
            executor.setDaemon(true);
            executor.start();
        }

        @Override
        protected Soundtrack call() throws Exception {
            WavSoundtrack soundtrack = new WavSoundtrack();
            WavFile wav = WavFile.openWavFile(this.file);

            int totalFrameRead = 0;
            int totalFrameCount = Math.toIntExact(wav.getNumFrames());
            int channelCount = wav.getNumChannels();

            soundtrack.file = this.file;
            soundtrack.channels = new ArrayList<>(channelCount);
            soundtrack.sampleRate = Math.toIntExact(wav.getSampleRate());

            List<List<Sample>> channels = new ArrayList<>(channelCount);
            for (int i = 0; i < channelCount; i++) {
                channels.add(new ArrayList<>(totalFrameCount));
            }
            
            double[][] buffer = new double[channelCount][CHUNK_SIZE];
            int frameRead = wav.readFrames(buffer, CHUNK_SIZE);

            while(frameRead > 0) {
                for (int i = 0; i < channelCount; i++) {
                    List<Sample> channel = channels.get(i);
                    for (int j = 0; j < frameRead; j++) {
                        channel.add(new Sample(buffer[i][j]));
                    }
                }

                totalFrameRead += frameRead;
                this.updateProgress(totalFrameRead, totalFrameCount);

                frameRead = wav.readFrames(buffer, CHUNK_SIZE);
            }

            for (List<Sample> channel : channels) {
                soundtrack.channels.add(new Channel(channel, soundtrack.sampleRate));
            }

            return soundtrack;
        }
    }

}
