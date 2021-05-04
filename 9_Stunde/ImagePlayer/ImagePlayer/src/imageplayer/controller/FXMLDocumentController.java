/*
 * Copyright © 2020.
 * Department of Informatics and Media Technique, HTBLA Leonding,
 * Limesstr. 12 - 14, 4060 Leonding, AUSTRIA. All Rights Reserved.
 */
package imageplayer.controller;

import java.io.File;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;

/**
 *
 * @author syt@htl-leonding.ac.at
 */
public class FXMLDocumentController implements Initializable, Observer {

    @FXML
    private Button pauseButton;
    @FXML
    private BorderPane borderPane;
    @FXML
    private Label labelPath;
    @FXML
    private ImageView imageView;
    @FXML
    private FlowPane buttonBar;
    @FXML
    private Button btnStart;
    @FXML
    private Button btnLoad;
    private ImageDiscoverer discoverer;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Label labelProgress;
      
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pauseButton.setOnMouseClicked(this::onPauseClick);
    }

    @FXML
    private void handleStartButton(ActionEvent event) {
        System.out.println("Start button pressed...");
        if (discoverer != null) {
            Thread t = new Thread(() -> {
                discoverer.discover();
            });
            t.start();
        }
    }

    @FXML
    private void handleLoadButton(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image");
        fileChooser.setInitialDirectory(new File("."));
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
        File imageFile = fileChooser.showOpenDialog(null);
        labelPath.setText(imageFile.getName());
        setImage(new Image(imageFile.toURI().toString()));
    }

    private void setImage(Image image) {
        progressBar.setProgress(0);
        discoverer = new ImageDiscoverer(image);
        discoverer.addObserver(this);
        imageView.setImage(discoverer.getDestinationImage());
        int width = (int)image.getWidth();
        int height = (int)image.getHeight();
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);  
        borderPane.setCenter(null);
        borderPane.setCenter(imageView);
        borderPane.setPrefHeight(height + buttonBar.getPrefHeight());
        borderPane.setPrefWidth(Math.max(width, buttonBar.getPrefWidth()));
        Scene scene = borderPane.getScene();
        if (scene != null){
            scene.getWindow().sizeToScene();
        }
    }

    public void updateProgressLabel(double progress) {
        labelProgress.setText("" + (Math.round(progress * 10000)/ (double) 100) + " %");
        if (progress > 0.99) {
            labelProgress.setText("" + 100 + " %");
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof Double) {
            double value = (Double) arg;
            updateProgressLabel(value);
            progressBar.setProgress(value);
        }
    }

    @FXML
    public void onPauseClick(MouseEvent mouseEvent) {
        if (discoverer != null) {
            discoverer.setPaused(!discoverer.isPaused());
            pauseButton.setText(discoverer.isPaused() ? "Resume": "Pause");
        }
    }
}
