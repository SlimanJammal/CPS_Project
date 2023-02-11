package il.cshaifasweng.OCSFMediatorExample.entities;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class parks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ParkId;
    @NotNull
    int dimintions;
    @NotNull
    boolean full;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "park")
    private List<ParkingSlot> Slots;

    public parks(){
    }
    public parks(int dims,boolean isfull)
    {
        this.dimintions=dims;
        this.full=isfull;
        for(int i=0;i<dimintions;i++)
        {
            for(int j=0;j<3;j++)
            {
                for(int k=0;k<3;k++)
                {
                    Slots.add(new ParkingSlot(i,j,k,"0",this));
                }
            }
        }
    }

    public boolean isFull() {
        return full;
    }

    public int getParkId() {
        return ParkId;
    }

    public int getDimintions() {
        return dimintions;
    }

    public List<ParkingSlot> getSlots() {
        return Slots;
    }

    public void setDimintions(int dimintions) {
        this.dimintions = dimintions;
    }

    public void setParkId(int parkId) {
        ParkId = parkId;
    }

    public void setFull(boolean full) {
        this.full = full;
    }

    public void setSlots(List<ParkingSlot> slots) {
        Slots = slots;
    }

}
