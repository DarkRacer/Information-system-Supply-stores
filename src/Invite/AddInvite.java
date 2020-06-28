package Invite;

import Database.Connect;
import Login.Login;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class AddInvite {
    private Connect connect = null;
    private ObservableList<Invite> Invite = FXCollections.observableArrayList();
    final char dm = (char) 34;

    @FXML
    private ComboBox<String> market;

    @FXML
    private ComboBox<String> delivery;

    @FXML
    private ComboBox<String> product;

    @FXML
    private TextField col;

    @FXML
    private Label sum;

    @FXML
    private Button add;

    @FXML
    private void initialize (){
        final float[] _sum = new float[1];
        final float[] price = {0};

        try {
            connect = new Connect();
            Statement statement = connect.getConnection().createStatement();
            if (Objects.equals(Login.getRoleDB(), "Администратор")) {
                final ResultSet resultSetMarket = statement.executeQuery("select `Market` from snab_mag.market");
                while (resultSetMarket.next()) {
                    String market1 = resultSetMarket.getString("Market");
                    if (!market.getItems().contains(market1)) {
                        market.getItems().addAll(market1);
                    }
                }
            }
            else market.setValue(Login.getNameDB());

                final ResultSet resultSetDelivery = statement.executeQuery("select `Delivery` from snab_mag.delivery");
                while (resultSetDelivery.next()) {
                    String delivery1 = resultSetDelivery.getString("Delivery");
                    if (!delivery.getItems().contains(delivery1)) {
                        delivery.getItems().addAll(delivery1);
                    }
                }

                final ResultSet resultSetProduct = statement.executeQuery("select `Product` from snab_mag.product");
                while (resultSetProduct.next()) {
                    String product1 = resultSetProduct.getString("Product");
                    if (!product.getItems().contains(product1)) {
                        product.getItems().addAll(product1);
                    }
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        col.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String s2) {
                if (product.getValue() != null) {
                    try {
                        connect = new Connect();
                        Statement statement = connect.getConnection().createStatement();
                        final ResultSet resultSet = statement.executeQuery("select `price` from snab_mag.product where Product = " + dm + product.getValue() + dm);
                        while (resultSet.next()) {
                            price[0] = resultSet.getFloat("price");
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    if (col.getText().matches("^[0-9]+$")) {
                        _sum[0] = price[0] * Float.valueOf(col.getText());
                        sum.setText(String.valueOf(_sum[0]));
                    } else {
                        sum.setText(" Не корректное кол-во товара");
                    }
                }
            }
        });
    }

    @FXML
    private void addI () {
        if (market.getValue() != null){
            if (delivery.getValue() != null){
                if (product.getValue() != null){
                    if (col.getText() != null && col.getText().matches("^[0-9]+$")){
                        String currentTime;
                        Date date = new Date();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        currentTime = sdf.format(date);
                        int idP = 0, idM = 0, idD = 0;

                        try {
                            connect = new Connect();
                            Statement statement = connect.getConnection().createStatement();
                            final ResultSet resultSetProduct = statement.executeQuery("select `idProduct` from snab_mag.product where Product = " + dm + product.getValue() + dm);
                            while (resultSetProduct.next()) {
                                idP = resultSetProduct.getInt("idProduct");
                            }

                            final ResultSet resultSetMarket = statement.executeQuery("select `idMarket` from snab_mag.market where Market = " + dm + market.getValue() + dm);
                            while (resultSetMarket.next()) {
                                idM = resultSetMarket.getInt("idMarket");
                            }

                            final ResultSet resultSetDelivery = statement.executeQuery("select `idDelivery` from snab_mag.delivery where Delivery = " + dm + delivery.getValue() + dm);
                            while (resultSetDelivery.next()) {
                                idD = resultSetDelivery.getInt("idDelivery");
                            }

                            statement.execute("insert into snab_mag.invite (Invitecol_prod, Sum_Invite, Date, idStatus, Market_idMarket, idP, idD) " +
                                    "values (" + col.getText() + ", " + sum.getText() +", DATE " + dm + currentTime + dm + " , 1, " + idM + " , " + idP + " , " + idD + ");");
                            alert(Alert.AlertType.INFORMATION, "Успешно", "Заявка успешно добавлена");
                            clear(market, delivery, product, col, sum);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }else alert(Alert.AlertType.ERROR, "Ошибка", "Введите корректное количество товара");
                } else alert(Alert.AlertType.ERROR, "Ошибка", "Выберите товар");
            }else alert(Alert.AlertType.ERROR, "Ошибка", "Выберите доставку");
        } else alert(Alert.AlertType.ERROR, "Ошибка", "Выберите магазин");

    }

    @FXML
    private void tov(){
        final float[] _sum = new float[1];
        final float[] price = {0};
        if (col.getText() != null){
            try {
                connect = new Connect();
                Statement statement = connect.getConnection().createStatement();
                final ResultSet resultSet = statement.executeQuery("select `price` from snab_mag.product where Product = " + dm + product.getValue() + dm);
                while (resultSet.next()) {
                    price[0] = resultSet.getFloat("price");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (col.getText().matches("^[0-9]+$")) {
                _sum[0] = price[0] * Float.valueOf(col.getText());
                sum.setText(String.valueOf(_sum[0]));
            } else {
                sum.setText(" Не корректное кол-во товара");
            }
        }
    }

    private void alert (Alert.AlertType alertType, String title, String text){
        Alert alert = new Alert(alertType);

        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(text);

        alert.showAndWait();
    }

    private void clear (ComboBox<String> market, ComboBox<String> delivery, ComboBox<String> product, TextField col, Label sum){
        market.setValue("");
        delivery.setValue("");
        product.setValue("");
        col.setText("");
        sum.setText("");
    }
}
