package Market;

import Database.Connect;
import Login.Login;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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

public class ProductMarket extends ExportProductMarket {
    private Connect connect = null;
    private ObservableList<MarketBase> Product_Market = FXCollections.observableArrayList();
    final char dm = (char) 34;

    @FXML
    private ComboBox<String> ProductM;

    @FXML
    private TableView<MarketBase> tableTov;

    @FXML
    private TableColumn<MarketBase, String> market;

    @FXML
    private TableColumn<MarketBase, String> product;

    @FXML
    private TableColumn<MarketBase, String> kolTov;

    @FXML
    void initialize() {

        try {
            connect = new Connect();
            Statement statement = connect.getConnection().createStatement();
            final ResultSet resultSet = statement.executeQuery("select `Product` from snab_mag.product");
            while (resultSet.next()) {
                String product = resultSet.getString("Product");
                if (!ProductM.getItems().contains(product)){
                    ProductM.getItems().addAll(product);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ProductM.setOnAction(event -> infoTov());
        }


    private void infoTov() {
            try {
                connect = new Connect();
                Statement statement = connect.getConnection().createStatement();
                if (Objects.equals(Login.getRoleDB(), "Администратор")  || Objects.equals(Login.getRoleDB(), "Склад")) {
                    final ResultSet resultSet = statement.executeQuery("select market.Market, product.Product, market_base.Col_prod_market_base, unit.unit " +
                            " from snab_mag.market_base inner join snab_mag.market on market.idMarket = market_base.Market_idMarket " +
                            "inner join snab_mag.product on product.idProduct = market_base.Product_idProduct " +
                            "inner join snab_mag.unit on unit.idU = market_base.idU" +
                            " where product.Product = " + dm + ProductM.getValue() + dm);
                    while (resultSet.next()) {
                        setTableTov(resultSet.getString(1), resultSet.getString(2), (resultSet.getInt(3) + " " + resultSet.getString(4)));
                    }
                }
                else{
                    final ResultSet resultSet = statement.executeQuery("select market.Market, product.Product, market_base.Col_prod_market_base, unit.unit " +
                            " from snab_mag.market_base inner join snab_mag.market on market.idMarket = market_base.Market_idMarket " +
                            "inner join snab_mag.product on product.idProduct = market_base.Product_idProduct " +
                            "inner join snab_mag.unit on unit.idU = market_base.idU" +
                            " where product.Product = " + dm + ProductM.getValue() + dm + " and market.Market =" + dm + Login.getNameDB() + dm);
                    while (resultSet.next()) {
                        setTableTov(resultSet.getString(1), resultSet.getString(2), (resultSet.getInt(3) + " " + resultSet.getString(4)));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    @FXML
    private void selectall(ActionEvent event){
        Product_Market.clear();
        ProductM.setValue("");

        try {
            connect = new Connect();
            Statement statement = connect.getConnection().createStatement();
            if (Objects.equals(Login.getRoleDB(), "Администратор") || Objects.equals(Login.getRoleDB(), "Склад")) {
                final ResultSet resultSet = statement.executeQuery("select market.Market, product.Product, market_base.Col_prod_market_base, unit.unit " +
                        " from snab_mag.market_base inner join snab_mag.market on market.idMarket = market_base.Market_idMarket " +
                        "inner join snab_mag.product on product.idProduct = market_base.Product_idProduct " +
                        "inner join snab_mag.unit on unit.idU = market_base.idU");
                while (resultSet.next()) {
                    setTableTov(resultSet.getString(1), resultSet.getString(2), (resultSet.getInt(3) + " " + resultSet.getString(4)));
                }
            }

            else{
                final ResultSet resultSet = statement.executeQuery("select market.Market, product.Product, market_base.Col_prod_market_base, unit.unit " +
                        " from snab_mag.market_base inner join snab_mag.market on market.idMarket = market_base.Market_idMarket " +
                        "inner join snab_mag.product on product.idProduct = market_base.Product_idProduct " +
                        "inner join snab_mag.unit on unit.idU = market_base.idU where market.Market =" + dm + Login.getNameDB() + dm);
                while (resultSet.next()) {
                    setTableTov(resultSet.getString(1), resultSet.getString(2), (resultSet.getInt(3) + " " + resultSet.getString(4)));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void clear(ActionEvent event){
        tableTov.getItems().clear();
        ProductM.setValue("");
    }

    @FXML
    private void exportExcel () { reportProductMarket(Product_Market);}

    @FXML
    private void exportWord() throws IOException {
        reportProdBaseWord(Product_Market);
    }

    @FXML
    private void print() throws IOException {
        reportProdBaseWordPrint(Product_Market);
    }

    private void setTableTov (String _market, String _product, String _kolTov){
        
        Product_Market.add(new MarketBase(_market, _product, _kolTov));

        market.setCellValueFactory(new PropertyValueFactory<MarketBase, String>("market"));
        product.setCellValueFactory(new PropertyValueFactory<MarketBase, String>("product"));
        kolTov.setCellValueFactory(new PropertyValueFactory<MarketBase, String>("kolTov"));

        tableTov.setItems(Product_Market);
    }
}
