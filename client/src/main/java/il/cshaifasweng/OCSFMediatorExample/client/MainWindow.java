/**
 * Sample Skeleton for 'mainWindow.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class MainWindow {

    @FXML // fx:id="KioskBtn"
    private Button KioskBtn; // Value injected by FXMLLoader

    @FXML // fx:id="WebsiteBtn"
    private Button WebsiteBtn; // Value injected by FXMLLoader

    @FXML
    void KioskBtn(ActionEvent event) throws IOException {
        try{
            App.setRoot("ComplaintSubmittion");
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @FXML
    void WebsiteBtn(ActionEvent event) throws IOException {
        try{
            App.setRoot("ocasionalParking");
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
