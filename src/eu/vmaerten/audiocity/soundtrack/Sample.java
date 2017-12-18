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

    /**
     * Return the current value contained in the sample
     * @return the sample value
     */
    public double get() { return this.sample; }

    /**
     * Set the inner value of the sample. The method will make sure the value stays between -1 and 1
     * @param sample new value of the sample
     */
    public void set(double sample) {
        this.sample = Double.max(Double.min(sample, 1.0), -1.0);
    }
}
