package parking_lot_system;

import java.util.Date;

public class ParkedVehicle {


    private int lotNo;
    private String vehicleModel;
    private String number;
    private String color;
    private Date parkedDate;
    private boolean parkedStatus;


    public boolean getParkedStatus() {
        return parkedStatus;
    }

    public void setParkedStatus(boolean parkedStatus) {
        this.parkedStatus = parkedStatus;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Date getParkedDate() {
        return parkedDate;
    }

    public void setParkedDate(Date parkedDate) {
        this.parkedDate = parkedDate;
    }

    public ParkedVehicle(String vehicleModel, String number, Date parkedDate, String color) {
        this.vehicleModel = vehicleModel;
        this.number = number;
        this.color = color;
        this.parkedDate = parkedDate;
    }

    public int getLotNo() {
        return lotNo;
    }

    public void setLotNo(int lotNo) {
        this.lotNo = lotNo;
    }

    @Override
    public String toString() {
        return "Car{" +
                "carModel='" + vehicleModel + '\'' +
                ", number='" + number + '\'' +
                ", color='" + color + '\'' +
                ", parkedDate=" + parkedDate +
                '}';
    }
}
