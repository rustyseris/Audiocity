package eu.vmaerten.audiocity.soundtrack;

import java.time.Duration;
import java.util.List;

public abstract class Soundtrack {
    public abstract String getName();
    public abstract Duration getDuration();
    public abstract long getSamplesPerChannel();
    public abstract long getSampleRate();
    public abstract List<Channel> getChannels();

    public Duration getTimeStep() {
        return this.getDuration().dividedBy(this.getSamplesPerChannel());
    }
}
