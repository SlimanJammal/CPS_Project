package il.cshaifasweng.OCSFMediatorExample.entities;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class Parks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ParkId;
    @NotNull
    int dimintions;
    @NotNull
    boolean full;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "park")
    private List<ParkingSpot> Spots;

    public Parks(){
    }
    public Parks(int dims, boolean isfull)
    {
        this.dimintions=dims;
        this.full=isfull;
        for(int i=0;i<dimintions;i++)
        {
            for(int j=0;j<3;j++)
            {
                for(int k=0;k<3;k++)
                {
                    Spots.add(new ParkingSpot(i,j,k,"0",this));
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

    public List<ParkingSpot> getSpots() {
        return Spots;
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

    public void setSpots(List<ParkingSpot> spots) {
        Spots = spots;
    }

}
