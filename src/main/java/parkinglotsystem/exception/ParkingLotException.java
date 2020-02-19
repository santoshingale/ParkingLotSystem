package parkinglotsystem.exception;

public class ParkingLotException extends RuntimeException{

    public static ExceptionType type;

    public enum ExceptionType {
        LOT_FULL, VEHICLE_NOT_PARKED;
    }

    public ParkingLotException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }
}
