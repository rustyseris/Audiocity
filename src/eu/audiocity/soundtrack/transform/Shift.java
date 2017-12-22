package eu.audiocity.soundtrack.transform;

import eu.audiocity.soundtrack.Channel;
import eu.audiocity.soundtrack.Sample;

import java.util.ArrayList;
import java.util.List;

public class Shift extends Transform {

	private double offsetSecond;
	
	public Shift(double offsetSecond) {
		this.offsetSecond = offsetSecond;
	}
	
	@Override
	public void apply(List<Channel> channels) {
        for (Channel channel : channels) {
            List<Sample> samples = channel.getSamples();
            int offsetSamples = durationToNumberOfSamples(this.offsetSecond, channel);
            List<Sample> newSamples = new ArrayList<>(offsetSamples + samples.size());
			if(offsetSamples > 0) {
				for(int j = 0; j < offsetSamples; j++) {
					newSamples.add(new Sample(0));
				}
				newSamples.addAll(samples);
			} else {
			    newSamples.addAll(samples.subList(Math.abs(offsetSamples), samples.size()));
			}
            channel.setSamples(newSamples);
        }
	}

}
