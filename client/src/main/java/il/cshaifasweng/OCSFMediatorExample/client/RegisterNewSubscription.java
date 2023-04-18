/**
 * Sample Skeleton for 'RegisterNewSubscription.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;
import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

//Previous Buttons doesn't work just yet
public class RegisterNewSubscription implements Initializable {

    static String CarsList = "";
    static int firstRun = 0;

    @FXML // fx:id="AddCarButton"
    private Button AddCarButton; // Value injected by FXMLLoader

    @FXML // fx:id="CarList"
    private Label CarListLabel; // Value injected by FXMLLoader

    @FXML // fx:id="CarNumberTF"
    private TextField CarNumberTF; // Value injected by FXMLLoader

    @FXML // fx:id="CustomerIdTF"
    private TextField CustomerIdTF; // Value injected by FXMLLoader

    @FXML // fx:id="Date"
    private DatePicker Date; // Value injected by FXMLLoader

    @FXML // fx:id="DepatureHourTF"
    private TextField DepatureHourTF; // Value injected by FXMLLoader

    @FXML // fx:id="EntranceHourTF"
    private TextField EntranceHourTF; // Value injected by FXMLLoader

    @FXML // fx:id="LabelOutput"
    private Label LabelOutput; // Value injected by FXMLLoader

    @FXML // fx:id="ParkingLotOptions"
    private ComboBox<String> ParkingLotOptions; // Value injected by FXMLLoader

    @FXML // fx:id="PreviousWindow"
    private Button PreviousWindow; // Value injected by FXMLLoader

    @FXML // fx:id="Radio1"
    private RadioButton Radio1; // Value injected by FXMLLoader

    @FXML // fx:id="Radio2"
    private RadioButton Radio2; // Value injected by FXMLLoader

    @FXML // fx:id="Radio3"
    private RadioButton Radio3; // Value injected by FXMLLoader

    @FXML // fx:id="RegisterBtn"
    private Button RegisterBtn; // Value injected by FXMLLoader

    @FXML // fx:id="RegularParkingTF"
    private TextField EmailTF; // Value injected by FXMLLoader

    @FXML // fx:id="RemoveCarButton"
    private Button RemoveCarButton; // Value injected by FXMLLoader

    @FXML // fx:id="SubscriptionType"
    private ToggleGroup SubscriptionType; // Value injected by FXMLLoader

    @FXML
    void Datepick(ActionEvent event) {
        LocalDate now = LocalDate.now();
        if(Date.getValue().isBefore(now)){
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION,
                        "Please use a date that is not in the past"
                );
                alert.show();
            });
            Date.setValue(now);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        ObservableList<String> options = FXCollections.observableArrayList("German_Colony", "Hanmal", "Bat-Galim");
        ParkingLotOptions.setItems(options);

        AddCarButton.setVisible(false);
        RemoveCarButton.setVisible(false);

    }

    @FXML
    void RegisterBtn(ActionEvent event) {
        //REgisterBtn.setVisible(false);
        String CustomerID = CustomerIdTF.getText();
        String CarLicense = CarNumberTF.getText();
        LocalDate StartingDate = Date.getValue();
        String Email = EmailTF.getText(); // this is the email adress of the customer
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
        //Object #3 - Starting Date ; YYYY:MM:DD
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
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            if(
               isnumeric((String) msg.getObject2() )&&
               isnumeric((String) msg.getObject4()) )
         {
                System.out.println("fsdfsdfdfgdaffgdgdfgsdgdfgdfgd");
            SimpleClient.getClient().sendToServer(msg);
        }
            System.out.println("##################################");
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        RegisterBtn.setVisible(false);
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
        if(Radio2.isSelected())
        {
            AddCarButton.setVisible(true);
            RemoveCarButton.setVisible(true);
        }
        else
        {
            AddCarButton.setVisible(false);
            RemoveCarButton.setVisible(false);

            CarsList = "";
            CarNumberTF.setText("");
            LabelOutput.setTextFill(Color.web("#ff0000"));
            LabelOutput.setText("Please, set Car ID again!");

        }
        CarNumberTF.setText("");
    }


    //Input Validiation!!
    //=====================================================================================
    @FXML
    void AddToList(ActionEvent event){
        String input = CarNumberTF.getText();
        String [] listt = CarsList.split(",");
        for (String number : listt){
            if(number.equals(input))
            {
                LabelOutput.setTextFill(Color.web("#0000ff"));
                //LabelOutput.setText("Car Has been removed from list!");
                LabelOutput.setText("The Car ID is already in list!");

                return;
            }
        }

        if(firstRun == 0)
        {
            CarsList = input;
            firstRun++;
        }
        else
        {
            CarsList = CarsList + "," + input;
        }
        CarListLabel.setText("The Current CarList Is " + CarsList);
        CarNumberTF.setText("");
        LabelOutput.setTextFill(Color.web("#00ff00"));
        LabelOutput.setText("Car Has been added!");

    }

    @FXML
    void RemoveFromList(ActionEvent event) {
        String output = "";
        String input = CarNumberTF.getText();
        String [] listt = CarsList.split(",");
        boolean found = false;
        for (String number : listt){
            if(number.equals(input))
            {

                found = true;
                break;
            }else{
                output += number+",";
            }
        }
        if(!found){
            LabelOutput.setTextFill(Color.web("#0000ff"));
            //LabelOutput.setText("Car Has been removed from list!");
            LabelOutput.setText("The Car ID is not in list!");
        }
        CarsList = output.substring(0,output.length()-1);


        LabelOutput.setTextFill(Color.web("#0000ff"));
        CarNumberTF.setText("");
        CarListLabel.setText("The Current CarList Is " + CarsList);
    }

    @FXML
    void ArrivalTimeTextChange(KeyEvent event) {
        InputValidation test = new InputValidation();
        if(test.TimeValidation(EntranceHourTF.getText().toString()))
        {
            LabelOutput.setText("");

        }
        else
        {
            if(!EntranceHourTF.getText().toString().equals(""))
            {
                LabelOutput.setTextFill(Color.web("#ff0000"));
                LabelOutput.setText("Entrance Time is not valid! Please try again.");
            }
            else
            {
                LabelOutput.setTextFill(Color.web("#ff0000"));
                LabelOutput.setText("Entrance Time is empty, please fill it up!");
            }
        }
    }

    @FXML
    void CarIDTextChange(KeyEvent event) {
        InputValidation test = new InputValidation();
        if(test.CarIDValidation(CarNumberTF.getText().toString()))
        {
            LabelOutput.setText("");

        }
        else
        {
            if(!CarNumberTF.getText().toString().equals(""))
            {
                LabelOutput.setTextFill(Color.web("#ff0000"));
                LabelOutput.setText("Car ID is not valid! Please try again.");
                CarNumberTF.setText("");
            }
            else
            {
                LabelOutput.setTextFill(Color.web("#ff0000"));
                LabelOutput.setText("Car ID is empty, please fill it up!");
                CarNumberTF.setText("");
            }
        }
    }

    @FXML
    void CustomerIDTextChange(KeyEvent event) {
        InputValidation test = new InputValidation();
        if(test.CustomerIDValidation(CustomerIdTF.getText().toString()))
        {
            LabelOutput.setText("");

        }
        else
        {
            if(!CustomerIdTF.getText().toString().equals(""))
            {
                LabelOutput.setTextFill(Color.web("#ff0000"));
                LabelOutput.setText("Customer ID is not valid! Please try again.");
                CustomerIdTF.setText("");
            }
            else
            {
                LabelOutput.setTextFill(Color.web("#ff0000"));
                LabelOutput.setText("Customer ID is empty, please fill it up!");
                CustomerIdTF.setText("");
            }
        }
    }

    @FXML
    void DepartureTimeTextChange(KeyEvent event) {
        InputValidation test = new InputValidation();
        if(test.TimeValidation(DepatureHourTF.getText().toString()))
        {
            LabelOutput.setText("");

        }
        else
        {
            if(!DepatureHourTF.getText().toString().equals(""))
            {
                LabelOutput.setTextFill(Color.web("#ff0000"));
                LabelOutput.setText("Departure Time is not valid! Please try again.");
            }
            else
            {
                LabelOutput.setTextFill(Color.web("#ff0000"));
                LabelOutput.setText("Departure Time is empty, please fill it up!");
            }
        }
    }

    @FXML
    void EmailTextChange(KeyEvent event) {
        InputValidation test = new InputValidation();
        if(test.EmailValidation(EmailTF.getText().toString()))
        {
            LabelOutput.setText("");

        }
        else
        {
            if(!EmailTF.getText().toString().equals(""))
            {
                LabelOutput.setTextFill(Color.web("#ff0000"));
                LabelOutput.setText("Emailis not valid! Please try again.");
            }
            else
            {
                LabelOutput.setTextFill(Color.web("#ff0000"));
                LabelOutput.setText("Email is empty, please fill it up!");
            }
        }
    }
    //=============================================================================================

    public boolean IsNumber(String Input)
    {
        for(char a : Input.toCharArray())
        {
            if(a < '0' || a > '9')
                return false;
        }
        return true;
    }


    boolean isnumeric(String str)
    {
        for(int i=0;i<str.length();i++)
        {
            if(str.charAt(i)<'0'||str.charAt(i)>'9')
            {
                return false;
            }
        }
        return true;
    }
    boolean isMail(String str)
    {
        if(str.endsWith(".com")&&(str.contains("@gmail")||str.contains("@hotmail")))
        {
            return true;
        }
        return false;
    }


}
