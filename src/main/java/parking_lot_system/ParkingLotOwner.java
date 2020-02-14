package parking_lot_system;

import java.time.LocalDateTime;

public class ParkingLotOwner {
    private static boolean parkingStatus;
    private static LocalDateTime parkedDuration;


    public static void setLotIsFull() {
        parkingStatus = true;
    }

    public static void setLotIsEmpty() {
        parkingStatus = false;
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
