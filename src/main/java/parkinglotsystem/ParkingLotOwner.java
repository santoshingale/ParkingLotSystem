package parkinglotsystem;

import java.time.LocalDateTime;

public class ParkingLotOwner implements ParkingLotObserver {

    private static boolean parkingStatus;
    public static LocalDateTime parkedDuration;

    public void updateStatus(boolean status) {
        parkingStatus = status;
    }

    public static boolean isParkingStatus() {
        return parkingStatus;
    }
}
