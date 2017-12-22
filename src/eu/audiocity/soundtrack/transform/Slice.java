package eu.audiocity.soundtrack.transform;

import eu.audiocity.soundtrack.Channel;
import eu.audiocity.soundtrack.Sample;

import java.util.ArrayList;
import java.util.List;

public class Slice extends Transform{

	private double fromIndexTime;
	private double toIndexTime;
	
	public Slice(double fromIndexTime, double toIndexTime) {
		this.fromIndexTime = fromIndexTime;
		this.toIndexTime = toIndexTime;
	}
	
	@Override
	public void apply(List<Channel> channels) {
		for(Channel channel : channels) {
			int fromIndexNumberSamples = durationToNumberOfSamples(fromIndexTime, channel);
			int toIndexNumberSamples = durationToNumberOfSamples(toIndexTime, channel);

			if(fromIndexNumberSamples >= 0 && toIndexNumberSamples <= channel.getSamples().size()){
			    List<Sample> samples = new ArrayList<>(channel.getSamples().subList(fromIndexNumberSamples, toIndexNumberSamples));
                channel.setSamples(samples);
            } else {
			    break;
            }
		}
	}

}
