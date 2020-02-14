package parking_lot_system;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.IntStream;

public class ParkingLotSystem {

    public final int CAPACITY ;

    public Map<Integer, ParkedVehicle> parkingLots = new TreeMap<Integer, ParkedVehicle>();

    public ParkingLotSystem(int capacity) {
        CAPACITY = capacity;
        IntStream.range(1, CAPACITY + 1)
                .forEach(i -> parkingLots.put(i, null));
    }


    public boolean isVehicleParked(ParkedVehicle parkedVehicle1) {
        if (parkingLots.containsValue(parkedVehicle1)) {
            return true;
        }
        throw new ParkingLotException("Car is not parked", ParkingLotException.ExceptionType.VEHICLE_NOT_PARKED);
    }

    public boolean parkCar(ParkedVehicle parkedVehicle, int... lotNo) throws ParkingLotException {
        if (this.parkingLots.containsValue(null)) {
            int parkingLot = this.getEmptyParkingLot(lotNo);
            parkedVehicle.setLotNo(parkingLot);
            parkedVehicle.setParkedTime(LocalDateTime.now());
            parkingLots.put(parkingLot, parkedVehicle);
            isParkingSlotEmpty();
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

    public boolean unparkCar(ParkedVehicle parkedVehicle) throws ParkingLotException {
        if (isVehicleParked(parkedVehicle)) {
            int carParkedLotNumber = findCarParkedSlotNumber(parkedVehicle);
            ParkingLotOwner.setParkedDuration(parkedVehicle.getParkedTime());
            parkingLots.put(carParkedLotNumber, null);
            isParkingSlotEmpty();
            return true;
        }
        return false;
    }

    public int findCarParkedSlotNumber(ParkedVehicle parkedVehicle1) {
        return parkedVehicle1.getLotNo();
    }

    private void isParkingSlotEmpty() throws ParkingLotException {
        if (parkingLots.containsValue(null)) {
            AirportSecurity.setLotIsEmpty();
            ParkingLotOwner.setLotIsEmpty();
        } else {
            AirportSecurity.setLotIsFull();
            ParkingLotOwner.setLotIsFull();
        }
    }
}
