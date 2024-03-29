package il.cshaifasweng.OCSFMediatorExample.client;
//System StartUp (Parking ID,Command) has no input validation due to lack of knowledge about their functionality...

import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.ParkingLot;
import il.cshaifasweng.OCSFMediatorExample.entities.ParkingWorker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class EmployeeWindow {

    @FXML
    private Button ActivateParkingSpot;

    @FXML
    private Button DectivateParkingSpot;

    @FXML
    private Label Label1;

    @FXML
    private TextField OcassionVehicleID;

    @FXML
    private TextField OccasionID;

    @FXML
    private TextField OccasionParkingSlot;

    @FXML
    private Button OccasionSaveButton;

    @FXML
    private TextField ResendLicenseID;

    @FXML
    private Button ResendParkingButton;

    @FXML
    private TextField ResendParkingID;

    @FXML
    private TextField ResendUserID;

    @FXML
    private TextField SystemCommandID;

    @FXML
    private TextField SystemParkingID;

    @FXML
    private Button SystemStartUpButton;

    @FXML
    private TextField UpdateParkingSpot;

    @FXML
    private Button PreviousWindowBT1;

    @FXML
    private Button PreviousWindowBT2;

    @FXML
    private Button PreviousWindowBT3;

    @FXML
    private Button PreviousWindowBT4;

    @FXML
    private Label LabelOutput;
    @FXML
    private  TextField rowTF;
    @FXML
    private  TextField colTF;
    @FXML
    private  TextField depthTF;


    @FXML
    private TextField ParkingLotNameOccasion;



    @FXML
    void ActivateParkingSpot(ActionEvent event) {


        System.out.println("activate client ");
        Message msg = new Message("Activate Parking Spot");
        msg.setObject1(rowTF.getText());
        int worker_id  = (int) DataSingleton.getInstance().getData();
        msg.setObject2(worker_id);


        try
        {
            SimpleClient.getClient().sendToServer(msg);
        }
        catch (IOException e)
        {

            e.printStackTrace();
        }

    }

    @FXML
    void DeactivateParkingSpot(ActionEvent event) {


        Message msg = new Message("Deactivate Parking Spot");
        msg.setObject1(rowTF.getText());
        int worker_id  = (int) DataSingleton.getInstance().getData();
        msg.setObject2(worker_id);
        System.out.println("deactivate client ");

        try
        {
            SimpleClient.getClient().sendToServer(msg);
        }
        catch (IOException e)
        {

            e.printStackTrace();
        }


    }

    @FXML
    void OnChangeOcassionID(ActionEvent event) {
        //Occasion ID - ON Change!
        String Input = OccasionID.getText().toString();
        if(IsNumber(Input))
        {

        }
        else
        {
            OccasionID.setText("Only digits allowed!");
            try
            {
                wait(2000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            OccasionID.setText("");
        }
    }

    @FXML
    void OnChangeOccasionParkingSlotID(ActionEvent event) {
        String Input = OccasionParkingSlot.getText().toString();
        if(IsNumber(Input))
        {

        }
        else
        {
            OccasionParkingSlot.setText("Only digits allowed!");
            try
            {
                wait(2000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            OccasionParkingSlot.setText("");
        }

    }

    @FXML
    void OnChangeParkingID(ActionEvent event) {
        String Input = ResendParkingID.getText().toString();
        if(IsNumber(Input))
        {

        }
        else
        {
            ResendParkingID.setText("Only digits allowed!");
            try
            {
                wait(2000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            ResendParkingID.setText("");
        }
    }

    @FXML
    void OnChangeSystemCommandID(ActionEvent event) {
        //SYSTEM COMMANDS ID'S...

    }

    @FXML
    void OnChangeSystemStartUp(ActionEvent event) {
        //System Parking Spot Startup

    }
    //========================== SEND TO ANOTHER PARKING =============================
    @FXML
    void OnChangeUserID(ActionEvent event) {
        //Resend ID - ON Change!
        String Input = ResendUserID.getText().toString();
        if(IsNumber(Input))
        {

        }
        else
        {
            ResendUserID.setText("Only digits allowed!");
            try
            {
                wait(2000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            ResendUserID.setText("");
        }
    }
    //========================== SEND TO ANOTHER PARKING =============================
    @FXML
    void OnChangeVehicleParkingID(ActionEvent event) {
        //Resend ID - ON Change!
        String Input = ResendParkingID.getText().toString();
        if(IsNumber(Input))
        {

        }
        else
        {
            ResendParkingID.setText("Only digits allowed!");
            try
            {
                wait(2000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            ResendParkingID.setText("");
        }
    }
    //========================== SEND TO ANOTHER PARKING =============================
    @FXML
    void OnLicensePlateID(ActionEvent event) {
        //License Plate ID - ON Change!
        String Input = ResendLicenseID.getText().toString();
        if(IsNumber(Input))
        {

        }
        else
        {
            ResendLicenseID.setText("Only digits allowed!");
            try
            {
                wait(2000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            ResendLicenseID.setText("");
        }
    }
    //========================== SEND TO ANOTHER PARKING =============================
    @FXML
    void SubmitAnotherParking(ActionEvent event) {
        //Send Customer To Another Parking Spot Procedure...
        String LicenseID = ResendLicenseID.getText();
        String UserID = ResendUserID.getText();
        String ParkingID = ResendParkingID.getText();
        //Reset Textboxes after receiving request information.
        {
            ResendLicenseID.setText("");
            ResendUserID.setText("");
            ResendParkingID.setText("");
        }
        //send message to server ...
        Message msg = new Message("Send To Other Parking");
        msg.setObject1(LicenseID);
        msg.setObject2(UserID);
        msg.setObject3(ParkingID);

        try
        {
            SimpleClient.getClient().sendToServer(msg);
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //========================== Submit Occasion Park Saving =============================
    @FXML
    void SubmitOccasionSavingSpot(ActionEvent event) {

             Message msg = new Message("submit_occasion");


             msg.setObject1(OccasionParkingSlot.getText());
             msg.setObject2(DataSingleton.getInstance().getData());
//             msg.setObject2(ParkingLotNameOccasion.getText());


        try
        {
            SimpleClient.getClient().sendToServer(msg);
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

    @FXML
    void CancelSubmitOccasionSavingSpot(ActionEvent event) {

        Message msg = new Message("cancel_Occasion");

        msg.setObject1(OccasionParkingSlot.getText());
        msg.setObject2(DataSingleton.getInstance().getData());

        try
        {
            SimpleClient.getClient().sendToServer(msg);
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

    //========================== Initialize System Startup =============================
    @FXML
    void SubmitSystemStartUp(ActionEvent event) {
        String ParkingID = SystemParkingID.getText().toString();
        String SystemCommand = SystemCommandID.getText().toString();
        //Reset Textboxes after receiving request information.
        {
            SystemParkingID.setText("");
            SystemCommandID.setText("");
        }
        //send message to server ...
        Message msg = new Message("System Request");
        msg.setObject1(ParkingID);
        msg.setObject2(SystemCommand);

        try
        {
            if(isnumeric(ParkingID)) {
                SimpleClient.getClient().sendToServer(msg);
            }
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    //========================== Update Parking Spot =============================
    @FXML
    void UpdateTheParkingSpot(ActionEvent event) {
        //Parking Spot Status Update - Taken/Not Taken
    }
    //_________________________________________________________________________________________________________________
    //_________________________________________________________________________________________________________________
    //___________________________________________ Previous Window Click _______________________________________________
    @FXML
    void OnPreviousWindowButton1(ActionEvent event) throws IOException {
        Message EmployeeMsg = new Message("EmployeeLogout");
        EmployeeMsg.setObject1(DataSingleton.getInstance().getData());
        EmployeeMsg.setObject2(DataSingleton.getInstance().getCaller());
        SimpleClient.getClient().sendToServer(EmployeeMsg);

        try {
            App.setRoot(DataSingleton.getInstance().getCaller());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    @FXML
//    void OnPreviousWindowButton2(ActionEvent event) {
//        try {
//            App.setRoot(DataSingleton.getInstance().getCaller());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    @FXML
//    void OnPreviousWindowButton3(ActionEvent event) {
//        try {
//            App.setRoot(DataSingleton.getInstance().getCaller());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @FXML
//    void OnPreviousWindowButton4(ActionEvent event) {
//        try {
//            App.setRoot(DataSingleton.getInstance().getCaller());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public boolean IsNumber(String Input)
    {
        for(char a : Input.toCharArray())
        {
            if(a < '0' || a > '9')
                return false;
        }
        return true;
    }

    @Subscribe
    public void complaintHandleFromServer(EmployeeWindowEvent event){

        Message msg = event.getMessage();
        if (msg.getMessage().equals("tryLogOut_LoggedOut")){
            try {
                App.setRoot((String)msg.getObject1());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("employee subscribe");
        if(msg.getObject1().toString().equals("failed_occasion") || msg.getObject1().toString().equals("occasion_canceled") ||
                msg.getObject1().toString().equals("occasion_submitted")){
            OccasionParkingSlot.setText( msg.getObject1().toString());
            OcassionVehicleID.clear();

            try {
                TimeUnit.SECONDS.sleep(4);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            OccasionParkingSlot.clear();

        }

        if(msg.getObject1().toString().equals("failed") || msg.getObject1().toString().equals("Activated") ||
                msg.getObject1().toString().equals( "Deactivated") ){
            rowTF.setText(msg.getObject1().toString());

            try {
                TimeUnit.SECONDS.sleep(4);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            rowTF.clear();

        }

    }
    @FXML
    void initialize() {
        EventBus.getDefault().register(this);

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
}
