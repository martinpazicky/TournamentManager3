package tm.controller.bracketFX;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class FXMLUtils {
    public static <T extends Parent> void loadFXML(T component) {
        FXMLLoader loader = new FXMLLoader();
        loader.setRoot(component);
        loader.setControllerFactory(theClass -> component);

        String fileName = component.getClass().getSimpleName() + ".fxml";
        try {
            fileName = "bracket.fxml";
            loader.load(component.getClass().getResourceAsStream(fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
