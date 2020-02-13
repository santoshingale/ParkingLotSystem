package parking_lot_system;

public class ParkingLotOwner {
    private static boolean parkingStatus;


    public static void setLotIsFull() {
        parkingStatus = true;
    }

    public static void setLotIsEmpty() {
        parkingStatus = false;
    }

    public static boolean isParkingStatus() {
        return parkingStatus;
    }

}
