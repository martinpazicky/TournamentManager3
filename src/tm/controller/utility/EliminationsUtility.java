package tm.controller.utility;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.property.ObjectProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import tm.controller.BracketDetailController;
import tm.controller.ScreenController;
import tm.controller.TournamentDetailController;
import tm.controller.bracketFX.BracketFX;
import tm.model.Bracket;
import tm.model.Participant;
import tm.model.tournament.Tournament;

import java.util.List;
import java.util.Map;

public class EliminationsUtility {

    public static void renderLines(List<Bracket>[] brackets, Map<Bracket,BracketFX> brToBrFX, AnchorPane rootAP){
        double breakPoint = 30;
        for(int i = 0; i < brackets.length; i++) {
            for (Bracket bracket : brackets[i]) {
                BracketFX brFX = brToBrFX.get(bracket);
                BracketFX nextBrFX = brToBrFX.get(bracket.getNextBracket());
                if(brFX == null || nextBrFX == null)
                    continue;
                double xStart = brFX.getLayoutX() + BracketFX.WIDTH;
                double yStart = brFX.getLayoutY() + BracketFX.HEIGHT/2;
                double xEnd = nextBrFX.getLayoutX();
                double yEnd = nextBrFX.getLayoutY() + BracketFX.HEIGHT/2;

                Line line1 = new Line();
                line1.setStartX(xStart);
                line1.setStartY(yStart);
                line1.setEndX(xEnd - breakPoint);
                line1.setEndY(yStart);
                rootAP.getChildren().add(line1);
                Line line2 = new Line();
                line2.setStartX(xEnd - breakPoint);
                line2.setStartY(yStart);
                line2.setEndX(xEnd - breakPoint);
                line2.setEndY(yEnd);
                rootAP.getChildren().add(line2);
                Line line3 = new Line();
                line3.setOpacity(0.9);
                line3.setStartX(xEnd - breakPoint);
                line3.setStartY(yEnd);
                line3.setEndY(yEnd);
                line3.setEndX(xEnd);
                rootAP.getChildren().add(line3);
                brFX.addLine(line1);
                brFX.addLine(line2);
                brFX.addLine(line3);
            }
        }
    }

    public static void initializeHighlightListeners(BracketFX brFX, ObjectProperty<Participant> highlightedParticipant){
        brFX.getParticipant1Lbl().setOnMouseEntered(
                t -> highlightedParticipant.set(brFX.getBracket().getMatch().getParticipant1().getValue())
        );
        brFX.getParticipant1Lbl().setOnMouseExited(
                t -> highlightedParticipant.set(null)
        );
        brFX.getParticipant2Lbl().setOnMouseEntered(
                t -> highlightedParticipant.set(brFX.getBracket().getMatch().getParticipant2().getValue())
        );
        brFX.getParticipant2Lbl().setOnMouseExited(
                t -> highlightedParticipant.set(null)
        );
        highlightedParticipant.addListener(
                (observable, oldValue, newValue) ->
                {
                    if(highlightedParticipant.getValue() == null) {
                        brFX.highlightWinner();
                    }
                    else if (brFX.getBracket().getMatch().getParticipant1().getValue() != null &&
                            brFX.getBracket().getMatch().getParticipant1().getValue()
                                    .equals(highlightedParticipant.getValue())){
                        brFX.getParticipant1Lbl().setStyle("-fx-text-fill:#feff26;");
                    }
                    else if (brFX.getBracket().getMatch().getParticipant2().getValue() != null &&
                            brFX.getBracket().getMatch().getParticipant2().getValue()
                                    .equals(highlightedParticipant.getValue())){
                        brFX.getParticipant2Lbl().setStyle("-fx-text-fill:#feff26;");
                    }
                    highlightLines(brFX,highlightedParticipant);
                    highlightBracket(brFX,highlightedParticipant);
                }
        );
    }

    private static void highlightLines(BracketFX brFX, ObjectProperty<Participant> highlightedParticipant){
        if (brFX.getBracket().getMatch().containsParticipant(highlightedParticipant.getValue())
                && highlightedParticipant.getValue() != null
                && brFX.getBracket().getNextBracket() != null
                && brFX.getBracket().getNextBracket().getMatch().containsParticipant(highlightedParticipant.getValue())) {
            for (Line line : brFX.getLines()) {
                line.setStyle("-fx-stroke:  #ffe400; -fx-stroke-width: 3px;");
                line.toFront();
            }
        }else {
            for (Line line : brFX.getLines()) {
                line.setStyle("-fx-stroke: black;");
            }
        }
    }

    private static void highlightBracket(BracketFX brFX, ObjectProperty<Participant> highlightedParticipant){
        if (brFX.getBracket().getMatch().containsParticipant(highlightedParticipant.getValue())
                && highlightedParticipant.getValue() != null) {
                brFX.setStyle("-fx-border-color: #e3ba1e;" +
                        "-fx-border-width: 5px;" +
                        "-fx-border-radius: 5"
                );
        }else {
                brFX.setStyle(
    "-fx-background-insets: 0,1,4,5,6;" +
    "-fx-background-radius: 9,8,5,4,3;" +
    "-fx-effect: dropshadow( three-pass-box , rgba(255,255,255,0.2) , 1, 0.0 , 0 , 1);");
        }
    }

    public static void renderUtilities(Tournament tournament, Map<Bracket,BracketFX> brToBrFX,
    Button finishButton, Button backButton, Label winnerNameLabel, Label tournamentState){
        if (tournament.getTournamentWinner() != null)
            winnerNameLabel.setText(tournament.getTournamentWinner().getNickName());
        tournament.tournamentWinnerProperty().addListener(
                ((observable, oldValue, newValue) ->
                {
                    if(newValue != null)
                        winnerNameLabel.setText(newValue.getNickName());
                    else
                        winnerNameLabel.setText("");
                })
        );
        if (tournament.isFinished())
            tournamentState.setText("Ukončený");

        backButton.setOnAction(
                e ->{
                    TournamentDetailController.tournament = tournament;
                    ScreenController.activate("tournamentDetail");
                }
        );
        finishButton.setOnAction(
                e ->{
                    if (tournament.getTournamentWinner() != null) {
                        tournamentState.setText("Ukončený");
                        tournament.setFinished(true);
                        changeButtonsIcons(brToBrFX);
                        }
                    }
        );
    }

    public static void initializeEditButtonAction(Tournament tournament, Bracket bracket,
                                                  BracketFX brFX,Map<Bracket,BracketFX> brToBrFX) {
        brFX.getEditButton().setOnAction(e -> {
            BracketDetailController.setBracket(bracket);
            BracketDetailController.setEditable(!tournament.isFinished());
            ScreenController.activateInNewWindow("bracketDetail", 500, 500);
        });
        if (tournament.isFinished())
            changeButtonsIcons(brToBrFX);
    }

    public static void changeButtonsIcons(Map<Bracket,BracketFX> brToBrFX){
        for (Bracket bracket: brToBrFX.keySet()) {
            FontAwesomeIconView fontIconView = new FontAwesomeIconView();
            fontIconView.setGlyphName("INFO_CIRCLE");
            fontIconView.setSize("25");
            fontIconView.setStyle("-fx-text-alignment: center;" +
                    "-fx-fill:white;");
            brToBrFX.get(bracket).getEditButton().setGraphic(fontIconView);
        }
    }

}
