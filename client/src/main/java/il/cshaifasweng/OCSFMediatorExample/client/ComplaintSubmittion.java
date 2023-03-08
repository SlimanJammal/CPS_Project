/**
 * Sample Skeleton for 'ComplaintSubmittion.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Comp;
import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class ComplaintSubmittion {

    @FXML // fx:id="ComplaintTA"
    private TextArea ComplaintTA; // Value injected by FXMLLoader


    @FXML
    private TextField emailtf;
    @FXML // fx:id="CustomerIdTF"
    private TextField CustomerIdTF; // Value injected by FXMLLoader

    @FXML // fx:id="SubmitBtn"
    private Button SubmitBtn; // Value injected by FXMLLoader

    @FXML // fx:id="BackBtn"
    private Button BackBtn; // Value injected by FXMLLoader

    @FXML
    void emailtf(ActionEvent event) {

    }
    @FXML
    void CustomerIdTF(ActionEvent event) {

    }

    @FXML
    void SubmitBtn(ActionEvent event) {
        try {
//            System.out.println("kelhom 4nole wma gedro");
            Message message = new Message("Complaint");
            Comp newComplaint = new Comp(CustomerIdTF.getText(),ComplaintTA.getText(),emailtf.getText());
            message.setObject1(newComplaint);
            CustomerIdTF.clear();
            ComplaintTA.clear();
            emailtf.clear();

            //send message to the server containing the new prices
            SimpleClient.getClient().sendToServer(message);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @FXML
    void BackBtn(ActionEvent event){
        try{

            App.setRoot(DataSingleton.getInstance().getCaller());
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Subscribe
    public void complaintHandleFromServer(ComplaintEvent event){

        Message msg = event.getMessage();
//        Alert alert = new Alert(Alert.AlertType.WARNING,
//                msg.getMessage());
//        alert.show();
        ComplaintTA.setText("complaint sent");
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ComplaintTA.clear();

    }
    @FXML
    void initialize() {
        EventBus.getDefault().register(this);

    }

}
