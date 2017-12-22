package eu.audiocity.soundtrack.transform;

import eu.audiocity.soundtrack.Channel;

import java.util.List;

public class SwapChannels extends Transform {
    @Override
    public void apply(List<Channel> channels) {
        if(channels.size() == 2) {
            Channel left = channels.get(0);
            Channel right = channels.get(1);
            channels.clear();
            channels.add(left);
            channels.add(right);
        }
    }
}
