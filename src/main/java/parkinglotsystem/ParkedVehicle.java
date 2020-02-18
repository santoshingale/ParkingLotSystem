package parkinglotsystem;

import java.time.LocalDateTime;

public class ParkedVehicle {


    public int lotNo;
    public int spotNo;
    public Driver driverType;
    public LocalDateTime parkedTime;
    public VehicleSize vehicleSize;
    public String carColor;


    public ParkedVehicle(Driver driverType, VehicleSize vehicleSize) {
        this.driverType = driverType;
        this.vehicleSize = vehicleSize;
    }

    public ParkedVehicle(String carColor) {
        this.carColor = carColor;
    }

    public void setParkedTime(LocalDateTime parkedTime) {
        this.parkedTime = parkedTime;
    }

    @Override
    public String toString() {
        return "ParkedVehicle{" +
                "lotNo=" + lotNo +
                ", spotNo=" + spotNo +
                ", driverType=" + driverType +
                ", parkedTime=" + parkedTime +
                ", vehicleSize=" + vehicleSize +
                ", carColor='" + carColor + '\'' +
                '}';
    }
}
