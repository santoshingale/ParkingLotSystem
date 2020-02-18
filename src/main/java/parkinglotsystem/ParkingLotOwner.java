package parkinglotsystem;

import java.time.LocalDateTime;

public class ParkingLotOwner implements ParkingLotObserver {
    private static boolean parkingStatus;
    private static LocalDateTime parkedDuration;

    public void updateStatus(boolean status) {
        parkingStatus = status;
    }

    public static LocalDateTime getParkedDuration() {
        return parkedDuration;
    }

    public static boolean isParkingStatus() {
        return parkingStatus;
    }

    public static void setParkedDuration(LocalDateTime parkedTime) {
        parkedDuration = parkedTime;
        System.out.println("parkedDuration" + parkedDuration);
    }
}
