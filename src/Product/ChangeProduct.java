package Product;

import Database.Connect;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ChangeProduct {
    private Connect connect = null;
    private ObservableList<Product> Product = FXCollections.observableArrayList();
    final char dm = (char) 34;

    @FXML
    private TableView<Product> tableP;

    @FXML
    private TableColumn<Product, Integer> idP;

    @FXML
    private TableColumn<Product, String> name;

    @FXML
    private TableColumn<Product, String> kol_tov;

    @FXML
    private TableColumn<Product, Float> price;

    @FXML
    private TextField idTF;

    @FXML
    private TextField nameTF;

    @FXML
    private TextField priceTF;

    @FXML
    private Button change;

    @FXML
    private Button delete;

    @FXML
    private void initialize() {
        idTF.setEditable(false);
        Product.clear();
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

        TableView.TableViewSelectionModel<Product> selectionModel = tableP.getSelectionModel();
        selectionModel.selectedItemProperty().addListener(new ChangeListener<Product>(){

            @Override
            public void changed(ObservableValue<? extends Product> observableValue, Product p, Product product) {
                if(product != null) {
                    idTF.setEditable(true);
                    idTF.setText(String.valueOf(product.getId()));
                    nameTF.setText(product.getName());
                    priceTF.setText(String.valueOf(product.getPrice()));
                    idTF.setEditable(false);
                }
            }
        });

    }

    @FXML
    private void change() {
        if (!idTF.getText().isEmpty()) {
            if (!nameTF.getText().isEmpty() && !nameTF.getText().matches("^[0-9]+$")) {
                if (!priceTF.getText().isEmpty() && !priceTF.getText().matches("^[а-яА-Я]+$") && !priceTF.getText().matches("^[A-Za-z]+$")) {
                    try {
                        connect = new Connect();
                        Statement statement = connect.getConnection().createStatement();
                        statement.execute("update snab_mag.product set Product = "
                                + dm + nameTF.getText() + dm + ", price = " + dm + priceTF.getText() + dm
                                + " where idProduct = " + idTF.getText());
                        alert(Alert.AlertType.INFORMATION, "Успешно", "Товар №" + idTF.getText() + " изменен");
                        initialize();
                        clear(idTF, nameTF, priceTF);
                    } catch (SQLException e) {
                        alert(Alert.AlertType.ERROR, "Ошибка", "Данная операция не возможна! Код ошибки: " + e);
                    }
                }else {
                    alert(Alert.AlertType.ERROR, "Ошибка", "Поле цены товара пусто или содержит недопустимые символы");
                }
            }
            else {
                alert(Alert.AlertType.ERROR, "Ошибка", "Поле названия товара пусто или содержит недопустимые символы");
            }
        } else {
            alert(Alert.AlertType.ERROR, "Ошибка", "Поле id товара пусто. Чтобы его заполнить выберите товар из таблицы");
        }
    }


    @FXML
    private void delete(){
        if (!idTF.getText().isEmpty()) {
                try {
                    connect = new Connect();
                    Statement statement = connect.getConnection().createStatement();
                    statement.execute("delete from snab_mag.product where idProduct = " + idTF.getText());
                    alert(Alert.AlertType.INFORMATION, "Успешно","Товар №" + idTF.getText()+" удален");
                    initialize();
                    clear(idTF, nameTF, priceTF);
                } catch (SQLException e) {
                    alert(Alert.AlertType.ERROR, "Ошибка", "Данная операция не возможна! Код ошибки: " + e);
                }
        } else {
            alert(Alert.AlertType.ERROR, "Ошибка", "Поле id товара пусто. Чтобы его заполнить выберите товар из таблицы");
        }
        }

    private void setTableTov (int id, String _name, String _kolTov, float _price){
        Product.add(new Product(id, _name, _kolTov, _price));

        idP.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
        name.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        kol_tov.setCellValueFactory(new PropertyValueFactory<Product, String>("kolTov"));
        price.setCellValueFactory(new PropertyValueFactory<Product, Float>("price"));

        tableP.setItems(Product);
    }

    private void alert (Alert.AlertType alertType, String title, String text){
        Alert alert = new Alert(alertType);

        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(text);

        alert.showAndWait();
    }

    private void clear (TextField idTF, TextField nameTF, TextField priceTF){
        idTF.setText("");
        nameTF.setText("");
        priceTF.setText("");
    }
}