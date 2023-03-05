package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Complaint;
import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.PricesUpdateRequest;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomerService {

    @FXML
    private Button AcceptBtn;

    @FXML
    private Button DeclineBtn;

    @FXML
    private TableColumn<PriceRequest, String> ManagerNameCol;

    @FXML
    private TableColumn<PriceRequest, Integer> NumberColMainWin;

    @FXML
    private TableColumn<PriceRequest, String> RequestCol;

    @FXML
    private TableView<PriceRequest> RequestsTable;

    @FXML
    private Button backbtn;

    @FXML
    private Button refreshRequestsBTN;

    @FXML
    private TextField requestTF;

    @FXML
    void AcceptBtn(ActionEvent event) {
        Message ms = new Message("cs_accept");
        ms.setObject1(requestTF.getText());
        try {
            SimpleClient.getClient().sendToServer(ms);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void DeclineBtn(ActionEvent event) {
        Message ms = new Message("cs_decline");
        ms.setObject1(requestTF.getText());
        try {
            SimpleClient.getClient().sendToServer(ms);
        } catch (IOException e) {
            e.printStackTrace();
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
    void refreshRequestsBTN(ActionEvent event) {
            Message ms = new Message("cs_refresh");

        try {
            SimpleClient.getClient().sendToServer(ms);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void requestTF(ActionEvent event) {

    }


    @Subscribe
    public void handelstuff(CustomerServiceEvent event){
        System.out.println("back to customer service client");

        List<Complaint> Complaints = (List<Complaint>)event.getMessage().getObject1();

        List<PriceRequest> list2 = new ArrayList<>();

        for(Complaint A : Complaints){
            PriceRequest new_req = new PriceRequest(A.getComplaintId_()," customer id -"+A.getCustomerId_(),A.getComplaintText());
            list2.add(new_req);
        }

        ObservableList<PriceRequest> list = FXCollections.observableArrayList(list2);
        NumberColMainWin.setCellValueFactory(new PropertyValueFactory<PriceRequest,Integer>("number"));
        ManagerNameCol.setCellValueFactory(new PropertyValueFactory<PriceRequest,String>("managerName"));
        RequestCol.setCellValueFactory(new PropertyValueFactory<PriceRequest,String>("Request"));
        RequestsTable.setItems(list);
    }

}
