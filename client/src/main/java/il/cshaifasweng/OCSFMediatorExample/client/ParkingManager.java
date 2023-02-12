/**
 * Sample Skeleton for 'ParkingManager.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import antlr.debug.MessageAdapter;
import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;

public class ParkingManager {

    @FXML // fx:id="FullSubsTF"
    private TextField FullSubsTF; // Value injected by FXMLLoader

    @FXML // fx:id="MultiTF"
    private TextField MultiTF; // Value injected by FXMLLoader

    @FXML // fx:id="OcasionalTF"
    private TextField OcasionalTF; // Value injected by FXMLLoader

    @FXML // fx:id="PartTimeTF"
    private TextField PartTimeTF; // Value injected by FXMLLoader

    @FXML // fx:id="PreOrderTF"
    private TextField PreOrderTF; // Value injected by FXMLLoader

    @FXML // fx:id="PricesTable"
    private TableView<?> PricesTable; // Value injected by FXMLLoader

    @FXML // fx:id="ShowStatBtn"
    private Button ShowStatBtn; // Value injected by FXMLLoader

    @FXML // fx:id="SubmitBtn"
    private Button SubmitBtn; // Value injected by FXMLLoader

    @FXML // fx:id="price"
    private TableColumn<?, ?> price; // Value injected by FXMLLoader

    @FXML // fx:id="pricetype"
    private TableColumn<?, ?> pricetype; // Value injected by FXMLLoader

    @FXML // fx:id="showPricesBTN"
    private Button showPricesBTN; // Value injected by FXMLLoader

    @FXML
    void FullSubsTF(ActionEvent event) {
    }

    @FXML
    void MultiTF(ActionEvent event) {
    }

    @FXML
    void OcasionalTF(ActionEvent event) {
    }

    @FXML
    void PartTimeTF(ActionEvent event) {
    }

    @FXML
    void PreOrderTF(ActionEvent event) {
    }

    @FXML
    void ShowStatBtn(ActionEvent event) throws IOException {
        SimpleClient.getClient().sendToServer("showStats");
    }
    @Subscribe
    void showSTats(showStatsEvent event)
    {
        Alert alert = new Alert(Alert.AlertType.WARNING,
                String.format(": \n"+event.msg.getObject1(),
                        ": \n"+event.msg.getObject2(),
                        ": \n"+event.msg.getObject3()
                        )
        );
        alert.show();
//
    }

    @FXML
    void SubmitBtn(ActionEvent event) throws IOException {
        Message msg=new Message("alterPrices");
        msg.setObject1(OcasionalTF.getText());
        msg.setObject2(PreOrderTF.getText());
        msg.setObject3(PartTimeTF.getText());
        msg.setObject4(FullSubsTF.getText());
        msg.setObject5(MultiTF.getText());
        SimpleClient.getClient().sendToServer(msg);
    }

    @FXML
    void showPrices(ActionEvent event) throws IOException {
        Message msg=new Message("showPrices");
        SimpleClient.getClient().sendToServer(msg);
    }
    @Subscribe
    void showingPrices(showPricesEvent event){

    }

}