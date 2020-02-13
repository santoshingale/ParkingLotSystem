package parking_lot_system;

public class ParkingLotException extends RuntimeException{

    ExceptionType type;

    enum ExceptionType {
        LOT_FULL;
    }

    public ParkingLotException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }
}
