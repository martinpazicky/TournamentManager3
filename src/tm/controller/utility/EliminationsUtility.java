package tm.controller.utility;

import javafx.beans.property.ObjectProperty;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import tm.controller.bracketFX.BracketFX;
import tm.model.Bracket;
import tm.model.Participant;

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
                line3.setStartX(xEnd - breakPoint);
                line3.setStartY(yEnd);
                line3.setEndX(xEnd);
                line3.setEndY(yEnd);
                rootAP.getChildren().add(line3);
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
                        brFX.getParticipant1Lbl().setStyle("-fx-text-fill:red;");
                    }
                    else if (brFX.getBracket().getMatch().getParticipant2().getValue() != null &&
                            brFX.getBracket().getMatch().getParticipant2().getValue()
                                    .equals(highlightedParticipant.getValue())){
                        brFX.getParticipant2Lbl().setStyle("-fx-text-fill:red;");
                    }
                }
        );
    }
}
