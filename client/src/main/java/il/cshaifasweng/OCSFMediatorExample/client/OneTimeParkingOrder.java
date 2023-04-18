/**
 * Sample Skeleton for 'OneTimeParkingOrder.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import antlr.debug.MessageAdapter;
import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.PricesClass;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

public class OneTimeParkingOrder {

    private int msgId = 0;

    @FXML // fx:id="DesiredParkingTF"
    private TextField DesiredParkingTF; // Value injected by FXMLLoader
    @FXML // fx:id="CancelCarID"
    private TextField CancelCarID; // Value injected by FXMLLoader

    @FXML // fx:id="CancelCustomerID"
    private TextField CancelCustomerID; // Value injected by FXMLLoader

    @FXML // fx:id="CancelErrorLabel"
    private Label CancelEmail; // Value injected by FXMLLoader

    @FXML // fx:id="CancelErrorLabel"
    private Label CancelParkingLot; // Value injected by FXMLLoader

    @FXML // fx:id="CancelErrorLabel"
    private Label CancelErrorLabel; // Value injected by FXMLLoader

    @FXML // fx:id="CarNumberTF"
    private TextField CarNumberTF; // Value injected by FXMLLoader

    @FXML // fx:id="CheckoutBtn"
    private Button CheckoutBtn; // Value injected by FXMLLoader

    @FXML // fx:id="DepartureDateInput"
    private DatePicker DepartureDateInput; // Value injected by FXMLLoader

    @FXML // fx:id="DepartureHourInput"
    private TextField DepartureHourInput; // Value injected by FXMLLoader

    @FXML // fx:id="EmailTF"
    private TextField EmailTF; // Value injected by FXMLLoader

    @FXML // fx:id="EntranceDateInput"
    private DatePicker EntranceDateInput; // Value injected by FXMLLoader

    @FXML // fx:id="EntranceHourInput"
    private TextField EntranceHourInput; // Value injected by FXMLLoader

    @FXML // fx:id="IdNumberTF"
    private TextField IdNumberTF; // Value injected by FXMLLoader

    @FXML // fx:id="Option1"
    private RadioButton Option1; // Value injected by FXMLLoader

    @FXML // fx:id="Option2"
    private RadioButton Option2; // Value injected by FXMLLoader

    @FXML // fx:id="Option3"
    private RadioButton Option3; // Value injected by FXMLLoader

    @FXML // fx:id="ParkingLots"
    private ToggleGroup ParkingLots; // Value injected by FXMLLoader

    @FXML // fx:id="ParkingLots1"
    private ToggleGroup ParkingLots1; // Value injected by FXMLLoader

    @FXML // fx:id="PreOrderErrorLabel"
    private Label CreateErrorLabel; // Value injected by FXMLLoader

    @FXML // fx:id="backBtn"
    private Button backBtn; // Value injected by FXMLLoader

    @FXML // fx:id="cancelRes"
    private Button cancelRes; // Value injected by FXMLLoader


    @FXML
    void datepick(ActionEvent event) {

        LocalDate now = LocalDate.now();
        if(EntranceDateInput.getValue().isBefore(now)){
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION,
                        "Please use a date that is not in the past"
                );
                alert.show();
            });
            EntranceDateInput.setValue(now);
        }
    }
    //============================= Cancel Button ==================================
    @FXML
    void CancelResBtn(ActionEvent event)
    {
        Message msg = new Message("cancelOrder");

        msg.setObject1(CancelCarID.getText());
        msg.setObject2(CancelCustomerID.getText());
//        msg.setObject3(EmailTF.getText());
        //hh:mm-yyyy:mm:dd
        /*{
            //Entrance
            StringBuilder sb1 = new StringBuilder(EntranceDateInput.toString());
            String EntranceDateFormated = sb1.reverse().toString();
            EntranceDateFormated = EntranceDateFormated.replace("/", ".");

            //Departure
            StringBuilder sb2 = new StringBuilder(DepartureDateInput.toString());
            String DepartureDateFormated = sb2.reverse().toString();
            DepartureDateFormated = DepartureDateFormated.replace("/", ".");

            String Entrance = EntranceHourInput.getText() + "-" + EntranceDateFormated;
            String Departure = DepartureHourInput.getText() + "-" + DepartureDateFormated;

            msg.setObject4(Entrance);    //Estimated arrival time
            msg.setObject5(Departure);    //Estimated Departure time
        }*/
        msg.setObject6(IdNumberTF.getText());
        try{
            SimpleClient.getClient().sendToServer(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
        cancelRes.setVisible(false);
    }
    //============================= Create Button ==================================
    @FXML
    void CheckoutBtn(ActionEvent event) {
        Message msg = new Message("OneTimeParkingOrder_Submit");

        Vector<String> fields = new Vector<String>();

        String DesiredParking = "";
        {
            if(Option1.isSelected())
            {
                DesiredParking = "German_Colony";
            }
            else
            {
                if(Option2.isSelected())
                {
                    DesiredParking = "Hanmal";
                }
                else
                {
                    DesiredParking = "Bat-Galim";
                }
            }
        }

        //data is contained in a vector inside Object1
        // the data is stored in the following order
        // 0- car number             3- Eta
        // 1- DesiredParking         4- Etd
        // 2- Email                  5- Id number

//        msg.setObject1(CarNumberTF.getText());
//        msg.setObject1(DesiredParkingTF.getText());
//        msg.setObject1(EmailTF.getText());
//        msg.setObject1(EtaTF.getText());
//        msg.setObject1(EtdTF.getText());
//        msg.setObject1(IdNumberTF.getText());

        //Entrance
        StringBuilder sb1 = new StringBuilder(EntranceDateInput.toString());
        String EntranceDateFormated = sb1.reverse().toString();
        EntranceDateFormated = EntranceDateFormated.replace("/", ".");

        //Departure
        StringBuilder sb2 = new StringBuilder(DepartureDateInput.toString());
        String DepartureDateFormated = sb2.reverse().toString();
        DepartureDateFormated = DepartureDateFormated.replace("/", ".");

        String Entrance = EntranceHourInput.getText() ;
        String Departure = DepartureHourInput.getText() ;

        fields.add(CarNumberTF.getText());
        fields.add(DesiredParking);
        fields.add(EmailTF.getText());
        fields.add(Entrance);
        fields.add(Departure);
        fields.add(IdNumberTF.getText());
        msg.setObject1(fields);
        msg.setObject2(EntranceDateInput.getValue());
        msg.setObject3(DepartureDateInput.getValue());
        System.out.println("submit pre order pressed");
        //send to server
        try{
            if(
                    isnumeric(fields.elementAt(0))
                    && isnumeric(fields.elementAt(5))
                    && isMail(fields.elementAt(2))
            )
            SimpleClient.getClient().sendToServer(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
        CheckoutBtn.setVisible(false);
    }


    @Subscribe
   public void SubmissionAnswer(OneTimeParkingOrderEvent event){
        //todo  warning pop up if everything is okay/not with info
        // if everything is good clear fields
        if(event.getMessage().getObject1().toString().equals("success"))
        /*{
//            Platform.runLater(() -> {
//                Alert alert = new Alert(Alert.AlertType.WARNING,
//                        String.format("Message: %s\n",
//                                "Success")
//                );
//                alert.show();
//            });


        } */
        {
           // CreateErrorLabel.setText("sucess!");
            CreateErrorLabel.setTextFill(Color.web("#00ff00"));
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.WARNING,
                        String.format("Message: %s\n",
                                "order placed successfully! ")
                );
                alert.show();
            });

        }
        else if(event.getMessage().getObject1().toString().equals("fail")){
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.WARNING,
                        String.format("Message: %s\n",
                                "Submission Failed - Wrong Input, Try Again")
                );
                alert.show();
            });
        }
        else if(event.getMessage().getObject1().toString().startsWith("refund ")){
            CarNumberTF.setText(event.getMessage().getObject1().toString());
            DesiredParkingTF.setText(event.getMessage().getObject1().toString());
            EmailTF.setText(event.getMessage().getObject1().toString());
            //EtaTF.setText(event.getMessage().getObject1().toString());
           // EtdTF.setText(event.getMessage().getObject1().toString());
            IdNumberTF.setText(event.getMessage().getObject1().toString());


            try {
                TimeUnit.SECONDS.sleep(7);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            CarNumberTF.clear();
            DesiredParkingTF.clear();
            EmailTF.clear();
           // EtaTF.clear();
            //EtdTF.clear();
            IdNumberTF.clear();


        }
        else if(event.getMessage().getObject1().toString().equals("preorder_parking_is_full")){
            CarNumberTF.setText(event.getMessage().getObject1().toString());
            DesiredParkingTF.setText(event.getMessage().getObject1().toString());
            EmailTF.setText(event.getMessage().getObject1().toString());
            //EtaTF.setText(event.getMessage().getObject1().toString());
           // EtdTF.setText(event.getMessage().getObject1().toString());
            IdNumberTF.setText(event.getMessage().getObject1().toString());


            try {
                TimeUnit.SECONDS.sleep(7);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            CarNumberTF.clear();
            DesiredParkingTF.clear();
            EmailTF.clear();
           // EtaTF.clear();
           // EtdTF.clear();
            IdNumberTF.clear();
        }

    }

    //========================== INPUTS VALIDATION ================================
    //Cancel PreOrder!
    @FXML
    void CancelCarIDTextChange(KeyEvent event) {
        InputValidation test = new InputValidation();
        if(test.CarIDValidation(CancelCarID.getText().toString()))
        {
            CancelErrorLabel.setText("");

        }
        else
        {
            if(!CancelCarID.getText().toString().equals(""))
            {
                CancelErrorLabel.setText("Car ID is not valid! Please try again.");
                CancelCarID.setText("");
            }
            else
            {
                CancelErrorLabel.setText("Car ID is empty, please fill it up!");
                CancelCarID.setText("");
            }
        }
    }

    @FXML
    void CancelCustomerIDTextChange(KeyEvent event) {
        InputValidation test = new InputValidation();
        if(test.CustomerIDValidation(CancelCustomerID.getText().toString()))
        {
            CancelErrorLabel.setText("");

        }
        else
        {
            if(!CancelCustomerID.getText().toString().equals(""))
            {
                CancelErrorLabel.setText("Customer ID is not valid! Please try again.");
                CancelCustomerID.setText("");
            }
            else
            {
                CancelErrorLabel.setText("Customer ID is empty, please fill it up!");
                CancelCustomerID.setText("");
            }
        }
    }

    @FXML
    void CancelEmailTextChange(KeyEvent event) {

    }

    @FXML
    void CancelParkingLotTextChange(KeyEvent event) {

    }

    //==============================================================================

    @FXML
    void CreateCarIDTextChange(KeyEvent event) {
        InputValidation test = new InputValidation();
        if(test.CarIDValidation(CarNumberTF.getText().toString()))
        {
            CreateErrorLabel.setText("");

        }
        else
        {
            if(!CarNumberTF.getText().toString().equals(""))
            {
                CreateErrorLabel.setText("Car ID is not valid! Please try again.");
                CarNumberTF.setText("");
            }
            else
            {
                CreateErrorLabel.setText("Car ID is empty, please fill it up!");
                CarNumberTF.setText("");
            }
        }
    }

    @FXML
    void CreateCustomerIDTextChange(KeyEvent event) {
        InputValidation test = new InputValidation();
        if(test.CarIDValidation(IdNumberTF.getText().toString()))
        {
            CreateErrorLabel.setText("");

        }
        else
        {
            if(!IdNumberTF.getText().toString().equals(""))
            {
                CreateErrorLabel.setText("Customer ID is not valid! Please try again.");
                IdNumberTF.setText("");
            }
            else
            {
                CreateErrorLabel.setText("Customer ID is empty, please fill it up!");
                IdNumberTF.setText("");
            }
        }
    }

    @FXML
    void CreateDepartureHourChange(KeyEvent event) {
        InputValidation test = new InputValidation();
        if(test.TimeValidation(DepartureHourInput.getText().toString()))
        {
            CreateErrorLabel.setText("");

        }
        else
        {
            if(!DepartureHourInput.getText().toString().equals(""))
            {
                CreateErrorLabel.setText("Departure Hour Input is not valid! Please try again.");
            }
            else
            {
                CreateErrorLabel.setText("Departure Hour Input is empty, please fill it up!");
            }
        }
    }

    @FXML
    void CreateEmailIDTextChange(KeyEvent event) {
        InputValidation test = new InputValidation();
        if(test.EmailValidation(EmailTF.getText().toString()))
        {
            CreateErrorLabel.setText("");

        }
        else
        {
            if(!EmailTF.getText().toString().equals(""))
            {
                CreateErrorLabel.setText("Email is not valid! Please try again.");
            }
            else
            {
                CreateErrorLabel.setText("Email is empty, please fill it up!");
            }
        }
    }

    @FXML
    void CreateEntranceHourChange(KeyEvent event) {
        InputValidation test = new InputValidation();
        if(test.TimeValidation(EntranceHourInput.getText().toString()))
        {
            CreateErrorLabel.setText("");

        }
        else
        {
            if(!EntranceHourInput.getText().toString().equals(""))
            {
                CreateErrorLabel.setText("Entrance Hour Input is not valid! Please try again.");
            }
            else
            {
                CreateErrorLabel.setText("Entrance Hour Input is empty, please fill it up!");
            }
        }
    }


    //========================================== idk =======================================================
    @FXML
    void backBtn(ActionEvent event) {

        try {
            App.setRoot(DataSingleton.getInstance().getCaller());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void initialize() {
        EventBus.getDefault().register(this);

    }

    //============================================ Assistant Functions! =======================================
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
