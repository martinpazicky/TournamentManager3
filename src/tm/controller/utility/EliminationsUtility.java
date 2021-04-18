package tm.controller.utility;

import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import tm.controller.bracketFX.BracketFX;
import tm.model.Bracket;

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
                System.out.println("start: x = " + xStart + " y = " + yStart);
                System.out.println("end: x = " + xEnd + " y = " + yEnd);

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
}
