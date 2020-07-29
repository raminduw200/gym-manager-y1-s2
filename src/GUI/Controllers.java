package GUI;

import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class Controllers {

    //Label declaration.
    public static Label createLabel(String newLbName, double x, double y) {
        Label lbName = new Label(newLbName);
        lbName.setLayoutX(x);
        lbName.setLayoutY(y);
        return lbName;
    }

    //Button declaration.
    public static Button createButton(String newBtnName, double width, double height, double x, double y) {
        Button newBtn = new Button(newBtnName);
        newBtn.setPrefSize(width, height);
        newBtn.setLayoutX(x);
        newBtn.setLayoutY(y);
        return newBtn;
    }

    //TextField declaration.
    public static TextField createTxtField(double width, double height, double x, double y) {
        TextField newTxtField = new TextField();
        newTxtField.setPrefSize(width,height);
        newTxtField.setLayoutX(x);
        newTxtField.setLayoutY(y);
        return newTxtField;
    }

    public static ComboBox createComboBox(ObservableList selections, double width, double height, double x, double y){
        ComboBox cb = new ComboBox(selections);
        cb.setLayoutX(x);
        cb.setLayoutY(y);
        cb.setPrefSize(width,height);
        return cb;
    }

}
