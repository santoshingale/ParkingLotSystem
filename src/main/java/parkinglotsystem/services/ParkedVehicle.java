package parkinglotsystem.services;

import parkinglotsystem.enumerate.Driver;

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


    public ParkedVehicle(Driver driver) {
        this.driver = driver;
    }

    public ParkedVehicle(Driver driver, String carColor) {
        this.driver = driver;
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
                ", parkedTime=" + parkedTime +
                ", driver=" + driver +
                ", carColor='" + carColor + '\'' +
                ", plateNumber='" + plateNumber + '\'' +
                ", carManufacturer='" + carManufacturer + '\'' +
                ", attendantName='" + attendantName + '\'' +
                '}';
    }
}