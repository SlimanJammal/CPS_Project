package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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
        temp.setObject2(ParkingNameTf.getText());
        IdTf.clear();
        ParkingNameTf.clear();

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

    @Subscribe
    public void complaintHandleFromServer(CheckReservationEvent event){

        Message msg = event.getMessage();
        Alert alert = new Alert(Alert.AlertType.WARNING,
                msg.getMessage());
        alert.show();
    }
    @FXML
    void initialize() {
        EventBus.getDefault().register(this);

    }

}
