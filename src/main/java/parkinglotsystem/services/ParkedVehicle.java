package parkinglotsystem.services;

import parkinglotsystem.enumerate.Driver;
import parkinglotsystem.enumerate.VehicleSize;

import java.time.LocalDateTime;

public class ParkedVehicle {


    public int lotNo;
    public int spotNo;
    public LocalDateTime parkedTime;
    public Driver driver;
    public String carColor;
    public String plateNumber;
    public String carManufacturer;
    public String attendantName;
    public VehicleSize vehicleSize;


    public ParkedVehicle(Driver driver, VehicleSize vehicleSize) {
        this.driver = driver;
        this.vehicleSize = vehicleSize;
    }

    public ParkedVehicle(Driver driver,VehicleSize vehicleSize, String carColor) {
        this.driver = driver;
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
                ", driver=" + driver +
                ", carColor='" + carColor + '\'' +
                ", plateNumber='" + plateNumber + '\'' +
                ", carManufacturer='" + carManufacturer + '\'' +
                ", attendantName='" + attendantName + '\'' +
                '}';
    }
}