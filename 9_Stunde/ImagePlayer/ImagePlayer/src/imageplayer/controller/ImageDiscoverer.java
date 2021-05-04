package imageplayer.controller;

import javafx.application.Platform;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class ImageDiscoverer extends Observable {

    private Image sourceImage;
    private int width;
    private int height;
    private WritableImage destinationImage;
    private boolean isPaused = false;

    public ImageDiscoverer(Image image) {
        this.sourceImage = image;
        this.width = (int) sourceImage.getWidth();
        this.height = (int) sourceImage.getHeight();
        destinationImage = new WritableImage(width, height);
        initPixels();
    }

    private void initPixels() {
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                Color c = Color.rgb((int) (Math.random()*255), (int) (Math.random()*255), (int) (Math.random()*255), 0.5);
                destinationImage.getPixelWriter().setColor(column, row, c);
            }
        }
    }

    public Image getDestinationImage() {
        return destinationImage;
    }

    public void discover() {

        /*
        for (int i = 0; i < width*height*16; i++) {

            int randomX;
            int randomY;

            randomX = (int) (Math.random() * (width));
            randomY = (int) (Math.random() * (height));

            int finalRandomX = randomX;
            int finalRandomY = randomY;
            Platform.runLater(() -> {
                Color c = sourceImage.getPixelReader().getColor(finalRandomX, finalRandomY);
                destinationImage.getPixelWriter().setColor(finalRandomX, finalRandomY, c);
            });

            if (i % 100 == 0) {
                try {
                    Thread.sleep(1);
                }catch(InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }*/

        for (int row = 0; row < height; row++) {

            synchronized (this) {
                while (isPaused) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            int localRow = row;
            double actualProgress = row / (double) height;
            Platform.runLater(() -> {
                setChanged();
                notifyObservers(actualProgress);
                for (int column = 0; column < width; column++) {
                    Color c = sourceImage.getPixelReader().getColor(column, localRow);
                    destinationImage.getPixelWriter().setColor(column, localRow, c);
                }
            });
            try {
                Thread.sleep(10);
            }catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
        if (!paused) {
            synchronized (this) {
                this.notifyAll();
            }
        }
    }
}
