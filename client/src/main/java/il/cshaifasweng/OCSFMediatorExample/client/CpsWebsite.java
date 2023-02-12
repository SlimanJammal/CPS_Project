/**
 * Sample Skeleton for 'cpsWebsite.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;

public class CpsWebsite {

    @FXML // fx:id="CustomerBtn"
    private Button CustomerBtn; // Value injected by FXMLLoader

    @FXML // fx:id="ID_LOGIN_TF"
    private TextField ID_LOGIN_TF; // Value injected by FXMLLoader

    @FXML // fx:id="LICENSE_LOGIN_TF"
    private TextField LICENSE_LOGIN_TF; // Value injected by FXMLLoader

    @FXML // fx:id="ManagerBtn"
    private Button ManagerBtn; // Value injected by FXMLLoader

    @FXML // fx:id="PW_LOGIN_TF"
    private PasswordField PW_LOGIN_TF; // Value injected by FXMLLoader

    @FXML // fx:id="RenewSubsBtn"
    private Button RenewSubsBtn; // Value injected by FXMLLoader

    @FXML // fx:id="ReserveParkingBtn"
    private Button ReserveParkingBtn; // Value injected by FXMLLoader

    @FXML // fx:id="SUBSNUM_LOGIN_TF"
    private PasswordField SUBSNUM_LOGIN_TF; // Value injected by FXMLLoader

    @FXML // fx:id="WorkerBtn"
    private Button WorkerBtn; // Value injected by FXMLLoader

    @FXML // fx:id="checkReservBtn"
    private Button checkReservBtn; // Value injected by FXMLLoader

    @FXML // fx:id="createNewSubsBtn"
    private Button createNewSubsBtn; // Value injected by FXMLLoader

    @FXML
    void CustomerBtn(ActionEvent event) throws IOException {
        App.setRoot("ocasionalParking.fxml");
    }

    @FXML
    void ID_LOGIN_TF(ActionEvent event) {

    }

    @FXML
    void LICENSE_LOGIN_TF(ActionEvent event) {

    }

    @FXML
    void ManagerBtn(ActionEvent event) throws IOException {
        Message msg= new Message("loginManager");
        msg.setID(ID_LOGIN_TF.getId());
        msg.setPassword(PW_LOGIN_TF.getText());
        SimpleClient.getClient().sendToServer(msg);
    }
    @Subscribe
    public void allowManager(loginManagerEvent allowing) throws IOException {
         App.setRoot("ParkingManager.fxml"); // todo put in braces manager scene
    }


    @FXML
    void PW_LOGIN_TF(ActionEvent event) {

    }

    @FXML
    void RenewSubsBtn(ActionEvent event) throws IOException {
        Message msg= new Message("RenewSub");
        msg.setLicenesPlate(LICENSE_LOGIN_TF.getText());
        msg.setSubNum(SUBSNUM_LOGIN_TF.getText());
        SimpleClient.getClient().sendToServer(msg);
    }
    @Subscribe
    void setRenewSubsSuccess(SubRenewEvent event)
    {
        Alert alert = new Alert(Alert.AlertType.WARNING,
                String.format("Message: Sub renewed"));
        alert.show();
    }

    @FXML
    void ReserveParkingBtn(ActionEvent event) throws IOException {
        App.setRoot(".fxml");//todo check reservation window
    }

    @FXML
    void SUBSNUM_LOGIN_TF(ActionEvent event) {

    }

    @FXML
    void checkReservBtn(ActionEvent event) throws IOException {
        App.setRoot(".fxml");//todo check reservation window
    }

    @FXML
    void createNewSubsBtn(ActionEvent event) throws IOException {
        App.setRoot("RegisterNewSubscription.fxml");
    }

}
