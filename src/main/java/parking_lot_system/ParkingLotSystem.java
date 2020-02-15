package parking_lot_system;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.IntStream;

public class ParkingLotSystem {

    public final int CAPACITY;
    public final int NUMBER_OF_LOTS;
    public final int ROW_CAPACITY;
    public Map<Integer, ParkedVehicle> parkingLots = new TreeMap<Integer, ParkedVehicle>();

    public ParkingLotSystem(int capacity, int numberoflots) {
        CAPACITY = capacity;
        NUMBER_OF_LOTS = numberoflots;
        ROW_CAPACITY = CAPACITY / NUMBER_OF_LOTS;
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
        int parkingLotRow = findParkingLotRow();
        Optional<Map.Entry<Integer, ParkedVehicle>> lot = parkingLots.entrySet()
                .stream()
                .filter(integerParkedVehicleEntry -> {
                    if (integerParkedVehicleEntry.getKey() > (parkingLotRow * ROW_CAPACITY - ROW_CAPACITY) && integerParkedVehicleEntry.getKey() <= (parkingLotRow * ROW_CAPACITY)) {
                        return true;
                    }
                    return false;
                })
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

    public int findParkingLotRow() {

        Map<Integer, Long> rangeMap = new HashMap<>();
        int second = LocalDateTime.now().getNano()/1000000;
        for (int i = 0; i < NUMBER_OF_LOTS; i++) {
            int range = (i * ROW_CAPACITY + 1);
            rangeMap.put(i + 1, parkingLots.entrySet().stream().filter(val -> {
                System.out.println(val.getKey());
                if (range <= val.getKey() && val.getKey() < (range + ROW_CAPACITY) && val.getValue() == null) {
                    return true;
                }
                return false;
            }).count());
        }
        int second1 = LocalDateTime.now().getNano()/1000000;
        System.out.println("excecution time-->"+(second1-second));
        Set<Map.Entry<Integer, ParkedVehicle>> entries = parkingLots.entrySet();

        Integer key = rangeMap.entrySet().stream().max(Comparator.comparing(integerLongEntry -> integerLongEntry.getValue())).get().getKey();
       // System.out.println(rangeMap.values());
        System.out.println(key);
        return key;
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
