package eu.audiocity.soundtrack.transform;

import eu.audiocity.soundtrack.Channel;
import eu.audiocity.soundtrack.Sample;
import java.util.List;

public class Smooth extends Transform {
	private int windowSize;
	
	public Smooth(int windowSize) {
		this.windowSize = windowSize;
	}
	
	@Override
	public void apply(List<Channel> channels) {
		for (Channel channel : channels) {
			List<Sample> samples = channel.getSamples();
			for (int cursor = 0, window_end = this.windowSize; cursor < samples.size(); cursor++, window_end++) {
				List<Sample> window = samples.subList(cursor, Integer.min(window_end, samples.size()));
				double average = 0.0;
				for(Sample sample : window) {
					average += sample.get();
				}
				samples.get(cursor).set(average / (double) this.windowSize);
			}
		}
	}
}
