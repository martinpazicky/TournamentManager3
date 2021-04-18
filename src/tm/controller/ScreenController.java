package tm.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.util.HashMap;

public class ScreenController {
    private static HashMap<String, String> screenMap = new HashMap<>();
    public static Window stage;
    public static Scene main;


    public static void addScreen(String name, String path) {
        screenMap.put(name, path);
    }

    public static void activate(String name){
        try {
            main.setRoot(FXMLLoader.load(ScreenController.class.getResource(screenMap.get(name))));
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void activateInNewWindow(String name, int width, int height) {
        final Stage dialog = new Stage();
        try {
            Parent root = FXMLLoader.load(ScreenController.class.getResource(screenMap.get(name)));
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(stage);
            Scene dialogScene = new Scene(root, width, height);
//            dialogScene.getStylesheets().add("booking/resources/style.css");
//            dialog.getIcons().add(new Image("booking/resources/hotelIcon.jpg"));
            dialog.setMaxWidth(width);
            dialog.setMaxHeight(height);
            dialog.setMinWidth(width);
            dialog.setMinHeight(height);
            dialog.setScene(dialogScene);
            dialog.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
