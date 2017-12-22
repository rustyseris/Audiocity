package eu.audiocity.soundtrack.transform;
import eu.audiocity.soundtrack.Channel;
import eu.audiocity.soundtrack.Soundtrack;

import java.util.List;

public abstract class Transform {
	public abstract void apply(List<Channel> channels);
	
	static int durationToNumberOfSamples(double seconds, Channel channel) {
		return (int) seconds * channel.getSampleRate();
	}
}
