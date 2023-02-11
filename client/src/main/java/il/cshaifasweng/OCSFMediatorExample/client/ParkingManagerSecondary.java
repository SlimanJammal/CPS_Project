/**
 * Sample Skeleton for 'ParkingManagerSecondary.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.ParkingLot;
import il.cshaifasweng.OCSFMediatorExample.entities.PricesClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;

public class ParkingManagerSecondary {

    @FXML // fx:id="PricesTable"
    private TableView<PricesClass> PricesTable; // Value injected by FXMLLoader

    @FXML // fx:id="ShowManagerSecondaryBTN"
    private Button ShowManagerSecondaryBTN; // Value injected by FXMLLoader

    @FXML // fx:id="backbtn"
    private Button backbtn; // Value injected by FXMLLoader

    @FXML // fx:id="cancled1"
    private TextField cancled1; // Value injected by FXMLLoader

    @FXML // fx:id="cancled2"
    private TextField cancled2; // Value injected by FXMLLoader

    @FXML // fx:id="cancled3"
    private TextField cancled3; // Value injected by FXMLLoader

    @FXML // fx:id="late1"
    private TextField late1; // Value injected by FXMLLoader

    @FXML // fx:id="late2"
    private TextField late2; // Value injected by FXMLLoader

    @FXML // fx:id="late3"
    private TextField late3; // Value injected by FXMLLoader

    @FXML // fx:id="price"
    private TableColumn<PricesClass, Integer> price; // Value injected by FXMLLoader

    @FXML // fx:id="pricetype"
    private TableColumn<PricesClass, String> pricetype; // Value injected by FXMLLoader

    @FXML // fx:id="showPricesBTN"
    private Button showPricesBTN; // Value injected by FXMLLoader

    @FXML // fx:id="uitiMedianTF"
    private TextField uitiMedianTF; // Value injected by FXMLLoader

    private int msgId;

    @FXML // fx:id="utiAvgTF"
    private TextField utiAvgTF; // Value injected by FXMLLoader

    @FXML // fx:id="utiNumberTF"
    private TextField utiNumberTF; // Value injected by FXMLLoader


    //show data in fields about the information about the parking
    @FXML
    void ShowData(ActionEvent event) {

//        Message qq = new Message(msgId++, "ShowData_ParkingManager");
//        try {
//
//            SimpleClient.getClient().sendToServer(qq);
//
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }

    }

    @FXML
    void backToMain(ActionEvent event) {

    }

    @FXML
    void cancled1(ActionEvent event) {

    }

    @FXML
    void cancled2(ActionEvent event) {

    }

    @FXML
    void cancled3(ActionEvent event) {

    }

    @FXML
    void late1(ActionEvent event) {

    }

    @FXML
    void late2(ActionEvent event) {

    }

    @FXML
    void late3(ActionEvent event) {

    }

    @FXML
    void showPrices(ActionEvent event) {
//        Message qq = new Message(msgId++, "ShowPrices_ParkingManager");
////		MessageTF.clear();
//
//        try {
//
//            SimpleClient.getClient().sendToServer(qq);
//
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
    }

    // prints the numbers and avgs
    @Subscribe
    public void setData(ParkingManagerSecondaryEvent event){
        //first line
        utiNumberTF.setText(event.getUtilRes1());
        utiAvgTF.setText(event.getUtilRes2());
        uitiMedianTF.setText(event.getUtilRes3());

        //second line
        cancled1.setText(event.getCancelRes1());
        cancled2.setText(event.getCancelRes2());
        cancled3.setText(event.getCancelRes3());


        //third line
        late1.setText(event.getLate1());
        late2.setText(event.getLate2());
        late3.setText(event.getLate3());


    }

    //prints the table of prices
    @Subscribe
    public void setPricesData(NewSubscriberEvent event) {

//
//        ObservableList<PricesClass> list1 = FXCollections.observableArrayList(event.getMessage().getPricesVector());
//        PricesTable.setItems(list1);
//

    }

    @FXML
    void uitiMedianTF(ActionEvent event) {

    }

    @FXML
    void utiAvgTF(ActionEvent event) {

    }

    @FXML
    void utiNumberTF(ActionEvent event) {

    }


    @FXML
    void initialize() {
        EventBus.getDefault().register(this);
        msgId = 222222222;
        price.setCellValueFactory(new PropertyValueFactory<PricesClass, Integer>("price"));
        pricetype.setCellValueFactory(new PropertyValueFactory<PricesClass, String>("priceType"));


    }

}
