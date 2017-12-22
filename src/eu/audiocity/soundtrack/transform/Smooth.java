package eu.audiocity.soundtrack.transform;

import java.util.List;

import eu.audiocity.soundtrack.Channel;
import eu.audiocity.soundtrack.Sample;
import eu.audiocity.soundtrack.Soundtrack;

public class Smooth extends Transform {
	private int windowSize;
	
	public Smooth(int windowSize) {
		this.windowSize = windowSize;
	}
	
	@Override
	public void apply(List<Channel> channels) {
		for (Channel channel : channels) {
			int sequenceOfWindowSize, numberOfWindowSize;
			double sumSamples = 0;
			List<Sample> samples = channel.getSamples();
			
			for (numberOfWindowSize=0; numberOfWindowSize<samples.size(); numberOfWindowSize=numberOfWindowSize+windowSize) {
				for(sequenceOfWindowSize=0; sequenceOfWindowSize<windowSize; sequenceOfWindowSize++) {
					sumSamples = sumSamples + samples.get(sequenceOfWindowSize).get();
					samples.remove(sequenceOfWindowSize);
					samples.add(numberOfWindowSize, new Sample(sumSamples));
				}
			}
		}
	}
}
