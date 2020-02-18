package parkinglotsystem;

import java.time.LocalDateTime;

public class ParkedVehicle {


    public int lotNo;
    public int spotNo;
    public Driver driverType;
    public LocalDateTime parkedTime;
    public VehicleSize vehicleSize;

    public ParkedVehicle(Driver driverType, VehicleSize vehicleSize) {
        this.driverType = driverType;
        this.vehicleSize = vehicleSize;
    }

    public void setParkedTime(LocalDateTime parkedTime) {
        this.parkedTime = parkedTime;
    }


}
