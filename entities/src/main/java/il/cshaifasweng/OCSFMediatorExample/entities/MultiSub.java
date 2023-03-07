package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "MULTISUBS" )
public class MultiSub extends Subscription  implements Serializable {
    public void setStartDate(LocalDate startDate) {
        StartDate = startDate;
    }

    public void updateEndDate(){
        LocalDate temp = LocalDate.now();
        this.EndDate = temp.plusDays(28);
    }
    public void setEndDate(LocalDate endDate) {
        EndDate = endDate;
    }

    public void setCarNumber(String carNumber) {
        CarNumber = carNumber;
    }

    public void setCustomerId(String customerId) {
        CustomerId = customerId;
    }

    public LocalDate getStartDate() {
        return StartDate;
    }

    public LocalDate getEndDate() {
        return EndDate;
    }

    public String getCarNumber() {
        return CarNumber;
    }

    public String getCustomerId() {
        return CustomerId;
    }

    private LocalDate StartDate;
    private LocalDate EndDate;
    private String CarNumber;
    private String CustomerId;


//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "Subscriptionssssubsid_")
//    private List<PartialSub> cars;

//    public MultiSub(String ID) {
//        this.setSubscriptionNumber(ID);
//        /** very important if you add another constructor add this line!!*/
//
//    }

    public MultiSub(String id,String carnum) {
        this.CustomerId=id;
        this.CarNumber=carnum;
        this.setSubscriptionNumber(id);

        this.StartDate =  LocalDate.now();
        this.EndDate =  LocalDate.now();
        mail_sent =false;
    }

    public MultiSub() {

    }

    public void InsertToList(String CustomerID,String CarNumber )
    {

        PartialSub input = new PartialSub(CustomerID,CarNumber);
//        cars.add(input);
    }
    public void InsertToList(String CustomerID, String CarNumber , LocalDate Date, String EntranceHour , String DepartureHour)
    {

        String CarsNumbers[] = CarNumber.split(" ");
        for (int i =0 ; i < CarsNumbers.length ; i++)
        {
            PartialSub input = new PartialSub(CustomerID,CarsNumbers[i]);
            input.setStartDate(Date);
            input.setEntranceHour(EntranceHour);
            input.setDepartureHour(DepartureHour);
//            cars.add(input);
        }
    }

    public String getSubNum() {
        return getSubscriptionNumber();
    }

    public void print(){
        System.out.println("Customer ID = "+CustomerId);
        System.out.println("Subscription number= "+ getSubscriptionNumber());
        System.out.println("Car number= "+ getCarNumber());
        System.out.println("Subscription start= "+ getStartDate().toString());
        System.out.println("Subscription End= "+ getEndDate().toString());
    }

//    public List<PartialSub> getCars() {
//        return cars;
//    }

//    public void setCars(List<PartialSub> cars) {
//        this.cars = cars;
//    }

}
