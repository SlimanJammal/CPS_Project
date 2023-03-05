/**
 * Sample Skeleton for 'CustomerService.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class CustomerService {

    @FXML // fx:id="AcceptBtn"
    private Button AcceptBtn; // Value injected by FXMLLoader

    @FXML // fx:id="DeclineBtn"
    private Button DeclineBtn; // Value injected by FXMLLoader

    @FXML // fx:id="ManagerNameCol"
    private TableColumn<?, ?> ManagerNameCol; // Value injected by FXMLLoader

    @FXML // fx:id="NumberColMainWin"
    private TableColumn<?, ?> NumberColMainWin; // Value injected by FXMLLoader

    @FXML // fx:id="RequestCol"
    private TableColumn<?, ?> RequestCol; // Value injected by FXMLLoader

    @FXML // fx:id="RequestsTable"
    private TableView<?> RequestsTable; // Value injected by FXMLLoader

    @FXML // fx:id="refreshRequestsBTN"
    private Button refreshRequestsBTN; // Value injected by FXMLLoader

    @FXML // fx:id="requestTF"
    private TextField requestTF; // Value injected by FXMLLoader

    @FXML
    void AcceptBtn(ActionEvent event) {

    }

    @FXML
    void DeclineBtn(ActionEvent event) {

    }

    @FXML
    void refreshRequestsBTN(ActionEvent event) {

    }

    @FXML
    void requestTF(ActionEvent event) {

    }

}
