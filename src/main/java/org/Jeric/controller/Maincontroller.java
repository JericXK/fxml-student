package org.Jeric.controller;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import org.Jeric.DatabaseConnection;
import javafx.fxml.FXML;

import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;

public class Maincontroller {
    @FXML
    private TextField firstname;
    @FXML
    private TextField middlename;
    @FXML
    private TextField lastName;
    @FXML
    private TextField Address;
    @FXML
    private TextField PhoneNumber;
    @FXML
    private TextField Email;
    @FXML
    private RadioButton Male;
    @FXML
    private RadioButton Female;


    public ToggleGroup gender;

    @FXML
    private DatabaseConnection db;

    public void initialize(){
        db = new DatabaseConnection();
    }

    @FXML
    private void save() throws SQLException {
        String sql ="INSERT INTO student(first_name, middle_name, last_name, address, phone_number, email, gender) VALUES (?, ?, ?, ?, ?, ?,?)";
        PreparedStatement stmt = this.db.connection.prepareStatement(sql);
        stmt.setString(1, this.firstname.getText());
        stmt.setString(2, this.middlename.getText());
        stmt.setString(3, this.lastName.getText());
        stmt.setString(4, this.Address.getText());
        stmt.setString(5, this.PhoneNumber.getText());
        stmt.setString(6, this.Email.getText());
        if(Male.isSelected()){
            stmt.setString(7,"Male");
        }
        else if (Female.isSelected()){
            stmt.setString(7,"Female");
        }
        stmt.executeUpdate();
    }

}
