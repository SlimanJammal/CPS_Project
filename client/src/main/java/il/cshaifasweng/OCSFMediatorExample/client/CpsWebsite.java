/**
 * Sample Skeleton for 'cpsWebsite.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.greenrobot.eventbus.EventBus;
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
        Message msg= new Message("loginManager_WEBSITE");
        msg.setID(ID_LOGIN_TF.getText());
        msg.setPassword(PW_LOGIN_TF.getText());

        SimpleClient.getClient().sendToServer(msg);
    }
    @Subscribe
    public void allowManager(loginManagerWebsitekEvent allowing) throws IOException {
        int permission =(int) allowing.getMsg().getObject3();
        System.out.println("subscribe allow manager website"+permission);
        System.out.println("permission="+permission);

        if(allowing.getMsg().getObject1().toString().equals("success") && permission ==  1) {

            System.out.println("succes login should change window");
            DataSingleton data = DataSingleton.getInstance();
            data.setDataName("ParkingManager");
            il.cshaifasweng.OCSFMediatorExample.entities.ParkingManager parkingManager = (il.cshaifasweng.OCSFMediatorExample.entities.ParkingManager) allowing.getMsg().getObject2();
            data.setData(parkingManager.getid());
//            il.cshaifasweng.OCSFMediatorExample.entities.ParkingManager parkingManager2 = (il.cshaifasweng.OCSFMediatorExample.entities.ParkingManager) DataSingleton.getInstance().getData();
//            System.out.println(parkingManager2.getid());
            data.setCaller("cpsWebsite");
            System.out.println("succes login should change window22");

            App.setRoot("ParkingManager");

        } else  if(allowing.getMsg().getObject1().toString().equals("success") && permission ==  0){

            DataSingleton data = DataSingleton.getInstance();
            data.setDataName("RegionalManager");
            il.cshaifasweng.OCSFMediatorExample.entities.RegionalManager regionalManager = (il.cshaifasweng.OCSFMediatorExample.entities.RegionalManager) allowing.getMsg().getObject2();
            data.setData(regionalManager.getUserID());
            data.setRegionalListOfRequests(allowing.getMsg().getObject4());
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
        Message msg= new Message("loginEmployee_CPS");
        msg.setID(ID_LOGIN_TF.getText());
        msg.setPassword(PW_LOGIN_TF.getText());
        SimpleClient.getClient().sendToServer(msg);
    }
    @Subscribe
    public void allowWorker(loginWorkerWebsiteEvent allowing) throws IOException {
        int perm_lvl = (Integer) allowing.getMsg().getObject3();

        if (allowing.getMsg().getObject1().toString().equals("success") && perm_lvl == 2) {
            DataSingleton data = DataSingleton.getInstance();
            data.setDataName("ParkingWorker");
            il.cshaifasweng.OCSFMediatorExample.entities.ParkingWorker parkingWorker = (il.cshaifasweng.OCSFMediatorExample.entities.ParkingWorker) allowing.getMsg().getObject2();
            data.setData(parkingWorker.getUserID());

            data.setCaller("cpsWebsite");
            App.setRoot("EmployeeWindow");
        } else if(allowing.getMsg().getObject1().toString().equals("success") && perm_lvl == 3){
            DataSingleton data = DataSingleton.getInstance();
            data.setDataName("CustomerService");
            il.cshaifasweng.OCSFMediatorExample.entities.ParkingWorker parkingWorker = (il.cshaifasweng.OCSFMediatorExample.entities.ParkingWorker) allowing.getMsg().getObject2();
            data.setData(parkingWorker.getUserID());

            data.setCaller("cpsWebsite");

            App.setRoot("CustomerService");

        } else {

            ID_LOGIN_TF.setText("access denied");
            PW_LOGIN_TF.clear();

        }
    }

    @FXML
    void PW_LOGIN_TF(ActionEvent event) {

    }

    @FXML
    void RenewSubsBtn(ActionEvent event) throws IOException {
        Message msg= new Message("RenewSub_website");
        msg.setLicensePlate(LICENSE_LOGIN_TF.getText());
        msg.setSubNum(SUBSNUM_LOGIN_TF.getText());
        SimpleClient.getClient().sendToServer(msg);
    }
    @Subscribe
   public void setRenewSubsSuccess(SubRenewEventWebsite event)
    {
//        System.out.println("cps websiter subs");
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION,
                    "Message: Sub renewed"
            );
            alert.show();
        });
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
        App.setRoot("CheckReservation");//todo check reservation window
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

    @FXML
    void initialize() {
        EventBus.getDefault().register(this);

    }

}
