/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package approject_2;

import java.io.IOException;
import static java.lang.Double.MAX_VALUE;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
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
    TextField nameTxtField = new TextField();
    static DatePicker d = new DatePicker();
    static Label addMessage;
    static Label searchMessage;
    GridPane gp = new GridPane();
    double defaultSliderValue = 0.0;
    String result = "0.0";
    static boolean isSearched = false;
    static ObservableList<Student> students = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) {

        // DEFINING COMPONENTS FOR TAB 1 -----------------------------------------------------------------------------------------
        TabPane tab = new TabPane();

        Tab tb1 = new Tab("add record");
        Tab tb2 = new Tab("Show all records");

        BorderPane border1 = new BorderPane();

        VBox vb1 = new VBox(15);
        vb1.setPadding(new Insets(20));
        vb1.setAlignment(Pos.CENTER);
        vb1.setFillWidth(true);

        HBox hb1 = new HBox(15);
        hb1.setAlignment(Pos.CENTER);

        gp.setAlignment(Pos.CENTER);
        gp.setVgap(30);
        gp.setHgap(25);

        Label tab1HeadLabel = new Label();

        Label addRecordLbl = new Label("Add Student Record");

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
        tab1HeadLabel.setText("AbdulazizAlamoudi-441016500-AbdulazizBahamid-441016576");
        tab1HeadLabel.setFont(Font.font("", FontWeight.BOLD, 16));

        addRecordLbl.setFont(Font.font("", FontWeight.BOLD, 20));

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

        // ACTION FUNCTIONS FOR TAB 1
        addBtn.setOnAction(e -> {
            String dateInput;
            if (d.getValue() == null) {
                dateInput = "";
            } else {
                dateInput = d.getValue().toString();
            }
            String nameInput = nameTxtField.getText();
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
        border1.setTop(tab1HeadLabel);
        border1.setAlignment(tab1HeadLabel, Pos.CENTER);
        vb1.getChildren().add(addRecordLbl);
        gp.add(sName, 0, 0);
        gp.add(nameTxtField, 1, 0);
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
        border1.setCenter(vb1);
        tb1.setContent(border1);
        tb1.setClosable(false);

        // DEFINING COMPONENTS FOR TAB 2
        Text tab2HeadLabel = new Text("AbdulazizAlamoudi-441016500-AbdulazizBahamid-441016576");
        tab2HeadLabel.setFont(Font.font("", FontWeight.BOLD, 16));
        Label searchLabel = new Label("Search By Name:");
        searchLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        searchMessage = new Label("");
        searchMessage.setFont(Font.font("", FontWeight.BOLD, 16));
        search = new TextField();
        search.setMaxWidth(200);
        Button searchBtn = new Button("Search");
        Button refreshBtn = new Button("Refresh");
        Button exitBtn2 = new Button("Exit");

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

        tableView.getColumns().add(column1);
        tableView.getColumns().add(column2);
        tableView.getColumns().add(column3);
        tableView.getColumns().add(column4);

        // ACTION FUNCTIONS FOR TAB 2
        tb2.setOnSelectionChanged(e -> {
            if (tb2.isSelected()) {
                input = search.getText();
                Methods.passName(input);
                clear();
            }
            addMessage.setText("");
        });
        
        searchBtn.setOnAction(e -> {
            input = search.getText();
            Methods.passName(input);
            clear();
        });

        refreshBtn.setOnAction(e -> {
            search.setText("");
            Methods.passName("");
            clear();
        });

        exitBtn2.setOnAction(e -> {
            Platform.exit();
        });

        //SECOND TAB DATA
        tableView.setItems(students);

        BorderPane border2 = new BorderPane();

        VBox vb2 = new VBox(15);
        vb2.setAlignment(Pos.CENTER);
        vb2.setMaxWidth(600);

        HBox hb2 = new HBox(15);
        hb2.setAlignment(Pos.CENTER);

        hb2.getChildren().add(searchBtn);
        hb2.getChildren().add(refreshBtn);

        vb2.setPadding(new Insets(20, 10, 20, 10));
        vb2.getChildren().add(searchLabel);
        vb2.getChildren().add(search);
        vb2.getChildren().add(hb2);
        vb2.getChildren().add(searchMessage);
        vb2.getChildren().add(tableView);
        vb2.getChildren().add(exitBtn2);
        border2.setTop(tab2HeadLabel);
        border2.setAlignment(tab2HeadLabel, Pos.CENTER);
        border2.setCenter(vb2);

        tb2.setContent(border2);
        tb2.setClosable(false);

        // SCENE Details
        Scene scene = new Scene(tab);
        primaryStage.setTitle("Student DataBase");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setMinHeight(500);
        primaryStage.setMinWidth(500);

    }// end of start method

    public void clear() {
        nameTxtField.setText("");
        d.setValue(null);
        slider.setValue(0.0);
    }// end of clear method 

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }// end of main method
}// end of class
