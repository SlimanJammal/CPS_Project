/**
 * Sample Skeleton for 'RegisterNewSubscription.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;
import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.time.LocalDate;


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

    @FXML
    private TextField DateTF;

    @FXML
    private DatePicker StartDate;

    @FXML
    private RadioButton Radio1;

    @FXML
    private RadioButton Radio2;

    @FXML
    private RadioButton Radio3;

    @FXML
    private Button PreviousWindow;

    @FXML
    private Label LabelOutput;

    @FXML
    void CarNumberTF(ActionEvent event) {
        if(!isNumeric(CarNumberTF.getText())){
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION,
                        "Car Number Should contain numbers only !"
                );
                alert.show();
            });
            CarNumberTF.clear();
        }

    }

    @FXML
    void CustomerIdTF(ActionEvent event) {
        if(!isNumeric(CustomerIdTF.getText())){
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION,
                        "your ID Number Should contain numbers only !"
                );
                alert.show();
            });
            CustomerIdTF.clear();
        }
    }


    @FXML
    void StartDate(ActionEvent event) {

        LocalDate ld = StartDate.getValue();
        if(ld != null &&ld.isBefore(LocalDate.now())){
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION,
                        "plese pick a date that is not in the past."
                );
                alert.show();
            });
            StartDate.setValue(null);
        }
    }


    @FXML
    void RegisterBtn(ActionEvent event) {
        //REgisterBtn.setVisible(false);
        String CustomerID = CustomerIdTF.getText();
        String CarLicense = CarNumberTF.getText();
        LocalDate StartingDate = StartDate.getValue();
        String Email = RegularParkingTF.getText(); // this is the email adress of the customer
        String EntranceParkingTime = EntranceHourTF.getText();
        String DepartureParkingTime = DepatureHourTF.getText();
        String ParkingType = "";

        if(Radio1.isSelected())
        {
            ParkingType = "Single Monthly Subscription";
        }
        else if(Radio2.isSelected())
        {
            ParkingType = "Multi Monthly Subscription";
        }
        else if(Radio3.isSelected())
        {
            ParkingType = "Fully Subscription";
        }

        //Send Data TO SERVER!!!
        Message msg = new Message("Register New Subscriber");
        //Object #1 - Subscriber Type
        //Object #2 - Customer ID
        //Object #3 - Starting Date
        //Object #4 - Car Number
        //Object #5 - Entrance Hour	; HH:MM
        //Object #6 - Departure Hour ; HH:MM
        //Object #7 - Email
        msg.setObject1(ParkingType);
        msg.setObject2(CustomerID);
        msg.setObject3(StartingDate);
        msg.setObject4(CarLicense);
        msg.setObject5(EntranceParkingTime);
        msg.setObject6(DepartureParkingTime);
        msg.setObject7(Email);

        try
        {
            SimpleClient.getClient().sendToServer(msg);
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        RegisterBtn.setVisible(false);
    }

    @FXML
    void OnEntranceHourTF(ActionEvent event) {
        /*String input = EntranceHourTF.getText();
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

        }*/
    }

    @FXML
    void OnDepatureHourTF(ActionEvent event) {
        /*//FORMAT INPUT HH:MM
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

        }*/
    }

    @FXML
    void RegularParkingTF(ActionEvent event) {
       /* String RegularParking_ID = RegularParkingTF.getText();
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
        //check If It is available by asking the server to check the parking slot*/
    }



    @FXML
    void OnPreviousWindow(ActionEvent event)
    {
        try {
            PreviousWindow.setVisible(false);
            App.setRoot(DataSingleton.getInstance().getCaller());

        } catch (IOException e) {
            e.printStackTrace();
        }

        //Go To Previous Window!
    }

    @FXML
    void OnRadioButton(ActionEvent event)
    {
        CarNumberTF.setText("");
    }


    public void OnStartingDate(ActionEvent event) {
    }
    @Subscribe
    public void msgHandler(RegisterNewSubscriptionEvent event){
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION,
                    event.getMsg().getMessage()
            );
            alert.show();
        });
    }
    void initialize() {

        EventBus.getDefault().register(this);

    }
    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
}
