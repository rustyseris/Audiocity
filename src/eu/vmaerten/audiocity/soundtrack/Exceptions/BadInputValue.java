package eu.vmaerten.audiocity.soundtrack.Exceptions;

public class BadInputValue extends Exception {
    public BadInputValue(Double input) {
        super(String.format("A channel can only contains value between -1 and 1. Value %f was given.", input));
    }
}
