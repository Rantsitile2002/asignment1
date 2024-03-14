package com.example.gallery;



import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class HelloApplication extends Application {

    private List<Image> images = new ArrayList<>();
    private int currentIndex = 0;
    private Stage primaryStage;
    private Stage fullSizeStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        loadImagesFromResources();

        // Initialize thumbnail grid
        FlowPane thumbnailGrid = new FlowPane();
        thumbnailGrid.setPadding(new Insets(10));
        thumbnailGrid.setHgap(10);
        thumbnailGrid.setVgap(10);
        thumbnailGrid.setBackground(new Background(new BackgroundFill(Color.GREY, null, null)));

        // Add title label
        Label titleLabel = new Label("This is Image Gallery");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        thumbnailGrid.getChildren().add(titleLabel);

        for (int i = 0; i < images.size(); i++) {
            int index = i; // Capture the index in a final variable
            ImageView thumbnail = createThumbnail(index);
            thumbnail.setOnMouseClicked(event -> showFullSizeImage(index)); // Show full-size image on click
            thumbnailGrid.getChildren().add(thumbnail);
        }

        // Scrollable pane for thumbnail grid
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(thumbnailGrid);

        // Set up scene
        Scene scene = new Scene(scrollPane, 800, 600);

        primaryStage.setTitle("Image Gallery");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void loadImagesFromResources() {
        File directory = new File(getClass().getResource("/images").getFile());
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                images.add(new Image(file.toURI().toString()));
            }
        }
    }

    private ImageView createThumbnail(int index) {
        Image image = images.get(index);
        ImageView thumbnail = new ImageView(image);
        thumbnail.setFitWidth(150);
        thumbnail.setPreserveRatio(true);

        return thumbnail;
    }

    private void showFullSizeImage(int index) {
        ImageView currentImageView = new ImageView(images.get(index));
        currentImageView.setFitWidth(400);
        currentImageView.setPreserveRatio(true);

        Label rateLabel = new Label("Rate this picture");
        Button rateButton = new Button("Rate");
        rateButton.setOnAction(event -> ratePicture(index));

        Button backButton = new Button("Back");
        backButton.setOnAction(event -> fullSizeStage.close()); // Close full-size stage

        VBox vbox = new VBox(currentImageView, rateLabel, rateButton, backButton);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);

        currentIndex = index;
        fullSizeStage = new Stage();
        fullSizeStage.setTitle("Full Size Image");
        fullSizeStage.setScene(new Scene(new StackPane(vbox), 800, 600));
        fullSizeStage.show();
    }

    private void ratePicture(int index) {
        // Implementation for rating the picture goes here
        System.out.println("Rating picture at index: " + index);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
