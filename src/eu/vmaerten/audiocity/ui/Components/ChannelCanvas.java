package eu.vmaerten.audiocity.ui.Components;
import eu.vmaerten.audiocity.soundtrack.Channel;
import eu.vmaerten.audiocity.soundtrack.Sample;
import eu.vmaerten.audiocity.soundtrack.Soundtrack;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class ChannelCanvas extends Canvas {
    Channel channel;
    final int pixelsPerSecond = 200;

    public ChannelCanvas(Channel channel) {
        super(100, 100);
        this.channel = channel;
    }

    @Override
    public void resize(double width, double height) {
        System.out.println("resize");
        super.resize(width, height);
        this.setWidth(width);
        this.setHeight(height);
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
        GraphicsContext gc = this.getGraphicsContext2D();
        double width = this.getWidth();
        double height = this.getHeight();

        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(0, 0, width, height);

        List<Sample> samples = this.channel.getSamples();
        int step = (int) Math.floor(this.channel.getSize() / width);
        int partition_count = (int) Math.floor(this.channel.getSize() / step);
        List<List<Sample>> partitions = new ArrayList<>(partition_count);
        for (int i = 0; i < partition_count; i++) {
            int x = i * step;
            partitions.add(samples.subList(i * step, (i + 1) * step));
        }

        // p = 100 pixels/seconds
        // s = 44100 samples/seconds
        // [s/p] = samples/pixel

        gc.setStroke(Color.DARKBLUE);
        gc.setLineWidth(1);
        gc.beginPath();
        int x = 0;
        for (List<Sample> partition : partitions) {
            double min = 1;
            double max = -1;
            for (Sample sample : partition) {
                double value = sample.get();
                if(value > max) {
                    max = value;
                } else if(value < min) {
                    min = value;
                }
            }

            gc.moveTo(x, (1.0 - min) / 2 * height);
            gc.lineTo(x, (1.0 - max) / 2 * height);

            x += 1;
        }
        gc.stroke();
        gc.closePath();
    }
}
