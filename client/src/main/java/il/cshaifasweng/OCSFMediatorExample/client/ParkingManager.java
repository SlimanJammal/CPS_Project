/**
 * Sample Skeleton for 'ParkingManager.fxml' Controller Class
 */

/**
 * Sample Skeleton for 'ParkingManager.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.greenrobot.eventbus.Subscribe;

public class ParkingManager {

    @FXML // fx:id="FullSubsTF"
    private TextField FullSubsTF; // Value injected by FXMLLoader

    @FXML // fx:id="MultiTF"
    private TextField MultiTF; // Value injected by FXMLLoader

    @FXML // fx:id="OcasionalTF"
    private TextField OcasionalTF; // Value injected by FXMLLoader

    @FXML // fx:id="PartTimeTF"
    private Text PartTimeTF; // Value injected by FXMLLoader

    @FXML // fx:id="PreOrderTF"
    private TextField PreOrderTF; // Value injected by FXMLLoader

    @FXML // fx:id="PricesTable"
    private TableView<?> PricesTable; // Value injected by FXMLLoader

    @FXML // fx:id="ShowStatBtn"
    private Button ShowStatBtn; // Value injected by FXMLLoader

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
    void ShowStatBtn(ActionEvent event) {

    }

    @FXML
    void showPrices(ActionEvent event) {

    }

    @Subscribe
    public void setPricesData(NewSubscriberEvent event) {

//
//        ObservableList<PricesClass> list1 = FXCollections.observableArrayList(event.getMessage().getPricesVector());
//        PricesTable.setItems(list1);
//

    }

//    @FXML
//    void initialize() {
////        App.getDefault().register(this);
////        msgId = 222222222;
////        price.setCellValueFactory(new PropertyValueFactory<PricesClass, Integer>("price"));
////        pricetype.setCellValueFactory(new PropertyValueFactory<PricesClass, String>("priceType"));
////
//
//    }

}


