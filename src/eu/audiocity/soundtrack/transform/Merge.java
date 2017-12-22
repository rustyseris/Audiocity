package eu.audiocity.soundtrack.transform;

import eu.audiocity.soundtrack.Channel;
import eu.audiocity.soundtrack.Sample;
import eu.audiocity.soundtrack.Soundtrack;

import java.util.List;

public class Merge extends Transform {
    private final List<Channel> mergeWith;

    public Merge(Soundtrack mergeWith){
        this.mergeWith = mergeWith.getChannels();
    }

    @Override
    public void apply(List<Channel> channels) {
        int channelCount = Math.min(channels.size(), this.mergeWith.size());
        if(channelCount > 0) {
            int sampleCount = Math.min(channels.get(0).getSamples().size(), this.mergeWith.get(0).getSamples().size());
            for (int i = 0; i < channelCount; i++) {
                List<Sample> mergeWithSamples = this.mergeWith.get(i).getSamples();
                List<Sample> baseSamples = channels.get(i).getSamples();

                int j;
                for (j = 0; j < sampleCount; j++) {
                    Sample base = baseSamples.get(j);
                    base.set((base.get() + mergeWithSamples.get(j).get()) / 2.0);
                }

                for(; j < mergeWithSamples.size(); j++) {
                    baseSamples.add(new Sample(mergeWithSamples.get(j)));
                }
            }
        }
    }
}
