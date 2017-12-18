package eu.vmaerten.audiocity.soundtrack;

import eu.vmaerten.audiocity.soundtrack.Exceptions.BadInputValue;

public class Sample implements Cloneable {
    private double sample;

    /**
     * Create a sample from a double.
     * @param sample Sample value must be between 1 and -1 (inclusive)
     * @throws BadInputValue When value is not inside the inclusive range [-1;1]
     */
    public Sample(double sample) throws BadInputValue {
        if(sample >= -1.0 && sample <= 1.0) {
            this.sample = sample;
        } else {
            throw new BadInputValue(sample);
        }
    }

    /**
     * Create a copy of the given sample
     * @param sample Sample to copy
     */
    public Sample(Sample sample) {
        this.sample = sample.get();
    }

    public double get() { return this.sample; }

    public void set(double sample) {
        this.sample = Double.min(Double.max(sample, 1.0), -1.0);
    }
}
