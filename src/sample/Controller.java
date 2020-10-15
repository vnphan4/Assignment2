package sample;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.io.FileWriter;
import java.net.URL;
import java.sql.*;
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
    private Button viewTable;
    @FXML
    private Button deleteTable;
    @FXML
    private ChoiceBox filterChoice;
    @FXML
    private Button filterButton;

    @FXML
    private ListView<Patient> patientList;
    private ObservableList<Patient> patients;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        patients = patientList.getItems();
        LoadPatients();


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
        viewTable.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                LoadPatients();
                dbStatus.setText("Click 'Populate Data' to add more data to table" );

            }
        });
        deleteTable.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                deleteTable();
                dbStatus.setText("Table deleted.\n Click 'Populate Database' to create new table with data" );
            }
        });


    }

    public void createTable(){
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

                System.out.println("table created, adding data...");
                dbStatus.setText("Table created, adding data...");
                FillTable();
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

    // Load patients to GUI from database
    private void LoadPatients()
    {
        try
        {
            Connection conn = DriverManager.getConnection(AWS_URL);
            Statement stmt = conn.createStatement();
            System.out.println("CONNECTION ESTABLISHED");

            String sqlStatement = "SELECT * FROM Asset";
            ResultSet result = stmt.executeQuery(sqlStatement);
            while (patients.size()>0){
                patients.remove(patients.size()-1);
            }
            //Create patient objects and add to Observable List to display on GUI
            while (result.next())
            {
                Patient patient1 = new Patient(UUID.fromString(result.getString("id")),
                                            result.getString("name"),
                                            result.getString("gender"),
                                            result.getString("bloodType"),
                                            Integer.parseInt(result.getString("age")),
                                            Integer.parseInt(result.getString("weight")),
                                            Integer.parseInt(result.getString("height")));
                patients.add(patient1);

                //Display in console
                System.out.print(result.getString("id"));
                System.out.print("\t");
                System.out.print(result.getString("name"));
                System.out.print("\t");
                System.out.print(result.getString("age"));
                System.out.print("\t");
                System.out.print(result.getString("gender"));
                System.out.print("\t");
                System.out.print(result.getString("bloodType"));
                System.out.print("\t");
                System.out.print(result.getString("weight"));
                System.out.print("\t");
                System.out.print(result.getString("height"));
                System.out.println();
            }
        }
        catch (Exception ex)
        {
            System.out.println("ERROR: " + ex.getMessage());
            dbStatus.setText("Table does not exist. \n Click 'Populate Database' to create table and data");
        }
    }

    //Extra function for easy testing
    public void deleteTable(){
        try
        {
            Connection conn = DriverManager.getConnection(AWS_URL);
            Statement stmt = conn.createStatement();
            System.out.println("CONNECTION ESTABLISHED");

            String sql = "DROP TABLE Asset ";
            stmt.executeUpdate(sql);
            System.out.println("Table Asset DELETED");

            while (patients.size()>0){
                patients.remove(patients.size()-1);
            }
        }
        catch (Exception ex)
        {
            System.out.println("ERROR: " + ex.getMessage());
        }

    }

    // Create Database??? how???
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

    // Populate data into Asset table
    private void FillTable()
    {
        try
        {
            Connection conn = DriverManager.getConnection(AWS_URL);
            Statement stmt = conn.createStatement();
            System.out.println("CONNECTION ESTABLISHED");
            try {
                // Adding patient 1
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

                // Adding patient 2
                UUID id2 = UUID.randomUUID();
                String name2 = "Robert";
                String gender2 = "F";
                String bloodType2 = "A";
                int age2 = 28;
                int weight2 = 150;
                int height2 = 78;
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
                dbStatus.setText("Data added. \nClick 'View Table' to view data." );
            } catch(Exception ex){
                System.out.println("table does not exist, creating table...");
                createTable();
            }
        }
        catch (Exception ex)
        {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }

}
