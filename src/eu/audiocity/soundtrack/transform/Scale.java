package eu.audiocity.soundtrack.transform;

import java.util.List;

import eu.audiocity.soundtrack.Channel;
import eu.audiocity.soundtrack.Sample;

public class Scale extends Transform {
	
	private double coefficient;
	
	public Scale(double coefficient) {
		this.coefficient = coefficient;
	}
	
	@Override
	public void apply(List<Channel> channels) {
		for (Channel channel : channels) {	
			for(Sample sample : channel) {
				sample.set(sample.get() * this.coefficient);
			}
		}	
	}
		
}
