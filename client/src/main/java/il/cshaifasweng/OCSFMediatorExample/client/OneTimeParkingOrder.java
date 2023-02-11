/**
 * Sample Skeleton for 'OneTimeParkingOrder.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

public class OneTimeParkingOrder {

    @FXML // fx:id="CarNumberTF"
    private TextField CarNumberTF; // Value injected by FXMLLoader

    @FXML // fx:id="CheckoutBtn"
    private Button CheckoutBtn; // Value injected by FXMLLoader

    @FXML // fx:id="DesiredParkingTF"
    private TextField DesiredParkingTF; // Value injected by FXMLLoader

    @FXML // fx:id="EmailTF"
    private TextField EmailTF; // Value injected by FXMLLoader

    @FXML // fx:id="EtaTF"
    private TextField EtaTF; // Value injected by FXMLLoader

    @FXML // fx:id="EtdTF"
    private TextField EtdTF; // Value injected by FXMLLoader

    @FXML // fx:id="IdNumberTF"
    private TextField IdNumberTF; // Value injected by FXMLLoader

    private int msgId = 0;
    @FXML
    void CarNumberTF(ActionEvent event) {

    }

    @FXML
    void CheckoutBtn(ActionEvent event) {
//        Message qq = new Message(msgId++, "checkout");
//
//        qq.addToOneTimeParkingOrderInfo(CarNumberTF.getText());
//        qq.addToOneTimeParkingOrderInfo(DesiredParkingTF.getText());
//        qq.addToOneTimeParkingOrderInfo(EmailTF.getText());
//        qq.addToOneTimeParkingOrderInfo(EtaTF.getText());
//        qq.addToOneTimeParkingOrderInfo(EtdTF.getText());
//        qq.addToOneTimeParkingOrderInfo(IdNumberTF.getText());
//        try{
//
//            SimpleClient.getClient().sendToServer(qq);
//
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
    }

    @FXML
    void DesiredParkingTF(ActionEvent event) {

    }

    @FXML
    void EmailTF(ActionEvent event) {

    }

    @FXML
    void EtaTF(ActionEvent event) {

    }

    @FXML
    void EtdTF(ActionEvent event) {

    }

    @FXML
    void IdNumberTF(ActionEvent event) {

    }

}
