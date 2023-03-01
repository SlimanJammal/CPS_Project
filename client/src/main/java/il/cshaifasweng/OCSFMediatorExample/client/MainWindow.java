/**
 * Sample Skeleton for 'mainWindow.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.io.IOException;

public class MainWindow {
    @FXML
    private Button MagicButton;
    @FXML
    private Circle green;
    @FXML
    private Circle red;

    @FXML // fx:id="KioskBtn"
    private Button KioskBtn; // Value injected by FXMLLoader

    @FXML // fx:id="WebsiteBtn"
    private Button WebsiteBtn; // Value injected by FXMLLoader

    @FXML
    void play(ActionEvent event)
    {
        setRotate(green,true,360,10);
        setRotate(red,true,360,10);
    }

    private void setRotate(Circle c ,boolean r , int angle,int duration)
    {
        RotateTransition rt=new RotateTransition(Duration.seconds(duration),c);
        rt.setByAngle(angle);
        rt.setAutoReverse(r);
        rt.setRate(3);
        rt.setDelay(Duration.seconds(0));
        rt.setCycleCount(20);
        rt.play();
    }
    @FXML
    void KioskBtn(ActionEvent event) throws IOException {
        try{
            DataSingleton.getInstance().setCaller("MainWindow");
            App.setRoot("cpsKiosk");
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

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
