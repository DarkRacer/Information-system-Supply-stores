package Login;

import Database.Connect;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class ChangeUser {
    private Connect connect = null;
    private ObservableList<LoginConst> Login = FXCollections.observableArrayList();
    final char dm = (char) 34;

    @FXML
    private ComboBox<String> roleBox;

    @FXML
    private TextField idTF;

    @FXML
    private TextField loginTF;

    @FXML
    private TextField nameTF;

    @FXML
    private TableView<LoginConst> tableL;

    @FXML
    private TableColumn<LoginConst, Integer> id;

    @FXML
    private TableColumn<LoginConst, String> name;

    @FXML
    private TableColumn<LoginConst, String> login;

    @FXML
    private TableColumn<LoginConst, String> role;

    @FXML
    private void initialize () {
        idTF.setEditable(false);
        Login.clear();

        try {
            connect = new Connect();
            Statement statement = connect.getConnection().createStatement();
            final ResultSet resultSet = statement.executeQuery("select login.idlogin, login.name, " +
                    "login.login, login.Role from snab_mag.login");
            while (resultSet.next()) {
                setTableLog(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),  resultSet.getString(4));
                String role1 = resultSet.getString("Role");
                if (!roleBox.getItems().contains(role1)){
                    roleBox.getItems().addAll(role1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        TableView.TableViewSelectionModel<LoginConst> selectionModel = tableL.getSelectionModel();
        selectionModel.selectedItemProperty().addListener(new ChangeListener<LoginConst>(){

            @Override
            public void changed(ObservableValue<? extends LoginConst> observableValue, LoginConst l, LoginConst loginconst) {
                if(loginconst != null) {
                    idTF.setEditable(true);
                    idTF.setText(String.valueOf(loginconst.getId()));
                    nameTF.setText(loginconst.getName());
                    loginTF.setText(loginconst.getLogin());
                    roleBox.setValue(loginconst.getRole());
                    idTF.setEditable(false);
                }
            }
        });


    }

    @FXML
    private void change (ActionEvent event){
        if (!idTF.getText().isEmpty()) {
            if (!loginTF.getText().isEmpty()) {

                String[] log = new String[5000];
                int c = 0;

                try {
                    connect = new Connect();
                    Statement statement = connect.getConnection().createStatement();
                    final ResultSet resultSet = statement.executeQuery("select `login` from snab_mag.login where Role = " + dm + roleBox.getValue() + dm);
                    while (resultSet.next()) {
                        log[c] = resultSet.getString("login");
                        c++;
                    }
                } catch (
                        SQLException e) {
                    e.printStackTrace();
                }
                int l = 0;
                for (int k = 0; k <= c; k++) {
                    if (Objects.equals(log[k], loginTF.getText())) {
                        l = 1;
                        break;
                    }
                }
                if (l == 0) {
                if (!nameTF.getText().isEmpty()) {
                    if (roleBox.getValue() != null) {

                        if (Objects.equals(roleBox.getValue(), "Магазин")) {
                            String[] markt = new String[5000];
                            int i = 0;
                            try {
                                connect = new Connect();
                                Statement statement = connect.getConnection().createStatement();
                                final ResultSet resultSet = statement.executeQuery("select Market from snab_mag.market");
                                while (resultSet.next()) {
                                    markt[i] = resultSet.getString("Market");
                                    i++;
                                }
                            } catch (
                                    SQLException e) {
                                e.printStackTrace();
                            }
                            int g = 0;
                            for (int k = 0; k <= i; k++) {
                                if (Objects.equals(markt[k], nameTF.getText())) {
                                    g = 1;
                                    break;
                                }
                            }
                            if (g != 0) {
                                try {
                                    connect = new Connect();
                                    Statement statement = connect.getConnection().createStatement();

                                    statement.execute("update snab_mag.login set `name` = "
                                            + dm + nameTF.getText() + dm + ", login = " + dm +loginTF.getText() + dm
                                            + ", Role = " + dm + roleBox.getValue() + dm + " where idlogin = " + idTF.getText());

                                    alert(Alert.AlertType.INFORMATION, "Успешно", "Пользователь №" + idTF.getText() + " изменен");
                                    initialize();
                                    clear(idTF, nameTF, loginTF, roleBox);
                                } catch (SQLException e) {
                                    alert(Alert.AlertType.ERROR, "Ошибка", "Данная операция не возможна! Код ошибки: " + e);
                                }
                            }
                            else {
                                alert(Alert.AlertType.ERROR, "Ошибка", "Данного магазина нет в базе");
                            }
                        }
                        else {
                            try {
                                connect = new Connect();
                                Statement statement = connect.getConnection().createStatement();

                                statement.execute("update snab_mag.login set `name` = "
                                        + dm + nameTF.getText() + dm + ", login = " + dm +loginTF.getText() + dm
                                        + ", Role = " + dm + roleBox.getValue() + dm + " where idlogin = " + idTF.getText());

                                alert(Alert.AlertType.INFORMATION, "Успешно", "Пользователь №" + idTF.getText() + " изменен");
                                initialize();
                                clear(idTF, nameTF, loginTF, roleBox);
                            } catch (SQLException e) {
                                alert(Alert.AlertType.ERROR, "Ошибка", "Данная операция не возможна! Код ошибки: " + e);
                            }
                        }
                    }
                    else {
                        alert(Alert.AlertType.ERROR, "Ошибка", "Роль пользователя не выбрана");
                    }
                }
                else {
                    alert(Alert.AlertType.ERROR, "Ошибка", "Поле имени пользователя пусто");
                }
                }
                else alert(Alert.AlertType.ERROR, "Ошибка", "Данный логин уже используется");
            }
            else {
                alert(Alert.AlertType.ERROR, "Ошибка", "Поле логина пользователя пусто");
            }
        }
        else {
            alert(Alert.AlertType.ERROR, "Ошибка", "Поле id пользователя пусто. Чтобы его заполнить выберите пользователя из таблицы");
        }
    }

    @FXML
    private void delete (ActionEvent event) {
        if (!idTF.getText().isEmpty()) {
            try {
                connect = new Connect();
                Statement statement = connect.getConnection().createStatement();
                statement.execute("delete from snab_mag.login where idlogin = " + idTF.getText());
                alert(Alert.AlertType.INFORMATION, "Успешно","Пользователь №" + idTF.getText()+" удален");
                initialize();
                clear(idTF, nameTF, loginTF, roleBox);
            } catch (SQLException e) {
                alert(Alert.AlertType.ERROR, "Ошибка", "Данная операция не возможна! Код ошибки: " + e);
            }
        } else {
            alert(Alert.AlertType.ERROR, "Ошибка", "Поле id пользователя пусто. Чтобы его заполнить выберите пользователя из таблицы");
        }
    }

    private void setTableLog (int _id, String _name, String _login, String _role){
        Login.add(new LoginConst(_id, _name, _login, _role));

        id.setCellValueFactory(new PropertyValueFactory<LoginConst, Integer>("id"));
        name.setCellValueFactory(new PropertyValueFactory<LoginConst, String>("name"));
        login.setCellValueFactory(new PropertyValueFactory<LoginConst, String>("login"));
       role.setCellValueFactory(new PropertyValueFactory<LoginConst, String>("role"));

        tableL.setItems(Login);
    }

    private void alert (Alert.AlertType alertType, String title, String text){
        Alert alert = new Alert(alertType);

        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(text);

        alert.showAndWait();
    }

    private void clear (TextField idTF, TextField nameTF, TextField loginTF, ComboBox<String> roleBox){
        idTF.setText("");
        nameTF.setText("");
        loginTF.setText("");
        roleBox.setValue("");
    }
}
