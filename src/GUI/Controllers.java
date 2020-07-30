package GUI;

import Member.DefaultMember;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import static CLI.Main.getMembers;

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

    //comboBox declaration
    public static ComboBox createComboBox(ObservableList selections, double width, double height, double x, double y){
        ComboBox cb = new ComboBox(selections);
        cb.setLayoutX(x);
        cb.setLayoutY(y);
        cb.setPrefSize(width,height);
        return cb;
    }

    //TableView declaration - ignore error occur in TableView : @SuppressWarnings
    @SuppressWarnings("unchecked")
    public static TableView createTable() {

        TableView table = new TableView();

        TableColumn<DefaultMember, String> membershipNo = new TableColumn<>("Membership No");
        membershipNo.setCellValueFactory(new PropertyValueFactory<>("membershipNumber"));
        membershipNo.setPrefWidth(150);
        table.getColumns().add(membershipNo);

        TableColumn<DefaultMember, String> memberName = new TableColumn<>("Membership Name");
        memberName.setCellValueFactory(new PropertyValueFactory<>("name"));
        memberName.setPrefWidth(262);
        table.getColumns().add(memberName);

        TableColumn<DefaultMember, String> memberDate = new TableColumn<>("Membership Date");
        memberDate.setCellValueFactory(new PropertyValueFactory<>("startMembershipDate"));
        memberDate.setPrefWidth(291);
        table.getColumns().add(memberDate);

        table.getItems().addAll(getMembers());
        return table;

    }

}
