package com.itbd.energymanager.utiles;


import javafx.event.EventHandler;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;

import java.io.File;
import java.net.MalformedURLException;

public class ImageZoomView extends ScrollPane {
    private final ImageView imageView = new ImageView();
    private File dropFile;

    public ImageZoomView(boolean zoomControl, boolean fileDropControl) {
        parentViewConfig();
        imageViewConfig();

        if (zoomControl) imageZoomControl();
        if (fileDropControl) imageDragAndDropControl();
    }

    public ImageZoomView() {
        parentViewConfig();
        imageViewConfig();
    }

    public File getDropFile() {
        return dropFile;
    }

    private void parentViewConfig() {
        this.setPannable(true);
        this.setFitToHeight(true);
        this.setFitToWidth(true);
        this.setContent(imageView);
    }

    private void imageViewConfig() {
        imageView.setFocusTraversable(true);
        imageView.setPreserveRatio(true);
        this.widthProperty().addListener((observableValue, number, number2) -> imageView.setFitWidth((double) number2 - 10));
        this.heightProperty().addListener((observableValue, number, number2) -> imageView.setFitHeight((double) number2 - 10));
    }

    public void setImage(Image image) {
        imageView.setVisible(true);
        imageView.setImage(image);
    }

    public void clearImage() {
        imageView.setVisible(false);
    }

    private void imageZoomControl() {
        EventHandler<ScrollEvent> eventHandler = scrollEvent -> {
            double zoomFactor = 1.05;
            double deltaY = scrollEvent.getDeltaY();
            if (deltaY < 0) {
                zoomFactor = 2 - zoomFactor;
            }
            imageView.setFitWidth(imageView.getFitWidth() * zoomFactor);
            imageView.setFitHeight(imageView.getFitHeight() * zoomFactor);
        };
        this.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.CONTROL) {
                imageView.addEventHandler(ScrollEvent.SCROLL, eventHandler);
            }
        });
        this.setOnKeyReleased(keyEvent -> imageView.removeEventHandler(ScrollEvent.SCROLL, eventHandler));
        this.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                imageView.setFitWidth(this.getWidth() - 10);
                imageView.setFitHeight(this.getHeight() - 10);
            }
        });
    }

    private void imageDragAndDropControl() {
        EventHandler<DragEvent> eventHandler = event -> {
            if (event.getDragboard().hasFiles()) {
                event.acceptTransferModes(TransferMode.ANY);
                File dropFile = event.getDragboard().getFiles().stream().findFirst().get();
                if (dropFile.exists()) {
                    try {
                        this.dropFile = dropFile;
                        String imageUrl = this.dropFile.toURI().toURL().toString();
                        this.setImage(new Image(imageUrl, false));
                    } catch (MalformedURLException ignored) {
                    }
                }
            }
        };
        this.addEventHandler(DragEvent.DRAG_OVER, eventHandler);
    }
}
