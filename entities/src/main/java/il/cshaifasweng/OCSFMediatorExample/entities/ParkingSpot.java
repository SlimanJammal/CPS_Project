package il.cshaifasweng.OCSFMediatorExample.entities;

//import com.sun.istack.NotNull;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;

@Entity
@Table
public class ParkingSpot {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int spotId ;
    @NotNull
    int x,y,z;
    String CurrentState;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ParkId")
    private Parks park;
    public ParkingSpot(int a, int b, int c, String state, Parks thispark)
    {
        x=a;
        y=b;
        z=c;
        CurrentState=state;
        park=thispark;

    }


    public ParkingSpot() {

    }
}
