package Login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginMarket {
    @FXML
    private Button exitB;

    @FXML
    private void tov_base(ActionEvent event) {
        Stage Stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/Product/Tov_Base.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage.setTitle("Товары на базе");
        Stage.setScene(new Scene(root, 600, 417));
        Stage.setResizable(false);
        Stage.centerOnScreen();
        Stage.show();
    }

    @FXML
    private void market(ActionEvent event) {
        Stage Stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/Market/market.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage.setTitle("Магазины");
        Stage.setScene(new Scene(root, 600, 418));
        Stage.setResizable(false);
        Stage.centerOnScreen();
        Stage.show();
    }

    @FXML
    private void marketbase(ActionEvent event) {
        Stage Stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/Market/product_market.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage.setTitle("Товары в магазинах");
        Stage.setScene(new Scene(root, 600, 400));
        Stage.setResizable(false);
        Stage.centerOnScreen();
        Stage.show();
    }

    @FXML
    private void inventor(ActionEvent event){
        Stage Stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/Product/inventory.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage.setTitle("Ивентаризация");
        Stage.setScene(new Scene(root, 432.0, 355.0));
        Stage.setResizable(false);
        Stage.centerOnScreen();
        Stage.show();
    }

    @FXML
    private void deliv(ActionEvent event){
        Stage Stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/Delivery/delivery.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage.setTitle("Способы доставки");
        Stage.setScene(new Scene(root, 392.0, 356.0));
        Stage.setResizable(false);
        Stage.centerOnScreen();
        Stage.show();
    }

    @FXML
    private void invite(ActionEvent actionEvent) {
        Stage Stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/Invite/invite.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage.setTitle("Завки");
        Stage.setScene(new Scene(root, 768.0, 487.0));
        Stage.setResizable(false);
        Stage.centerOnScreen();
        Stage.show();
    }

    @FXML
    private void add_invite(ActionEvent actionEvent) {
        Stage Stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/Invite/addInvite.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage.setTitle("Добавить заявку");
        Stage.setScene(new Scene(root, 527.0, 143.0));
        Stage.setResizable(false);
        Stage.centerOnScreen();
        Stage.show();
    }

    @FXML
    private void changeinv(ActionEvent event){
        Stage Stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/Invite/changeInvite.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage.setTitle("Изменение или удаление заявок");
        Stage.setScene(new Scene(root, 776.0, 498.0));
        Stage.setResizable(false);
        Stage.centerOnScreen();
        Stage.show();
    }

    @FXML
    private void exit(ActionEvent actionEvent){
        Stage st = (Stage) exitB.getScene().getWindow();
        st.close();
        Stage Stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/Login/login.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage.setTitle("Авторизация в систему");
        Stage.setScene(new Scene(root, 234.0, 200.0));
        Stage.setResizable(false);
        Stage.centerOnScreen();
        Stage.show();
    }
}
