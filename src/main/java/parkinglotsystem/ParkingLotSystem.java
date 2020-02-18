package parkinglotsystem;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.IntStream;

public class ParkingLotSystem {

    public int CAPACITY;
    public int NUMBER_OF_LOTS;
    public int ROW_CAPACITY;
    public int RESERVED_LOT_FOR_HANDICAP = 1;

    ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
    AirportSecurity airportSecurity = new AirportSecurity();
    ObserversHandler observersHandler = new ObserversHandler();

    public Map<Integer, TreeMap<Integer, ParkedVehicle>> parkingLots = new TreeMap<>();
    public boolean parkingStatus;

    public ParkingLotSystem(int capacity, int numberoflots) {
        CAPACITY = capacity;
        NUMBER_OF_LOTS = numberoflots;
        ROW_CAPACITY = CAPACITY / NUMBER_OF_LOTS;

        IntStream.range(1, NUMBER_OF_LOTS + 1)
                .forEach(i -> parkingLots.put(i, getTreeMap()));

        observersHandler.registerObserver(parkingLotOwner);
        observersHandler.registerObserver(airportSecurity);
    }

    private TreeMap<Integer, ParkedVehicle> getTreeMap() {
        TreeMap<Integer, ParkedVehicle> parkingSpots = new TreeMap<>();
        IntStream.range(1, ROW_CAPACITY + 1).forEach(i -> parkingSpots.put(i, null));
        return parkingSpots;
    }

    public boolean isVehicleParked(ParkedVehicle parkedVehicle) {
        if (parkingLots.entrySet()
                .stream()
                .filter(parkingLots -> parkingLots.getValue().containsValue(parkedVehicle))
                .count() > 0) {
            return true;
        }
        throw new ParkingLotException("Car is not parked", ParkingLotException.ExceptionType.VEHICLE_NOT_PARKED);
    }

    public boolean parkVehicle(ParkedVehicle parkedVehicle, int... lotNo) throws ParkingLotException {
        if (isParkingSlotEmpty() && parkedVehicle.driverType == Driver.HANDICAP) {
            return getSpotForHandicapDriver(parkedVehicle);
        } else if (isParkingSlotEmpty()) {
            int parkingLot = this.getEmptyParkingLot(lotNo);
            int spotNumber = this.getEmptySpot(parkingLot, lotNo);
            parkedVehicle.lotNo = parkingLot;
            parkedVehicle.spotNo = spotNumber;
            parkedVehicle.setParkedTime(LocalDateTime.now());
            parkingLots.get(parkingLot).put(spotNumber, parkedVehicle);
            isParkingSlotEmpty();
            System.out.println(parkingLots.values());
            return true;
        } else {
            throw new ParkingLotException("Lot is full", ParkingLotException.ExceptionType.LOT_FULL);
        }
    }

    private boolean getSpotForHandicapDriver(ParkedVehicle parkedVehicle) {
        parkedVehicle.lotNo = RESERVED_LOT_FOR_HANDICAP;
        Map.Entry<Integer, ParkedVehicle> spotForHandicap = parkingLots.get(RESERVED_LOT_FOR_HANDICAP).entrySet()
                .stream().filter(parkingSpot -> parkingSpot.getValue() == null || parkingSpot.getValue().driverType != Driver.HANDICAP)
                .findFirst().get();
        ParkedVehicle value = spotForHandicap.getValue();
        parkedVehicle.spotNo = spotForHandicap.getKey();
        parkingLots.get(RESERVED_LOT_FOR_HANDICAP).put(spotForHandicap.getKey(), parkedVehicle);
        if (value != null)
            this.parkVehicle(value);
        return true;
    }

    private int getEmptySpot(int parkingLot, int... spotNo) {
        if (spotNo.length != 0 && parkingLots.get(spotNo[0]).get(spotNo[1]) == null) {
            return spotNo[1];
        }
        Integer key = parkingLots.get(parkingLot)
                .entrySet().stream()
                .filter(spotNumber -> spotNumber.getValue() == null)
                .findFirst().get().getKey();
        return key;
    }

    private int getEmptyParkingLot(int[] lotNo) {
        if (lotNo.length != 0 && parkingLots.get(lotNo[0]).get(lotNo[1]) == null) {
            return lotNo[0];
        }
        int lotNumber = parkingLots.entrySet()
                .stream()
                .sorted(Comparator.comparing(lots -> lots.getValue()
                        .entrySet().stream().filter(spot -> spot.getValue() == null).count()))
                .reduce((first, second) -> second)
                .orElse(null)
                .getKey();
        return lotNumber;
    }

    public ParkedVehicle unparkCar(ParkedVehicle parkedVehicle) throws ParkingLotException {
        if (isVehicleParked(parkedVehicle)) {
            int carParkedLotNumber = parkedVehicle.lotNo;
            int carParkedSpotNumber = parkedVehicle.spotNo;
            LocalDateTime parkedTime = parkingLots.get(carParkedLotNumber).get(carParkedSpotNumber).parkedTime;
            ParkingLotOwner.parkedDuration = parkedTime;
            ParkedVehicle unparkedCar = parkingLots.get(carParkedLotNumber).get(carParkedSpotNumber);
            parkingLots.get(carParkedLotNumber).put(carParkedSpotNumber, null);
            isParkingSlotEmpty();
            return unparkedCar;
        }
        return null;
    }

    public int findCarParkedSlotNumber(ParkedVehicle parkedVehicle1) {
        return parkedVehicle1.lotNo;
    }

    private boolean isParkingSlotEmpty() throws ParkingLotException {
        if (parkingLots.entrySet().stream()
                .filter(integerArrayListEntry -> integerArrayListEntry.getValue().containsValue(null))
                .count() > 0)
            this.parkingStatus = false;
        else
            this.parkingStatus = true;
        observersHandler.notifyObservers(parkingStatus);
        return !parkingStatus;
    }

    public List<ParkedVehicle> getCarDetails(String carDetails) {
        List<ParkedVehicle> sortedData = new ArrayList<ParkedVehicle>();
        parkingLots.entrySet()
                .stream()
                .forEach(integerTreeMapEntry -> integerTreeMapEntry.getValue().entrySet().stream()
                .filter(integerParkedVehicleEntry -> integerParkedVehicleEntry.getValue().toString().contains(carDetails))
                .forEach(sortByColor ->{sortedData.add(sortByColor.getValue());
                }));
        System.out.println(sortedData.get(0));
        return sortedData;
    }
}
