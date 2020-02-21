package parkinglotsystem.model;

import parkinglotsystem.enumerate.DriverType;
import parkinglotsystem.enumerate.VehicleSize;

import java.time.LocalDateTime;

public class ParkedVehicle {


    public int lotNo;
    public int spotNo;
    public LocalDateTime parkedTime;
    public DriverType driverType;
    public String carColor;
    public String plateNumber;
    public String carManufacturer;
    public String attendantName;
    public VehicleSize vehicleSize;


    public ParkedVehicle(DriverType driverType, VehicleSize vehicleSize) {
        this.driverType = driverType;
        this.vehicleSize = vehicleSize;
    }

    public ParkedVehicle(DriverType driverType, VehicleSize vehicleSize, String carColor) {
        this.driverType = driverType;
        this.carColor = carColor;
        this.vehicleSize = vehicleSize;
    }

    public void setParkedTime(LocalDateTime parkedTime) {
        this.parkedTime = parkedTime;
    }

    @Override
    public String toString() {
        return "ParkedVehicle{" +
                "lotNo=" + lotNo +
                ", spotNo=" + spotNo +
                ", parkedTime=" + parkedTime +
                ", driver=" + driverType +
                ", carColor='" + carColor + '\'' +
                ", plateNumber='" + plateNumber + '\'' +
                ", carManufacturer='" + carManufacturer + '\'' +
                ", attendantName='" + attendantName + '\'' +
                '}';
    }
}