package tm.controller;

import com.sun.xml.internal.ws.wsdl.writer.document.Part;
import javafx.beans.property.IntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import tm.controller.tableFX.CellFX;
import tm.model.Participant;
import tm.model.ParticipantRecord;
import tm.model.TableCell;
import tm.model.tournament.FreeForAll;

import java.net.URL;
import java.util.*;

public class FreeForAllController implements Initializable {

    @FXML
    private AnchorPane rootAP;

    @FXML
    private Button backButton;
    @FXML
    private Button resultsButton;
    @FXML
    private Button finishTournamentButton;
    @FXML
    private Label tournamentWinnerLabel;
    @FXML
    private Label tournamentStateLabel;

    @FXML
    private TableView<ParticipantRecord> participantsTable;
    @FXML
    private TableColumn<ParticipantRecord,String> nameCol;
    @FXML
    private TableColumn<ParticipantRecord, Integer> pointsCol;
    @FXML
    private TableColumn<ParticipantRecord, Integer> matchesPlayedCol;
    @FXML
    private TableColumn<ParticipantRecord, Integer> numOfWinsCol;
    @FXML
    private TableColumn<ParticipantRecord, Integer> numOfLossesCol;
    @FXML
    private TableColumn<ParticipantRecord, Integer> numOfDrawsCol;


    private Map<TableCell, CellFX> cellToCellFX = new HashMap<>();

    public static FreeForAll freeForAll;

    private List<List<TableCell>[]> allRounds;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        rootAP.getStylesheets().add("tm/resources/css/swissSystem.css");
        participantsTable.getStylesheets().add("tm/resources/css/table.css");

        int rounds = freeForAll.getRounds();
        System.out.println(freeForAll.getName());
        int amount = freeForAll.getParticipants().size();
        double heightOfTable = 85*(amount+1);
        allRounds = freeForAll.getAllRounds();


        double yLayout = 50;
        for(int k = 0; k < rounds; k++) {

            GridPane table = new GridPane();


            Label label = new Label(" " + "Round" + " " + (k+1));
            label.setTextFill(Color.web("#6bfc03"));
            label.setFont(new Font(30));
            label.setWrapText(true);
            label.setLayoutY(-20);
            label.setLayoutX(20);

            AnchorPane anchorPane = new AnchorPane();
            anchorPane.setLayoutY(yLayout);
            anchorPane.getChildren().add(label);
            anchorPane.getChildren().add(table);

            table.setPadding(new Insets(20, 20, 20, 20));
            table.setGridLinesVisible(true);
            table.getChildren().removeAll();

            TableCell actualCell;

            List<TableCell>[] allCells = allRounds.get(k);

            for (int i = 0; i <= amount; i++) {
                for (int j = 0; j <= amount; j++) {
                    if (i == j) {
                        AnchorPane empty = new AnchorPane();
                        empty.setStyle("-fx-background-color: black");
                        table.add(empty, j, i);
                        continue;
                    }
                    if (i == 0) {
                        StackPane p = new StackPane();
                        p.setPrefSize(138, 85);
                        actualCell = allCells[i].get(j - 1);
                        Label lbl = new Label(actualCell.getMatch().getParticipant2().getValue().getNickName());
                        lbl.setTextFill(Color.web("#6bfc03"));

                        p.getChildren().add(lbl);
                        StackPane.setAlignment(lbl, Pos.CENTER);

                        table.add(p, j, i);
                        continue;
                    }
                    if (j == 0) {
                        StackPane p = new StackPane();
                        p.setPrefSize(138, 85);
                        actualCell = allCells[i - 1].get(j);
                        Label lbl = new Label(actualCell.getMatch().getParticipant1().getValue().getNickName());
                        lbl.setTextFill(Color.web("#6bfc03"));

                        p.getChildren().add(lbl);
                        StackPane.setAlignment(lbl, Pos.CENTER);

                        table.add(p, j, i);
                        continue;
                    }

                    actualCell = allCells[i - 1].get(j - 1);
                    CellFX cellFX = new CellFX(actualCell, allCells);

                    //if cell have score
                    if (cellFX.getTableCell().getMatch().getParticipant1ScoreProperty().getValue() >= 0){
                        String score1 = String.valueOf(cellFX.getTableCell().getMatch().getParticipant1ScoreProperty().getValue());
                        String score2 = String.valueOf(cellFX.getTableCell().getMatch().getParticipant2ScoreProperty().getValue());
                        cellFX.getParticipant1Score().setText((score1));
                        cellFX.getParticipant2Score().setText((score2));
                    }
                    table.add(cellFX, j, i);
                    cellToCellFX.put(actualCell, cellFX);
                }
            }
            rootAP.getChildren().addAll(anchorPane);
            yLayout = yLayout + heightOfTable + 80;

        }
        setTable();
        if (freeForAll.isFinished()){
            tournamentStateLabel.setText("Ukon??en??");
            resultsButton.setDisable(true);
            ParticipantRecord winnerRecord = participantsTable.getItems().get(0);
            Participant winner = winnerRecord.getParticipant();
            tournamentWinnerLabel.setText(winner.getNickName());
        }
    }

    public void setTable() {
        participantsTable.getItems().clear();
        participantsTable.setItems(getParticipantsRecords());
        nameCol.setCellValueFactory(new PropertyValueFactory<>("participant"));
        pointsCol.setCellValueFactory(new PropertyValueFactory<>("points"));
        matchesPlayedCol.setCellValueFactory(new PropertyValueFactory<>("matchesPlayed"));
        numOfWinsCol.setCellValueFactory(new PropertyValueFactory<>("numOfWins"));
        numOfLossesCol.setCellValueFactory(new PropertyValueFactory<>("numOfLosses"));
        numOfDrawsCol.setCellValueFactory(new PropertyValueFactory<>("numOfDraws"));
        pointsCol.setSortType(TableColumn.SortType.DESCENDING);
        participantsTable.getSortOrder().add(pointsCol);
    }

    public ObservableList<ParticipantRecord> getParticipantsRecords() {
        Map<Participant, ParticipantRecord> allRecords = freeForAll.getParticipantsToRecords();
        ObservableList<Participant> allPlayers = FXCollections.observableArrayList();
        allPlayers.addAll(freeForAll.getParticipants());
        ObservableList<ParticipantRecord> participantRecords = FXCollections.observableArrayList();
        for (Map.Entry<Participant, ParticipantRecord> entry : allRecords.entrySet()) {
            participantRecords.add(entry.getValue());
        }
        return participantRecords;
    }


    public void updateTable(){

        int amount = freeForAll.getParticipants().size();
        List<Participant> participants = freeForAll.getParticipants();
        for (Participant participant : participants) {
            ParticipantRecord record = freeForAll.getParticipantsToRecords().get(participant);
            record.setPoints(0);
            record.setMatchesPlayed(0);
            record.setNumOfLosses(0);
            record.setNumOfWins(0);
            record.setNumOfDraws(0);
        }

        for (List<TableCell>[] AllCells : allRounds) {
            for (int i = 0; i < amount; i++) {
                for (int j = 0; j < amount; j++) {
                    if (i <= j) {
                        continue;
                    } else {
                        TableCell actualCell = AllCells[i].get(j);
                        Participant participant1 = actualCell.getMatch().getParticipant1().get();
                        Participant participant2 = actualCell.getMatch().getParticipant2().get();
                        IntegerProperty participant1Score = actualCell.getMatch().getParticipant1ScoreProperty();
                        IntegerProperty participant2Score = actualCell.getMatch().getParticipant2ScoreProperty();

                        ParticipantRecord record1 = freeForAll.getParticipantsToRecords().get(participant1);
                        ParticipantRecord record2 = freeForAll.getParticipantsToRecords().get(participant2);
                        if (participant1Score.getValue() == -1 || participant2Score.getValue() == -1) {
                            continue;
                        }

                        if (participant1Score.getValue() != null && participant2Score.getValue() != null) {
                            record1.setMatchesPlayed(record1.getMatchesPlayed() + 1);
                            record2.setMatchesPlayed(record2.getMatchesPlayed() + 1);
                            if (actualCell.getMatch().getParticipant1ScoreProperty().get() == actualCell.getMatch().getParticipant2ScoreProperty().get()) {
                                record1.setPoints(record1.getPoints() + 1);
                                record2.setPoints(record2.getPoints() + 1);
                                record1.setNumOfDraws(record1.getNumOfDraws() + 1);
                                record2.setNumOfDraws(record2.getNumOfDraws() + 1);
                            } else if (actualCell.getMatch().getParticipant1ScoreProperty().get() > actualCell.getMatch().getParticipant2ScoreProperty().get()) {
                                record1.setPoints(record1.getPoints() + 3);
                                record1.setNumOfWins(record1.getNumOfWins() + 1);
                                record2.setNumOfLosses(record2.getNumOfLosses() + 1);
                            } else if (actualCell.getMatch().getParticipant1ScoreProperty().get() < actualCell.getMatch().getParticipant2ScoreProperty().get()) {
                                record2.setPoints(record2.getPoints() + 3);
                                record2.setNumOfWins(record2.getNumOfWins() + 1);
                                record1.setNumOfLosses(record1.getNumOfLosses() + 1);
                            }
                        }
                    }
                }
            }
        }
        participantsTable.getItems().clear();
        participantsTable.setItems(getParticipantsRecords());
        pointsCol.setSortType(TableColumn.SortType.DESCENDING);
        participantsTable.getSortOrder().add(pointsCol);
    }

    @FXML
    public void goBack(){
        TournamentDetailController.tournament = freeForAll;
        ScreenController.activate("tournamentDetail");
    }
    @FXML
    public void finish(){
        freeForAll.setFinished(true);
        tournamentStateLabel.setText("Ukon??en??");
        ParticipantRecord winnerRecord = participantsTable.getItems().get(0);
        Participant winner = winnerRecord.getParticipant();
        tournamentWinnerLabel.setText(winner.getNickName());
        freeForAll.setTournamentWinner(winner);
    }
}
