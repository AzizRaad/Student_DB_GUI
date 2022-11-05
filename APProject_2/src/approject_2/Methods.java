/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package approject_2;

import static approject_2.APProject_2.addMessage;
import static approject_2.APProject_2.searchMessage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Properties;
import java.util.Scanner;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import static approject_2.APProject_2.students;
import javafx.scene.control.Label;

/**
 *
 * @author Aziz
 */
public class Methods {

    public static Connection getConnection() throws SQLException, FileNotFoundException, IOException {
        // grapping the configuration file to extract our secret information like the database url, the user and his password
        Properties config = new Properties();
        FileInputStream ip = new FileInputStream("config.properties");
        config.load(ip);
        //concecting to the database then retrun connection object
        return DriverManager.getConnection(config.getProperty("DBUrl"), config.getProperty("user"), config.getProperty("password"));
    }// end of getConnection method

    public static boolean add(String fullName, String date, String gpa) throws SQLException, IOException {

        if (checkName(fullName) && checkDate(date) && readGPA(gpa)) {
            // cunstructing the query and connect to the database
            String insertquery = "insert into StudentsTBL_abdulaziz_abdulaziz(FullName,DateOfBirth,GPA) values(?,?,?)";
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(insertquery);
            statement.setString(1, fullName);
            statement.setDate(2, java.sql.Date.valueOf(date));
            statement.setDouble(3, Double.parseDouble(gpa));
            statement.execute();
            addMessage.setTextFill(Color.GREEN);
            addMessage.setText("++info added successfully++");

            // closing the opened connections
            statement.close();
            connection.close();

            return true;
        } else {
            return false;
        }

    }

    public static void search(String fullName) throws SQLException, IOException {
        // cunstructing the query and connect to the database
        String searchquery = "select * from StudentsTBL_abdulaziz_abdulaziz where fullName like ? ";
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(searchquery);
        String searchName = "%" + fullName + "%"; // there is an error when we isnert the '%' in the query so we adding it her
        statement.setString(1, searchName);
        ResultSet searchResult = statement.executeQuery();

        students.clear();//clearing the list so we can add data to it

        // handling not found records exception
        if (searchResult.isBeforeFirst()) { //checks if there is records returned
            while (searchResult.next()) {
                students.add(new Student(searchResult.getInt(1), searchResult.getString(2), searchResult.getString(3), searchResult.getDouble(4)));
            }
            searchMessage.setTextFill(Color.GREEN);
            searchMessage.setText("Records with name: "+fullName);
        } else {
            searchMessage.setTextFill(Color.RED);
            searchMessage.setText("there was no record with the given name " + fullName);
        }
        // closing the opened connections
        statement.close();
        connection.close();

    }// end of search Method

    public static boolean checkName(String name) {
        // constructin the regular expression to check the format and valditiy of the input with it
        String Name_Pattern = "[A-Za-z ]{3,40}";

        if (!name.matches(Name_Pattern)) {//matching the input with the regular expression
            addMessage.setTextFill(Color.RED);
            if (name.length() > 40 || name.length() < 3) {//checks what error the user madde
                addMessage.setText("your name should be 3 to 40 characters!!");
            } else {
                addMessage.setText("please just enter characters not numbers!!");
            }
            return false;
        }
        return true;
    }// end of checkName method

    public static boolean checkDate(String date) {
        // constructin the regular expression to check the format and valditiy of the input with it
        if (date.equals("")) { //matching the input with the regular expression
            addMessage.setTextFill(Color.RED);
            addMessage.setText("please enter a valid date!!");
            return false;
        }
        return true;
    }// end of checkDate method

    public static boolean readGPA(String GPA) {
        // constructin the regular expression to check the format and valditiy of the input with it
        Double doubleGPA = Double.parseDouble(GPA);
        if (doubleGPA < 0 || doubleGPA > 4) { //matching the input with the regular expression
            addMessage.setTextFill(Color.RED);
            addMessage.setText("please enter GPA in numbers between 0.00 to 4.00 !!");
            return false;
        }
        return true;
    }// end of readGPA method
    
    public static void passName(String x){
        try {
                search(x);
            } catch (SQLException ex) {
                searchMessage.setTextFill(Color.RED);
                searchMessage.setText("there is something wrong with the database connection");
            } catch (IOException ex) {
                searchMessage.setTextFill(Color.RED);
                searchMessage.setText("there is something wrong with the 'config.properties' file");
            }
    }
    
}
