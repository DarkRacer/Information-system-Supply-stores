package Invite;

import Database.Connect;
import Login.Login;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class ChangeInvite {
    private Connect connect = null;
    private ObservableList<Invite> Invite = FXCollections.observableArrayList();
    final char dm = (char) 34;

    @FXML
    private TextField idTF;

    @FXML
    private TextField colTF;

    @FXML
    private Label sumL;

    @FXML
    private Label unit;

    @FXML
    private Label statusL;

    @FXML
    private ComboBox<String> marketBox;

    @FXML
    private ComboBox<String> productBox;

    @FXML
    private ComboBox<String> deliveryBox;

    @FXML
    private TableView<Invite> tableI;

    @FXML
    private TableColumn<Invite, Integer> idI;

    @FXML
    private TableColumn<Invite, String> market;

    @FXML
    private TableColumn<Invite, String> product;

    @FXML
    private TableColumn<Invite, String> delivery;

    @FXML
    private TableColumn<Invite, String> col;

    @FXML
    private TableColumn<Invite, String> date;

    @FXML
    private TableColumn<Invite, Float> sum;

    @FXML
    private TableColumn<Invite, String> status;

    @FXML
    private Button changeB;

    @FXML
    private Button deleteB;

    @FXML
    private void initialize(){
        final float[] _sum = new float[1];
        final float[] price = {0};
        Invite.clear();


        try {
            connect = new Connect();
            Statement statement = connect.getConnection().createStatement();
            if (Objects.equals(Login.getRoleDB(), "Администратор")){
            final ResultSet resultSet = statement.executeQuery("select invite.idInvite, invite.Invitecol_prod, " +
                    "invite.Sum_Invite, invite.Date, status.status, market.Market, product.Product, delivery.Delivery, unit.unit" +
                    " from snab_mag.invite inner join snab_mag.market on market.idMarket = invite.Market_idMarket" +
                    " inner join snab_mag.product on product.idProduct = invite.idP" +
                    " inner join snab_mag.delivery on delivery.idDelivery = invite.idD" +
                    " inner join snab_mag.unit on unit.idU = product.idU" +
                    " inner join snab_mag.status on status.idStatus = invite.idStatus");
            while (resultSet.next()) {
                setTableI(resultSet.getInt(1), (resultSet.getString(2) + " " + resultSet.getString(9)),
                        resultSet.getFloat(3), resultSet.getString(4), resultSet.getString(5),
                        resultSet.getString(6), resultSet.getString(7), resultSet.getString(8));
            }
                final ResultSet resultSetMarket = statement.executeQuery("select `Market` from snab_mag.market");
                while (resultSetMarket.next()) {
                    String market1 = resultSetMarket.getString("Market");
                    if (!marketBox.getItems().contains(market1)) {
                        marketBox.getItems().addAll(market1);
                    }
                }
        }
        else {
                final ResultSet resultSet = statement.executeQuery("select invite.idInvite, invite.Invitecol_prod, " +
                        "invite.Sum_Invite, invite.Date, status.status, market.Market, product.Product, delivery.Delivery, unit.unit" +
                        " from snab_mag.invite inner join snab_mag.market on market.idMarket = invite.Market_idMarket" +
                        " inner join snab_mag.product on product.idProduct = invite.idP" +
                        " inner join snab_mag.delivery on delivery.idDelivery = invite.idD" +
                        " inner join snab_mag.unit on unit.idU = product.idU" +
                        " inner join snab_mag.status on status.idStatus = invite.idStatus where market.Market = " + dm + Login.getNameDB() + dm);
                while (resultSet.next()) {
                    setTableI(resultSet.getInt(1), (resultSet.getString(2) + " " + resultSet.getString(9)),
                            resultSet.getFloat(3), resultSet.getString(4), resultSet.getString(5),
                            resultSet.getString(6), resultSet.getString(7), resultSet.getString(8));
                }
                marketBox.setValue(Login.getNameDB());
            }

            final ResultSet resultSetDelivery = statement.executeQuery("select `Delivery` from snab_mag.delivery");
            while (resultSetDelivery.next()) {
                String delivery1 = resultSetDelivery.getString("Delivery");
                if (!deliveryBox.getItems().contains(delivery1)) {
                    deliveryBox.getItems().addAll(delivery1);
                }
            }

            final ResultSet resultSetProduct = statement.executeQuery("select `Product` from snab_mag.product");
            while (resultSetProduct.next()) {
                String product1 = resultSetProduct.getString("Product");
                if (!productBox.getItems().contains(product1)) {
                    productBox.getItems().addAll(product1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        marketBox.setEditable(false);
        productBox.setEditable(false);
        deliveryBox.setEditable(false);
        colTF.setEditable(false);
        idTF.setEditable(false);

        TableView.TableViewSelectionModel<Invite> selectionModel = tableI.getSelectionModel();
        selectionModel.selectedItemProperty().addListener(new ChangeListener<Invite>(){

            @Override
            public void changed(ObservableValue<? extends Invite> observableValue, Invite i, Invite invite) {
                if(invite != null) {
                    idTF.setEditable(true);
                    colTF.setEditable(true);
                    marketBox.setEditable(false);
                    productBox.setEditable(false);
                    deliveryBox.setEditable(false);

                    idTF.setText(String.valueOf(invite.getId()));
                    idTF.setEditable(false);
                    marketBox.setValue(invite.getMarket());
                    productBox.setValue(invite.getTov());
                    deliveryBox.setValue(invite.getDelivery());
                    statusL.setText(invite.getStatus());
                    sumL.setText(String.valueOf(invite.getSum()));

                    String col = invite.getCol();
                    String [] col1;
                    col1 = col.split(" ");
                    colTF.setText(col1[0]);
                    unit.setText(col1[1]);
                }
            }
        });

        colTF.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String s2) {
                if (productBox.getValue() != null) {
                    try {
                        connect = new Connect();
                        Statement statement = connect.getConnection().createStatement();
                        final ResultSet resultSet = statement.executeQuery("select `price` from snab_mag.product where Product = " + dm + productBox.getValue() + dm);
                        while (resultSet.next()) {
                            price[0] = resultSet.getFloat("price");
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    if (colTF.getText().matches("^[0-9]+$")) {
                        _sum[0] = price[0] * Float.valueOf(colTF.getText());
                        sumL.setText(String.valueOf(_sum[0]));
                    } else {
                        sumL.setText(" Не корректное кол-во товара");
                    }
                }
            }
        });


    }

    @FXML
    private void change (){
        if (!idTF.getText().isEmpty()) {
            if (!Objects.equals(statusL.getText(), "Выполнена")) {
                if (marketBox.getValue() != null) {
                    if (deliveryBox.getValue() != null) {
                        if (productBox.getValue() != null) {
                            if (colTF.getText() != null && colTF.getText().matches("^[0-9]+$")) {
                                String currentTime;
                                Date date = new Date();
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                currentTime = sdf.format(date);
                                int idP = 0, idM = 0, idD = 0;

                                try {
                                    connect = new Connect();
                                    Statement statement = connect.getConnection().createStatement();
                                    final ResultSet resultSetProduct = statement.executeQuery("select `idProduct` from snab_mag.product where Product = " + dm + productBox.getValue() + dm);
                                    while (resultSetProduct.next()) {
                                        idP = resultSetProduct.getInt("idProduct");
                                    }

                                    final ResultSet resultSetMarket = statement.executeQuery("select `idMarket` from snab_mag.market where Market = " + dm + marketBox.getValue() + dm);
                                    while (resultSetMarket.next()) {
                                        idM = resultSetMarket.getInt("idMarket");
                                    }

                                    final ResultSet resultSetDelivery = statement.executeQuery("select `idDelivery` from snab_mag.delivery where Delivery = " + dm + deliveryBox.getValue() + dm);
                                    while (resultSetDelivery.next()) {
                                        idD = resultSetDelivery.getInt("idDelivery");
                                    }

                                    statement.execute("update snab_mag.invite set Market_idMarket = " + idM + ", idP = " + idP + ", idD = " + idD
                                            + ", Invitecol_prod = " + colTF.getText() + ", Sum_Invite = " + sumL.getText() + ", Date = DATE " + dm + currentTime + dm + " where idInvite =" + idTF.getText());
                                    alert(Alert.AlertType.INFORMATION, "Успешно", "Заявка успешно изменена");
                                    initialize();
                                } catch (SQLException e) {
                                    alert(Alert.AlertType.ERROR, "Ошибка", "Данная операция не возможна! Код ошибки: " + e);
                                }
                            } else alert(Alert.AlertType.ERROR, "Ошибка", "Введите корректное количество товара");
                        } else alert(Alert.AlertType.ERROR, "Ошибка", "Выберите товар");
                    } else alert(Alert.AlertType.ERROR, "Ошибка", "Выберите доставку");
                } else alert(Alert.AlertType.ERROR, "Ошибка", "Выберите магазин");
            } else alert(Alert.AlertType.ERROR, "Ошибка", "Выполненные заявки изменять нельзя");
        } else alert(Alert.AlertType.ERROR, "Ошибка", "Поле id заявки пусто. Чтобы его заполнить выберите зявку из таблицы");
        clear(idTF, colTF, sumL, unit, statusL, marketBox, productBox, deliveryBox);
    }

    @FXML
    private void delete (){
        if (!idTF.getText().isEmpty()) {
            if (!Objects.equals(statusL.getText(), "Выполнена")) {
            try {
                connect = new Connect();
                Statement statement = connect.getConnection().createStatement();
                statement.execute("delete from snab_mag.invite where idInvite = " + idTF.getText());
                alert(Alert.AlertType.INFORMATION, "Успешно","Заявка №" + idTF.getText()+" удалена");
                initialize();
            } catch (SQLException e) {
                alert(Alert.AlertType.ERROR, "Ошибка", "Данная операция не возможна! Код ошибки: " + e);
            }
        } else alert(Alert.AlertType.ERROR, "Ошибка", "Выполненные заявки удалять нельзя");
        } else {
            alert(Alert.AlertType.ERROR, "Ошибка", "Поле id заявки пусто. Чтобы его заполнить выберите зявку из таблицы");
        }
        clear(idTF, colTF, sumL, unit, statusL, marketBox, productBox, deliveryBox);
    }

    @FXML
    private void prod(){
        final float[] _sum = new float[1];
        final float[] price = {0};
        if (colTF != null) {
            try {
                connect = new Connect();
                Statement statement = connect.getConnection().createStatement();
                final ResultSet resultSet = statement.executeQuery("select `price` from snab_mag.product where Product = " + dm + productBox.getValue() + dm);
                while (resultSet.next()) {
                    price[0] = resultSet.getFloat("price");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (colTF.getText().matches("^[0-9]+$")) {
                _sum[0] = price[0] * Float.valueOf(colTF.getText());
                sumL.setText(String.valueOf(_sum[0]));
            } else {
                sumL.setText(" Не корректное кол-во товара");
            }
        }
    }

    private void setTableI (int _id, String _col, float _sum, String _date, String _status, String _market, String _tov, String _delivery){
        Invite.add(new Invite(_id, _col, _sum, _date, _status, _market, _tov, _delivery));

        idI.setCellValueFactory(new PropertyValueFactory<Invite, Integer>("id"));
        market.setCellValueFactory(new PropertyValueFactory<Invite, String>("market"));
        product.setCellValueFactory(new PropertyValueFactory<Invite, String>("tov"));
        delivery.setCellValueFactory(new PropertyValueFactory<Invite, String>("delivery"));
        col.setCellValueFactory(new PropertyValueFactory<Invite, String>("col"));
        date.setCellValueFactory(new PropertyValueFactory<Invite, String>("date"));
        sum.setCellValueFactory(new PropertyValueFactory<Invite, Float>("sum"));
        status.setCellValueFactory(new PropertyValueFactory<Invite, String>("status"));

        tableI.setItems(Invite);
    }

    private void alert (Alert.AlertType alertType, String title, String text){
        Alert alert = new Alert(alertType);

        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(text);

        alert.showAndWait();
    }

    private void clear (TextField idTF, TextField colTF, Label sumL, Label unit, Label statusL, ComboBox<String> marketBox, ComboBox<String> productBox, ComboBox<String> deliveryBox){
        idTF.setText("");
        colTF.setText("");
        statusL.setText("");
        marketBox.setValue("");
        productBox.setValue("");
        deliveryBox.setValue("");
        unit.setText("");
        sumL.setText("");
    }
}
