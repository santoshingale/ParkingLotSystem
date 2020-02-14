package parking_lot_system;

import java.util.*;
import java.util.stream.IntStream;

public class ParkingLotSystem {

    public static final int CAPACITY = 2;

    public Map<Integer, ParkedVehicle> parkingLots = new TreeMap<Integer, ParkedVehicle>();

    public ParkingLotSystem() {
        IntStream.range(1, CAPACITY + 1)
                .forEach(i -> parkingLots.put(i, null));
    }

    public boolean isVehicleParked(ParkedVehicle parkedVehicle1) {
        if (parkingLots.containsValue(parkedVehicle1)) {
            return true;
        }
        throw new ParkingLotException("Car is not parked", ParkingLotException.ExceptionType.VEHICLE_NOT_PARKED);    }

    public boolean parkCar(ParkedVehicle parkedVehicle, int... lotNo) throws ParkingLotException {
        if (this.parkingLots.containsValue(null)) {
            int parkingLot = this.getEmptyParkingLot(lotNo);
            parkedVehicle.setLotNo(parkingLot);
            parkingLots.put(parkingLot, parkedVehicle);
            isParkingLotEmpty();
            System.out.println(parkingLots.values());
            return true;
        } else {
            throw new ParkingLotException("Lot is full", ParkingLotException.ExceptionType.LOT_FULL);
        }
    }

    private int getEmptyParkingLot(int[] i) {
        if (i.length != 0 && parkingLots.get(i[0]) == null) {
            return i[0];
        }
        Optional<Map.Entry<Integer, ParkedVehicle>> lot = parkingLots.entrySet()
                .stream()
                .filter(emptyLot -> emptyLot.getValue() == null)
                .findFirst();
        return lot.get().getKey();
    }

    public boolean unparkCar(ParkedVehicle parkedVehicle1) throws ParkingLotException {
        if (isVehicleParked(parkedVehicle1)) {
            int carParkedLotNumber = findCarParkedLotNumber(parkedVehicle1);
            parkingLots.put(carParkedLotNumber, null);
            isParkingLotEmpty();
            return true;
        }
        return false;
    }

    public int findCarParkedLotNumber(ParkedVehicle parkedVehicle1) {
        return parkedVehicle1.getLotNo();
    }

    private void isParkingLotEmpty() throws ParkingLotException {
        if (parkingLots.containsValue(null)) {
            AirportSecurity.setLotIsEmpty();
            ParkingLotOwner.setLotIsEmpty();
        } else {
            AirportSecurity.setLotIsFull();
            ParkingLotOwner.setLotIsFull();
        }
    }
}
