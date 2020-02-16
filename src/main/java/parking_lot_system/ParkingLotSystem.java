package parking_lot_system;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.IntStream;

public class ParkingLotSystem {

    public int CAPACITY;
    public int NUMBER_OF_LOTS;
    public int ROW_CAPACITY;

    public Map<Integer, ParkedVehicle> parkingLots = new TreeMap<Integer, ParkedVehicle>();
    public Map<Integer, Integer> parkingLotsCapacity = new TreeMap<>();
    ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
    AirportSecurity airportSecurity = new AirportSecurity();
    ObserversHandler observersHandler = new ObserversHandler();

    public boolean parkingStatus;
    int emptySpotCounter = 0;
    int lotNumber = 0;

    public ParkingLotSystem(int capacity, int numberoflots) {
        CAPACITY = capacity;
        NUMBER_OF_LOTS = numberoflots;
        ROW_CAPACITY = CAPACITY / NUMBER_OF_LOTS;
        IntStream.range(1, CAPACITY + 1)
                .forEach(i -> parkingLots.put(i, null));
        observersHandler.registerObserver(parkingLotOwner);
        observersHandler.registerObserver(airportSecurity);

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
        System.out.println(parkingLotRow);
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
        this.lotNumber = 0;
        parkingLots.entrySet()
                .stream()
                .filter(data -> data.getValue() == null)
                .map(this::findLotsEmptySpot).count();

        return this.parkingLotsCapacity.entrySet()
                .stream()
                .max(Comparator.comparing(integerLongEntry -> integerLongEntry.getValue()))
                .get()
                .getKey();
    }

    public int findLotsEmptySpot(Map.Entry<Integer, ParkedVehicle> entry) {
        if ((entry.getKey()) % ROW_CAPACITY == 0) {
            this.parkingLotsCapacity.put(lotNumber += 1, emptySpotCounter);
            emptySpotCounter = 0;
        }
        emptySpotCounter++;
        return 0;
    }

    public int findCarParkedSlotNumber(ParkedVehicle parkedVehicle1) {
        return parkedVehicle1.getLotNo();
    }

    private void isParkingSlotEmpty() throws ParkingLotException {
        if (parkingLots.containsValue(null))
            this.parkingStatus = false;
        else
            this.parkingStatus = true;
        observersHandler.notifyObservers(parkingStatus);
    }
}
