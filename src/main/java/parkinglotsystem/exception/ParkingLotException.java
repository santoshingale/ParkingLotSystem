package parkinglotsystem.exception;

public class ParkingLotException extends RuntimeException{

    public ExceptionType type;

    public enum ExceptionType {
        LOT_FULL, VEHICLE_NOT_PARKED, NO_SUCH_VEHICLE;
    }

    public ParkingLotException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }
}
