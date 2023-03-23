/**
 * Sample Skeleton for 'ParkingManager.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import antlr.debug.MessageAdapter;
import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.PricesClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.List;

public class ParkingManager {
    @FXML
    private Button backbtn;

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
    private TableView<PricesClass> PricesTable; // Value injected by FXMLLoader

    @FXML // fx:id="ShowStatBtn"
    private Button ShowStatBtn; // Value injected by FXMLLoader

    @FXML // fx:id="SubmitBtn"
    private Button SubmitBtn; // Value injected by FXMLLoader

    @FXML // fx:id="price"
    private TableColumn<PricesClass,Integer> price; // Value injected by FXMLLoader

    @FXML // fx:id="pricetype"
    private TableColumn<PricesClass,String> pricetype; // Value injected by FXMLLoader

    @FXML
    private TextArea statsScreen;

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

        Message msg = new Message("ParkingManager_showStats");

        //MANAGER NAME/PARKING NUMBER
        msg.setObject1(DataSingleton.getInstance().getData());

        SimpleClient.getClient().sendToServer(msg);
    }

    @Subscribe
   public void showSTats(showStatsEvent event)
    {
        Message ms = event.getMsg();

        Integer first = (Integer) ms.getObject1();
        Integer second = (Integer) ms.getObject2();
        Integer third = (Integer) ms.getObject3();

        String data = "Canceled Mean: " + first + " \n" + "Late Mean: "+ second  + " \n" + "Completed Mean: " + third;
        statsScreen.setText(data);
//
    }

    @FXML
    void SubmitBtn(ActionEvent event) throws IOException {
        Message msg=new Message("ParkingManager_alterPrices");
        //data stored in seperate objects
        // ocasional price -> object1
        // preOrder price -> object2
        // partTime price -> object3
        // FullSubs price -> object4
        // Multi price -> object5
        System.out.println("ParkingManager_alterPrices");
        msg.setObject1(OcasionalTF.getText());
        msg.setObject2(PreOrderTF.getText());
        msg.setObject3(PartTimeTF.getText());
        msg.setObject4(FullSubsTF.getText());
        msg.setObject5(MultiTF.getText());

        //MANAGER NAME/PARKING NUMBER
        msg.setObject6(DataSingleton.getInstance().getData());

        if(     isnumeric((String)msg.getObject1())&&
                isnumeric((String)msg.getObject2())&&
                isnumeric((String)msg.getObject3())&&
                isnumeric((String)msg.getObject4())&&
                isnumeric((String)msg.getObject5())
        ) {
            SimpleClient.getClient().sendToServer(msg);
        }
    }

    @FXML
    void showPrices(ActionEvent event)   {

        Message msg=new Message("ParkingManager_showPrices");

        //PARKING MANGER MANAGER LOAD
        System.out.println("in show prices");
        Integer data = (Integer) DataSingleton.getInstance().getData();
        msg.setObject1(data);
        System.out.println("22in show prices");

        try {
            SimpleClient.getClient().sendToServer(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Subscribe
  public void showingPrices(showPricesEvent event){
        Message returned = event.msg;
        if (returned.getMessage().equals("pricesReturned")){
            //the prices are returned here as a List of PricesClass
            // i.e List<PricesClass> which is stored in Object1
            List<PricesClass> pricesList = (List<PricesClass>)returned.getObject1();
            ObservableList<PricesClass> list = FXCollections.observableArrayList(pricesList);
            price.setCellValueFactory(new PropertyValueFactory<PricesClass,Integer>("price"));
            pricetype.setCellValueFactory(new PropertyValueFactory<PricesClass,String>("priceType"));
            PricesTable.setItems(list);
        }

        if(returned.getMessage().equals("prices update request sent")){
//            Alert alert = new Alert(Alert.AlertType.INFORMATION,
//                   "REQUEST SENT"
//                    );
//
//            alert.show();
           OcasionalTF.setText("request sent");
            PreOrderTF.setText("request sent");
            PartTimeTF.setText("request sent");
            FullSubsTF.setText("request sent");
            MultiTF.setText("request sent");


        }
    }


    @FXML
    void backbtn(ActionEvent event) {
        try {
            App.setRoot(DataSingleton.getInstance().getCaller());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() {
        EventBus.getDefault().register(this);

    }
    boolean isnumeric(String str)
    {
        for(int i=0;i<str.length();i++)
        {
            if(str.charAt(i)<'0'||str.charAt(i)>'9')
            {
                return false;
            }
        }
        return true;
    }

}