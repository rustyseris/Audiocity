package eu.vmaerten.audiocity.soundtrack;

import java.time.Duration;
import java.util.List;

public abstract class Soundtrack implements Cloneable {
    public abstract String getName();
    public abstract Duration getDuration();
    public abstract long getSamplesPerChannel();
    public abstract long getSampleRate();
    public abstract List<Channel> getChannels();

    public Duration getTimeStep() {
        return this.getDuration().dividedBy(this.getSamplesPerChannel());
    }

    /**
     * Create a deep copy of the soundtrack
     * @return a copy of the soundtrack
     */
    public abstract Soundtrack copy();

    /**
     * Scale all channel by a factor
     * @param factor
     */
    public void scale(double factor) {
        for (Channel channel : this.getChannels()) {
            channel.scale(factor);
        }
    }
}
