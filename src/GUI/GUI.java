package GUI;

import CLI.Date;
import Manager.MyGymManager;
import Member.DefaultMember;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;

import static CLI.Main.getMembers;

public class GUI extends Application {
    private static Stage stage;
    private static TableView table;
    private static ComboBox comboBox;
    private static String searchTerm;

    public static void main(){
        launch();
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("My GYM Manager");

        Label heading = Controllers.createLabel("My Gym Manager", 242, 30);
        heading.setFont(new Font(40));
        TextField searchBar = Controllers.createTxtField(301, 32 , 462, 117);
        ObservableList<String> selections = FXCollections.observableArrayList(
                "Membership No", "Membership Name", "Membership Date");
        comboBox = Controllers.createComboBox(selections, 301, 32, 58, 117);
        comboBox.setValue("Membership Name");
        Button goBtn = Controllers.createButton("Show", 53, 32, 384, 117);

        goBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //search content refresh by setting searchbar space and then set to null.
                searchBar.setText(" ");
                searchBar.setText("");
            }
        });

        //keep track on searchbar - call updateTable function
        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            searchTerm = newValue;
            updateTable();
        });

        table = Controllers.createTable();
        table.setPrefSize(705,400);
        table.setLayoutY(170);
        table.setLayoutX(58);

        AnchorPane pane = new AnchorPane(table);
        pane.setPrefSize(821,670);
        pane.getChildren().addAll(
                heading,
                searchBar,
                comboBox,
                goBtn
        );

        Scene scene = new Scene(pane, 821, 670);
        primaryStage.setTitle("My Gym Manager");
        primaryStage.setMaxWidth(821);
        primaryStage.setMaxHeight(670);
        primaryStage.setMinWidth(821);
        primaryStage.setMinHeight(670);
        primaryStage.setScene(scene);
        stage = primaryStage;

    }

    public static void stageShow(){
        stage.show();
    }

    private static void updateTable() {
        /*
        *clear all table entries
        * Looping through each object in getMember array list
        * if searchTerm of the user found, it will add to the table
        * refresh the table to show the output - searchTerm matching items
        */
        table.getItems().clear();
        if (comboBox.getValue().equals("Membership No")){
            for (DefaultMember member : getMembers()) {
                if (member.getMembershipNumber().contains(searchTerm)) {
                    table.getItems().add(member);
                }
            }
        }
        else if (comboBox.getValue().equals("Membership Name")){
            for (DefaultMember member : getMembers()) {
                if (member.getName().toLowerCase().contains(searchTerm.toLowerCase())) {
                    table.getItems().add(member);
                }
            }
        } else if (comboBox.getValue().equals("Membership Date")){
            for (DefaultMember member : getMembers()) {
                if (String.valueOf(member.getStartMembershipDate()).contains(searchTerm)) {
                    table.getItems().add(member);
                }
            }
        }
        table.refresh();
    }

}
