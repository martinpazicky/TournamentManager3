package tm.controller;

import javafx.fxml.FXML;

public class HomeController {

    @FXML
    public void handleSingleEliminationButton(){
        ScreenController.activate("singleElimination");
    }

    @FXML
    public void handleDoubleEliminationButton(){
        ScreenController.activate("doubleElimination");
    }

    @FXML
    public void handleFreeForAllButton(){
        ScreenController.activate("freeForAll");
    }

    @FXML
    public void handleSwissSystemButton(){
        ScreenController.activate("swissSystem");
    }

}
