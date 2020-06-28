package Product;

import Database.Connect;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AdmissionProduct {
    private Connect connect = null;
    final char dm = (char) 34;

    @FXML
    private ComboBox<String> Product;

    @FXML
    private TextField colTF;

    @FXML
    private Button saveProduct;

    @FXML
    private Button back;

    @FXML
    void initialize() {
        Product.setValue("");
        colTF.setText("");

        try {
            connect = new Connect();
            Statement statement = connect.getConnection().createStatement();
            final ResultSet resultSet = statement.executeQuery("select `Product` from snab_mag.product");
            while (resultSet.next()) {
                String product = resultSet.getString("Product");
                Product.getItems().addAll(product);
            }
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        saveProduct.setOnAction(event -> save());
        back.setOnAction(event -> cancel());
    }

    @FXML
    private void save(){
        int buff = 0;
        if (Product.getValue() != null) {

            if (!colTF.getText().isEmpty() && colTF.getText().matches("^[0-9]+$")) {

                try {
                    connect = new Connect();
                    Statement statement = connect.getConnection().createStatement();
                    final ResultSet resultSet = statement.executeQuery("select Productcol_base from snab_mag.product where Product = "+
                            dm + Product.getValue() + dm);
                    while (resultSet.next()) {
                        buff = resultSet.getInt(1);
                    }
                    buff += Integer.parseInt(colTF.getText());

                    statement.execute("update snab_mag.product set Productcol_base = " + buff + " where Product = "+
                            dm + Product.getValue() + dm);

                    alert(Alert.AlertType.INFORMATION,"Успешно", "Поступление товара \"" + Product.getValue() + "\" занесено в базу");
                    initialize();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } else {
                alert(Alert.AlertType.ERROR, "Ошибка", "Поле количества товаров пусто или содержит недопустимые символы");
            }
        }
        else {
            alert(Alert.AlertType.ERROR, "Ошибка", "Выберите поступивший товар");
        }
    }

    @FXML
    private void cancel(){
        Stage stage = (Stage) back.getScene().getWindow();
        stage.close();
    }

    private void alert (Alert.AlertType alertType, String title, String text){
        Alert alert = new Alert(alertType);

        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(text);

        alert.showAndWait();
    }
}
