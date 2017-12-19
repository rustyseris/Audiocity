package eu.vmaerten.audiocity.ui.components;
import eu.vmaerten.audiocity.soundtrack.Channel;
import eu.vmaerten.audiocity.soundtrack.Sample;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.List;

class ChannelCanvas extends Canvas {
    static final double CANVAS_HEIGHT = 120;
    private static final double SOUNDWAVE_HEIGHT = CANVAS_HEIGHT;
    private static final Color BACKGROUND = new Color(32 / 255.0, 34 / 255.0, 37 / 255.0, 1);
    private static final Color SOUNDWAVE_COLOR = new Color(67 / 255.0, 125 / 255.0, 67 / 255.0, 1);

    private Channel channel;
    private Service soundwaveDrawer;

    ChannelCanvas(Channel channel) {
        super(100, CANVAS_HEIGHT);
        this.channel = channel;
        this.soundwaveDrawer = new SoundwaveDrawer(this);
    }

    @Override
    public void resize(double width, double height) {
        System.out.println("resize");
        super.resize(width, CANVAS_HEIGHT);
        this.setWidth(width);
        draw();
    }

    @Override
    public double prefWidth(double height) {
        return this.getWidth();
    }

    @Override
    public double prefHeight(double width) {
        return this.getHeight();
    }

    @Override
    public boolean isResizable() {
        return true;
    }

    private void draw() {
        this.soundwaveDrawer.restart();
    }

    private void drawSoundwave() {
        GraphicsContext gc = this.getGraphicsContext2D();
        double width = this.getWidth();
        int sampleCount = this.channel.getSamples().size();

        int partitionCount = (int) Math.ceil(width);
        int chunkSize = sampleCount / partitionCount;
        if(chunkSize == 0) {
            chunkSize = 2;
        }

        List<Sample> samples = this.channel.getSamples();
        int rangeStart = 0, rangeEnd = chunkSize;

        gc.setFill(BACKGROUND);
        gc.fillRect(0, 0, this.getWidth(), CANVAS_HEIGHT);

        gc.setStroke(SOUNDWAVE_COLOR);
        gc.setLineWidth(0.8);
        gc.beginPath();

        for (int x = 0; x < partitionCount; x++) {
            if(x % 2 == 0) {
                double min = 1, max = -1;
                for (Sample sample : samples.subList(rangeStart, Math.min(rangeEnd, sampleCount))) {
                    double samp = sample.get();
                    if(samp > max)
                        max = samp;
                    if(samp< min)
                        min = samp;
                }

                gc.moveTo(x, (1.0 - min) / 2 * SOUNDWAVE_HEIGHT);
                gc.lineTo(x, (1.0 - max) / 2 * SOUNDWAVE_HEIGHT);
            }

            rangeStart = rangeEnd;
            rangeEnd += chunkSize;
        }

        gc.stroke();
        gc.closePath();
    }

    private class SoundwaveDrawer extends Service {
        private ChannelCanvas canvas;

        private SoundwaveDrawer(ChannelCanvas canvas) {
            this.canvas = canvas;
        }

        @Override
        protected Task createTask() {
            ChannelCanvas canvas = this.canvas;
            return new Task() {
                @Override
                protected Object call() throws Exception {
                    canvas.drawSoundwave();
                    return null;
                }
            };
        }
    }
}
