/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package approject_2;

import java.io.IOException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 *
 * @author Aziz
 */
public class APProject_2 extends Application {

    TextField search;
    String input = "";
    Slider slider;
    TextField txt1 = new TextField();
    DatePicker d = new DatePicker();
    static Label addMessage;
    static Label searchMessage;
    GridPane gp = new GridPane();
    double defaultSliderValue = 0.0;
    String result = "0.0";
    static ObservableList<Student> students = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) {

        // DEFINING COMPONENTS -----------------------------------------------------------------------------------------
        TabPane tab = new TabPane();

        Tab tb1 = new Tab("add record");
        Tab tb2 = new Tab("Show all records");

        VBox vb1 = new VBox(15);
        vb1.setPadding(new Insets(20));
        vb1.setAlignment(Pos.CENTER);

        HBox hb1 = new HBox(15);
        hb1.setAlignment(Pos.CENTER);

        gp.setAlignment(Pos.CENTER);
//        gp.setGridLinesVisible(true);
        gp.setVgap(25);
        gp.setHgap(25);

        Label name = new Label();

        Label sName = new Label("Name");

        Label date = new Label("Date");

        Label gpa = new Label("GPA");
        slider = new Slider(0, 4, defaultSliderValue);
        Label sliderValue = new Label("Value: 0.0");

        addMessage = new Label("");
        addMessage.setFont(Font.font("", FontWeight.BOLD, 16));

        Button addBtn = new Button("ADD");
        Button exitBtn = new Button("EXIT");

        // CHANGING DETAILS OF COMPONENTS AND MODIFYING IT -----------------------------------------------------------------------------------------
        name.setText("AbdulazizAlamoudi-441016500-AbdulazizBahamid-441016576");
        name.setFont(Font.font("", FontWeight.BOLD, 16));

        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(0.5);
        slider.setMinorTickCount(5);
        slider.setBlockIncrement(0.1);
        slider.setSnapToTicks(true);

        d.setEditable(false);

        // ADDING COMPONENTS INTO EACHOTHER AND INTO THE SCENE -----------------------------------------------------------------------------------------
        tab.getTabs().add(tb1);
        tab.getTabs().add(tb2);

        // ACTION FUNCTIONS
        addBtn.setOnAction(e -> {
            String dateInput;
            if (d.getValue() == null) {
                dateInput = "";
            } else {
                dateInput = d.getValue().toString();
            }
            String nameInput = txt1.getText();
            try {
                if (Methods.add(nameInput, dateInput, result)) {
                    clear();
                }
            } catch (SQLException ex) {
                addMessage.setTextFill(Color.RED);
                addMessage.setText("there is something wrong with the database connection");
            } catch (IOException ex) {
                addMessage.setTextFill(Color.RED);
                addMessage.setText("there is something wrong with the 'config.properties' file");
            }
        });

        exitBtn.setOnAction(e -> {
            Platform.exit();
        });

        slider.valueProperty().addListener(
                new ChangeListener<Number>() {

            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                double value = newValue.doubleValue();
                result = String.format("%,.1f", value);
                slider.setValue(Double.parseDouble(result));
                sliderValue.setText("value: " + result);
            }
        });

        //FIRST TAB DATA
        vb1.getChildren().add(name);
        gp.add(sName, 0, 0);
        gp.add(txt1, 1, 0);
        gp.add(date, 0, 1);
        gp.add(d, 1, 1);
        gp.add(gpa, 0, 2);
        gp.add(slider, 1, 2);
        vb1.getChildren().add(gp);
        vb1.getChildren().add(sliderValue);
        vb1.getChildren().add(addMessage);
        hb1.getChildren().add(addBtn);
        hb1.getChildren().add(exitBtn);
        vb1.getChildren().add(hb1);
        tb1.setContent(vb1);

        //SECOND TAB DATA
        TableView<Student> tableView = new TableView<Student>();
        tableView.setMaxWidth(500);

        TableColumn column1 = new TableColumn<>("ID");
        column1.setCellValueFactory(
                new PropertyValueFactory<>("ID"));
        column1.setMinWidth(100);

        TableColumn column2 = new TableColumn<>("Full name");
        column2.setCellValueFactory(
                new PropertyValueFactory<>("FullName"));

        column2.setMinWidth(150);

        TableColumn column3 = new TableColumn<>("Date of birth");
        column3.setCellValueFactory(
                new PropertyValueFactory<>("DateOfBirth"));
        column3.setMinWidth(150);

        TableColumn column4 = new TableColumn<>("GPA");
        column4.setCellValueFactory(
                new PropertyValueFactory<>("GPA"));
        column4.setMinWidth(100);

        Text text = new Text("Searching For Students Record");
        text.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        Label searchLabel = new Label("Search By Name:");
        searchLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        searchMessage = new Label("");
        searchMessage.setFont(Font.font("", FontWeight.BOLD, 16));
        search = new TextField();
        search.setMaxWidth(200);
        Button searchBtn = new Button("Search");
        Button refreshBtn = new Button("Refresh");
        Button exitBtn2 = new Button("Exit");

        tableView.getColumns().add(column1);
        tableView.getColumns().add(column2);
        tableView.getColumns().add(column3);
        tableView.getColumns().add(column4);

        tb2.setOnSelectionChanged(e -> {                         /////////////////////////////// EDIT ME !!!!!!!!!!!!!!!!!!!
            Methods.passName(input);
        });

        searchBtn.setOnAction(e -> {
            input = search.getText();
            Methods.passName(input);
        });

        refreshBtn.setOnAction(e -> {
            Methods.passName(input);
        });

        exitBtn2.setOnAction(e -> {
            Platform.exit();
        });

        tableView.setItems(students);

        VBox vb2 = new VBox(10);
        vb2.setAlignment(Pos.CENTER);

        HBox hb2 = new HBox(15);
        hb2.setAlignment(Pos.CENTER);

        hb2.getChildren().add(searchBtn);
        hb2.getChildren().add(refreshBtn);

        vb2.setPadding(new Insets(20, 10, 20, 10));
        vb2.getChildren().addAll(text, searchLabel, search, hb2, searchMessage, tableView, exitBtn2);

        tb2.setContent(vb2);

        // SCENE Details
        Scene scene = new Scene(tab);
        primaryStage.setTitle("Student DataBase");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setMinHeight(500);
        primaryStage.setMinWidth(500);

    }

    public void clear() {
        txt1.setText("");
        d.setValue(null);
        slider.setValue(0.0);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
