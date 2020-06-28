package Market;

import Database.Connect;
import Login.Login;
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
import java.util.Objects;

public class MarketInfo extends ExportMarket {
    private Connect connect = null;
    private ObservableList<Market> MarketBase = FXCollections.observableArrayList();
    final char dm = (char) 34;

    @FXML
    private ComboBox<String> market;

    @FXML
    private TableView<Market> tableM;

    @FXML
    private TableColumn<Market, Integer> idM;

    @FXML
    private TableColumn<Market, String> nameM;

    @FXML
    private TableColumn<Market, String> requisites;

    @FXML
    private Button select;

    @FXML
    private  Button clear;

    @FXML
    void initialize() {
        if (Objects.equals(Login.getRoleDB(), "Администратор") || Objects.equals(Login.getRoleDB(), "Склад")) {
            try {
                connect = new Connect();
                Statement statement = connect.getConnection().createStatement();
                final ResultSet resultSet = statement.executeQuery("select `Market` from snab_mag.market");
                while (resultSet.next()) {
                    String product = resultSet.getString("Market");
                    if (!market.getItems().contains(product)) {
                        market.getItems().addAll(product);
                    }
                }
            } catch (SQLException e) {

                e.printStackTrace();
            }

            market.setOnAction(event -> infoMarket());
        }
        else {
            market.setValue(Login.getNameDB());
            market.setEditable(false);
            infoMarket();
            select.setVisible(false);
            clear.setVisible(false);
        }
    }

    private void infoMarket() {

        try {
            connect = new Connect();
            Statement statement = connect.getConnection().createStatement();
            final ResultSet resultSet = statement.executeQuery("select * from snab_mag.market where `Market`=" + dm + market.getValue() + dm);
            while (resultSet.next()) {
                    setTableM(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void selectall(ActionEvent event){
        MarketBase.clear();
        market.setValue("");

        try {
            connect = new Connect();
            Statement statement = connect.getConnection().createStatement();
            final ResultSet resultSet = statement.executeQuery("select * from snab_mag.market");
            while (resultSet.next()) {
                setTableM(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void clear(ActionEvent event){
        tableM.getItems().clear();
        market.setValue("");
    }

    @FXML
    private void exportExcel () {
        reportMarket(MarketBase);
    }

    @FXML
    private void exportWord() throws IOException {
        reportMarketWord(MarketBase);
    }

    @FXML
    private void print () throws IOException {
        reportMarketWordPrint(MarketBase);
    }

    private void setTableM (int id, String _name, String _requisites){
        MarketBase.add(new Market(id, _name, _requisites));

        idM.setCellValueFactory(new PropertyValueFactory<Market, Integer>("id"));
        nameM.setCellValueFactory(new PropertyValueFactory<Market, String>("name"));
        requisites.setCellValueFactory(new PropertyValueFactory<Market, String>("requisites"));

        tableM.setItems(MarketBase);
    }
}
