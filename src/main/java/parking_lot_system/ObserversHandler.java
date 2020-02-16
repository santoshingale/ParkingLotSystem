package parking_lot_system;

import java.util.ArrayList;

public class ObserversHandler {
    private ArrayList<ParkingLotObserver> observers = new ArrayList<ParkingLotObserver>();

    public void registerObserver(ParkingLotObserver observer) {
        observers.add(observer);
        System.out.println(observer.toString());
    }

    public void removeObserver(ParkingLotObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers(boolean parkingStatus) {
        for (ParkingLotObserver ob : observers) {
            ob.updateStatus(parkingStatus);
        }
    }
}
