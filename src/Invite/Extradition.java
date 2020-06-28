package Invite;

import Database.Connect;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.poi.xwpf.usermodel.*;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Extradition {
    private Connect connect = null;
    private ObservableList<Invite> Invite = FXCollections.observableArrayList();
    final char dm = (char) 34;
    private Invite _invite;

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
    private Label info;

    @FXML
    private Button extraditionB;

    @FXML
    private void initialize(){
        info.setText("");
        marketBox.getItems().clear();
        Invite.clear();

        marketBox.getItems().add("Все");
        try {
            connect = new Connect();
            Statement statement = connect.getConnection().createStatement();
            final ResultSet resultSet = statement.executeQuery("select `Market` from snab_mag.market");
            while (resultSet.next()) {
                String market = resultSet.getString("Market");
                if (!marketBox.getItems().contains(market)){
                    marketBox.getItems().addAll(market);
                }
            }

            final ResultSet resultSet1 = statement.executeQuery("select invite.idInvite, invite.Invitecol_prod, " +
                    "invite.Sum_Invite, invite.Date, status.status, market.Market, product.Product, delivery.Delivery, unit.unit" +
                    " from snab_mag.invite inner join snab_mag.market on market.idMarket = invite.Market_idMarket" +
                    " inner join snab_mag.product on product.idProduct = invite.idP" +
                    " inner join snab_mag.delivery on delivery.idDelivery = invite.idD" +
                    " inner join snab_mag.unit on unit.idU = product.idU" +
                    " inner join snab_mag.status on status.idStatus = invite.idStatus where status.status  =" + dm + "Не выполнена"+ dm);
            while (resultSet1.next()) {
                setTableI(resultSet1.getInt(1), (resultSet1.getString(2) + " " + resultSet1.getString(9)),
                        resultSet1.getFloat(3), resultSet1.getString(4), resultSet1.getString(5),
                        resultSet1.getString(6), resultSet1.getString(7), resultSet1.getString(8));
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }

        TableView.TableViewSelectionModel<Invite> selectionModel = tableI.getSelectionModel();
        selectionModel.selectedItemProperty().addListener(new ChangeListener<Invite>(){

            @Override
            public void changed(ObservableValue<? extends Invite> observableValue, Invite i, Invite invite) {
                if(invite != null) {
                    info.setText("Выдать " + dm + invite.getTov() + dm + " в количестве " + invite.getCol() + " магазину " + dm + invite.getMarket() + dm + "?");
                    _invite = invite;
                }
            }
        });

        marketBox.setOnAction(event -> infoInvite());
    }

    @FXML
    private void extradition() throws IOException {
        int base = 0, colMB = 0, colI = 0, idP = 0, idM = 0, g =0 ;
        float price = 0;

        if (_invite != null) {
            try {
                connect = new Connect();
                Statement statement = connect.getConnection().createStatement();
                final ResultSet resultSet = statement.executeQuery("select product.Productcol_base, invite.Invitecol_prod, product.price, invite.idP, invite.Market_idMarket" +
                        " from snab_mag.invite" +
                        " inner join snab_mag.product on product.idProduct = invite.idP" +
                        " where idInvite = " + _invite.getId());
                while (resultSet.next()) {
                    base = resultSet.getInt(1);
                    colI = resultSet.getInt(2);
                    price = resultSet.getFloat(3);
                    idP = resultSet.getInt(4);
                    idM = resultSet.getInt(5);
                }
            } catch (SQLException e) {

                e.printStackTrace();
            }

            if (colI <= base){
                base -= colI;

                try {
                    connect = new Connect();
                    Statement statement = connect.getConnection().createStatement();
                    final ResultSet resultSet = statement.executeQuery("select Col_prod_market_base from snab_mag.market_base where Product_idProduct = " + idP + " and Market_idMarket = " + idM);
                    while (resultSet.next()) {
                        colMB = resultSet.getInt(1);
                        g = 1;
                    }

                    colMB += colI;
                    statement.execute("update snab_mag.invite set idStatus = 2 where idInvite = " + _invite.getId());
                    statement.execute("update snab_mag.product set Productcol_base = " + base + " where idProduct = " + idP);

                    if (g !=0 ) {
                        statement.execute("update snab_mag.market_base set Col_prod_market_base = " + colMB + " where Product_idProduct = " + idP + " and Market_idMarket = " + idM);
                    }
                    else{
                        statement.execute("insert into snab_mag.market_base (Col_prod_market_base, Product_idProduct, Market_idMarket, idU) values ( "
                        + colMB + ", " + idP + ", " + idM + ", (select idU from snab_mag.product where idProduct = " + idP + "))");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                extraditionprint(_invite, price);

                alert(Alert.AlertType.INFORMATION, "Успешно", "Накладная отправлена на печать, так же она сохранена в папку Waybill");
                initialize();
            }
            else alert(Alert.AlertType.ERROR, "Ошибка", "На базе не достаточно товара для осуществления выдачи" + _invite.getCol());


        }
        else alert(Alert.AlertType.ERROR, "Ошибка", "Выберите заявку");
    }

    private void infoInvite(){
        info.setText("");
        Invite.clear();
        if (marketBox.getValue() == "Все"){
            try {
                connect = new Connect();
                Statement statement = connect.getConnection().createStatement();
                final ResultSet resultSet = statement.executeQuery("select invite.idInvite, invite.Invitecol_prod, " +
                        "invite.Sum_Invite, invite.Date, status.status, market.Market, product.Product, delivery.Delivery, unit.unit" +
                        " from snab_mag.invite inner join snab_mag.market on market.idMarket = invite.Market_idMarket" +
                        " inner join snab_mag.product on product.idProduct = invite.idP" +
                        " inner join snab_mag.delivery on delivery.idDelivery = invite.idD" +
                        " inner join snab_mag.unit on unit.idU = product.idU " +
                        " inner join snab_mag.status on status.idStatus = invite.idStatus where status.status =" + dm + "Не выполнена" + dm);
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
                        " inner join snab_mag.status on status.idStatus = invite.idStatus where `Market`=" + dm + marketBox.getValue() + dm + " and status.status =" + dm + "Не выполнена" + dm);
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

    private void extraditionprint(Invite invite, float price) throws IOException {
        String currentTime;
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        currentTime = sdf.format(date);

        String output = "Waybill/Накладная №" + invite.getId() + ".docx";

        XWPFDocument document = new XWPFDocument();

        XWPFParagraph dateT = document.createParagraph();
        dateT.setAlignment(ParagraphAlignment.RIGHT);

        XWPFRun dateTRun = dateT.createRun();
        dateTRun.setText("от " + currentTime);
        dateTRun.setColor("000000");
        dateTRun.setFontFamily("Times New Roman");
        dateTRun.setFontSize(14);

        XWPFParagraph title = document.createParagraph();
        title.setAlignment(ParagraphAlignment.CENTER);

        XWPFRun titleRun = title.createRun();
        titleRun.setText("Накладная № " + invite.getId());
        titleRun.setColor("000000");
        titleRun.setBold(true);
        titleRun.setFontFamily("Times New Roman");
        titleRun.setFontSize(18);

        XWPFParagraph subTitle = document.createParagraph();
        subTitle.setAlignment(ParagraphAlignment.LEFT);

        XWPFRun subTitleRun = subTitle.createRun();
        subTitleRun.setText("Кому: ");
        subTitleRun.setColor("000000");
        subTitleRun.setFontFamily("Times New Roman");
        subTitleRun.setFontSize(14);

        XWPFRun subTitleRu = subTitle.createRun();
        subTitleRu.setText(invite.getMarket() + "                                                                            _");
        subTitleRu.setColor("000000");
        subTitleRu.setFontFamily("Times New Roman");
        subTitleRu.setFontSize(14);
        subTitleRu.setUnderline(UnderlinePatterns.SINGLE);
        subTitleRu.addBreak();

        XWPFParagraph subTitle2 = document.createParagraph();
        subTitle2.setAlignment(ParagraphAlignment.LEFT);

        XWPFRun subTitleRun2 = subTitle.createRun();
        subTitleRun2.setText("От: ");
        subTitleRun2.setColor("000000");
        titleRun.setBold(true);
        subTitleRun2.setFontFamily("Times New Roman");
        subTitleRun2.setFontSize(14);

        XWPFRun subTitleRu2 = subTitle.createRun();
        subTitleRu2.setText("База товаров                                                                             _");
        subTitleRu2.setColor("000000");
        subTitleRu2.setFontFamily("Times New Roman");
        subTitleRu2.setFontSize(14);
        subTitleRu2.setUnderline(UnderlinePatterns.SINGLE);
        subTitleRu2.addBreak();

        XWPFParagraph subTitle3 = document.createParagraph();
        subTitle3.setAlignment(ParagraphAlignment.LEFT);

        XWPFRun subTitleRun3 = subTitle.createRun();
        subTitleRun3.setText("Грузоперевозчик: ");
        subTitleRun3.setColor("000000");
        subTitleRun3.setFontFamily("Times New Roman");
        subTitleRun3.setFontSize(14);

        XWPFRun subTitleRu3 = subTitle.createRun();
        subTitleRu3.setText(invite.getDelivery() + "                                                                    _");
        subTitleRu3.setColor("000000");
        subTitleRu3.setFontFamily("Times New Roman");
        subTitleRu3.setFontSize(14);
        subTitleRu3.setUnderline(UnderlinePatterns.SINGLE);

        XWPFTable table = document.createTable();
        table.getCTTbl().addNewTblPr().addNewTblW().setW(BigInteger.valueOf(9500));

        XWPFTableRow tableRow0 = table.getRow(0);
        tableRow0.getCell(0).setText("№");

        XWPFTableCell tableCell1 = tableRow0.addNewTableCell();
        tableCell1.setText("Наименование товара");

        XWPFTableCell tableCell2 = tableRow0.addNewTableCell();
        tableCell2.setText("Количество");

        XWPFTableCell tableCell3 = tableRow0.addNewTableCell();
        tableCell3.setText("Цена за единицу(р.)");

        XWPFTableCell tableCell4 = tableRow0.addNewTableCell();
        tableCell4.setText("Сумма (р.)");

        XWPFTableRow tableRow1 = table.createRow();
        tableRow1.getCell(0).setText("1");
        tableRow1.getCell(1).setText(invite.getTov());
        tableRow1.getCell(2).setText(invite.getCol());
        tableRow1.getCell(3).setText(String.valueOf(price));
        tableRow1.getCell(4).setText(String.valueOf(invite.getSum()));

        XWPFParagraph passed = document.createParagraph();
        passed.setAlignment(ParagraphAlignment.LEFT);

        XWPFRun passedRun = passed.createRun();
        passedRun.addBreak();
        passedRun.addBreak();
        passedRun.setText("Сдал___________________________   ");
        passedRun.setColor("000000");
        passedRun.setFontFamily("Times New Roman");
        passedRun.setFontSize(14);

        XWPFParagraph accepted = document.createParagraph();
        accepted.setAlignment(ParagraphAlignment.RIGHT);

        XWPFRun acceptedRun = passed.createRun();
        acceptedRun.setText("Принял___________________________");
        acceptedRun.setColor("000000");
        acceptedRun.setFontFamily("Times New Roman");
        acceptedRun.setFontSize(14);

        FileOutputStream out = new FileOutputStream(output);
        document.write(out);
        out.close();

        Desktop.getDesktop().print(new File(output));
    }
}
