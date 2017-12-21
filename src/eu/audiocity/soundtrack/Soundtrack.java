package eu.audiocity.soundtrack;

import javafx.concurrent.Task;

import javax.sound.sampled.AudioInputStream;
import java.time.Duration;
import java.util.List;

public abstract class Soundtrack {
    public abstract String getName();
    public abstract int getSampleRate();
    public abstract Duration getDuration();
    public abstract List<Channel> getChannels();
    public abstract AudioInputStream getAudioStream();
}
