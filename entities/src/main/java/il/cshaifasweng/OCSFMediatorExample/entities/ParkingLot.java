package il.cshaifasweng.OCSFMediatorExample.entities;


import java.io.Serializable;


public class ParkingLot implements Serializable {
    private int parking_id;
    static int count = 0;
    private int width;
    private int slots_num;
    public ParkingLot(int width_)
    {
        slots_num = width_ *9;
        parking_id = count;
        count++;
        width=width_;
    }

    public ParkingLot() {
    }

    public int getSlots_num() {
        return slots_num;
    }

    public int getParking_id() {
        return parking_id;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}