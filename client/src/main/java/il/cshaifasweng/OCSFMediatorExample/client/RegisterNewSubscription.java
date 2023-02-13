/**
 * Sample Skeleton for 'RegisterNewSubscription.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

public class RegisterNewSubscription {

    @FXML // fx:id="CarNumberTF"
    private TextField CarNumberTF; // Value injected by FXMLLoader

    @FXML // fx:id="CustomerIdTF"
    private TextField CustomerIdTF; // Value injected by FXMLLoader

    @FXML // fx:id="REgisterBtn"
    private Button REgisterBtn; // Value injected by FXMLLoader

    @FXML // fx:id="RegularHoursTF"
    private TextField RegularHoursTF; // Value injected by FXMLLoader

    @FXML // fx:id="RegularParkingTF"
    private TextField RegularParkingTF; // Value injected by FXMLLoader

    @FXML // fx:id="StartingDateTF"
    private DatePicker StartingDateTF;

    @FXML // fx:id="SubscriptionTypeMenue"
    private MenuButton SubscriptionTypeMenue; // Value injected by FXMLLoader

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
    void REgisterBtn(ActionEvent event) {
        String CustomerID = CustomerIdTF.getText();
        String CarLicense = CarNumberTF.getText();
        String StartingDate = StartingDateTF.toString();
        String RegularParking = RegularParkingTF.getText();
        String RegularParkingTime = RegularHoursTF.getText();
        String ParkingType = SubscriptionTypeMenue.getTypeSelector().toString();

        //Send Data TO SERVER!!!
        Message msg = new Message("Register New Subscriber");
        msg.setObject1(ParkingType);
        msg.setObject2(CustomerID);
        msg.setObject3(StartingDate);
        msg.setObject4(RegularParking);
        msg.setObject5(RegularParkingTime);
        msg.setObject6(ParkingType);

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
    void RegularHoursTF(ActionEvent event) {
        String input = RegularParkingTF.getText();
        switch (input.length()) {
            case 2:
                RegularParkingTF.setText(input + ":");
                break;
            case 5:
                RegularParkingTF.setText(input + " - ");
                break;
            case 10:
                RegularParkingTF.setText(input + ":");
                break;
            case 12:
                //Check If Input is Legal
            {
                char ConvertedInput[] = input.toCharArray();
                for (int i = 0; i < ConvertedInput.length; i++) {
                    if (ConvertedInput[i] <= 'z' && ConvertedInput[i] >= 'a') {
                        RegularParkingTF.setText("");
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

        SubscriptionTypeMenue = new MenuButton("Options: ", null,menuItem1,menuItem2,menuItem3);
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
