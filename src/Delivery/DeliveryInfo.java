package Delivery;

import Database.Connect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DeliveryInfo extends ExportDelivery{
    private Connect connect = null;
    private ObservableList<Delivery> Delivery = FXCollections.observableArrayList();
    final char dm = (char) 34;

    @FXML
    private ComboBox<String> deliveryBox;

    @FXML
    private Button all;

    @FXML
    private Button clear;

    @FXML
    private TableView<Delivery> tableD;

    @FXML
    private TableColumn<Delivery, Integer> id;

    @FXML
    private TableColumn<Delivery, String> name;

    @FXML
    void initialize(){
        try {
            connect = new Connect();
            Statement statement = connect.getConnection().createStatement();
            final ResultSet resultSet = statement.executeQuery("select `Delivery` from snab_mag.delivery");
            while (resultSet.next()) {
                String delivery = resultSet.getString("Delivery");
                if (!deliveryBox.getItems().contains(delivery)){
                    deliveryBox.getItems().addAll(delivery);
                }
            }
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        deliveryBox.setOnAction(event -> info());
    }

    private void info(){
        try {
            connect = new Connect();
            Statement statement = connect.getConnection().createStatement();
            final ResultSet resultSet = statement.executeQuery("select delivery.idDelivery, delivery.Delivery" +
                    " from snab_mag.delivery " +
                    "where `Delivery` = " + dm + deliveryBox.getValue() + dm);
            while (resultSet.next()) {
                setTableD(resultSet.getInt(1), resultSet.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void all(ActionEvent event){
        Delivery.clear();
        deliveryBox.setValue("");

        try {
            connect = new Connect();
            Statement statement = connect.getConnection().createStatement();
            final ResultSet resultSet = statement.executeQuery("select delivery.idDelivery, delivery.Delivery" +
                    " from snab_mag.delivery");
            while (resultSet.next()) {
                setTableD(resultSet.getInt(1), resultSet.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void clear(ActionEvent event){
        tableD.getItems().clear();
        deliveryBox.setValue("");
    }

    @FXML
    private void exportExcel () {
        reportDelivery(Delivery);
    }

    @FXML
    private void exportWord() throws IOException {
        reportDeliveryWord(Delivery);
    }

    @FXML
    private void print () throws IOException {
        reportDeliveryPrint(Delivery);
    }

    private void setTableD (int _id, String _name){
        Delivery.add(new Delivery(_id, _name));

        id.setCellValueFactory(new PropertyValueFactory<Delivery, Integer>("id"));
        name.setCellValueFactory(new PropertyValueFactory<Delivery, String>("name"));

        tableD.setItems(Delivery);
    }
}
