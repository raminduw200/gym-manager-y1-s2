package GUI;

import Member.DefaultMember;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;

import static CLI.Main.getMembers;

public class Controllers {

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
        memberName.setPrefWidth(350);
        table.getColumns().add(memberName);

        TableColumn<DefaultMember, LocalDate> memberDate = new TableColumn<>("Membership Date");
        memberDate.setCellValueFactory(new PropertyValueFactory<>("startMembershipDate"));
        memberDate.setPrefWidth(298);
        table.getColumns().add(memberDate);

        table.getItems().addAll(getMembers());
        return table;

    }

}
