/**
 * Sample Skeleton for 'OneTimeParkingOrder.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import antlr.debug.MessageAdapter;
import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.Vector;

public class OneTimeParkingOrder {

    @FXML // fx:id="CarNumberTF"
    private TextField CarNumberTF; // Value injected by FXMLLoader
    @FXML
    private Button cancelRes;

    @FXML // fx:id="CheckoutBtn"
    private Button CheckoutBtn; // Value injected by FXMLLoader

    @FXML // fx:id="DesiredParkingTF"
    private TextField DesiredParkingTF; // Value injected by FXMLLoader

    @FXML // fx:id="EmailTF"
    private TextField EmailTF; // Value injected by FXMLLoader

    @FXML // fx:id="EtaTF"
    private TextField EtaTF; // Value injected by FXMLLoader

    @FXML // fx:id="EtdTF"
    private TextField EtdTF; // Value injected by FXMLLoader

    @FXML // fx:id="IdNumberTF"
    private TextField IdNumberTF; // Value injected by FXMLLoader

    @FXML
    private Button backBtn;


    private int msgId = 0;
    @FXML
    void CarNumberTF(ActionEvent event) {

    }

    @FXML
    void CancelResBtn(ActionEvent event)
    {
        Message msg = new Message("cancelOrder");

        msg.setObject1(CarNumberTF.getText());
        msg.setObject2(DesiredParkingTF.getText());
        msg.setObject3(EmailTF.getText());
        msg.setObject4(EtaTF.getText());
        msg.setObject5(EtdTF.getText());
        msg.setObject6(IdNumberTF.getText());
        try{
            SimpleClient.getClient().sendToServer(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @FXML
    void CheckoutBtn(ActionEvent event) {
        Message msg = new Message("OneTimeParkingOrder_Submit");

        Vector<String> fields = new Vector<String>();

        //data is contained in a vector inside Object1
        // the data is stored in the following order
        // 0- car number             3- Eta
        // 1- DesiredParking         4- Etd
        // 2- Email                  5- Id number

        fields.add(CarNumberTF.getText());
        fields.add(DesiredParkingTF.getText());
        fields.add(EmailTF.getText());
        fields.add(EtaTF.getText());
        fields.add(EtdTF.getText());
        fields.add(IdNumberTF.getText());
        msg.setObject1(fields);

        //send to server
        try{
            SimpleClient.getClient().sendToServer(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Subscribe
    void SubmissionAnswer(OneTimeParkingOrderEvent event){
        //todo  warning pop up if everything is okay/not with info
        // if everything is good clear fields
        if(event.getMessage().getObject1().toString().equals("success")) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.WARNING,
                        String.format("Message: %s\n",
                                "Success")
                );
                alert.show();
            });

            CarNumberTF.clear();
            DesiredParkingTF.clear();
            EmailTF.clear();
            EtaTF.clear();
            EtdTF.clear();
            IdNumberTF.clear();

        } else if(event.getMessage().getObject1().toString().equals("fail")){
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.WARNING,
                        String.format("Message: %s\n",
                                "Submission Failed - Wrong Input, Try Again")
                );
                alert.show();
            });
        }

    }

    @FXML
    void DesiredParkingTF(ActionEvent event) {

    }

    @FXML
    void EmailTF(ActionEvent event) {

    }

    @FXML
    void EtaTF(ActionEvent event) {

    }

    @FXML
    void EtdTF(ActionEvent event) {

    }

    @FXML
    void IdNumberTF(ActionEvent event) {

    }

    @FXML
    void backBtn(ActionEvent event) {

        try {
            App.setRoot(DataSingleton.getInstance().getCaller());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
