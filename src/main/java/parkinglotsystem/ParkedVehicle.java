package parkinglotsystem;

import java.time.LocalDateTime;

public class ParkedVehicle {


    public int lotNo;
    public int spotNo;
    public Driver driverType;
    public LocalDateTime parkedTime;
    public VehicleSize vehicleSize;
    public String carColor;
    public String plateNumber;
    public String carManufacturer;
    public String attendantName;


    public ParkedVehicle(Driver driverType, VehicleSize vehicleSize) {
        this.driverType = driverType;
        this.vehicleSize = vehicleSize;
    }

    public ParkedVehicle(String carColor) {
        this.carColor = carColor;
    }

    public ParkedVehicle(String carColor, String carManufacturer) {
        this.carColor = carColor;
        this.carManufacturer = carManufacturer;
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
                ", plateNumber='" + plateNumber + '\'' +
                ", carManufacturer='" + carManufacturer + '\'' +
                ", attendantName='" + attendantName + '\'' +
                '}';
    }
}