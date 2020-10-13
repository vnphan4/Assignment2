package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.UUID;

public class Controller implements Initializable {

    final String hostname = "patientdb.ccfh9bpxbab1.us-east-1.rds.amazonaws.com";
    final String dbname = "testdb";
    final String port = "3306";
    final String username = "admin";
    final String password = "admin3368";
    final String AWS_URL = "jdbc:mysql://" + hostname + ":" + port + "/" + dbname + "?user=" + username + "&password=" + password;

    @FXML
    private Button createdb;
    @FXML
    private Button populatedb;
    @FXML
    private Button viewdb;
    @FXML
    private ChoiceBox filterChoice;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



        createdb.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    createDatabase();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    public void createDatabase() throws SQLException {
        try
        {
            Connection conn = DriverManager.getConnection(AWS_URL);
            Statement stmt = conn.createStatement();
            System.out.println("CONNECTION ESTABLISHED");

        }
        catch (SQLException ex)
        {
            System.out.println("ERROR: " + ex.getMessage());
            /*Connection conn = DriverManager.getConnection(AWS_URL);
            conn = DriverManager.getConnection
                    ("jdbc:mysql://localhost/?user=root&password=rootpassword");
            Statement s = conn.createStatement();
            int Result=s.executeUpdate("CREATE DATABASE patient-db");*/

        }
    }
}
