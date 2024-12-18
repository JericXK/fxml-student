package org.Jeric.controller;
import org.Jeric.DatabaseConnection;
import org.Jeric.model.List;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
    private TableView<List> table;

    @FXML
    private TableColumn<List, String> colfn;

    @FXML
    private TableColumn<List, String> colmn;

    @FXML
    private TableColumn<List, String> collas;

    @FXML
    private TableColumn<List, String> coladd;

    @FXML
    private TableColumn<List, String> colpn;

    @FXML
    private TableColumn<List, String> colem;

    @FXML
    private TableColumn<List, String> colgen;

    private boolean isEditing = false;

    private int studentID = 0;

    private DatabaseConnection db;

    private ObservableList<List> studentlist = FXCollections.observableArrayList();

    public Maincontroller() {
    }

    public void loadStudent() throws SQLException {
        studentlist.clear();
        String sql = "SELECT * FROM students";

        Statement stmt = db.getConnection().createStatement();
        ResultSet result = stmt.executeQuery(sql);

        while (result.next()) {
            List student = new List(result.getInt("id"),
                    result.getString("first_name"),
                    result.getString("middle_name"),
                    result.getString("last_name"),
                    result.getString("address"),
                    result.getString("phone_number"),
                    result.getString("email"),
                    result.getString("gender"));
            studentlist.add(student);
        }
        table.setItems(studentlist);
    }

    public void initialize() throws SQLException {
        db = new DatabaseConnection();

        colfn.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        colmn.setCellValueFactory(new PropertyValueFactory<>("middlename"));
        collas.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        coladd.setCellValueFactory(new PropertyValueFactory<>("address"));
        colpn.setCellValueFactory(new PropertyValueFactory<>("PhoneNumber"));
        colem.setCellValueFactory(new PropertyValueFactory<>("email"));
        colgen.setCellValueFactory(new PropertyValueFactory<>("gender"));

        loadStudent();
    }



    @FXML
    private void save() throws SQLException {
        if (!isEditing) { //if creating new
            String sql = "INSERT INTO students(first_name, middle_name, last_name, address, phone_number, email, gender) VALUES (?, ?, ?, ?, ?, ?,?)";
            PreparedStatement pstmt = db.getConnection().prepareStatement(sql);
            pstmt.setString(1, firstname.getText());
            pstmt.setString(2, middlename.getText());
            pstmt.setString(3, lastName.getText());
            pstmt.setString(4, Address.getText());
            pstmt.setString(5, PhoneNumber.getText());
            pstmt.setString(6, Email.getText());
            if (Male.isSelected()) {
                pstmt.setString(7, "Male");
            } else if (Female.isSelected()) {
                pstmt.setString(7, "Female");
            }

            if (pstmt.executeUpdate() == 1) {
                firstname.clear();
                middlename.clear();
                lastName.clear();
                Address.clear();
                PhoneNumber.clear();
                Email.clear();
                Male.setSelected(false);
                Female.setSelected(false);
                loadStudent();
            }
        } else { //if editing
            String sql = "UPDATE students SET first_name = ?, middle_name = ?, last_name = ?, address = ?, phone_number = ?, email = ?, gender = ? WHERE id = ? ";
            try {
                PreparedStatement pstmt = db.getConnection().prepareStatement(sql);
                pstmt.setString(1, firstname.getText());
                pstmt.setString(2, middlename.getText());
                pstmt.setString(3, lastName.getText());
                pstmt.setString(4, Address.getText());
                pstmt.setString(5, PhoneNumber.getText());
                pstmt.setString(6, Email.getText());
                if (Male.isSelected()) {
                    pstmt.setString(7, "Male");
                } else if (Female.isSelected()) {
                    pstmt.setString(7, "Female");
                }
                pstmt.setInt(8, studentID);

                if (pstmt.executeUpdate() == 1) {
                    firstname.clear();
                    middlename.clear();
                    lastName.clear();
                    Address.clear();
                    PhoneNumber.clear();
                    Email.clear();
                    Male.setSelected(false);
                    Female.setSelected(false);
                    loadStudent();
                }

                loadStudent();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void delete() {
        List selectedStudent = table.getSelectionModel().getSelectedItem();
        if(selectedStudent != null) {
            String sql = "DELETE from students WHERE id = ?";
            try {
                PreparedStatement pstmt = db.getConnection().prepareStatement(sql);
                pstmt.setInt(1, selectedStudent.getId());
                pstmt.executeUpdate();

                studentlist.remove(selectedStudent);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void edit() {
        List selectedStudent = table.getSelectionModel().getSelectedItem();
        if(selectedStudent != null){
            firstname.setText(selectedStudent.getFirstname());
            middlename.setText(selectedStudent.getMiddlename());
            lastName.setText(selectedStudent.getLastName());
            Address.setText(selectedStudent.getAddress());
            PhoneNumber.setText(selectedStudent.getPhoneNumber());
            Email.setText(selectedStudent.getEmail());
            if ("Male".equals(selectedStudent.getGender())) {
                Male.setSelected(true);
            } else if ("Female".equals(selectedStudent.getGender())) {
                Female.setSelected(true);
            }
            isEditing = true;
            studentID = selectedStudent.getId();
        }
    }
}