package parkinglotsystem.services;


import java.util.ArrayList;

public class ParkingStatusObserver {
    private ArrayList<ParkingLotObserver> observers = new ArrayList<>();

    public void registerObserver(ParkingLotObserver observer) {
        observers.add(observer);
    }

    public void notifyObservers(boolean parkingStatus) {
        for (ParkingLotObserver ob : observers) {
            ob.updateStatus(parkingStatus);
        }
    }
}
