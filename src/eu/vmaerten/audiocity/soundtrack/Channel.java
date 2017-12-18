package eu.vmaerten.audiocity.soundtrack;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import eu.vmaerten.audiocity.soundtrack.Exceptions.BadInputValue;

/**
 * @author Valentin Maerten
 * @version 1.0
 * @since 1.0
 */
public class Channel implements Iterable<Sample>, Cloneable {
    private List<Sample> samples;
    private long sampleRate;

    /**
     * Constructor of Channel.
     *
     * @param samples An iterator of Float that represents samples. Values *MUST* be between -1 and 1.
     * @throws BadInputValue Throw
     */
    public Channel(Iterable<Double> samples_input, long sampleRate) throws BadInputValue {
        this.sampleRate = sampleRate;
        this.samples = new ArrayList<>();
        for (Double sample : samples_input) {
            this.samples.add(new Sample(sample));
        }
    }

    /**
     * Create a deep copy of the given channel.
     * @param channel Channel to copy
     */
    public Channel(Channel channel) {
        this.sampleRate = channel.getSampleRate();
        this.samples = new ArrayList<>(channel.getSize());
        for(Sample sample : channel) {
            this.samples.add(new Sample(sample));
        }
    }

    /**
     * Return the bitrate of the channel
     * @return bitrate of the channel
     */
    public long getSampleRate() {
        return this.sampleRate;
    }

    /**
     * Return the number of samples in the channel.
     * @return number of samples in the channel
     */
    public int getSize() {
        return this.samples.size();
    }

    /**
     * Return the samples in the channel as a list
     * @return list containing all samples
     */
    public List<Sample> getSamples() {
        return this.samples;
    }

    /**
     * Scale all sound values by a factor. If a value will be larger than 1, it will be set to 1. The same things
     * happens if a value become lower than -1.
     *
     * @param factor Multiply each sample with this value.
     * @return Return self for easy chaining of methods.
     */
    public Channel scale(double factor) {
        this.samples.forEach(u -> u.set(u.get() * factor));
        return this;
    }

    /**
     * Apply a moving average algorithm to the signal in order to smooth it.
     * @param window_size Size of the window
     */
    public void smooth(int window_size) {
        for (int cursor = 0, window_end = window_size; cursor < this.samples.size(); cursor++, window_end++) {
            List<Sample> window = this.samples.subList(cursor, Integer.min(window_end, this.samples.size()));
            double average = 0.0;
            for(Sample sample : window) {
                average += sample.get();
            }
            this.samples.get(cursor).set(average / (double) window_size);
        }
    }

    @Override
    public Iterator<Sample> iterator() {
        return this.samples.iterator();
    }
}
