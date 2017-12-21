package eu.audiocity.audio;

import eu.audiocity.soundtrack.Soundtrack;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import java.time.Duration;

public class AudioManager {
    private enum Status {
        PLAY, PAUSE, STOP
    }

    private Soundtrack currentSoundtrack = null;
    private Clip currentClip = null;
    private Status currentStatus = Status.STOP;

    public void play(Soundtrack soundtrack) throws Exception {
        if(soundtrack != null) {
            if(this.getSoundtrack() != soundtrack) {
                this.stop();
                this.setSoundtrack(soundtrack);
            }

            if(this.currentStatus != Status.PLAY) {
                this.currentClip.start();
                this.currentStatus = Status.PLAY;
            }
        }
    }

    public void pause() {
        if(this.currentClip != null && this.currentStatus != Status.PAUSE) {
            this.currentClip.stop();
        }
        this.currentStatus = Status.PAUSE;
    }

    public void stop() {
        if(this.currentClip != null) {
            this.currentClip.close();
            this.currentClip = null;
            this.currentSoundtrack = null;
        }
        this.currentStatus = Status.STOP;
    }

    public Soundtrack getSoundtrack() {
        return this.currentSoundtrack;
    }

    private void setSoundtrack(Soundtrack soundtrack) throws Exception {
        this.currentSoundtrack = soundtrack;
        if(this.currentClip != null) {
            this.currentClip.close();
        }

        AudioInputStream stream = this.currentSoundtrack.getAudioStream();
        DataLine.Info info = new DataLine.Info(Clip.class, stream.getFormat());
        this.currentClip = (Clip) AudioSystem.getLine(info);
        this.currentClip.open(stream);
    }

    public void setCurrentTime(Duration time) {
        if(this.currentClip != null) {
            this.currentClip.setMicrosecondPosition(time.toNanos() / 1000);
        }
    }


    public Duration getCurrentTime() {
        Duration currentTime = Duration.ZERO;
        if(this.currentClip != null) {
            currentTime = Duration.ofNanos(this.currentClip.getMicrosecondPosition() * 1000);
        }
        return currentTime;
    }

    public Duration getDuration() {
        Duration maxTime = Duration.ZERO;
        if(this.currentSoundtrack != null) {
            maxTime = this.currentSoundtrack.getDuration();
        }
        return maxTime;
    }
}
