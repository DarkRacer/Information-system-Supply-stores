package Invite;

import Database.Connect;
import Login.Login;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class InviteInfo extends ExportInvite {
    private Connect connect = null;
    private ObservableList<Invite> Invite = FXCollections.observableArrayList();
    final char dm = (char) 34;

    @FXML
    private ComboBox<String> marketBox;

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
    private DatePicker Date_ot;

    @FXML
    private DatePicker Date_do;

    @FXML
    private Button accountingB;

    @FXML
    private Button allB;

    @FXML
    private Button serchB;

    @FXML
    private Button clearB;

    @FXML
    private MenuItem exportW;

    @FXML
    private MenuItem exportE;

    @FXML
    private MenuItem print;

    @FXML
    private void initialize(){
        if (Objects.equals(Login.getRoleDB(), "Магазин")) {
            marketBox.setValue(Login.getNameDB());
            infoInvite();
        }
        else {
            try {
                connect = new Connect();
                Statement statement = connect.getConnection().createStatement();
                final ResultSet resultSet = statement.executeQuery("select `Market` from snab_mag.market");
                while (resultSet.next()) {
                    String market = resultSet.getString("Market");
                    if (!marketBox.getItems().contains(market)) {
                        marketBox.getItems().addAll(market);
                    }
                }
            } catch (SQLException e) {

                e.printStackTrace();
            }
            marketBox.setOnAction(event -> infoInvite());
        }
    }

    private void infoInvite(){
        try {
            connect = new Connect();
            Statement statement = connect.getConnection().createStatement();
            final ResultSet resultSet = statement.executeQuery("select invite.idInvite, invite.Invitecol_prod, " +
                    "invite.Sum_Invite, invite.Date, status.status, market.Market, product.Product, delivery.Delivery, unit.unit" +
                    " from snab_mag.invite inner join snab_mag.market on market.idMarket = invite.Market_idMarket" +
                    " inner join snab_mag.product on product.idProduct = invite.idP" +
                    " inner join snab_mag.delivery on delivery.idDelivery = invite.idD" +
                    " inner join snab_mag.unit on unit.idU = product.idU" +
                    " inner join snab_mag.status on status.idStatus = invite.idStatus where `Market`=" + dm + marketBox.getValue() + dm);
            while (resultSet.next()) {
                setTableI(resultSet.getInt(1), (resultSet.getString(2) + " " + resultSet.getString(9)),
                        resultSet.getFloat(3), resultSet.getString(4), resultSet.getString(5),
                        resultSet.getString(6), resultSet.getString(7), resultSet.getString(8));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void all(){
        Invite.clear();

        if (marketBox.getValue() != null){
            try {
                connect = new Connect();
                Statement statement = connect.getConnection().createStatement();
                final ResultSet resultSet = statement.executeQuery("select invite.idInvite, invite.Invitecol_prod, " +
                        "invite.Sum_Invite, invite.Date, status.status, market.Market, product.Product, delivery.Delivery, unit.unit" +
                        " from snab_mag.invite inner join snab_mag.market on market.idMarket = invite.Market_idMarket" +
                        " inner join snab_mag.product on product.idProduct = invite.idP" +
                        " inner join snab_mag.delivery on delivery.idDelivery = invite.idD" +
                        " inner join snab_mag.unit on unit.idU = product.idU" +
                        " inner join snab_mag.status on status.idStatus = invite.idStatus where market.Market =" + dm + marketBox.getValue() + dm);
                while (resultSet.next()) {
                    setTableI(resultSet.getInt(1), (resultSet.getString(2) + " " + resultSet.getString(9)),
                            resultSet.getFloat(3), resultSet.getString(4), resultSet.getString(5),
                            resultSet.getString(6), resultSet.getString(7), resultSet.getString(8));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                connect = new Connect();
                Statement statement = connect.getConnection().createStatement();
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
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void search() {
        if (marketBox.getValue() != null) {
            if (Date_ot.getValue() != null) {
                if (Date_do.getValue() != null) {
                    if (Date_ot.getValue().isBefore(Date_do.getValue())) {

                        Invite.clear();
                        try {
                            connect = new Connect();
                            Statement statement = connect.getConnection().createStatement();
                            final ResultSet resultSet = statement.executeQuery("select invite.idInvite, invite.Invitecol_prod, " +
                                    "invite.Sum_Invite, invite.Date, status.status, market.Market, product.Product, delivery.Delivery, unit.unit" +
                                    " from snab_mag.invite inner join snab_mag.market on market.idMarket = invite.Market_idMarket" +
                                    " inner join snab_mag.product on product.idProduct = invite.idP" +
                                    " inner join snab_mag.delivery on delivery.idDelivery = invite.idD" +
                                    " inner join snab_mag.unit on unit.idU = product.idU" +
                                    " inner join snab_mag.status on status.idStatus = invite.idStatus where market.Market = " + dm + marketBox.getValue() + dm + " and" +
                                    " `Date` between " + dm + Date_ot.getValue() + dm +
                                    " and " + dm + Date_do.getValue() + dm + ";");
                            while (resultSet.next()) {
                                setTableI(resultSet.getInt(1), (resultSet.getString(2) + " " + resultSet.getString(9)),
                                        resultSet.getFloat(3), resultSet.getString(4), resultSet.getString(5),
                                        resultSet.getString(6), resultSet.getString(7), resultSet.getString(8));
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                        alert(Alert.AlertType.ERROR, "Ошибка", "Неккоректный интервал дат");
                    }
                }
                else {
                    Invite.clear();
                    try {
                        connect = new Connect();
                        Statement statement = connect.getConnection().createStatement();
                        final ResultSet resultSet = statement.executeQuery("select invite.idInvite, invite.Invitecol_prod, " +
                                "invite.Sum_Invite, invite.Date, status.status, market.Market, product.Product, delivery.Delivery, unit.unit" +
                                " from snab_mag.invite inner join snab_mag.market on market.idMarket = invite.Market_idMarket" +
                                " inner join snab_mag.product on product.idProduct = invite.idP" +
                                " inner join snab_mag.delivery on delivery.idDelivery = invite.idD" +
                                " inner join snab_mag.unit on unit.idU = product.idU" +
                                " inner join snab_mag.status on status.idStatus = invite.idStatus where market.Market = " + dm + marketBox.getValue() + dm + " and" +
                                " `Date` > " + dm + Date_ot.getValue() + dm + ";");
                        while (resultSet.next()) {
                            setTableI(resultSet.getInt(1), (resultSet.getString(2) + " " + resultSet.getString(9)),
                                    resultSet.getFloat(3), resultSet.getString(4), resultSet.getString(5),
                                    resultSet.getString(6), resultSet.getString(7), resultSet.getString(8));
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            else if (Date_do.getValue() != null) {
                Invite.clear();

                try {
                    connect = new Connect();
                    Statement statement = connect.getConnection().createStatement();
                    final ResultSet resultSet = statement.executeQuery("select invite.idInvite, invite.Invitecol_prod, " +
                            "invite.Sum_Invite, invite.Date, status.status, market.Market, product.Product, delivery.Delivery, unit.unit" +
                            " from snab_mag.invite inner join snab_mag.market on market.idMarket = invite.Market_idMarket" +
                            " inner join snab_mag.product on product.idProduct = invite.idP" +
                            " inner join snab_mag.delivery on delivery.idDelivery = invite.idD" +
                            " inner join snab_mag.unit on unit.idU = product.idU" +
                            " inner join snab_mag.status on status.idStatus = invite.idStatus where market.Market = " + dm + marketBox.getValue() + dm + " and" +
                            " `Date` < " + dm + Date_do.getValue() + dm + ";");
                    while (resultSet.next()) {
                        setTableI(resultSet.getInt(1), (resultSet.getString(2) + " " + resultSet.getString(9)),
                                resultSet.getFloat(3), resultSet.getString(4), resultSet.getString(5),
                                resultSet.getString(6), resultSet.getString(7), resultSet.getString(8));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } else {
                alert(Alert.AlertType.ERROR, "Ошибка", "Введите интервал дат");
            }
        }
        else {
            if (Date_ot.getValue() != null) {
                if (Date_do.getValue() != null) {
                    if (Date_ot.getValue().isBefore(Date_do.getValue())) {

                        Invite.clear();
                        try {
                            connect = new Connect();
                            Statement statement = connect.getConnection().createStatement();
                            final ResultSet resultSet = statement.executeQuery("select invite.idInvite, invite.Invitecol_prod, " +
                                    "invite.Sum_Invite, invite.Date, status.status, market.Market, product.Product, delivery.Delivery, unit.unit" +
                                    " from snab_mag.invite inner join snab_mag.market on market.idMarket = invite.Market_idMarket" +
                                    " inner join snab_mag.product on product.idProduct = invite.idP" +
                                    " inner join snab_mag.delivery on delivery.idDelivery = invite.idD" +
                                    " inner join snab_mag.unit on unit.idU = product.idU" +
                                    " inner join snab_mag.status on status.idStatus = invite.idStatus where `Date` between " + dm + Date_ot.getValue() + dm +
                                    " and " + dm + Date_do.getValue() + dm + ";");
                            while (resultSet.next()) {
                                setTableI(resultSet.getInt(1), (resultSet.getString(2) + " " + resultSet.getString(9)),
                                        resultSet.getFloat(3), resultSet.getString(4), resultSet.getString(5),
                                        resultSet.getString(6), resultSet.getString(7), resultSet.getString(8));
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    } else {
                        alert(Alert.AlertType.ERROR, "Ошибка", "Неккоректный интервал дат");
                    }
                } else {
                    Invite.clear();
                    try {
                        connect = new Connect();
                        Statement statement = connect.getConnection().createStatement();
                        final ResultSet resultSet = statement.executeQuery("select invite.idInvite, invite.Invitecol_prod, " +
                                "invite.Sum_Invite, invite.Date, status.status, market.Market, product.Product, delivery.Delivery, unit.unit" +
                                " from snab_mag.invite inner join snab_mag.market on market.idMarket = invite.Market_idMarket" +
                                " inner join snab_mag.product on product.idProduct = invite.idP" +
                                " inner join snab_mag.delivery on delivery.idDelivery = invite.idD" +
                                " inner join snab_mag.unit on unit.idU = product.idU" +
                                " inner join snab_mag.status on status.idStatus = invite.idStatus where `Date` > " + dm + Date_ot.getValue() + dm + ";");
                        while (resultSet.next()) {
                            setTableI(resultSet.getInt(1), (resultSet.getString(2) + " " + resultSet.getString(9)),
                                    resultSet.getFloat(3), resultSet.getString(4), resultSet.getString(5),
                                    resultSet.getString(6), resultSet.getString(7), resultSet.getString(8));
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            } else if (Date_do.getValue() != null) {
                Invite.clear();

                try {
                    connect = new Connect();
                    Statement statement = connect.getConnection().createStatement();
                    final ResultSet resultSet = statement.executeQuery("select invite.idInvite, invite.Invitecol_prod, " +
                            "invite.Sum_Invite, invite.Date, status.status, market.Market, product.Product, delivery.Delivery, unit.unit" +
                            " from snab_mag.invite inner join snab_mag.market on market.idMarket = invite.Market_idMarket" +
                            " inner join snab_mag.product on product.idProduct = invite.idP" +
                            " inner join snab_mag.delivery on delivery.idDelivery = invite.idD" +
                            " inner join snab_mag.unit on unit.idU = product.idU" +
                            " inner join snab_mag.status on status.idStatus = invite.idStatus where `Date` < " + dm + Date_do.getValue() + dm + ";");
                    while (resultSet.next()) {
                        setTableI(resultSet.getInt(1), (resultSet.getString(2) + " " + resultSet.getString(9)),
                                resultSet.getFloat(3), resultSet.getString(4), resultSet.getString(5),
                                resultSet.getString(6), resultSet.getString(7), resultSet.getString(8));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } else {
                alert(Alert.AlertType.ERROR, "Ошибка", "Введите интервал дат");
            }
        }

    }

    @FXML
    private void clear(){
        if (Objects.equals(Login.getRoleDB(), "Магазин")) {
            tableI.getItems().clear();
        }
        else {
            tableI.getItems().clear();
            marketBox.setValue(null);
        }
        Date_ot.setValue(null);
        Date_do.setValue(null);
    }

    @FXML
    private void accounting (){
        Stage Stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/Invite/accountingw.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage.setTitle("Учёт заявок");
        Stage.setScene(new Scene(root, 445.0, 333.0));
        Stage.setResizable(false);
        Stage.centerOnScreen();
        Stage.show();
    }

    @FXML
    private void exportExcel () {
        reportInvite(Invite);
    }

    @FXML
    private void exportWord() throws IOException {
        reportInviteWord(Invite);
    }

    @FXML
    private void print () throws IOException {
        reportInviteWordPrint(Invite);
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

}
