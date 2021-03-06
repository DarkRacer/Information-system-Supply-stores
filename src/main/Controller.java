package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;


public class Controller {
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
    private void addmarket(ActionEvent event) {
        Stage Stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/Market/addMarket.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage.setTitle("Добавить магазин");
        Stage.setScene(new Scene(root, 309.0, 132.0));
        Stage.setResizable(false);
        Stage.centerOnScreen();
        Stage.show();
    }

    @FXML
    private void addproduct(ActionEvent event) {
        Stage Stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/Product/addproduct.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage.setTitle("Добавить товар");
        Stage.setScene(new Scene(root, 355.0, 173.0));
        Stage.setResizable(false);
        Stage.centerOnScreen();
        Stage.show();
    }

    @FXML
    private void admission(ActionEvent event) {
        Stage Stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/Product/admissionProduct.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage.setTitle("Поступление товара");
        Stage.setScene(new Scene(root, 392.0, 159.0));
        Stage.setResizable(false);
        Stage.centerOnScreen();
        Stage.show();
    }

    @FXML
    private void changemarket(ActionEvent event){
        Stage Stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/Market/change.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage.setTitle("Изменение или удаление магазина");
        Stage.setScene(new Scene(root, 600.0, 179.0));
        Stage.setResizable(false);
        Stage.centerOnScreen();
        Stage.show();
    }

    @FXML
    private void changetov(ActionEvent event){
        Stage Stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/Product/changeProduct.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage.setTitle("Изменение или удаление товара");
        Stage.setScene(new Scene(root, 651.0, 185.0));
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
    private void changedeliv(ActionEvent event){
        Stage Stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/Delivery/changeDelivery.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage.setTitle("Изменение или удаление доставки");
        Stage.setScene(new Scene(root, 589.0, 167.0));
        Stage.setResizable(false);
        Stage.centerOnScreen();
        Stage.show();
    }

    @FXML
    private void add_Delivery(ActionEvent event){
        Stage Stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/Delivery/addDelivery.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage.setTitle("Добавление доставки");
        Stage.setScene(new Scene(root, 308.0, 98.0));
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
    private void extradition(ActionEvent actionEvent) {
        Stage Stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/Invite/extradition.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage.setTitle("Выдача товара магазину");
        Stage.setScene(new Scene(root, 761.0, 316.0));
        Stage.setResizable(false);
        Stage.centerOnScreen();
        Stage.show();
    }

    @FXML
    private void add_user(ActionEvent actionEvent){
        Stage Stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/Login/addUser.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage.setTitle("Добавление пользователя");
        Stage.setScene(new Scene(root, 302.0, 208.0));
        Stage.setResizable(false);
        Stage.centerOnScreen();
        Stage.show();
    }

    @FXML
    private void change_user(ActionEvent actionEvent){
        Stage Stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/Login/changeUser.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage.setTitle("Изменить или удалить пользователя");
        Stage.setScene(new Scene(root, 684.0, 253.0));
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
