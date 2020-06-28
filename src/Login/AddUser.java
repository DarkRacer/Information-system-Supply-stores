package Login;

import Database.Connect;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class AddUser {
    private Connect connect = null;
    final char dm = (char) 34;

    @FXML
    private ComboBox <String> role;

    @FXML
    private ComboBox <String> marketBox;

    @FXML
    private Label marketL;

    @FXML
    private Label name;

    @FXML
    private TextField nameTF;

    @FXML
    private TextField login;

    @FXML
    private TextField password;

    @FXML
    private Button addB;

    @FXML
    private void initialize(){
        marketL.setVisible(false);
        marketBox.setVisible(false);
        name.setVisible(false);
        nameTF.setVisible(false);

        try {
            connect = new Connect();
            Statement statement = connect.getConnection().createStatement();
            final ResultSet resultSet = statement.executeQuery("select Role from snab_mag.login");
            while (resultSet.next()) {
                String role1 = resultSet.getString("Role");
                if (!role.getItems().contains(role1)){
                    role.getItems().addAll(role1);
                }
            }
        } catch (
                SQLException e) {
            e.printStackTrace();
        }

        role.setOnAction(event -> setRole());
    }

    @FXML
    private void add(ActionEvent event){
        if (marketBox.getValue() != null){
            String[] markt = new String[5000];
            String market = marketBox.getValue();
            int i = 0;
            try {
                connect = new Connect();
                Statement statement = connect.getConnection().createStatement();
                final ResultSet resultSet = statement.executeQuery("select `name` from snab_mag.login where Role = \"Магазин\" ");
                while (resultSet.next()) {
                    markt[i] = resultSet.getString("name");
                    i++;
                }
            } catch (
                    SQLException e) {
                e.printStackTrace();
            }
            int g = 0;
            for (int k = 0; k <= i; k++) {
                if (Objects.equals(markt[k], market)) {
                    g = 1;
                    break;
                }
            }

            if (g == 0){
                if (login.getText() != null){

                    String[] log = new String[5000];
                    String lg = login.getText();
                    int c = 0;

                    try {
                        connect = new Connect();
                        Statement statement = connect.getConnection().createStatement();
                        final ResultSet resultSet = statement.executeQuery("select `login` from snab_mag.login");
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
                        if (Objects.equals(log[k], lg)) {
                            l = 1;
                            break;
                        }
                    }

                    if (l == 0) {

                        if (password.getText() != null) {
                            try {
                                connect = new Connect();
                                Statement statement = connect.getConnection().createStatement();

                                statement.execute("insert into snab_mag.login (`name`, login, password, Role) values ("
                                        + dm + marketBox.getValue() + dm + ", " + dm + login.getText() + dm +
                                        ", " + dm + password.getText() + dm + ", " + dm + role.getValue() + dm + ")");

                                alert(Alert.AlertType.INFORMATION, "Успешно", "Учётная запись создана");
                                clear();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        } else alert(Alert.AlertType.ERROR, "Ошибка", "Введите пароль");
                    }
                    else alert(Alert.AlertType.ERROR, "Ошибка", "Данный логин уже используется");
                }
                else alert(Alert.AlertType.ERROR, "Ошибка", "Введите логин");
            }
            else alert(Alert.AlertType.ERROR, "Ошибка", "Данный магазин уже имеет аккаунт");

        }
        else {
            if (nameTF.getText() != null){
                String[] nam = new String[5000];
                String nm = nameTF.getText();
                int i = 0;

                try {
                    connect = new Connect();
                    Statement statement = connect.getConnection().createStatement();
                    final ResultSet resultSet = statement.executeQuery("select `name` from snab_mag.login where Role = " + dm + role.getValue() + dm);
                    while (resultSet.next()) {
                        nam[i] = resultSet.getString("name");
                        i++;
                    }
                } catch (
                        SQLException e) {
                    e.printStackTrace();
                }
                int q = 0;
                for (int k = 0; k <= i; k++) {
                    if (Objects.equals(nam[k], nm)) {
                        q = 1;
                        break;
                    }
                }

                if (q == 0){
                    if (login.getText() != null){

                        String[] log = new String[5000];
                        String lg = login.getText();
                        int c = 0;

                        try {
                            connect = new Connect();
                            Statement statement = connect.getConnection().createStatement();
                            final ResultSet resultSet = statement.executeQuery("select `login` from snab_mag.login where Role = " + dm + role.getValue() + dm);
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
                            if (Objects.equals(log[k], lg)) {
                                l = 1;
                                break;
                            }
                        }

                        if (l == 0) {
                            if (password.getText() != null) {
                                try {
                                    connect = new Connect();
                                    Statement statement = connect.getConnection().createStatement();

                                    statement.execute("insert into snab_mag.login (`name`, login, password, Role) values ("
                                            + dm + nameTF.getText() + dm + ", " + dm + login.getText() + dm +
                                            ", " + dm + password.getText() + dm + ", " + dm + role.getValue() + dm + ")");

                                    alert(Alert.AlertType.INFORMATION, "Успешно", "Учётная запись создана");
                                    clear();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            } else alert(Alert.AlertType.ERROR, "Ошибка", "Введите пароль");
                        }
                        else alert(Alert.AlertType.ERROR, "Ошибка", "Данный логин уже используется");
                    }
                    else alert(Alert.AlertType.ERROR, "Ошибка", "Введите логин");
                }
                else alert(Alert.AlertType.ERROR, "Ошибка", "Аккаунт с таким именем и ролью уже существует");
            }
            else alert(Alert.AlertType.ERROR, "Ошибка", "Введите имя аккаунта");
        }
    }

    private void setRole () {
        if (role.getValue().equals("Магазин")){
            marketL.setVisible(true);
            marketBox.setVisible(true);
            name.setVisible(false);
            nameTF.setVisible(false);

            nameTF.setText("");
            try {
                connect = new Connect();
                Statement statement = connect.getConnection().createStatement();
                final ResultSet resultSet = statement.executeQuery("select Market from snab_mag.market");
                while (resultSet.next()) {
                    String market = resultSet.getString("Market");
                    if (!marketBox.getItems().contains(market)){
                        marketBox.getItems().addAll(market);
                    }
                }
            } catch (
                    SQLException e) {
                e.printStackTrace();
            }
        }
        else {
            marketL.setVisible(false);
            marketBox.setVisible(false);
            name.setVisible(true);
            nameTF.setVisible(true);

            marketBox.setValue(null);
        }
    }

    private void alert (Alert.AlertType alertType, String title, String text){
        Alert alert = new Alert(alertType);

        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(text);

        alert.showAndWait();
    }

    private void clear () {
        role.setValue("");
        marketBox.setValue("");
        nameTF.setText("");
        login.setText("");
        password.setText("");

        marketL.setVisible(false);
        marketBox.setVisible(false);
        name.setVisible(false);
        nameTF.setVisible(false);
    }
}
