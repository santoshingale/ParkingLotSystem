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
        return false;
    }

    public boolean parkCar(ParkedVehicle parkedVehicle, int... lotNo) throws ParkingLotException {
        if (this.parkingLots.containsValue(null)){
            int parkingLot = this.getEmptyParkingLot(lotNo);
            parkedVehicle.setLotNo(parkingLot);
            parkingLots.put(parkingLot, parkedVehicle);
            isParkingLotEmpty();
            System.out.println(parkingLots.values());
            return true;
        } else{
            throw new ParkingLotException("Lot is full", ParkingLotException.ExceptionType.LOT_FULL);
        }
    }

    private int getEmptyParkingLot(int[] i) {
        System.out.println(i.length);
        if (i.length != 0 && parkingLots.get(i[0]) == null) {
            System.out.println(i[0]);
            return i[0];
        }
        Optional<Map.Entry<Integer, ParkedVehicle>> lot = parkingLots.entrySet()
                .stream()
                .filter(emptyLot -> emptyLot.getValue() == null)
                .findFirst();
        System.out.println("hiii");
        return lot.get().getKey();
    }

    public boolean unparkCar(ParkedVehicle parkedVehicle1) throws ParkingLotException {
        if (parkingLots.containsValue(parkedVehicle1)) {
            parkingLots.put(parkedVehicle1.getLotNo(), null);
            isParkingLotEmpty();
            return true;
        }
        return false;
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
