package parking_lot_system;

import java.time.LocalDateTime;

public class ParkedVehicle {


    private int lotNo;
    private boolean parkedStatus;
    private LocalDateTime parkedTime;

    public LocalDateTime getParkedTime() {
        return parkedTime;
    }

    public void setParkedTime(LocalDateTime parkedTime) {
        this.parkedTime = parkedTime;
    }

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
