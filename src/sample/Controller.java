package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

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
    final String AWS_URL2 = "jdbc:mysql://" + hostname + ":" + port;

    public Label dbStatus;

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

        populatedb.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FillTable();

            }
        });


    }

    public void populateDB(){
        try
        {
            Connection conn = DriverManager.getConnection(AWS_URL);
            Statement stmt = conn.createStatement();
            System.out.println("CONNECTION ESTABLISHED");
            try
            {
                stmt.execute("CREATE TABLE Asset (" +
                                "id VARCHAR(36)," +
                                "name VARCHAR(25), " +
                                "gender VARCHAR(25), " +
                                "bloodType VARCHAR(25), "+
                                "age INT(25), "+
                                "weight INT(25), "+
                                "height INT (25)) ");

                dbStatus.setText("table created");
            }
            catch (Exception ex)
            {
                System.out.println("ERROR: " + ex.getMessage());
            }

        }
        catch (Exception ex)
        {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }

    private void FillTable()
    {
        try
        {
            Connection conn = DriverManager.getConnection(AWS_URL);
            Statement stmt = conn.createStatement();
            System.out.println("CONNECTION ESTABLISHED");

            //ADDING EMPLOYEES
            System.out.println("ADDING PATIENTS TO TABLE");
            UUID id1 = UUID.randomUUID();
            String name1 = "Anita";
            String gender1 = "F";
            String bloodType1 = "A";
            int age1 = 18;
            int weight1 = 120;
            int height1 = 67;
            String sql = "INSERT INTO Asset VALUES" + "('" +
                        id1.toString() + "', '" +
                        name1 + "', '" +
                        gender1 + "', '" +
                        bloodType1 + "', '" +
                        age1 + "', '" +
                        weight1 + "', '" +
                        height1 + "')";
            stmt.executeUpdate(sql);


            UUID id2 = UUID.randomUUID();
            String name2 = "Robert";
            String gender2 = "F";
            String bloodType2 = "A";
            int age2 = 18;
            int weight2 = 120;
            int height2 = 67;
            sql = "INSERT INTO Asset VALUES" + "('" +
                    id2.toString() + "', '" +
                    name2 + "', '" +
                    gender2 + "', '" +
                    bloodType2 + "', '" +
                    age2 + "', '" +
                    weight2 + "', '" +
                    height2 +
                    "')";
            stmt.executeUpdate(sql);
            System.out.println("PATIENTS ADDED TO TABLE");


        }
        catch (Exception ex)
        {
            System.out.println("ERROR: " + ex.getMessage());
            //populateDB();
        }
    }


    public void createDatabase() throws SQLException {
        try
        {
            Connection conn = DriverManager.getConnection(AWS_URL);
            //Statement stmt = conn.createStatement();
            System.out.println("CONNECTION ESTABLISHED");
            dbStatus.setText("Patient database is available.");

        }
        catch (SQLException ex)
        {
            System.out.println("ERROR: " + ex.getMessage());
            Connection conn = DriverManager.getConnection(AWS_URL2);
            Statement s = conn.createStatement();
            int Result=s.executeUpdate("CREATE DATABASE patient2db");

        }
    }
}
