package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Subscription" )
public class MultiSub extends Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    String MultiSubId;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "PartialSubId")
    private List<PartialSub> cars;

    public MultiSub() {
    }

    public void InsertToList(String CustomerID,String CarNumber )
    {
        MultiSubId = CustomerID;
        PartialSub input = new PartialSub(CustomerID,CarNumber);
        cars.add(input);
    }
    public void InsertToList(String CustomerID,String CarNumber , Date Date, String EntranceHour , String DepartureHour)
    {
        MultiSubId = CustomerID;
        String CarsNumbers[] = CarNumber.split(" ");
        for (int i =0 ; i < CarsNumbers.length ; i++)
        {
            PartialSub input = new PartialSub(CustomerID,CarsNumbers[i]);
            input.setStartDate(Date);
            input.setEntranceHour(EntranceHour);
            input.setDepartureHour(DepartureHour);
            cars.add(input);
        }
    }

    public List<PartialSub> getCars() {
        return cars;
    }

    public void setCars(List<PartialSub> cars) {
        this.cars = cars;
    }

}
