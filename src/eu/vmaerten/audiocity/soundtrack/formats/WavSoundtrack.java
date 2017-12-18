package eu.vmaerten.audiocity.soundtrack.formats;

import eu.vmaerten.audiocity.lib.wavfile.WavFile;
import eu.vmaerten.audiocity.soundtrack.Channel;
import eu.vmaerten.audiocity.soundtrack.Soundtrack;

import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

public class WavSoundtrack extends Soundtrack {
    private List<Channel> channels;
    private long sampleRate;
    private String name;
    private long durationMillis;
    private int samplesPerChannel;

    public WavSoundtrack(String path) throws Exception {
        File file = new File(path);
        WavFile wavFile = WavFile.openWavFile(file);
        int numChannels = wavFile.getNumChannels();

        this.name = file.getName();
        this.samplesPerChannel = Math.toIntExact(wavFile.getNumFrames());
        this.sampleRate = wavFile.getSampleRate();
        this.durationMillis = ((long) this.samplesPerChannel * 1000) / this.sampleRate;
        this.channels = new ArrayList<>(numChannels);

        double[][] samples = new double[numChannels][this.samplesPerChannel];
        wavFile.readFrames(samples, this.samplesPerChannel);
        wavFile.close();

        for (double[] channel_raw : samples) {
            List<Double> channel_boxed = DoubleStream.of(channel_raw).boxed().collect(Collectors.toList());
            this.channels.add(new Channel(channel_boxed, this.sampleRate));
        }
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Duration getDuration() {
        return Duration.ofMillis(this.durationMillis);
    }

    @Override
    public long getSamplesPerChannel() {
        return this.samplesPerChannel;
    }

    @Override
    public long getSampleRate() {
        return this.sampleRate;
    }

    @Override
    public List<Channel> getChannels() {
        return this.channels;
    }
}
