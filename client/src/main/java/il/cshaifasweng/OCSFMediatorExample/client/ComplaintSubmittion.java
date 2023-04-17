/**
 * Sample Skeleton for 'ComplaintSubmittion.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Comp;
import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
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

    @FXML // fx:id="CustomerErrorLabel"
    private Label CustomerErrorLabel; // Value injected by FXMLLoader

    @FXML
    void SubmitBtn(ActionEvent event) {

//            System.out.println("kelhom 4nole wma gedro");
        Message message = new Message("Complaint");
        Comp newComplaint = new Comp(CustomerIdTF.getText(), ComplaintTA.getText(), emailtf.getText());
            /*for(int i=0;i<CustomerIdTF.getLength();i++)
            {
                if(CustomerIdTF.getText().charAt(i)<'0'||CustomerIdTF.getText().charAt(i)>'9')
                {
                    CustomerIdTF.setText("invalidInput");
                }
            }*/
        message.setObject1(newComplaint);

            /*if(     (!emailtf.getText().contains("@")) ||
                    !(!emailtf.getText().endsWith("hotmail.com")&&
                    !emailtf.getText().endsWith("gmail.com")))
            {
                emailtf.setText("invalidInput");
            }*/
        CustomerIdTF.clear();
        ComplaintTA.clear();
        emailtf.clear();

        try {
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

    @FXML
    void CustomerEmailTextChange(KeyEvent event) {
        InputValidation test = new InputValidation();
        if(test.EmailValidation(emailtf.getText().toString()))
        {
            CustomerErrorLabel.setText("");

        }
        else
        {
            if(!emailtf.getText().toString().equals(""))
            {
                CustomerErrorLabel.setText("Email is not valid! Please try again.");
            }
            else
            {
                CustomerErrorLabel.setText("Email is empty, please fill it up!");
            }
        }
    }

    @FXML
    void CustomerIDTextChange(KeyEvent event) {
        InputValidation test = new InputValidation();
        if(test.CustomerIDValidation(CustomerIdTF.getText().toString()))
        {
            CustomerErrorLabel.setText("");

        }
        else
        {
            if(!CustomerIdTF.getText().toString().equals(""))
            {
                CustomerErrorLabel.setText("Customer ID is not valid! Please try again.");
                CustomerIdTF.setText("");
            }
            else
            {
                CustomerErrorLabel.setText("Customer ID is empty, please fill it up!");
                CustomerIdTF.setText("");
            }
        }
    }

}
