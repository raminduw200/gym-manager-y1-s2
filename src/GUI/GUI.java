package GUI;

import Member.DefaultMember;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

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
    public void start(Stage primaryStage) throws FileNotFoundException {
        primaryStage.setTitle("My GYM Manager");

        TextField searchBar = Controllers.createTxtField(301, 42 , 563, 130);
        searchBar.setPromptText("Search....");
        ObservableList<String> selections = FXCollections.observableArrayList(
                "Membership No", "Membership Name", "Membership Date");
        comboBox = Controllers.createComboBox(selections, 301, 42, 65, 130);
        comboBox.setValue("Membership Name");
        comboBox.setId("selections");
        Button showBtn = Controllers.createButton("Show", 100, 38, 430, 132);

        showBtn.setOnAction(new EventHandler<ActionEvent>() {
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
        table.setPrefSize(800,410);
        table.setLayoutY(200);
        table.setLayoutX(65);


        FileInputStream imageFile = new FileInputStream("src/Images/background.jpg");
        Image backgroundImg = new Image(imageFile);
        ImageView backgroundView = new ImageView(backgroundImg);

        AnchorPane pane = new AnchorPane();
        pane.setPrefSize(950,720);
        pane.getChildren().addAll(
                backgroundView,
                table,
                searchBar,
                showBtn,
                comboBox
        );

        Scene scene = new Scene(pane, 950, 720);
        scene.getStylesheets().add("GUI/Design.css");
        primaryStage.setTitle("My Gym Manager");
        primaryStage.setMaxWidth(950);
        primaryStage.setMaxHeight(720);
        primaryStage.setMinWidth(950);
        primaryStage.setMinHeight(720);
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
