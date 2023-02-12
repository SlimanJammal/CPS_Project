/**
 * Sample Skeleton for 'ComplaintSubmittion.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Complaint;
import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;

public class ComplaintSubmittion {

    @FXML // fx:id="ComplaintTA"
    private TextArea ComplaintTA; // Value injected by FXMLLoader

    @FXML // fx:id="CustomerIdTF"
    private TextField CustomerIdTF; // Value injected by FXMLLoader

    @FXML // fx:id="SubmitBtn"
    private Button SubmitBtn; // Value injected by FXMLLoader

    @FXML
    void CustomerIdTF(ActionEvent event) {

    }

    @FXML
    void SubmitBtn(ActionEvent event) {
        try {
            System.out.println("kelhom 4nole wma gedro");
            Message message = new Message("Complaint");
            Complaint newComplaint = new Complaint(CustomerIdTF.getText(),ComplaintTA.getText());
            message.setObject1(newComplaint);
            CustomerIdTF.clear();
            ComplaintTA.clear();

            //send message to the server containing the new prices
            SimpleClient.getClient().sendToServer(message);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Subscribe
    public void complaintHandleFromServer(ComplaintEvent event){



        Message msg = event.getMessage();



        ComplaintTA.setText((String)event.getMessage().getObject1());
    }
    @FXML
    void initialize() {
        EventBus.getDefault().register(this);

    }

}
