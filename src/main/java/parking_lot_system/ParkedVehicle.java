package parking_lot_system;

import java.util.Date;

public class ParkedVehicle {


    private int lotNo;
    private boolean parkedStatus;

    public int getLotNo() {
        return lotNo;
    }

    public void setLotNo(int lotNo) {
        this.lotNo = lotNo;
    }

    public boolean isParkedStatus() {
        return parkedStatus;
    }

    public void setParkedStatus(boolean parkedStatus) {
        this.parkedStatus = parkedStatus;
    }
}
