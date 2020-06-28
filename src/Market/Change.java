package Market;

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

public class Change {
    private Connect connect = null;
    private ObservableList<Market> Market = FXCollections.observableArrayList();
    final char dm = (char) 34;

    @FXML
    private TableView<Market> tableM;

    @FXML
    private TableColumn<Market, Integer> idM;

    @FXML
    private TableColumn<Market, String> name;

    @FXML
    private TableColumn<Market, String> requisites;

    @FXML
    private TextField idTF;

    @FXML
    private TextField nameTF;

    @FXML
    private TextField requisitesTF;

    @FXML
    private Button change;

    @FXML
    private Button delete;

    @FXML
    private void initialize() {
        idTF.setEditable(false);
        Market.clear();
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
        TableView.TableViewSelectionModel<Market> selectionModel = tableM.getSelectionModel();
        selectionModel.selectedItemProperty().addListener(new ChangeListener<Market>(){

            @Override
            public void changed(ObservableValue<? extends Market> observableValue, Market m, Market market) {
                if(market != null) {
                    idTF.setEditable(true);
                    idTF.setText(String.valueOf(market.getId()));
                    nameTF.setText(market.getName());
                    requisitesTF.setText(market.getRequisites());
                    idTF.setEditable(false);
                }
            }
        });

    }

    @FXML
    private void change(){
        if (!idTF.getText().isEmpty()) {
            if (!nameTF.getText().isEmpty() && !nameTF.getText().matches("^[0-9]+$")) {
                if (!requisitesTF.getText().isEmpty() && requisitesTF.getText().matches("^[а-яА-Яa-zA-Z0-9]+$")) {
                    try {
                        connect = new Connect();
                        Statement statement = connect.getConnection().createStatement();
                        statement.execute("update snab_mag.market set Market = "
                                + dm + nameTF.getText() + dm + ", Requisites = " + dm + requisitesTF.getText() + dm + " where idMarket = " + idTF.getText());
                        alert(Alert.AlertType.INFORMATION, "Успешно", "Магазин №" + idTF.getText() + " изменен");
                        initialize();
                    } catch (SQLException e) {
                        e.printStackTrace();
                        alert(Alert.AlertType.ERROR, "Ошибка", "Данная операция не возможна! Код ошибки: " + e);
                    }
                } else {
                    alert(Alert.AlertType.ERROR, "Ошибка", "Поле реквизитов пусто или содержит недопустимые символы");
                }
            } else {
                alert(Alert.AlertType.ERROR, "Ошибка", "Поле названия магазина пусто или содержит недопустимые символы");
            }
        }
        else {
            alert(Alert.AlertType.ERROR, "Ошибка", "Поле id магазина пусто. Чтобы его заполнить выберите магазин из таблицы");
        }
        clear (idTF, nameTF, requisitesTF);
    }

    @FXML
    private void delete(){
        if (!nameTF.getText().isEmpty() && !nameTF.getText().matches("^[0-9]+$")){
            if (!requisitesTF.getText().isEmpty() && requisitesTF.getText().matches("^[а-яА-Яa-zA-Z0-9]+$")) {
                try {
                    connect = new Connect();
                    Statement statement = connect.getConnection().createStatement();
                    statement.execute("delete from snab_mag.market where idMarket = " + idTF.getText());
                    alert(Alert.AlertType.INFORMATION, "Успешно", "Магазин №" + idTF.getText()+" удален");
                    initialize();
                } catch (SQLException e) {
                    e.printStackTrace();
                    alert(Alert.AlertType.ERROR, "Ошибка", "Данная операция не возможна! Код ошибки: " + e);
                }
            }
            else {
                alert(Alert.AlertType.ERROR, "Ошибка", "Поле реквизитов пусто или содержит недопустимые символы");
            }
        }
        else {
            alert(Alert.AlertType.ERROR, "Ошибка", "Поле названия магазина пусто или содержит недопустимые символы");
        }
        clear (idTF, nameTF, requisitesTF);
    }

    private void setTableM (int id, String _name, String _requisites){
        Market.add(new Market(id, _name, _requisites));

        idM.setCellValueFactory(new PropertyValueFactory<Market, Integer>("id"));
        name.setCellValueFactory(new PropertyValueFactory<Market, String>("name"));
        requisites.setCellValueFactory(new PropertyValueFactory<Market, String>("requisites"));

        tableM.setItems(Market);
    }

    private void alert (Alert.AlertType alertType, String title, String text){
        Alert alert = new Alert(alertType);

        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(text);

        alert.showAndWait();
    }

    private void clear (TextField idTF, TextField nameTF, TextField requisitesTF){
        idTF.setText("");
        nameTF.setText("");
        requisitesTF.setText("");
    }
}

