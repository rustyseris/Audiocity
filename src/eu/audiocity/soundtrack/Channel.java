package eu.audiocity.soundtrack;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Channel implements Iterable<Sample> {
    private List<Sample> samples;
    private int sampleRate;

    public Channel(List<Sample> samples, int sampleRate) {
        this.sampleRate = sampleRate;
        this.samples = samples;
    }

    public Channel(Channel channel) {
        this.sampleRate = channel.sampleRate;
        this.samples = new ArrayList<>(channel.samples.size());
        for (Sample sample : channel.samples) {
            this.samples.add(new Sample(sample));
        }
    }

    public int getSampleRate() {
        return this.sampleRate;
    }

    public List<Sample> getSamples() {
        return this.samples;
    }

    public void setSamples(List<Sample> samples) {
        this.samples = samples;
    }

    @Override
    public Iterator<Sample> iterator() {
        return this.samples.iterator();
    }
}
