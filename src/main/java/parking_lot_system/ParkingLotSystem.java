package parking_lot_system;

import java.util.HashMap;
import java.util.Random;

public class ParkingLotSystem {

    public enum ParkingLotStatus {
        PARKING_LOT_FULL, PARKING_LOT_EMPTY
    }

    public static final int CAPACITY = 2;
    public ParkingLotStatus status = ParkingLotStatus.PARKING_LOT_EMPTY;
    public AirportSecurity airportSecurity = new AirportSecurity();

    HashMap<Integer, Car> parkingLots = new HashMap<>(CAPACITY);

    public void parkCar(Car car) {


        if (this.status.equals(ParkingLotStatus.PARKING_LOT_EMPTY)) {
            int parkingLot = this.getEmptyParkingLot();
            car.setLotNo(parkingLot);
            parkingLots.put(parkingLot, car);
            car.setParkedStatus(true);
            isParkingLotEmpty();

        } else {
            System.out.println("Parking lot is full");
        }
    }

    private int getEmptyParkingLot() {
        int lotNumber = 0;

        do {
            Random randomLotNumber = new Random();
            lotNumber = randomLotNumber.nextInt(CAPACITY) + 1;
        } while (parkingLots.containsKey(lotNumber));
        return lotNumber;
    }

    public void unparkCar(Car car1) {
        Car removed = parkingLots.remove(car1.getLotNo());
        isParkingLotEmpty();
    }

    private void isParkingLotEmpty() {
        if (parkingLots.size() != CAPACITY) {
            this.status = ParkingLotStatus.PARKING_LOT_EMPTY;
            airportSecurity.securityStatus = this.status;
        } else {
            this.status = ParkingLotStatus.PARKING_LOT_FULL;
            airportSecurity.securityStatus = this.status;
        }
    }
}
