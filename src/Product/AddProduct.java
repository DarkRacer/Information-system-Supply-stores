package Product;

import Database.Connect;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AddProduct {
    private Connect connect = null;
    final char dm = (char) 34;

    @FXML
    private TextField name;

    @FXML
    private TextField price;

    @FXML
    private ComboBox<String> unit;

    @FXML
    private Button add;

    @FXML
    private void initialize(){
        try {
            connect = new Connect();
            Statement statement = connect.getConnection().createStatement();
            final ResultSet resultSet = statement.executeQuery("select `unit` from snab_mag.unit");
            while (resultSet.next()) {
                String unit1 = resultSet.getString("unit");
                unit.getItems().addAll(unit1);
            }
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addp(){
        int buff = 0;
        if (unit.getValue() != null) {
            if (!name.getText().isEmpty() && !name.getText().matches("^[0-9]+$")) {
                if (!price.getText().isEmpty() && !price.getText().matches("^[а-яА-Я]+$") && !price.getText().matches("^[A-Za-z]+$")) {
                    try {
                        connect = new Connect();
                        Statement statement = connect.getConnection().createStatement();

                        final ResultSet resultSet = statement.executeQuery("select idU from snab_mag.unit where unit = " +
                                dm + unit.getValue() + dm);
                        while (resultSet.next()) {
                            buff = resultSet.getInt(1);
                        }

                        statement.execute("insert into snab_mag.product (Product, idU, price) values ("
                                + dm + name.getText() + dm + ", " + buff + ", " + dm + price.getText() + dm + ")");

                        alert(Alert.AlertType.INFORMATION,"Успешно","Новый товар занесён в базу");
                        initialize();
                        clear (name, price, unit);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }else {
                    alert(Alert.AlertType.ERROR, "Ошибка", "Поле цены товара пусто или содержит недопустимые символы");
                }
            } else {
                alert(Alert.AlertType.ERROR, "Ошибка", "Поле наименования товара пусто или содержит недопустимые символы");
            }
        }
        else {
            alert(Alert.AlertType.ERROR, "Ошибка", "Выберите единицу измерения");
    }
    }

    private void alert (Alert.AlertType alertType, String title, String text){
        Alert alert = new Alert(alertType);

        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(text);

        alert.showAndWait();
    }

    private void clear (TextField name,TextField price, ComboBox<String> unit){
        name.setText("");
        price.setText("");
        unit.setValue("");
    }
}
