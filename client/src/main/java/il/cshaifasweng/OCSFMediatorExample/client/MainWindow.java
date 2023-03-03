/**
 * Sample Skeleton for 'mainWindow.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

public class MainWindow {

//    @FXML // fx:id="KioskBtn"
//    private Button KioskBtn; // Value injected by FXMLLoader


    @FXML // fx:id="WebsiteBtn"
    private Button WebsiteBtn; // Value injected by FXMLLoader

    @FXML
    private MenuButton WhichKiosk;
    @FXML
    private MenuItem BatGalimItem;

    @FXML
    private MenuItem GermanColonyItem;

    @FXML
    private MenuItem HanamalItem;



    @FXML
    void BatGalimItem(ActionEvent event) {
        try{
            DataSingleton data =DataSingleton.getInstance();
            data.setCaller("MainWindow");
            data.setDataName("Bat-Galim");
            WhichKiosk.hide();
            App.setRoot("cpsKiosk");
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @FXML
    void GermanColonyItem(ActionEvent event) {
        try{
            DataSingleton data =DataSingleton.getInstance();
            data.setCaller("MainWindow");
            data.setDataName("German_Colony");
            WhichKiosk.hide();
            App.setRoot("cpsKiosk");
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    @FXML
    void HanamalItem(ActionEvent event) {
        try{
            DataSingleton data =DataSingleton.getInstance();
            data.setCaller("MainWindow");
            data.setDataName("Hanamal");
            WhichKiosk.hide();
            App.setRoot("cpsKiosk");
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
//    @FXML
//    void KioskBtn(ActionEvent event) throws IOException {
//        try{
//            data.setCaller("MainWindow");
//            App.setRoot("cpsKiosk");
//        }
//        catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//    }
    @FXML
    void WhichKiosk(ActionEvent event) {

    }

    @FXML
    void WebsiteBtn(ActionEvent event) throws IOException {
        try{
            DataSingleton.getInstance().setCaller("MainWindow");
            App.setRoot("cpsWebsite");
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
//    @FXML
//    void initialize() {
//        EventBus.getDefault().register(this);
//
//    }

}
