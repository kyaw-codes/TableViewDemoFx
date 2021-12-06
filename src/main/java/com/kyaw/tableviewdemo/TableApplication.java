package com.kyaw.tableviewdemo;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;


public class TableApplication extends Application implements Initializable {

    @FXML
    private Button btnAdd;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnEdit;
    @FXML
    private ComboBox<String> cmbGender;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TableView<Person> tableView;
    @FXML
    private TextField txtMail;
    @FXML
    private TextField txtName;
    @FXML
    private TableColumn<String, Person> colName;
    @FXML
    private TableColumn<String, Person> colEmail;
    @FXML
    private TableColumn<String, Person> colGender;
    @FXML
    private TableColumn<LocalDate, Person> colDob;

    private boolean isEditing = false;
    private final List<String> genders = Arrays.asList("Male", "Female", "Rather not specify");
    private final List<Person> people = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        people.add(new Person("Ko Kyaw", "Male", "kyaw@gmail.com", LocalDate.now()));

        btnAdd.setOnAction(e -> {
            Person person = instantiatePerson();
            if (isEditing) {
                int index = tableView.getSelectionModel().getSelectedIndex();
                people.set(index, person);
                tableView.getItems().set(index, person);

                isEditing = false;
            } else {
                // Add
                people.add(person);
                tableView.getItems().add(person);
            }
            reset();
        });

        btnDelete.setOnAction(e -> {
            int index = tableView.getSelectionModel().getSelectedIndex();
            people.remove(index);
            tableView.getItems().remove(index);
        });

        btnEdit.setOnAction(e -> {
            Person selectedPerson = tableView.getSelectionModel().getSelectedItem();
            txtName.setText(selectedPerson.getName());
            txtMail.setText(selectedPerson.getEmail());
            cmbGender.getSelectionModel().select(selectedPerson.getGender());
            datePicker.setValue(selectedPerson.getDateOfBirth());

            isEditing = true;
        });

        setupGenderComboBox();
        setupTableView();
    }

    private Person instantiatePerson() {
        Person p = new Person();
        String name = txtName.getText().isEmpty() ? "Anonymous" : txtName.getText();
        String email = txtMail.getText().isEmpty() ? "-" : txtMail.getText();
        String gender = cmbGender.getSelectionModel().getSelectedItem();
        LocalDate dob = datePicker.getValue();

        p.setName(name);
        p.setEmail(email);
        p.setGender(gender);
        p.setDateOfBirth(dob);

        return p;
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TableApplication.class.getResource("table-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    private void setupGenderComboBox() {
        cmbGender.getItems().addAll(genders);
        cmbGender.getSelectionModel().select(0);
    }

    private void setupTableView() {
        tableView.getItems().addAll(people);
    }

    private void reset() {
        txtName.clear();
        txtMail.clear();
        cmbGender.getSelectionModel().select(0);
        datePicker.getEditor().clear();
        datePicker.setValue(null);
    }

    public static void main(String[] args) {
        launch();
    }
}
