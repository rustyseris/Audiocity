package eu.audiocity.soundtrack;

public class Sample {
    private double sample;

    /**
     * Create a new sample from the given value
     * @param sample will be clipped if outside the [-1;1] range (inclusive)
     */
    public Sample(double sample) {
        this.set(sample);
    }

    public Sample(Sample sample) {
        this.sample = sample.sample;
    }

    public double get() {
        return this.sample;
    }

    public void set(double sample) {
        this.sample = Math.min(Math.max(sample, -1), 1);
    }
}
