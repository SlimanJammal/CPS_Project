/**
 * Sample Skeleton for 'cpsWebsite.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.ParkingWorker;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;

public class CpsWebsite {

    @FXML
    private Button backbtn;

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
        DataSingleton data = DataSingleton.getInstance();
        data.setCaller("cpsWebsite");
        App.setRoot("ocasionalParking");
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
        int permission =(int) allowing.getMsg().getObject3();

        if(allowing.getMsg().getObject1().toString().equals("success") && permission ==  1) {

            DataSingleton data = DataSingleton.getInstance();
            data.setDataName("ParkingManager");
            ParkingManager parkingManager = (ParkingManager) allowing.getMsg().getObject1();
            data.setData(parkingManager);

            data.setCaller("cpsWebsite");
            App.setRoot("ParkingManger");

        } else  if(allowing.getMsg().getObject1().toString().equals("success") && permission ==  0){

            DataSingleton data = DataSingleton.getInstance();
            data.setDataName("RegionalManager");
            RegionalManager regionalManager = (RegionalManager) allowing.getMsg().getObject1();
            data.setData(regionalManager);

            data.setCaller("cpsWebsite");
            App.setRoot("RegionalManager");

        }else {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION,
                        "UserName or PassWord Wrong"
                );
                alert.show();
            });

        }
    }

    @FXML
    void WorkerBtn(ActionEvent event) throws IOException {
        Message msg= new Message("loginEmployee");
        msg.setID(ID_LOGIN_TF.getId());
        msg.setPassword(PW_LOGIN_TF.getText());
        SimpleClient.getClient().sendToServer(msg);
    }
    @Subscribe
    public void allowWorker(loginWorkerEvent allowing) throws IOException {
        if(allowing.getMsg().getObject1().toString().equals("success")) {
            DataSingleton data = DataSingleton.getInstance();
            data.setDataName("ParkingWorker");
            ParkingWorker parkingWorker = (ParkingWorker) allowing.getMsg().getObject1();
            data.setData(parkingWorker);

            data.setCaller("cpsWebsite");
            App.setRoot("EmployeeWindow");
        } else {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION,
                        "UserName or PassWord Wrong"
                );
                alert.show();
            });

        }
    }


    @FXML
    void PW_LOGIN_TF(ActionEvent event) {

    }

    @FXML
    void RenewSubsBtn(ActionEvent event) throws IOException {
        Message msg= new Message("RenewSub");
        msg.setLicensePlate(LICENSE_LOGIN_TF.getText());
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
        DataSingleton data = DataSingleton.getInstance();
        data.setCaller("cpsWebsite");
        App.setRoot("OneTimeParkingOrder");//todo check reservation window
    }

    @FXML
    void SUBSNUM_LOGIN_TF(ActionEvent event) {

    }

    @FXML
    void checkReservBtn(ActionEvent event) throws IOException {
        DataSingleton data = DataSingleton.getInstance();
        data.setCaller("cpsWebsite");
        App.setRoot("RequestStatus");//todo check reservation window
    }

    @FXML
    void createNewSubsBtn(ActionEvent event) throws IOException {
        DataSingleton data = DataSingleton.getInstance();
        data.setCaller("cpsWebsite");
        App.setRoot("RegisterNewSubscription");
    }

    @FXML
    void backbtn(ActionEvent event) {
        try {
            App.setRoot("MainWindow");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
