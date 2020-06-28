package Delivery;

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

public class ChangeDelivery {
    private Connect connect = null;
    private ObservableList<Delivery> Delivery = FXCollections.observableArrayList();
    final char dm = (char) 34;

    @FXML
    private TableView<Delivery> tableD;

    @FXML
    private TableColumn<Delivery, Integer> id;

    @FXML
    private TableColumn<Delivery, String> name;

    @FXML
    private TextField idTF;

    @FXML
    private TextField nameTF;

    @FXML
    private Button changeB;

    @FXML
    private Button deleteB;

    @FXML
    private void initialize(){
        idTF.setText("");
        nameTF.setText("");
        idTF.setEditable(false);
        Delivery.clear();
        try {
            connect = new Connect();
            Statement statement = connect.getConnection().createStatement();
            final ResultSet resultSet = statement.executeQuery("select delivery.idDelivery, delivery.Delivery " +
                    " from snab_mag.delivery ");
            while (resultSet.next()) {
                setTableD(resultSet.getInt(1), resultSet.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        TableView.TableViewSelectionModel<Delivery> selectionModel = tableD.getSelectionModel();
        selectionModel.selectedItemProperty().addListener(new ChangeListener<Delivery>(){

            @Override
            public void changed(ObservableValue<? extends Delivery> observableValue, Delivery d, Delivery delivery) {
                if(delivery != null) {
                    idTF.setEditable(true);
                    idTF.setText(String.valueOf(delivery.getId()));
                    nameTF.setText(delivery.getName());
                    idTF.setEditable(false);
                }
            }
        });
    }

    @FXML
    private void change() {
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
            if (deliv[k] == name.getText()) {
                g = 1;
            }
        }

        if (!idTF.getText().isEmpty()) {
            if (!nameTF.getText().isEmpty() && !nameTF.getText().matches("^[0-9]+$")) {
                if (g == 0) {
                    try {
                        connect = new Connect();
                        Statement statement = connect.getConnection().createStatement();
                        statement.execute("update snab_mag.delivery set Delivery = "
                                + dm + nameTF.getText() + dm + " where idDelivery = " + idTF.getText());
                        alert(Alert.AlertType.INFORMATION, "Успешно", "Доставка №" + idTF.getText() + " изменена");
                        initialize();
                    } catch (SQLException e) {
                        alert(Alert.AlertType.ERROR, "Ошибка", "Данная операция не возможна! Код ошибки: " + e);
                    }
                } else {
                    alert(Alert.AlertType.ERROR, "Ошибка", "Введенная доставка уже есть в базе");
                }
            }
            else {
                alert(Alert.AlertType.ERROR, "Ошибка", "Поле названия доставки пусто или содержит недопустимые символы");
            }
        } else {
            alert(Alert.AlertType.ERROR, "Ошибка", "Поле id доставки пусто. Чтобы его заполнить выберите доставку из таблицы");
        }
    }


    @FXML
    private void delete(){
        if (!idTF.getText().isEmpty()) {
            try {
                connect = new Connect();
                Statement statement = connect.getConnection().createStatement();
                statement.execute("delete from snab_mag.delivery where idDelivery = " + idTF.getText());
                alert(Alert.AlertType.INFORMATION, "Успешно","Доставка №" + idTF.getText()+" удалена");
                initialize();
            } catch (SQLException e) {
                alert(Alert.AlertType.ERROR, "Ошибка", "Данная операция не возможна! Код ошибки: " + e);
            }
        } else {
            alert(Alert.AlertType.ERROR, "Ошибка", "Поле id доставки пусто. Чтобы его заполнить выберите доставку из таблицы");
        }
    }

    private void setTableD (int _id, String _name){
        Delivery.add(new Delivery(_id, _name));

        id.setCellValueFactory(new PropertyValueFactory<Delivery, Integer>("id"));
        name.setCellValueFactory(new PropertyValueFactory<Delivery, String>("name"));

        tableD.setItems(Delivery);
    }

    private void alert (Alert.AlertType alertType, String title, String text){
        Alert alert = new Alert(alertType);

        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(text);

        alert.showAndWait();
    }
}
