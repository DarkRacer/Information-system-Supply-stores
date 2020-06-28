package Market;

import Database.Connect;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.sql.Statement;

public class AddMarket {
    private Connect connect = null;
    final char dm = (char) 34;

    @FXML
    private TextField name;

    @FXML
    private TextField requisites;

    @FXML
    private Button add;

    @FXML
    private void addM(){
        Stage stage = (Stage) add.getScene().getWindow();
        if (!name.getText().isEmpty() && !name.getText().matches("^[0-9]+$")){
            if (!requisites.getText().isEmpty() && requisites.getText().matches("^[а-яА-Яa-zA-Z0-9]+$")) {
                try {
                    connect = new Connect();
                    Statement statement = connect.getConnection().createStatement();
                    statement.execute("insert into snab_mag.market (Market, Requisites) values ("
                            + dm + name.getText() + dm + ", " + dm + requisites.getText() + dm +")");
                    alert(Alert.AlertType.INFORMATION, "Успешно", "Магазин \"" + name.getText() + "\" добавлен");
                    stage.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            else {
                alert(Alert.AlertType.ERROR, "Ошибка","Поле реквизитов пусто или содержит недопустимые символы");
            }
        }
        else {
            alert(Alert.AlertType.ERROR, "Ошибка","Поле названия магазина пусто или содержит недопустимые символы");
        }
        clear(name, requisites);
    }

    private void alert (Alert.AlertType alertType, String title, String text){
        Alert alert = new Alert(alertType);

        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(text);

        alert.showAndWait();
    }

    private void clear(TextField name, TextField requisites){
        name.setText("");
        requisites.setText("");
    }
}
