package parking_lot_system;

import java.util.Date;

public class Car {


    private int lotNo;
    private String carModel;
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

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
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

    public Car(String carModel, String number, Date parkedDate, String color) {
        this.carModel = carModel;
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
                "carModel='" + carModel + '\'' +
                ", number='" + number + '\'' +
                ", color='" + color + '\'' +
                ", parkedDate=" + parkedDate +
                '}';
    }
}
