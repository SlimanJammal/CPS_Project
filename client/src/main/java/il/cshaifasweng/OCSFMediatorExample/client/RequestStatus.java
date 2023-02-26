/**
 * Sample Skeleton for 'RequestStatus.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.greenrobot.eventbus.EventBus;

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
    private TableView DataTable;

    @FXML
    void CarNumberTF(ActionEvent event) {
        String Input = CarNumberTF.getText().toString();
        if(IsNumber(Input))
        {

        }
        else
        {
            CarNumberTF.setText("Only digits allowed!");
            try
            {
                wait(2000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            CarNumberTF.setText("");
        }
    }

    @FXML
    void CustomerIdTF(ActionEvent event) {
        String Input = CustomerIdTF.getText().toString();
        if(IsNumber(Input))
        {

        }
        else
        {
            CustomerIdTF.setText("Only digits allowed!");
            try
            {
                wait(2000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            CustomerIdTF.setText("");
        }
    }

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
