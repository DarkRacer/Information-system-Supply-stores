package Delivery;

import Database.Connect;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class AddDelivery {
    private Connect connect = null;
    final char dm = (char) 34;

    @FXML
    private TextField name;

    @FXML
    private Button saveB;

    @FXML
    private void save() {
        String[] deliv = new String[5000];
        int i = 0;
        try {
            connect = new Connect();
            Statement statement = connect.getConnection().createStatement();
            final ResultSet resultSet = statement.executeQuery("select `Delivery` from snab_mag.delivery");
            while (resultSet.next()) {
                deliv[i] = resultSet.getString("Delivery");
                i++;
            }
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        int g = 0;
        for (int k = 0; k < deliv.length; k++) {
            if (Objects.equals(deliv[k], name.getText())) {
                g = 1;
            }
        }

            if (!name.getText().isEmpty() && !name.getText().matches("^[0-9]+$")) {
                if (g == 0) {
                    try {
                        connect = new Connect();
                        Statement statement = connect.getConnection().createStatement();

                        statement.execute("insert into `snab_mag`.delivery (`Delivery`) values ("
                                + dm + name.getText() + dm + ")");

                        alert(Alert.AlertType.INFORMATION, "Успешно", "Новый способ доставки занесён в базу");
                        name.setText("");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    alert(Alert.AlertType.ERROR, "Ошибка", "Введенная доставка уже есть в базе");
                }
            }
                else {
                alert(Alert.AlertType.ERROR, "Ошибка", "Поле наименования доставки пусто или содержит недопустимые символы");
            }
    }

    private void alert (Alert.AlertType alertType, String title, String text){
        Alert alert = new Alert(alertType);

        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(text);

        alert.showAndWait();
    }
}
