package parking_lot_system;

public class AirportSecurity implements ParkingLotObserver {
    private static boolean parkingStatus;

    public void updateStatus(boolean status) {
        parkingStatus = status;
    }

    public static boolean isParkingStatus() {
        return parkingStatus;
    }
}
