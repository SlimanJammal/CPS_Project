/**
 * Sample Skeleton for 'ocasionalParking.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;

public class OcasionalParking {

    @FXML // fx:id="IdNumberTF"
    private TextField IdNumberTF; // Value injected by FXMLLoader

    @FXML
    private TextField parkingNameTF;
    @FXML // fx:id="SubmitBTN"
    private Button SubmitBTN; // Value injected by FXMLLoader

    @FXML
    private Button backbtn;

    @FXML // fx:id="emailTF"
    private TextField emailTF; // Value injected by FXMLLoader

    @FXML // fx:id="leavingTimeTF"
    private TextField leavingTimeTF; // Value injected by FXMLLoader

    @FXML // fx:id="licensePlateTF"
    private TextField licensePlateTF; // Value injected by FXMLLoader

    @FXML
    void IdNumberTF(ActionEvent event) {

    }

    @FXML
    void SubmitBTN(ActionEvent event) {
        try {
            Message message = new Message("OcasionalParking");
            message.setObject1(IdNumberTF.getText());
            message.setObject2(licensePlateTF.getText());
            message.setObject3(emailTF.getText());
            message.setObject4(leavingTimeTF.getText());
            if(parkingNameTF.getText().equals("Hanmal") || parkingNameTF.getText().equals("German_Colony") ||parkingNameTF.getText().equals("Bat-Galim")) {
                message.setObject5(parkingNameTF.getText());

                // Id number is saved as a string in object1
                // license plate number is saved as a string in object2
                // email is saved as a string in object3
                // leaving time is saved as a string in object4

                IdNumberTF.clear();
                licensePlateTF.clear();
                emailTF.clear();
                leavingTimeTF.clear();
                parkingNameTF.clear();
                SimpleClient.getClient().sendToServer(message);

            } else {

             parkingNameTF.setText("Wrong ParkingLot Name");
            }

            //send message to the server containing the new prices

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    @FXML
    void emailTF(ActionEvent event) {

    }

    @FXML
    void leavingTimeTF(ActionEvent event) {

    }

    @FXML
    void licensePlateTF(ActionEvent event) {

    }
    @FXML
    void backbtn(ActionEvent event) {
        try {
            App.setRoot("cpsKiosk");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Subscribe
    public void HandleMessagesFromServer_(OcasionalEvent event){
        Message msg = event.getMessage();

        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.WARNING,
                    event.getMessage().getObject1().toString());
            alert.show();
        });
    }
    @FXML
    void initialize() {
        EventBus.getDefault().register(this);

    }

}
