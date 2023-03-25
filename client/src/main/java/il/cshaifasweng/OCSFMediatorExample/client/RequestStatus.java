/**
 * Sample Skeleton for 'RequestStatus.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;

public class RequestStatus {

    @FXML // fx:id="CarNumberTF"
    private TextField CarNumberTF; // Value injected by FXMLLoader

    @FXML // fx:id="CustomerIdTF"
    private TextField CustomerIdTF; // Value injected by FXMLLoader

    @FXML // fx:id="SendRequestBtn"
    private Button SendRequestBtn; // Value injected by FXMLLoader

    @FXML // fx:id="Previous Window"
    private Button PreviousButton; // Value injected by FXMLLoader

    @FXML
    private Label OutputLabel;

    @FXML
    void CarIDTextChange(KeyEvent event) {
        InputValidation test = new InputValidation();
        if(test.CarIDValidation(CarNumberTF.getText().toString()))
        {
            OutputLabel.setText("");

        }
        else
        {
            if(!CarNumberTF.getText().toString().equals(""))
            {

                OutputLabel.setTextFill(Color.web("#ff0000"));
                OutputLabel.setText("Car ID is not valid! Please try again.");
                OutputLabel.setVisible(true);
                CarNumberTF.setText("");
            }
            else
            {
                OutputLabel.setTextFill(Color.web("#ff0000"));
                OutputLabel.setText("Car ID is empty, please fill it up!");
                OutputLabel.setVisible(true);

                CarNumberTF.setText("");
            }
        }
    }

    @FXML
    void CustomerIDTextChange(KeyEvent event) {
        InputValidation test = new InputValidation();
        if(test.CustomerIDValidation(CustomerIdTF.getText().toString()))
        {
            OutputLabel.setText("");

        }
        else
        {
            if(!CarNumberTF.getText().toString().equals(""))
            {

                OutputLabel.setTextFill(Color.web("#ff0000"));
                OutputLabel.setText("Customer ID is not valid! Please try again.");
                OutputLabel.setVisible(true);
                CustomerIdTF.setText("");
            }
            else
            {
                OutputLabel.setTextFill(Color.web("#ff0000"));
                OutputLabel.setText("Customer ID is empty, please fill it up!");
                OutputLabel.setVisible(true);

                CustomerIdTF.setText("");
            }
        }
    }

    @FXML
    private TableView DataTable;

    @FXML
    void SendRequestBtn(ActionEvent event) {
        //Deactivate Parking Spot
        String CustomerId = CustomerIdTF.getText().toString();
        String CarNumber = CarNumberTF.getText().toString();

        //Reset TextBox's Values!
        {
            CustomerIdTF.setText("");
            CarNumberTF.setText("");
        }
        //send message to server ...
        Message msg = new Message("Check Client Spot Status");
        msg.setObject1(CustomerId);
        msg.setObject2(CarNumber);
        try
        {
            SimpleClient.getClient().sendToServer(msg);
            //?SimpleClient.getClient().handleMessageFromServer(msg);
        }
        catch (IOException e)
        {
            // TODO Auto-generated  catch block
            e.printStackTrace();
        }

    }

    @FXML
    void OnPreviousButton(ActionEvent event) throws IOException {
        App.setRoot(DataSingleton.getInstance().getCaller());

    }

    @Subscribe
    public void serverMcatch(RequestStatus event){

    }

    //_________________________________________________________________________________________________________________
    //_________________________________________________________________________________________________________________
    //___________________________________________ Assistnat Functions _________________________________________________
    //assistant function that detect input of type string is number or not...
    public boolean IsNumber(String Input)
    {
        for(char a : Input.toCharArray())
        {
            if(a < '0' || a > '9')
                return false;
        }
        return true;
    }




    @FXML
    void initialize() {
        EventBus.getDefault().register(this);

    }
}
