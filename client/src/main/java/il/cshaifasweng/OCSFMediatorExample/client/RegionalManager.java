/**
 * Sample Skeleton for 'RegionalManager.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
//
public class RegionalManager {

    @FXML // fx:id="AcceptBtn"
    private Button AcceptBtn; // Value injected by FXMLLoader

    @FXML // fx:id="DeclineBtn"
    private Button DeclineBtn; // Value injected by FXMLLoader

    @FXML // fx:id="FullSubsTF"
    private TextField FullSubsTF; // Value injected by FXMLLoader

    @FXML // fx:id="FullSubsTF1"
    private TextField FullSubsTF1; // Value injected by FXMLLoader

    @FXML // fx:id="FullSubsTF2"
    private TextField FullSubsTF2; // Value injected by FXMLLoader

    @FXML // fx:id="MultiTF"
    private TextField MultiTF; // Value injected by FXMLLoader

    @FXML // fx:id="MultiTF1"
    private TextField MultiTF1; // Value injected by FXMLLoader

    @FXML // fx:id="MultiTF2"
    private TextField MultiTF2; // Value injected by FXMLLoader

    @FXML // fx:id="OcasionalTF"
    private TextField OcasionalTF; // Value injected by FXMLLoader

    @FXML // fx:id="OcasionalTF1"
    private TextField OcasionalTF1; // Value injected by FXMLLoader

    @FXML // fx:id="OcasionalTF2"
    private TextField OcasionalTF2; // Value injected by FXMLLoader

    @FXML // fx:id="PDFRequest"
    private MenuButton PDFRequest; // Value injected by FXMLLoader

    @FXML // fx:id="ParkingStatisticsMen"
    private MenuButton ParkingStatisticsMen; // Value injected by FXMLLoader

    @FXML // fx:id="PartTimeTF"
    private TextField PartTimeTF; // Value injected by FXMLLoader

    @FXML // fx:id="PartTimeTF1"
    private Text PartTimeTF1; // Value injected by FXMLLoader

    @FXML // fx:id="PartTimeTF2"
    private Text PartTimeTF2; // Value injected by FXMLLoader

    @FXML // fx:id="PreOrderTF"
    private TextField PreOrderTF; // Value injected by FXMLLoader

    @FXML // fx:id="PreOrderTF1"
    private TextField PreOrderTF1; // Value injected by FXMLLoader

    @FXML // fx:id="PreOrderTF2"
    private TextField PreOrderTF2; // Value injected by FXMLLoader

    @FXML // fx:id="PricesTable"
    private TableView<?> PricesTable; // Value injected by FXMLLoader

    @FXML // fx:id="PricesTable1"
    private TableView<?> PricesTable1; // Value injected by FXMLLoader

    @FXML // fx:id="PricesTable2"
    private TableView<?> PricesTable2; // Value injected by FXMLLoader

    @FXML // fx:id="RequestsTable"
    private TableView<?> RequestsTable; // Value injected by FXMLLoader

    @FXML // fx:id="ShowStatBtn"
    private Button ShowStatBtn; // Value injected by FXMLLoader

    @FXML // fx:id="ShowStatBtn1"
    private Button ShowStatBtn1; // Value injected by FXMLLoader

    @FXML // fx:id="ShowStatBtn2"
    private Button ShowStatBtn2; // Value injected by FXMLLoader

    @FXML // fx:id="menuPdf1"
    private MenuItem menuPdf1; // Value injected by FXMLLoader

    @FXML // fx:id="menuPdf2"
    private MenuItem menuPdf2; // Value injected by FXMLLoader

    @FXML // fx:id="menuPdf3"
    private MenuItem menuPdf3; // Value injected by FXMLLoader

    @FXML // fx:id="menuStat1"
    private MenuItem menuStat1; // Value injected by FXMLLoader

    @FXML // fx:id="menuStat2"
    private MenuItem menuStat2; // Value injected by FXMLLoader

    @FXML // fx:id="menuStat3"
    private MenuItem menuStat3; // Value injected by FXMLLoader

    @FXML // fx:id="price"
    private TableColumn<?, ?> price; // Value injected by FXMLLoader

    @FXML // fx:id="price1"
    private TableColumn<?, ?> price1; // Value injected by FXMLLoader

    @FXML // fx:id="price2"
    private TableColumn<?, ?> price2; // Value injected by FXMLLoader

    @FXML // fx:id="pricetype"
    private TableColumn<?, ?> pricetype; // Value injected by FXMLLoader

    @FXML // fx:id="pricetype1"
    private TableColumn<?, ?> pricetype1; // Value injected by FXMLLoader

    @FXML // fx:id="pricetype2"
    private TableColumn<?, ?> pricetype2; // Value injected by FXMLLoader

    @FXML // fx:id="requestTF"
    private TextField requestTF; // Value injected by FXMLLoader

    @FXML // fx:id="showPricesBTN"
    private Button showPricesBTN; // Value injected by FXMLLoader

    @FXML // fx:id="showPricesBTN1"
    private Button showPricesBTN1; // Value injected by FXMLLoader

    @FXML // fx:id="showPricesBTN2"
    private Button showPricesBTN2; // Value injected by FXMLLoader

    @FXML
    void AcceptBtn(ActionEvent event) {

    }

    @FXML
    void DeclineBtn(ActionEvent event) {

    }

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
    void PDFRequest(ActionEvent event) {

    }

    @FXML
    void ParkingStatisticsMen(ActionEvent event) {

    }

    @FXML
    void PartTimeTF(ActionEvent event) {

    }

    @FXML
    void PreOrderTF(ActionEvent event) {

    }

    @FXML
    void ShowStatBtn(ActionEvent event) {

    }

    @FXML
    void menuPdf1(ActionEvent event) {
        Message msg = new Message("pdf_Parking1");
        //send to server
        try{
            SimpleClient.getClient().sendToServer(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void menuPdf2(ActionEvent event) {
        Message msg = new Message("pdf_Parking2");
        //send to server
        try{
            SimpleClient.getClient().sendToServer(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void menuPdf3(ActionEvent event) {
        Message msg = new Message("pdf_Parking3");
        //send to server
        try{
            SimpleClient.getClient().sendToServer(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void menuStat1(ActionEvent event) {
        Message msg = new Message("stat_Parking1");
        //send to server
        try{
            SimpleClient.getClient().sendToServer(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void menuStat2(ActionEvent event) {
        Message msg = new Message("stat_Parking2");
        //send to server
        try{
            SimpleClient.getClient().sendToServer(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void menuStat3(ActionEvent event) {
        Message msg = new Message("stat_Parking3");
        //send to server
        try{
            SimpleClient.getClient().sendToServer(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Subscribe
    public void statCatch(RegionalManagerEvent event){
        // message field decides what case we re in

        if(event.getMessage().getMessage().equals("stat_regional")){
//            Platform.runLater(() -> {
//                Alert alert = new Alert(Alert.AlertType.WARNING,
//                        String.format("Message: %s\n",
//                                "Submission Failed, Try Again")
//                );
//                alert.show();
//            });
            //todo alert about stats
        }







    }

    @FXML
    void requestTF(ActionEvent event) {

    }



    @FXML
    void showPrices(ActionEvent event) {

    }

}
