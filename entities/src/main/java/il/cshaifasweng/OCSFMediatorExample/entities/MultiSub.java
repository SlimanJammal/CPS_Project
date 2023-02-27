package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "MULTISUBS" )
public class MultiSub extends PartialSub  implements Serializable {


//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "Subscriptionssssubsid_")
//    private List<PartialSub> cars;

    public MultiSub() {
        /** very important if you add another constructor add this line!!*/

    }

    public void InsertToList(String CustomerID,String CarNumber )
    {

        PartialSub input = new PartialSub(CustomerID,CarNumber);
//        cars.add(input);
    }
    public void InsertToList(String CustomerID,String CarNumber , Date Date, String EntranceHour , String DepartureHour)
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

//    public List<PartialSub> getCars() {
//        return cars;
//    }

//    public void setCars(List<PartialSub> cars) {
//        this.cars = cars;
//    }

}
