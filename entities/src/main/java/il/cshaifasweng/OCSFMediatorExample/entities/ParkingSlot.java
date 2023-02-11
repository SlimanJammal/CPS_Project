package il.cshaifasweng.OCSFMediatorExample.entities;

//import com.sun.istack.NotNull;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;

@Entity
@Table
public class ParkingSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int slotid ;
    @NotNull
    int x,y,z;
    String CurrentState;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ParkId")
    private parks park;
    public ParkingSlot(int a,int b,int c,String state,parks thispark)
    {
        x=a;
        y=b;
        z=c;
        CurrentState=state;
        park=thispark;

    }


    public ParkingSlot() {

    }
}
