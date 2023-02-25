/**
 * Sample Skeleton for 'RegisterNewSubscription.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;
import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
//Previous Buttons doesn't work just yet
public class RegisterNewSubscription {

    @FXML // fx:id="CarNumberTF"
    private TextField CarNumberTF; // Value injected by FXMLLoader

    @FXML // fx:id="CustomerIdTF"
    private TextField CustomerIdTF; // Value injected by FXMLLoader

    @FXML // fx:id="RegisterBtn"
    private Button RegisterBtn; // Value injected by FXMLLoader

    @FXML // fx:id="EntranceHourTF"
    private TextField EntranceHourTF; // Value injected by FXMLLoader

    @FXML // fx:id="DepatureHourTF"
    private TextField DepatureHourTF; // Value injected by FXMLLoader

    @FXML // fx:id="RegularParkingTF"
    private TextField RegularParkingTF; // Value injected by FXMLLoader

    @FXML // fx:id="StartingDateTF"
    private DatePicker StartingDateTF;

    @FXML // fx:id="SubscriptionTypeMenue"
    private MenuButton SubscriptionTypeMenue; // Value injected by FXMLLoader

    @FXML
    private Button PreviousWindow;

    @FXML
    private Label LabelOutput;

    @FXML
    void CarNumberTF(ActionEvent event) {
        String Car_ID = CarNumberTF.getText();
        //int ID = Integer.parseInt(Customer_ID);
        boolean IsNumber = true;
        for(char a : Car_ID.toCharArray())
        {
            if(a < '0' || a > '9')
                IsNumber = false;
        }
        if(IsNumber)
        {
            //VALID NUMBER
            LabelOutput.setVisible(false);
        }
        else
        {
            LabelOutput.setText("Invalid Car ID! Only digits allowed.");
            LabelOutput.setVisible(true);
        }
    }

    @FXML
    void CustomerIdTF(ActionEvent event) {
        String Customer_ID = CustomerIdTF.getText();
        //int ID = Integer.parseInt(Customer_ID);
        boolean IsNumber = true;
        for(char a : Customer_ID.toCharArray())
        {
            if(a < '0' || a > '9')
                IsNumber = false;
        }
        if(IsNumber)
        {
            //VALID NUMBER
            LabelOutput.setVisible(false);
        }
        else
        {
            LabelOutput.setText("Invalid Customer ID! Only digits allowed.");
            LabelOutput.setVisible(true);
        }
    }

    @FXML
    void RegisterBtn(ActionEvent event) {
        //REgisterBtn.setVisible(false);
        String CustomerID = CustomerIdTF.getText();
        String CarLicense = CarNumberTF.getText();
        String StartingDate = StartingDateTF.toString();
        String RegularParking = RegularParkingTF.getText();
        String EntranceParkingTime = EntranceHourTF.getText();
        String DepartureParkingTime = DepatureHourTF.getText();
        String ParkingType = SubscriptionTypeMenue.getTypeSelector().toString();

        //Send Data TO SERVER!!!
        Message msg = new Message("Register New Subscriber");
        msg.setObject1(ParkingType);
        msg.setObject2(CustomerID);
        msg.setObject3(StartingDate);
        msg.setObject4(RegularParking);
        msg.setObject5(EntranceParkingTime);
        msg.setObject6(DepartureParkingTime);
        msg.setObject7(ParkingType);

        try
        {
            SimpleClient.getClient().sendToServer(msg);
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @FXML
    void OnEntranceHourTF(ActionEvent event) {
        String input = EntranceHourTF.getText();
        switch (input.length()) {
            case 2:
                EntranceHourTF.setText(input + ":");
                break;
            case 5:
                //Check If Input is Legal
            {
                char ConvertedInput[] = input.toCharArray();
                for (int i = 0; i < ConvertedInput.length; i++) {
                    if (ConvertedInput[i] <= 'z' && ConvertedInput[i] >= 'a') {
                        EntranceHourTF.setText("");
                    }
                }
            }
                break;
            default:
                break;

        }
    }

    @FXML
    void OnDepatureHourTF(ActionEvent event) {
        //FORMAT INPUT HH:MM
        String input = DepatureHourTF.getText();
        switch (input.length()) {
            case 2:
                DepatureHourTF.setText(input + ":");
                break;
            case 5:
                //Check If Input is Legal
            {
                char ConvertedInput[] = input.toCharArray();
                for (int i = 0; i < ConvertedInput.length; i++) {
                    if (ConvertedInput[i] <= 'z' && ConvertedInput[i] >= 'a') {
                        DepatureHourTF.setText("");
                    }
                }
            }
            break;
            default:
                break;

        }
    }

    @FXML
    void RegularParkingTF(ActionEvent event) {
        String RegularParking_ID = RegularParkingTF.getText();
        //int ID = Integer.parseInt(Customer_ID);
        boolean IsNumber = true;
        for(char a : RegularParking_ID.toCharArray())
        {
            if(a < '0' || a > '9')
                IsNumber = false;
        }
        if(IsNumber)
        {
            //VALID NUMBER
            LabelOutput.setVisible(false);
        }
        else
        {
            LabelOutput.setText("Invalid Customer ID! Only digits allowed.");
            LabelOutput.setVisible(true);
        }
        //check If It is available by asking the server to check the parking slot
    }

    @FXML
    void OnStartingDate(ActionEvent event) {
    }

    @FXML
    void SubscriptionTypeMenue(ActionEvent event) {
        //VBox root = new VBox();

        MenuItem menuItem1 = new MenuItem("Single Monthly Subscription");
        MenuItem menuItem2 = new MenuItem("Multi Monthly Subscription");
        MenuItem menuItem3 = new MenuItem("Fully Subscription");

        SubscriptionTypeMenue = new MenuButton("Subscription Type: ", null,menuItem1,menuItem2,menuItem3);
        //to prevent glitching - using Multi in Single/Fully
        CarNumberTF.setText("");
    }

    @FXML
    void OnPreviousWindow(ActionEvent event) throws IOException {
        //Go To Previous Window!
        //Data Singleton.... NEED TO ADD
        App.setRoot("mainWindow");
    }

    public boolean IsNumber(String Input)
    {
        for(char a : Input.toCharArray())
        {
            if(a < '0' || a > '9')
                return false;
        }
        return true;
    }

}
