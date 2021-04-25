package tm.controller.calendar;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

import java.time.LocalDate;

/**
 * Create an anchor pane that can store additional data.
 */
public class AnchorPaneNode extends AnchorPane {

    // Date associated with this pane
    private LocalDate date;
    private double TempLayoutY = 0;

    /**
     * Create a anchor pane node. Date is not assigned in the constructor.
     * @param children children of the anchor pane
     */
    public AnchorPaneNode(Node... children) {
        super(children);
        this.setMinHeight(150);
        this.getStyleClass().add("calendar-cell");
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getTempLayoutY() {
        return TempLayoutY;
    }

    public void setTempLayoutY(double tempLayoutY) {
        TempLayoutY = tempLayoutY;
    }
}
