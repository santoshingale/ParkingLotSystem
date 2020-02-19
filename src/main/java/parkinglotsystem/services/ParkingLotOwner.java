package parkinglotsystem.services;

import parkinglotsystem.services.ParkingLotObserver;

import java.time.LocalDateTime;

public class ParkingLotOwner implements ParkingLotObserver {

    public static boolean parkingStatus;
    public static LocalDateTime parkedDuration;

    public void updateStatus(boolean status) {
        parkingStatus = status;
    }
}
