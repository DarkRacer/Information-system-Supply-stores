package Login;

import Database.Connect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Login {
    private static String nameDB;
    private static String roleDB = "";
    private Connect connect = null;
    private ObservableList<LoginConst> Invite = FXCollections.observableArrayList();
    final char dm = (char) 34;

    @FXML
    private TextField login;

    @FXML
    private PasswordField password;

    @FXML
    private Button loginB;

    @FXML
    private void action_login(){
        nameDB = "";
        roleDB = "";
        if (login.getText() != null && password.getText() != null){
            try {
                connect = new Connect();
                Statement statement = connect.getConnection().createStatement();
                final ResultSet resultSet = statement.executeQuery("select login.name, login.login, login.password, login.Role from snab_mag.login");
                while (resultSet.next()) {
                        if (login.getText().equals(resultSet.getString(2))){
                            if (password.getText().equals(resultSet.getString(3)) ){
                                nameDB = resultSet.getString(1);
                                roleDB = resultSet.getString(4);
                                break;
                            }
                        }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            if (roleDB.equals("Администратор")){
                Stage st = (Stage) loginB.getScene().getWindow();
                st.close();
                Stage Stage = new Stage();
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("/main/sample.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Stage.setTitle("Снабжение магазинов. Пользователь: " + nameDB);
                Stage.setScene(new Scene(root, 607.0, 379.0));
                Stage.setResizable(false);
                Stage.centerOnScreen();
                Stage.show();
            }

            else if (roleDB.equals("Склад")){
                Stage st = (Stage) loginB.getScene().getWindow();
                st.close();
                Stage Stage = new Stage();
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("/Login/loginBase.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Stage.setTitle("Снабжение магазинов. Пользователь: " + nameDB);
                Stage.setScene(new Scene(root, 600.0, 237.0));
                Stage.setResizable(false);
                Stage.centerOnScreen();
                Stage.show();
            }

            else if (roleDB.equals("Магазин")){
                Stage st = (Stage) loginB.getScene().getWindow();
                st.close();
                Stage Stage = new Stage();
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("/Login/login_market.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Stage.setTitle("Снабжение магазинов. Пользователь: " + nameDB);
                Stage.setScene(new Scene(root, 600.0, 202.0));
                Stage.setResizable(false);
                Stage.centerOnScreen();
                Stage.show();

            } else alert(Alert.AlertType.ERROR, "Ошибка входа", "Логин или пароль неверны");

        } else alert(Alert.AlertType.ERROR, "Ошибка", "Для входа заполните поля");
    }

    private void alert (Alert.AlertType alertType, String title, String text){
        Alert alert = new Alert(alertType);

        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(text);

        alert.showAndWait();
    }

    public static String getNameDB() {
        return nameDB;
    }

    public static String getRoleDB() {
        return roleDB;
    }
}
