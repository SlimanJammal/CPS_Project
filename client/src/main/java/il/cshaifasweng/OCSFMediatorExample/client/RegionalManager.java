/**
 * Sample Skeleton for 'RegionalManager.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.PricesClass;
import il.cshaifasweng.OCSFMediatorExample.entities.PricesUpdateRequest;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    @FXML
    private TextField PartTimeTF1;

    @FXML
    private TextField PartTimeTF2;

    @FXML
    private TextField PartTimeTF3;

    @FXML
    private Button refreshRequestsBTN;

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

    @FXML
    private TextArea SCREEN1;

    @FXML
    private TextArea SCREEN2;

    @FXML
    private TextArea SCREEN3;


    @FXML // fx:id="PreOrderTF"
    private TextField PreOrderTF; // Value injected by FXMLLoader

    @FXML // fx:id="PreOrderTF1"
    private TextField PreOrderTF1; // Value injected by FXMLLoader

    @FXML // fx:id="PreOrderTF2"
    private TextField PreOrderTF2; // Value injected by FXMLLoader

    @FXML // fx:id="PricesTable"
    private TableView<PricesClass> PricesTable; // Value injected by FXMLLoader

    @FXML // fx:id="PricesTable1"
    private TableView<PricesClass> PricesTable1; // Value injected by FXMLLoader

    @FXML // fx:id="PricesTable2"
    private TableView<PricesClass> PricesTable2; // Value injected by FXMLLoader

    @FXML // fx:id="RequestsTable"
    private TableView<PriceRequest> RequestsTable; // Value injected by FXMLLoader

    @FXML
    private Button ShowStatBtn1;

    @FXML
    private Button ShowStatBtn2;

    @FXML
    private Button ShowStatBtn3;

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
    private TableColumn<PricesClass,Integer> price; // Value injected by FXMLLoader

    @FXML // fx:id="price1"
    private TableColumn<PricesClass,Integer> price1; // Value injected by FXMLLoader

    @FXML // fx:id="price2"
    private TableColumn<PricesClass,Integer> price2; // Value injected by FXMLLoader

    @FXML // fx:id="pricetype"
    private TableColumn<PricesClass,String> pricetype; // Value injected by FXMLLoader

    @FXML // fx:id="pricetype1"
    private TableColumn<PricesClass,String> pricetype1; // Value injected by FXMLLoader

    @FXML // fx:id="pricetype2"
    private TableColumn<PricesClass,String> pricetype2; // Value injected by FXMLLoader

    @FXML
    private TableColumn<PriceRequest, String> RequestCol;

    @FXML
    private TableColumn<PriceRequest, Integer> NumberColMainWin;

    @FXML
    private TableColumn<PriceRequest, String> ManagerNameCol;

    @FXML // fx:id="requestTF"
    private TextField requestTF; // Value injected by FXMLLoader

    @FXML
    private Button showPricesBTN1;

    @FXML
    private Button showPricesBTN2;

    @FXML
    private Button showPricesBTN3;

    @FXML
    private Button submitBTN1;

    @FXML
    private Button submitBtn2;

    @FXML
    private Button submitBtn3;
    @FXML
    private Button backbtn;


    @FXML
    void AcceptBtn(ActionEvent event) {

        String requestNumber = requestTF.getText();
        requestTF.clear();
        Message msg = new Message("accept_price_alter_regional");
        String regex = "\\d+";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(requestNumber);
        if(!m.matches()) {
            return;
        }
        msg.setObject1(requestNumber);
        //send to server
        try{
            SimpleClient.getClient().sendToServer(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void DeclineBtn(ActionEvent event) {
        String requestNumber = requestTF.getText();
        requestTF.clear();
        Message msg = new Message("decline_price_alter_regional");
        String regex = "\\d+";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(requestNumber);
        if(!m.matches()) {
            return;
        }
        msg.setObject1(requestNumber);
        //send to server
        try{
            SimpleClient.getClient().sendToServer(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void FullSubsTF(ActionEvent event) {

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
    void refreshRequestsBTN(ActionEvent event) {

        Message msg = new Message("RegionalManager_ShowPriceRequests");
        try{
            SimpleClient.getClient().sendToServer(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }


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
    void ShowStatBtn1(ActionEvent event) {
        Message msg = new Message("show_stat1_regional");
        msg.setObject1("German_Colony");
        //send to server
        try{
            SimpleClient.getClient().sendToServer(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void ShowStatBtn2(ActionEvent event) {
        Message msg = new Message("show_stat2_regional");
        msg.setObject1("Hanmal");
        //send to server
        try{
            SimpleClient.getClient().sendToServer(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void ShowStatBtn3(ActionEvent event) {
        Message msg = new Message("show_stat3_regional");
        msg.setObject1("Bat-Galim");
        //send to server
        try{
            SimpleClient.getClient().sendToServer(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void menuPdf1(ActionEvent event) {
        Message msg = new Message("pdf_Parking1_regional");

        //send to server
        try{
            SimpleClient.getClient().sendToServer(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void menuPdf2(ActionEvent event) {
        Message msg = new Message("pdf_Parking2_regional");
        //send to server
        try{
            SimpleClient.getClient().sendToServer(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void menuPdf3(ActionEvent event) {
        Message msg = new Message("pdf_Parking3_regional");
        //send to server
        try{
            SimpleClient.getClient().sendToServer(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void menuStat1(ActionEvent event) {
        Message msg = new Message("stat_Parking1_regional");
        //send to server
        try{
            SimpleClient.getClient().sendToServer(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void menuStat2(ActionEvent event) {
        Message msg = new Message("stat_Parking2_regional");
        //send to server
        try{
            SimpleClient.getClient().sendToServer(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void menuStat3(ActionEvent event) {
        Message msg = new Message("stat_Parking3_regional");
        //send to server
        try{
            SimpleClient.getClient().sendToServer(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Subscribe
    public void regionalhandlerr(RegionalManagerEvent event){
        // message field decides what case we re in
        //the first stat is for main window and show stat regional is for secondary windows
        System.out.println("back to regional's subscribe");
        String parkingName2 = (String) event.getMessage().getObject4();
        if(event.getMessage().getMessage().equals("stat_regional") || event.getMessage().getMessage().equals("show_stat_regional")){


            if(parkingName2.equals("German_Colony")){

                Message ms = event.getMessage();

                Integer first = (Integer) ms.getObject1();
                Integer second = (Integer) ms.getObject2();
                Integer third = (Integer) ms.getObject3();

                String data = "Canceled Mean: " + first + " \n" + "Late Mean: "+ second  + " \n" + "Completed Mean: " + third;
                SCREEN1.setText(data);

            }else if(parkingName2.equals("Hanmal")){
                Message ms = event.getMessage();

                Integer first = (Integer) ms.getObject1();
                Integer second = (Integer) ms.getObject2();
                Integer third = (Integer) ms.getObject3();

                String data = "Canceled Mean: " + first + " \n" + "Late Mean: "+ second  + " \n" + "Completed Mean: " + third;
                SCREEN2.setText(data);


            } else {


                Message ms = event.getMessage();

                Integer first = (Integer) ms.getObject1();
                Integer second = (Integer) ms.getObject2();
                Integer third = (Integer) ms.getObject3();

                String data = "Canceled Mean: " + first + " \n" + "Late Mean: "+ second  + " \n" + "Completed Mean: " + third;
                SCREEN3.setText(data);
            }


            //todo alert about stats





        } else if(event.getMessage().getMessage().equals("req_regional")){
            System.out.println("back to regional's request");

            List<PricesUpdateRequest> pricesList = (List<PricesUpdateRequest>)event.getMessage().getObject1();

            List<PriceRequest> list2 = new ArrayList<>();

            for(PricesUpdateRequest A : pricesList){
                PriceRequest new_req = new PriceRequest(A.getPricesUpdateReqId()," manager id -"+A.getParkingManagerID(),A.getRequest());
                list2.add(new_req);
            }

            ObservableList<PriceRequest> list = FXCollections.observableArrayList(list2);
            NumberColMainWin.setCellValueFactory(new PropertyValueFactory<PriceRequest,Integer>("number"));
            ManagerNameCol.setCellValueFactory(new PropertyValueFactory<PriceRequest,String>("managerName"));
            RequestCol.setCellValueFactory(new PropertyValueFactory<PriceRequest,String>("Request"));
            RequestsTable.setItems(list);

        } else if(event.getMessage().getMessage().equals("show_prices_regional")){
            System.out.println("client side - prices update : ");
           String parkingName = (String) event.getMessage().getObject1();

            if(parkingName.equals("1")){

                List<PricesClass> pricesList = (List<PricesClass>)event.getMessage().getObject2();
                ObservableList<PricesClass> list = FXCollections.observableArrayList(pricesList);
                price.setCellValueFactory(new PropertyValueFactory<PricesClass,Integer>("price"));
                pricetype.setCellValueFactory(new PropertyValueFactory<PricesClass,String>("priceType"));
                PricesTable.setItems(list);

            }else if(parkingName.equals("2")){

                List<PricesClass> pricesList = (List<PricesClass>)event.getMessage().getObject2();
                ObservableList<PricesClass> list = FXCollections.observableArrayList(pricesList);
                price1.setCellValueFactory(new PropertyValueFactory<PricesClass,Integer>("price"));
                pricetype1.setCellValueFactory(new PropertyValueFactory<PricesClass,String>("priceType"));
                PricesTable1.setItems(list);

            } else {

                List<PricesClass> pricesList = (List<PricesClass>)event.getMessage().getObject2();
                ObservableList<PricesClass> list = FXCollections.observableArrayList(pricesList);
                price2.setCellValueFactory(new PropertyValueFactory<PricesClass,Integer>("price"));
                pricetype2.setCellValueFactory(new PropertyValueFactory<PricesClass,String>("priceType"));
                PricesTable2.setItems(list);

            }


        } else if(event.getMessage().getMessage().equals("pdf_regional")){
            //todo do stuuffff
//            Alert alert = new Alert(Alert.AlertType.WARNING,
//                    "PDF SENT"
//            );
//            alert.show();

        }







    }

    @FXML
    void requestTF(ActionEvent event) {

    }


    @FXML
    void showPrices1(ActionEvent event) {
        Message msg = new Message("show_prices1_regional");
        //send to server
        try{
            SimpleClient.getClient().sendToServer(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void showPrices2(ActionEvent event) {
        Message msg = new Message("show_prices2_regional");
        //send to server
        try{
            SimpleClient.getClient().sendToServer(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void showPrices3(ActionEvent event) {
        Message msg = new Message("show_prices3_regional");
        //send to server
        try{
            SimpleClient.getClient().sendToServer(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void submit1(ActionEvent event) {
        Message msg=new Message("alterPrices1_regional");
        //data stored in seperate objects
        // ocasional price -> object1
        // preOrder price -> object2
        // partTime price -> object3
        // FullSubs price -> object4
        // Multi price -> object5
        msg.setObject1(OcasionalTF.getText());
        msg.setObject2(PreOrderTF.getText());
        msg.setObject3(PartTimeTF1.getText());
        msg.setObject4(FullSubsTF.getText());
        msg.setObject5(MultiTF.getText());

       OcasionalTF.setText("sent");
       PreOrderTF.setText("sent");
       PartTimeTF1.setText("sent");
       FullSubsTF.setText("sent");
       MultiTF.setText("sent");



        try {
            SimpleClient.getClient().sendToServer(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void submit2(ActionEvent event) {
        Message msg=new Message("alterPrices2_regional");
        //data stored in seperate objects
        // ocasional price -> object1
        // preOrder price -> object2
        // partTime price -> object3
        // FullSubs price -> object4
        // Multi price -> object5
        msg.setObject1(OcasionalTF1.getText());
        msg.setObject2(PreOrderTF1.getText());
        msg.setObject3(PartTimeTF2.getText());
        msg.setObject4(FullSubsTF1.getText());
        msg.setObject5(MultiTF1.getText());

        OcasionalTF1.setText("sent");
        PreOrderTF1.setText("sent");
        PartTimeTF2.setText("sent");
        FullSubsTF1.setText("sent");
        MultiTF1.setText("sent");



        try {
            SimpleClient.getClient().sendToServer(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void submit3(ActionEvent event) {
        Message msg=new Message("alterPrices3_regional");
        //data stored in separate objects
        // occasional price -> object1
        // preOrder price -> object2
        // partTime price -> object3
        // FullSubs price -> object4
        // Multi price -> object5
        msg.setObject1(OcasionalTF2.getText());
        msg.setObject2(PreOrderTF2.getText());
        msg.setObject3(PartTimeTF3.getText());
        msg.setObject4(FullSubsTF2.getText());
        msg.setObject5(MultiTF2.getText());

        OcasionalTF2.setText("sent");
        PreOrderTF2.setText("sent");
        PartTimeTF3.setText("sent");
        FullSubsTF2.setText("sent");
        MultiTF2.setText("sent");


        try {
            SimpleClient.getClient().sendToServer(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void initialize() {

        EventBus.getDefault().register(this);

    }

    public void PartTimeTF3(ActionEvent event) {
    }

    public void PartTimeTF2(ActionEvent event) {
    }
}
