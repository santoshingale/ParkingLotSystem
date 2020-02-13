package parking_lot_system;

import java.util.*;

public class ParkingLotSystem {

    public enum ParkingLotStatus {
        PARKING_LOT_FULL, PARKING_LOT_EMPTY
    }

    public static final int CAPACITY = 2;
    public ParkingLotStatus status = ParkingLotStatus.PARKING_LOT_EMPTY;

    public Map<Integer, ParkedVehicle> parkingLots = new TreeMap<Integer, ParkedVehicle>();

    public ParkingLotSystem() {

    }

    public boolean parkCar(ParkedVehicle parkedVehicle) {
        if (this.status.equals(ParkingLotStatus.PARKING_LOT_EMPTY)) {
            int parkingLot = this.getEmptyParkingLot();
            parkedVehicle.setLotNo(parkingLot);
            parkingLots.put(parkingLot, parkedVehicle);
            parkedVehicle.setParkedStatus(true);
            isParkingLotEmpty();
            return true;
        } else {
            System.out.println("Parking lot is full");
        }
        return false;
    }

    private int getEmptyParkingLot() {
        int lotNumber = 0;

        do {
            Random randomLotNumber = new Random();
            lotNumber = randomLotNumber.nextInt(CAPACITY) + 1;
        } while (parkingLots.containsKey(lotNumber));
        return lotNumber;
    }

    public boolean unparkCar(ParkedVehicle parkedVehicle1) {
        if (parkingLots.containsValue(parkedVehicle1)) {
            parkingLots.remove(parkedVehicle1.getLotNo());
            isParkingLotEmpty();
            return true;
        }
        return false;
    }

    private void isParkingLotEmpty() {
        if (parkingLots.size() != CAPACITY) {
            this.status = ParkingLotStatus.PARKING_LOT_EMPTY;
            AirportSecurity.setLotIsFull();
        } else {
            this.status = ParkingLotStatus.PARKING_LOT_FULL;
            AirportSecurity.setLotIsFull();
            ParkingLotOwner.setLotIsFull();
        }
    }
}
