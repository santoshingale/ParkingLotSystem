package parkinglotsystem;

public class AirportSecurity implements ParkingLotObserver {
    public static boolean securityStatus;

    public void updateStatus(boolean status) {
        securityStatus = status;
    }

}
