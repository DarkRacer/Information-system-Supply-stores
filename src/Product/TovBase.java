package Product;

import Database.Connect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TovBase extends ExportProduct{
    private Connect connect = null;
    private ObservableList<Product> ProductBase = FXCollections.observableArrayList();
    final char dm = (char) 34;

    @FXML
    private ComboBox<String> Product;

    @FXML
    private TableView<Product> tableTov;

    @FXML
    private TableColumn<Product, Integer> idTov;

    @FXML
    private TableColumn<Product, String> name;

    @FXML
    private TableColumn<Product, String> kolTov;

    @FXML
    private TableColumn<Product, Float> price;

    @FXML
    void initialize() {
        try {
            connect = new Connect();
            Statement statement = connect.getConnection().createStatement();
            final ResultSet resultSet = statement.executeQuery("select `Product` from snab_mag.product");
            while (resultSet.next()) {
                String product = resultSet.getString("Product");
                if (!Product.getItems().contains(product)){
                    Product.getItems().addAll(product);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Product.setOnAction(event -> infoTov());
    }

    private void infoTov() {
        
        try {
            connect = new Connect();
            Statement statement = connect.getConnection().createStatement();
            final ResultSet resultSet = statement.executeQuery("select product.idProduct, product.Product, " +
                    "product.Productcol_base, unit.unit, product.price from snab_mag.product inner join snab_mag.unit on unit.idU = product.idU where `Product`=" + dm + Product.getValue() + dm);
            while (resultSet.next()) {
                setTableTov(resultSet.getInt(1), resultSet.getString(2), (resultSet.getInt(3) + " " + resultSet.getString(4)), resultSet.getFloat(5));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void selectall(ActionEvent event){
        ProductBase.clear();
        Product.setValue("");

        try {
            connect = new Connect();
            Statement statement = connect.getConnection().createStatement();
            final ResultSet resultSet = statement.executeQuery("select product.idProduct, product.Product, " +
                    "product.Productcol_base, unit.unit, product.price from snab_mag.product inner join snab_mag.unit on unit.idU = product.idU");
            while (resultSet.next()) {
                setTableTov(resultSet.getInt(1), resultSet.getString(2), (resultSet.getInt(3) + " " + resultSet.getString(4)), resultSet.getFloat(5));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void clear(ActionEvent event){
        tableTov.getItems().clear();
        Product.setValue("");
    }

    @FXML
    private void exportExcel () {
        reportProduct(ProductBase);
    }

    @FXML
    private void exportWord() throws IOException {
        reportProductWord(ProductBase);
    }

    @FXML
    private void print () throws IOException{
        reportProductWordPrint(ProductBase);
    }


    private void setTableTov (int id, String _name, String _kolTov, float _price){
        ProductBase.add(new Product(id, _name, _kolTov, _price));

        idTov.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
        name.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        kolTov.setCellValueFactory(new PropertyValueFactory<Product, String>("kolTov"));
        price.setCellValueFactory(new PropertyValueFactory<Product, Float>("price"));


        tableTov.setItems(ProductBase);
    }

}
