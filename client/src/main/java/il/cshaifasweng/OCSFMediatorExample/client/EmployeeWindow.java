package il.cshaifasweng.OCSFMediatorExample.client;
//System StartUp (Parking ID,Command) has no input validation due to lack of knowledge about their functionality...

import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

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
    void ActivateParkingSpot(ActionEvent event) {
        //Activate Parking Spot
        String ActivateParkingSpotID = UpdateParkingSpot.getText().toString();
        //Reset TextBox's Values!
        {
            UpdateParkingSpot.setText("");
        }
        //send message to server ...
        Message msg = new Message("Activate Parking Spot");
        msg.setObject1(ActivateParkingSpotID);
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
    void DeactivateParkingSpot(ActionEvent event) {
        //Deactivate Parking Spot
        String DeactivateParkingSpotID = UpdateParkingSpot.getText().toString();
        //Reset TextBox's Values!
        {
            UpdateParkingSpot.setText("");
        }
        //send message to server ...
        Message msg = new Message("Deactivate Parking Spot");
        msg.setObject1(DeactivateParkingSpotID);
        try
        {
            SimpleClient.getClient().sendToServer(msg);
        }
        catch (IOException e)
        {
            // TODO Auto-generated  catch block
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
        String OccasionalParkingSlot = OccasionParkingSlot.getText().toString();
        String OccasionalVehicleID = OcassionVehicleID.getText().toString();
        String OccasionalCommandID = OccasionID.getText().toString();
        //Reset Textboxes after receiving request information.
        {
            SystemParkingID.setText("");
            SystemCommandID.setText("");
            SystemCommandID.setText("");

        }
        //send message to server ...
        Message msg = new Message("Occasion Request");
        msg.setObject1(OccasionalParkingSlot);
        msg.setObject2(OccasionalVehicleID);
        msg.setObject3(OccasionalCommandID);

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
            SimpleClient.getClient().sendToServer(msg);
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
    void OnPreviousWindowButton1(ActionEvent event) {
        //Go To Previous Window
    }

    @FXML
    void OnPreviousWindowButton2(ActionEvent event) {
        //Go To Previous Window

    }

    @FXML
    void OnPreviousWindowButton3(ActionEvent event) {
        //Go To Previous Window

    }

    @FXML
    void OnPreviousWindowButton4(ActionEvent event) {
        //Go To Previous Window

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
}
