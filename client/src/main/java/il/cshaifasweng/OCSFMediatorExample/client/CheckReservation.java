package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.persistence.Id;
import java.io.IOException;

public class CheckReservation {

    @FXML
    private Button BackBtn;

    @FXML
    private TextField IdTf;

    @FXML
    private TextField ParkingNameTf;

    @FXML
    private TextArea screenTf;

    @FXML
    private RadioButton Radio1;

    @FXML
    private RadioButton Radio2;

    @FXML
    private RadioButton Radio3;

    @FXML
    private Button SubmitBtn;

    @FXML
    void BackBtn(ActionEvent event) {
        DataSingleton data = DataSingleton.getInstance();
        String previousPage = data.getCaller();
        try {
            App.setRoot(previousPage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void SubmitBtn(ActionEvent event) {
        Message temp = new Message("CheckReservation");
        temp.setObject1(IdTf.getText());
        if(Radio1.isSelected())
        {
            temp.setObject2("German_Colony");
        }
        else if(Radio2.isSelected())
        {
            temp.setObject2("Hanmal");
        }
        else if(Radio3.isSelected())
        {
            temp.setObject2("Bat-Galim");
        }

        IdTf.clear();
//        ParkingNameTf.clear();

        try
        {
            SimpleClient.getClient().sendToServer(temp);
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }
    @FXML
    void OnRadioButton(ActionEvent event) {

    }

    @Subscribe
    public void complaintHandleFromServer(CheckReservationEvent event){

        Message msg = event.getMessage();

       screenTf.setText(msg.getObject1().toString());
    }
    @FXML
    void initialize() {
        EventBus.getDefault().register(this);

    }

}
