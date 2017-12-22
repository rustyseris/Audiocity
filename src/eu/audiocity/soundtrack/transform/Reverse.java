package eu.audiocity.soundtrack.transform;

import eu.audiocity.soundtrack.Channel;
import eu.audiocity.soundtrack.Sample;

import java.util.ArrayList;
import java.util.List;

public class Reverse extends Transform {
    @Override
    public void apply(List<Channel> channels) {
        for (Channel channel : channels) {
            List<Sample> samples = channel.getSamples();
            List<Sample> newSamples = new ArrayList<>(channel.getSamples().size());
            for (int i = samples.size() - 1; i >= 0; i--) {
                newSamples.add(samples.get(i));
            }
            channel.setSamples(newSamples);
        }
    }
}
