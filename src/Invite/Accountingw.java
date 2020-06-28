package Invite;

import Database.Connect;
import Login.Login;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class Accountingw extends ExportAccounting {
    private Connect connect = null;
    private ObservableList<AccountingConst> Accounting = FXCollections.observableArrayList();
    final char dm = (char) 34;


    @FXML
    private ComboBox<String> inf;

    @FXML
    private MenuItem exportW;

    @FXML
    private MenuItem exportE;

    @FXML
    private MenuItem print;

    @FXML
    private TableView<AccountingConst> tableI;

    @FXML
    private TableColumn<Invite, String> market;

    @FXML
    private TableColumn<Invite, String> kol;

    @FXML
    private TableColumn<Invite, Float> sum;

    @FXML
    private Button searchB;

    @FXML
    private TextField men;

    @FXML
    private TextField bol;

    @FXML
    private Label lmen;

    @FXML
    private Label lbol;

    @FXML
    private void initialize() {
        Accounting.clear();
        lmen.setVisible(false);
        lbol.setVisible(false);
        men.setVisible(false);
        bol.setVisible(false);
        men.setText("");
        bol.setText("");

        String[] str = {"Всем", "Выполненным", "Не выполненным"};

        inf.getItems().addAll(str);

        inf.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                lmen.setVisible(true);
                lbol.setVisible(true);
                men.setVisible(true);
                bol.setVisible(true);
            }
        });
    }

    @FXML
    private void search() {
        if (inf.getValue() != null) {
            if (Objects.equals(inf.getValue(), "Всем")) {
                information(0);
            } else if (Objects.equals(inf.getValue(), "Выполненным")) {
                information(2);
            } else if (Objects.equals(inf.getValue(), "Не выполненным")) {
                information(1);
            }
        } else alert(Alert.AlertType.ERROR, "Ошибка", "Выберите по какому условию произвести учёт");
    }

    @FXML
    private void exportExcel() {
          reportInvite(Accounting);
    }

    @FXML
    private void exportWord() throws IOException {
         reportInviteWord(Accounting);
    }

    @FXML
    private void print() throws IOException {
          reportInviteWordPrint(Accounting);
    }

    private void setTableI(String _market, String _col, float _sum) {
        Accounting.add(new AccountingConst(_market, _col, _sum));

        market.setCellValueFactory(new PropertyValueFactory<Invite, String>("market"));
        kol.setCellValueFactory(new PropertyValueFactory<Invite, String>("col"));
        sum.setCellValueFactory(new PropertyValueFactory<Invite, Float>("sum"));

        tableI.setItems(Accounting);
    }

    private void alert(Alert.AlertType alertType, String title, String text) {
        Alert alert = new Alert(alertType);

        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(text);

        alert.showAndWait();
    }

    private void information(int status) {
        Accounting.clear();
        if (status == 0) {
            if (Objects.equals(Login.getRoleDB(), "Администратор") || Objects.equals(Login.getRoleDB(), "Склад")) {

                if (!Objects.equals(men.getText(), "") && men.getText().matches("^[0-9]+$")) {
                    if (!Objects.equals(bol.getText(), "") && bol.getText().matches("^[0-9]+$")) {

                        try {
                            connect = new Connect();
                            Statement statement = connect.getConnection().createStatement();
                            final ResultSet resultSet = statement.executeQuery("select market.Market, count(invite.idInvite), sum(invite.Sum_Invite)" +
                                    " from snab_mag.invite inner join snab_mag.market on market.idMarket = invite.Market_idMarket" +
                                    " group by Market_idMarket having sum(invite.Sum_Invite) > " + men.getText() + " and sum(invite.Sum_Invite) < " + bol.getText());
                            while (resultSet.next()) {
                                setTableI(resultSet.getString(1), resultSet.getString(2), resultSet.getFloat(3));
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            connect = new Connect();
                            Statement statement = connect.getConnection().createStatement();
                            final ResultSet resultSet = statement.executeQuery("select market.Market, count(invite.idInvite), sum(invite.Sum_Invite)" +
                                    " from snab_mag.invite inner join snab_mag.market on market.idMarket = invite.Market_idMarket" +
                                    " group by Market_idMarket having sum(invite.Sum_Invite)> " + men.getText());
                            while (resultSet.next()) {
                                setTableI(resultSet.getString(1), resultSet.getString(2), resultSet.getFloat(3));
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                } else if (!Objects.equals(bol.getText(), "") && bol.getText().matches("^[0-9]+$")) {
                    try {
                        connect = new Connect();
                        Statement statement = connect.getConnection().createStatement();
                        final ResultSet resultSet = statement.executeQuery("select market.Market, count(invite.idInvite), sum(invite.Sum_Invite)" +
                                " from snab_mag.invite inner join snab_mag.market on market.idMarket = invite.Market_idMarket" +
                                " group by Market_idMarket having sum(invite.Sum_Invite)< " + bol.getText());
                        while (resultSet.next()) {
                            setTableI(resultSet.getString(1), resultSet.getString(2), resultSet.getFloat(3));
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        connect = new Connect();
                        Statement statement = connect.getConnection().createStatement();
                        final ResultSet resultSet = statement.executeQuery("select market.Market, count(invite.idInvite), sum(invite.Sum_Invite)" +
                                " from snab_mag.invite inner join snab_mag.market on market.idMarket = invite.Market_idMarket" +
                                " group by Market_idMarket");
                        while (resultSet.next()) {
                            setTableI(resultSet.getString(1), resultSet.getString(2), resultSet.getFloat(3));
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

            }
            else {
                if (!Objects.equals(men.getText(), "") && men.getText().matches("^[0-9]+$")) {
                    if (!Objects.equals(bol.getText(), "") && bol.getText().matches("^[0-9]+$")) {

                        try {
                            connect = new Connect();
                            Statement statement = connect.getConnection().createStatement();
                            final ResultSet resultSet = statement.executeQuery("select market.Market, count(invite.idInvite), sum(invite.Sum_Invite)" +
                                    " from snab_mag.invite inner join snab_mag.market on market.idMarket = invite.Market_idMarket" +
                                    " where market.Market = " + dm + Login.getNameDB() + dm + " group by Market_idMarket having sum(invite.Sum_Invite) > " + men.getText() + " and sum(invite.Sum_Invite) < " + bol.getText());
                            while (resultSet.next()) {
                                setTableI(resultSet.getString(1), resultSet.getString(2), resultSet.getFloat(3));
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            connect = new Connect();
                            Statement statement = connect.getConnection().createStatement();
                            final ResultSet resultSet = statement.executeQuery("select market.Market, count(invite.idInvite), sum(invite.Sum_Invite)" +
                                    " from snab_mag.invite inner join snab_mag.market on market.idMarket = invite.Market_idMarket" +
                                    " where market.Market = " + dm + Login.getNameDB() + dm + " group by Market_idMarket having sum(invite.Sum_Invite)> " + men.getText());
                            while (resultSet.next()) {
                                setTableI(resultSet.getString(1), resultSet.getString(2), resultSet.getFloat(3));
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                } else if (!Objects.equals(bol.getText(), "") && bol.getText().matches("^[0-9]+$")) {
                    try {
                        connect = new Connect();
                        Statement statement = connect.getConnection().createStatement();
                        final ResultSet resultSet = statement.executeQuery("select market.Market, count(invite.idInvite), sum(invite.Sum_Invite)" +
                                " from snab_mag.invite inner join snab_mag.market on market.idMarket = invite.Market_idMarket" +
                                " where market.Market = " + dm + Login.getNameDB() + dm + " group by Market_idMarket having sum(invite.Sum_Invite)< " + bol.getText());
                        while (resultSet.next()) {
                            setTableI(resultSet.getString(1), resultSet.getString(2), resultSet.getFloat(3));
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        connect = new Connect();
                        Statement statement = connect.getConnection().createStatement();
                        final ResultSet resultSet = statement.executeQuery("select market.Market, count(invite.idInvite), sum(invite.Sum_Invite)" +
                                " from snab_mag.invite inner join snab_mag.market on market.idMarket = invite.Market_idMarket" +
                                " where market.Market = " + dm + Login.getNameDB() + dm + " group by Market_idMarket");
                        while (resultSet.next()) {
                            setTableI(resultSet.getString(1), resultSet.getString(2), resultSet.getFloat(3));
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        else {
            if (Objects.equals(Login.getRoleDB(), "Администратор") || Objects.equals(Login.getRoleDB(), "Склад")) {

                if (!Objects.equals(men.getText(), "") && men.getText().matches("^[0-9]+$")) {
                    if (!Objects.equals(bol.getText(), "") && bol.getText().matches("^[0-9]+$")) {

                        try {
                            connect = new Connect();
                            Statement statement = connect.getConnection().createStatement();
                            final ResultSet resultSet = statement.executeQuery("select market.Market, count(invite.idInvite), sum(invite.Sum_Invite)" +
                                    " from snab_mag.invite inner join snab_mag.market on market.idMarket = invite.Market_idMarket" +
                                    " where invite.idStatus = " + status + " group by Market_idMarket having sum(invite.Sum_Invite) > "
                                    + men.getText() + " and sum(invite.Sum_Invite) < " + bol.getText());
                            while (resultSet.next()) {
                                setTableI(resultSet.getString(1), resultSet.getString(2), resultSet.getFloat(3));
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            connect = new Connect();
                            Statement statement = connect.getConnection().createStatement();
                            final ResultSet resultSet = statement.executeQuery("select market.Market, count(invite.idInvite), sum(invite.Sum_Invite)" +
                                    " from snab_mag.invite inner join snab_mag.market on market.idMarket = invite.Market_idMarket" +
                                    " where invite.idStatus = " + status + " group by Market_idMarket having sum(invite.Sum_Invite)> " + men.getText());
                            while (resultSet.next()) {
                                setTableI(resultSet.getString(1), resultSet.getString(2), resultSet.getFloat(3));
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                } else if (!Objects.equals(bol.getText(), "") && bol.getText().matches("^[0-9]+$")) {
                    try {
                        connect = new Connect();
                        Statement statement = connect.getConnection().createStatement();
                        final ResultSet resultSet = statement.executeQuery("select market.Market, count(invite.idInvite), sum(invite.Sum_Invite)" +
                                " from snab_mag.invite inner join snab_mag.market on market.idMarket = invite.Market_idMarket" +
                                " where invite.idStatus = " + status + " group by Market_idMarket having sum(invite.Sum_Invite)< " + bol.getText());
                        while (resultSet.next()) {
                            setTableI(resultSet.getString(1), resultSet.getString(2), resultSet.getFloat(3));
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        connect = new Connect();
                        Statement statement = connect.getConnection().createStatement();
                        final ResultSet resultSet = statement.executeQuery("select market.Market, count(invite.idInvite), sum(invite.Sum_Invite)" +
                                " from snab_mag.invite inner join snab_mag.market on market.idMarket = invite.Market_idMarket" +
                                " where invite.idStatus = " + status + " group by Market_idMarket");
                        while (resultSet.next()) {
                            setTableI(resultSet.getString(1), resultSet.getString(2), resultSet.getFloat(3));
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

            }
            else {
                if (!Objects.equals(men.getText(), "") && men.getText().matches("^[0-9]+$")) {
                    if (!Objects.equals(bol.getText(), "") && bol.getText().matches("^[0-9]+$")) {

                        try {
                            connect = new Connect();
                            Statement statement = connect.getConnection().createStatement();
                            final ResultSet resultSet = statement.executeQuery("select market.Market, count(invite.idInvite), sum(invite.Sum_Invite)" +
                                    " from snab_mag.invite inner join snab_mag.market on market.idMarket = invite.Market_idMarket" +
                                    " where market.Market = " + dm + Login.getNameDB() + dm + " and invite.idStatus = " + status +
                                    " group by Market_idMarket having sum(invite.Sum_Invite) > " + men.getText() + " and sum(invite.Sum_Invite) < " + bol.getText());
                            while (resultSet.next()) {
                                setTableI(resultSet.getString(1), resultSet.getString(2), resultSet.getFloat(3));
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            connect = new Connect();
                            Statement statement = connect.getConnection().createStatement();
                            final ResultSet resultSet = statement.executeQuery("select market.Market, count(invite.idInvite), sum(invite.Sum_Invite)" +
                                    " from snab_mag.invite inner join snab_mag.market on market.idMarket = invite.Market_idMarket" +
                                    " where market.Market = " + dm + Login.getNameDB() + dm + " and invite.idStatus = " + status +
                                    " group by Market_idMarket having sum(invite.Sum_Invite)> " + men.getText());
                            while (resultSet.next()) {
                                setTableI(resultSet.getString(1), resultSet.getString(2), resultSet.getFloat(3));
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                } else if (!Objects.equals(bol.getText(), "") && bol.getText().matches("^[0-9]+$")) {
                    try {
                        connect = new Connect();
                        Statement statement = connect.getConnection().createStatement();
                        final ResultSet resultSet = statement.executeQuery("select market.Market, count(invite.idInvite), sum(invite.Sum_Invite)" +
                                " from snab_mag.invite inner join snab_mag.market on market.idMarket = invite.Market_idMarket" +
                                " where market.Market = " + dm + Login.getNameDB() + dm + " and invite.idStatus = " + status +
                                " group by Market_idMarket having sum(invite.Sum_Invite)< " + bol.getText());
                        while (resultSet.next()) {
                            setTableI(resultSet.getString(1), resultSet.getString(2), resultSet.getFloat(3));
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        connect = new Connect();
                        Statement statement = connect.getConnection().createStatement();
                        final ResultSet resultSet = statement.executeQuery("select market.Market, count(invite.idInvite), sum(invite.Sum_Invite)" +
                                " from snab_mag.invite inner join snab_mag.market on market.idMarket = invite.Market_idMarket" +
                                " where market.Market = " + dm + Login.getNameDB() + dm + " and invite.idStatus = " + status +
                                " group by Market_idMarket");
                        while (resultSet.next()) {
                            setTableI(resultSet.getString(1), resultSet.getString(2), resultSet.getFloat(3));
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        men.setText("");
        bol.setText("");
    }
}
