package Product;

import Database.Connect;
import Login.Login;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class Inventory extends ExportInventory {
    private Connect connect = null;
    private ObservableList<Product> Tov = FXCollections.observableArrayList();
    final char dm = (char) 34;

    @FXML
    private ComboBox<String> Box;

    @FXML
    private TableView<Product> tableI;

    @FXML
    private TableColumn<Product, Integer> idT;

    @FXML
    private TableColumn<Product, String> tov;

    @FXML
    private TableColumn<Product, String> col;

    @FXML
    void initialize() {
        if (Objects.equals(Login.getRoleDB(), "Администратор")) {
            Box.getItems().add("Склад");

            try {
                connect = new Connect();
                Statement statement = connect.getConnection().createStatement();
                final ResultSet resultSet = statement.executeQuery("select `Market` from snab_mag.market");
                while (resultSet.next()) {
                    String market = resultSet.getString("Market");
                    Box.getItems().addAll(market);
                }
            } catch (
                    SQLException e) {
                e.printStackTrace();
            }
            Box.setOnAction(event -> invent());
        }
        else {
            if (Objects.equals(Login.getRoleDB(), "Склад")) {
                Box.setValue(Login.getRoleDB());
            }
            else {
                Box.setValue(Login.getNameDB());
            }
            Box.setEditable(false);
            invent();
        }
    }

    private void invent (){
        Tov.clear();
        if (Box.getValue().equals("Склад")){
            try {
                connect = new Connect();
                Statement statement = connect.getConnection().createStatement();
                final ResultSet resultSet = statement.executeQuery("select product.idProduct, product.Product, " +
                        "product.Productcol_base, unit. unit from snab_mag.product inner join snab_mag.unit on unit.idU = product.idU");
                while (resultSet.next()) {
                    setTableTov(resultSet.getInt(1), resultSet.getString(2), (resultSet.getInt(3) + " " + resultSet.getString(4)));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        else {
            try {
                connect = new Connect();
                Statement statement = connect.getConnection().createStatement();
                final ResultSet resultSet = statement.executeQuery("select product.idProduct, product.Product, market_base.Col_prod_market_base, unit.unit " +
                        " from snab_mag.market_base inner join snab_mag.market on market.idMarket = market_base.Market_idMarket " +
                        "inner join snab_mag.product on product.idProduct = market_base.Product_idProduct " +
                        "inner join snab_mag.unit on unit.idU = market_base.idU where market.Market = " + dm + Box.getValue() + dm + ";");
                while (resultSet.next()) {
                    setTableTov(resultSet.getInt(1), resultSet.getString(2), (resultSet.getInt(3) + " " + resultSet.getString(4)));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void exportExcel () {
        reportInventory(Tov, Box.getValue());
    }

    @FXML
    private void exportWord() throws IOException {
        reportInventoryWord(Tov, Box.getValue());
    }

    @FXML
    private void print () throws IOException {
        reportInventoryWordPrint(Tov, Box.getValue());
    }

    private void setTableTov (int id, String _name, String _kolTov){
        Tov.add(new Product(id, _name, _kolTov));

        idT.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
        tov.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        col.setCellValueFactory(new PropertyValueFactory<Product, String>("kolTov"));

        tableI.setItems(Tov);
    }
}
