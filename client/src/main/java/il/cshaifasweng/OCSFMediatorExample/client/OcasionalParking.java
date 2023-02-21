/**
 * Sample Skeleton for 'ocasionalParking.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Complaints;
import il.cshaifasweng.OCSFMediatorExample.entities.Message;
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

    @FXML // fx:id="SubmitBTN"
    private Button SubmitBTN; // Value injected by FXMLLoader

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
//            System.out.println("kelhom 4nole wma gedro");
            Message message = new Message("OcasionalParking");
            message.setObject1(IdNumberTF.getText());
            message.setObject2(licensePlateTF.getText());
            message.setObject3(emailTF.getText());
            message.setObject4(leavingTimeTF.getText());

            // Id number is saved as a string in object1
            // license plate number is saved as a string in object2
            // email is saved as a string in object3
            // leaving time is saved as a string in object4

            IdNumberTF.clear();
            licensePlateTF.clear();
            emailTF.clear();
            leavingTimeTF.clear();


            //send message to the server containing the new prices
            SimpleClient.getClient().sendToServer(message);

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

    @Subscribe
    public void HandleMessagesFromServer(OcasionalEvent event){
        Message msg = event.getMessage();

//        System.out.println(msg.getMessage());
        Alert alert = new Alert(Alert.AlertType.WARNING,
                msg.getMessage());
        alert.show();

    }
    @FXML
    void initialize() {
        EventBus.getDefault().register(this);

    }

}
