/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package approject_2;

/**
 *
 * @author Aziz
 */
public class Student {

    public int ID = 0;
    public String fullName = null;
    public String DateOfBirth = null;
    public double GPA = 0;

    public Student(int ID, String fullName, String DateOfBirth, double GPA) {
        this.ID = ID;
        this.fullName = fullName;
        this.DateOfBirth = DateOfBirth;
        this.GPA = GPA;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDateOfBirth() {
        return DateOfBirth;
    }

    public void setDateOfBirth(String DateOfBirth) {
        this.DateOfBirth = DateOfBirth;
    }

    public double getGPA() {
        return GPA;
    }

    public void setGPA(double GPA) {
        this.GPA = GPA;
    }

}

