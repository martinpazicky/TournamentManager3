package tm.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Pop up window
 * AlertBox is displayed when user input is wrong
 * @author martinpazicky
 */
public class AlertBox {


    private static Stage setWindow(String title, String message){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(300);
        window.setMinHeight(150);
        return window;
    }

    private static VBox setLayout(String message, Stage window){
        Label label = new Label();
        label.setText(message);
        Button closeButton = new Button("Zavrieť");
        closeButton.setOnAction(e->window.close());
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label,closeButton);
        layout.setAlignment(Pos.CENTER);
        return layout;
    }
    /**
     * Displays AlertBox
     * @param title title of the AlertBox
     * @param message message of the AlertBox
     */
    public static void displayError(String title, String message)
    {
        Stage window = setWindow(title,message);
        VBox layout = setLayout(message,window);
        Scene scene = new Scene(layout);
        window.getIcons().add(new Image("tm/resources/img/error.png"));
        window.setScene(scene);
        window.showAndWait();
    }

    public static void displaySuccess(String title, String message)
    {
        Stage window = setWindow(title,message);
        VBox layout = setLayout(message,window);
        Scene scene = new Scene(layout);
        window.getIcons().add(new Image("tm/resources/img/tm_icon.png"));
        window.setScene(scene);
        window.showAndWait();
    }
}
