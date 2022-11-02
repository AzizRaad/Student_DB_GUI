/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package approject_2;

import java.time.LocalDate;
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

/**
 *
 * @author Aziz
 */
public class APProject_2 extends Application {
    
    TextField txt1 = new TextField();
    DatePicker d = new DatePicker();
    GridPane gp = new GridPane();
    
    @Override
    public void start(Stage primaryStage) {

        // DEFINING COMPONENTS -----------------------------------------------------------------------------------------
        TabPane tab = new TabPane();
        tab.setMinSize(200, 200);

        Tab tb1 = new Tab("add record");
        Tab tb2 = new Tab("Show all records");

        VBox vb = new VBox(15);
        vb.setPadding(new Insets(20));
        vb.setAlignment(Pos.CENTER);

        
        gp.setAlignment(Pos.CENTER);
//        gp.setGridLinesVisible(true);
        gp.setVgap(25);
        gp.setHgap(25);

        Label name = new Label();

        Label sName = new Label("Name");
        
        Label date = new Label("Date");
        
        Label gpa = new Label("GPA");
        Slider slider = new Slider(0, 4, 0);
        Label sliderValue = new Label();
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
//        slider.setSnapToPixel(true);
        
        
        // ADDING COMPONENTS INTO EACHOTHER AND INTO THE SCENE -----------------------------------------------------------------------------------------
        tab.getTabs().add(tb1);
        tab.getTabs().add(tb2);
        
        // ACTION FUNCTIONS
        exitBtn.setOnAction(e -> {
            Platform.exit();
        });
        
        slider.valueProperty().addListener(
             new ChangeListener<Number>() {
 
            public void changed(ObservableValue <? extends Number >
                      observable, Number oldValue, Number newValue)
            {
                double value = newValue.doubleValue();
                String result = String.format("%,.1f", value);
                slider.setValue(Double.parseDouble(result));
                sliderValue.setText("value: " + result);
                clear();
            }
        });
        
        

        //FIRST TAB DATA
        vb.getChildren().add(name);
        gp.add(sName, 0, 0);
        gp.add(txt1, 1, 0);
        gp.add(date, 0, 1);
        gp.add(d, 1, 1);
        gp.add(gpa, 0, 2);
        gp.add(slider, 1, 2);
        vb.getChildren().add(gp);
        vb.getChildren().add(sliderValue);
        vb.getChildren().add(addBtn);
        vb.getChildren().add(exitBtn);
        tb1.setContent(vb);

        //SECOND TAB DATA
//        tb2.setContent(vb);
        Scene scene = new Scene(tab);
        primaryStage.setTitle("Student DataBase");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setMinHeight(500);
        primaryStage.setMinWidth(500);
    }
    
    public void clear(){
        txt1.setText("");
        d.setValue(null);

        
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
