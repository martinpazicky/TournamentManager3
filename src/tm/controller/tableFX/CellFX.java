package tm.controller.tableFX;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import tm.model.TableCell;
import java.util.List;

public class CellFX extends AnchorPane {

    @FXML
    private TextField participant1Score;
    @FXML
    private TextField participant2Score;
    @FXML
    private AnchorPane rootPane;
    private TableCell tableCell;
    private List<TableCell>[] table;


    @FXML
    private void initialize() {
        this.tableCell.getMatch().getParticipant1ScoreProperty().addListener(
                (observable, oldValue, newValue) ->
                {
                    participant1Score.setText(String.valueOf(newValue));
                }
        );
        this.tableCell.getMatch().getParticipant2ScoreProperty().addListener(
                (observable, oldValue, newValue) ->
                {
                    participant2Score.setText(String.valueOf(newValue));
                }
        );
        participant1Score.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!oldValue.equals(newValue)){
                if (newValue.equals("") || !(CellFXUtils.isNumeric(newValue)) )
                {
                    System.out.println("To si nemala robiiic");
                }
                else {
                    tableCell.getMatch().setParticipant1Score(Integer.valueOf(newValue));
                }
            }
        });
        participant2Score.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!oldValue.equals(newValue)){
                if (newValue.equals("")  || !(CellFXUtils.isNumeric(newValue)) )
                {
                    System.out.println("To si nemala robiiic");
                }
                else {
                    tableCell.getMatch().setParticipant2Score(Integer.valueOf(newValue));
                }
            }
        });
    }


    public CellFX(TableCell tableCell, List<TableCell>[] table){
        this.tableCell = tableCell;
        this.table = table;
        CellFXUtils.loadFXML(this);
    }
}
