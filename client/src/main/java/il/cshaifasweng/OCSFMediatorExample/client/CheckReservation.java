package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.PreOrder;
import il.cshaifasweng.OCSFMediatorExample.entities.PricesClass;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.beans.Expression;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Vector;

public class CheckReservation {

    @FXML
    private Button BackBtn;

    @FXML
    private TextField CarIdTB;


    @FXML
    private TextField IdTf;

    @FXML
    private TextField ParkingNameTf;

    @FXML
    private TextArea screenTf;

    @FXML
    private RadioButton Radio1;


    @FXML
    private Button cancelBTN;


    @FXML
    private RadioButton Radio2;

    @FXML
    private RadioButton Radio3;

    @FXML
    private Label ErrorLabel;

    @FXML
    private Button SubmitBtn;


    @FXML
    private TableColumn<PreOrder, Integer> ID;

    @FXML
    private TableColumn<PreOrder, LocalDate> arrivalDate;

    @FXML
    private TableColumn<PreOrder, LocalTime> arrivalTime;

    @FXML
    private TableColumn<PreOrder, LocalDate> departureDate;

    @FXML
    private TableColumn<PreOrder, LocalTime> departureTime;

    @FXML
    private TableColumn<PreOrder, String> parking;


    @FXML
    private TableView<PreOrder> tableView;


    @FXML
    void BackBtn(ActionEvent event) {
        DataSingleton data = DataSingleton.getInstance();
        String previousPage = data.getCaller();
        try {
            App.setRoot(previousPage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void CancelBTN(ActionEvent event) {

        PreOrder selectedOrder = tableView.getSelectionModel().getSelectedItem();
        Message temp = new Message("CancelReservation");
        temp.setObject1(selectedOrder);
        try{
            SimpleClient.getClient().sendToServer(temp);
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    @FXML
    void SubmitBtn(ActionEvent event) {
        Message temp = new Message("CheckReservation");





        temp.setObject1(IdTf.getText());
        temp.setObject2(CarIdTB.getText());

        IdTf.clear();
        CarIdTB.clear();
//        ParkingNameTf.clear();

        try
        {
            for(int i=0;i<IdTf.getLength();i++)
            {
                if(IdTf.getText().charAt(i)<'0'||IdTf.getText().charAt(i)>'9')
                {
                    IdTf.setText("invalidInput");
                }
            }
            if(!IdTf.getText().equals("invalidInput")) {
                SimpleClient.getClient().sendToServer(temp);
            }
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }
    @FXML
    void OnRadioButton(ActionEvent event) {

    }

    @Subscribe
    public void complaintHandleFromServer(CheckReservationEvent event){

        if(event.getMessage().getObject1().toString().equals("cancel")){
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.WARNING,event.getMessage().getObject2().toString()
                );
                alert.show();
            });
        }else{
            System.out.println("inside check reserve handle");


            Vector<PreOrder> vec = (Vector<PreOrder>) event.getMessage().getObject1();


            ObservableList<PreOrder> preOrders = FXCollections.observableArrayList();
            for (PreOrder order : vec){
                System.out.println(order.getCarNumber());
                preOrders.add(order);
            }
            tableView.setItems(preOrders);


        }



//       screenTf.setText(msg.getObject1().toString());
    }
    @FXML
    void initialize() {
        EventBus.getDefault().register(this);
        ID.setCellValueFactory(new PropertyValueFactory<PreOrder,Integer>("id_"));
        arrivalDate.setCellValueFactory(new PropertyValueFactory<PreOrder,LocalDate>("entranceDate"));
        arrivalTime.setCellValueFactory(new PropertyValueFactory<PreOrder, LocalTime>("entranceTime"));
        departureDate.setCellValueFactory(new PropertyValueFactory<PreOrder,LocalDate>("exitDate"));
        departureTime.setCellValueFactory(new PropertyValueFactory<PreOrder,LocalTime>("exitTime"));
        parking.setCellValueFactory(new PropertyValueFactory<PreOrder,String>("Parking_requested"));

    }

    @FXML
    void UserIDTextChange(KeyEvent event){
        //Validate Input!
        InputValidation test = new InputValidation();
        if(test.CustomerIDValidation(IdTf.getText().toString()))
        {
            ErrorLabel.setText("");

        }
        else
        {
            if(IdTf.getText().toString().equals(""))
            {
                ErrorLabel.setText("User ID is not valid! Please try again.");
                IdTf.setText("");
            }
            else
            {
                ErrorLabel.setText("User ID is empty, please fill it up!");
                IdTf.setText("");
            }
        }
    }

@FXML
    void CarIDTextChange(KeyEvent event){
        //Validate Input!
        InputValidation test = new InputValidation();
        if(test.CarIDValidation(CarIdTB.getText().toString()))
        {
            ErrorLabel.setText("");

        }
        else
        {
            if(!IdTf.getText().toString().equals(""))
            {
                ErrorLabel.setText("Car ID is not valid! Please try again.");
                CarIdTB.setText("");
            }
            else
            {
                ErrorLabel.setText("Car ID is empty, please fill it up!");
                CarIdTB.setText("");
            }
        }
    }



}
