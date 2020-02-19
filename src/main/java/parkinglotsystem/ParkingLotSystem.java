package parkinglotsystem;

import parkinglotsystem.enumerate.Driver;
import parkinglotsystem.exception.ParkingLotException;
import parkinglotsystem.services.AirportSecurity;
import parkinglotsystem.services.ParkedVehicle;
import parkinglotsystem.services.ParkingLotOwner;
import parkinglotsystem.services.ParkingStatusObserver;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.IntStream;

public class ParkingLotSystem {

    private int PARKING_LOT_CAPACITY;
    private int NUMBER_OF_LOTS;
    private int ROW_CAPACITY;
    private String parkingAttendant = "xyz";


    ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
    AirportSecurity airportSecurity = new AirportSecurity();
    ParkingStatusObserver parkingStatusObserver = new ParkingStatusObserver();

    private Map<Integer, TreeMap<Integer, ParkedVehicle>> parkingLots = new TreeMap<>();
    private boolean parkingStatus;

    public ParkingLotSystem(int capacity, int numberoflots) {
        PARKING_LOT_CAPACITY = capacity;
        NUMBER_OF_LOTS = numberoflots;
        ROW_CAPACITY = PARKING_LOT_CAPACITY / NUMBER_OF_LOTS;

        IntStream.range(1, NUMBER_OF_LOTS + 1)
                .forEach(i -> parkingLots.put(i, getNullTreeMap()));

        parkingStatusObserver.registerObserver(parkingLotOwner);
        parkingStatusObserver.registerObserver(airportSecurity);
    }

    private TreeMap<Integer, ParkedVehicle> getNullTreeMap() {
        TreeMap<Integer, ParkedVehicle> parkingSpots = new TreeMap<>();
        IntStream.range(1, ROW_CAPACITY + 1).forEach(i -> parkingSpots.put(i, null));
        return parkingSpots;
    }

    public boolean isVehicleParked(ParkedVehicle parkedVehicle) {
        if (parkingLots.entrySet()
                .stream()
                .filter(parkingLot -> parkingLot.getValue().containsValue(parkedVehicle))
                .count() > 0) {
            return true;
        }
        throw new ParkingLotException("Car is not parked", ParkingLotException.ExceptionType.VEHICLE_NOT_PARKED);
    }

    public boolean parkVehicle(ParkedVehicle parkedVehicle) {
        if (isParkingSlotEmpty())
            return parkedVehicle.driver.getVehicleParked(parkedVehicle, this);
        throw new ParkingLotException("Lot is full", ParkingLotException.ExceptionType.LOT_FULL);
    }

    public boolean getSpotForNormalDriver(ParkedVehicle parkedVehicle) {
        if (parkedVehicle.lotNo == 0 || parkedVehicle.spotNo == 0) {
            int parkingLot = this.getParkingLot();
            int spotNumber = this.getEmptySpot(parkingLot);
            parkedVehicle.lotNo = parkingLot;
            parkedVehicle.spotNo = spotNumber;
        }
        parkedVehicle.attendantName = parkingAttendant;
        parkedVehicle.setParkedTime(LocalDateTime.now());
        parkingLots.get(parkedVehicle.lotNo).put(parkedVehicle.spotNo, parkedVehicle);
        isParkingSlotEmpty();
        return true;
    }

    public boolean getSpotForHandicapDriver(ParkedVehicle parkedVehicle) {
        parkedVehicle.lotNo = this.getParkingLot();
        Map.Entry<Integer, ParkedVehicle> spotForHandicap = parkingLots.get(parkedVehicle.lotNo)
                .entrySet()
                .stream()
                .filter(parkingSpot -> parkingSpot.getValue() == null || parkingSpot.getValue().driver != Driver.HANDICAP_DRIVER)
                .findFirst().get();

        ParkedVehicle parkedVehicle1 = spotForHandicap.getValue();
        parkedVehicle.spotNo = spotForHandicap.getKey();
        parkingLots.get(parkedVehicle.lotNo).put(spotForHandicap.getKey(), parkedVehicle);
        if (parkedVehicle1 != null)
            this.parkVehicle(parkedVehicle1);
        return true;
    }

    public boolean getSpotForLargeVehicleDriver(ParkedVehicle parkedVehicle) {
        int parkingLot = this.getParkingLot();
        int spotNumber = parkingLots.get(parkingLot)
                .entrySet()
                .stream()
                .filter(spotNumberForLargeVehicle -> spotNumberForLargeVehicle.getValue() == null)
                .filter(integerParkedVehicleEntry -> getFilterLargeEmptySpace(parkingLot, integerParkedVehicleEntry))
                .findFirst()
                .get()
                .getKey();
        parkedVehicle.lotNo = parkingLot;
        parkedVehicle.spotNo = spotNumber + 1;
        this.getSpotForNormalDriver(parkedVehicle);
        return true;
    }

    private boolean getFilterLargeEmptySpace(int parkingLot, Map.Entry<Integer, ParkedVehicle> integerParkedVehicleEntry) {
        Integer emptySpot = integerParkedVehicleEntry.getKey();
        for (int i = emptySpot + 1; i < emptySpot + 3; i++)
            if (parkingLots.get(parkingLot).get(i) != null)
                return false;
        return true;
    }


    private int getEmptySpot(int parkingLot) {
        return parkingLots.get(parkingLot)
                .entrySet().stream()
                .filter(spotNumber -> spotNumber.getValue() == null)
                .findFirst().get().getKey();
    }

    private int getParkingLot() {
        return parkingLots.entrySet()
                .stream()
                .sorted(Comparator.comparing(lots -> lots.getValue()
                        .entrySet().stream().filter(spot -> spot.getValue() == null).count()))
                .reduce((first, second) -> second)
                .orElse(null)
                .getKey();
    }

    public ParkedVehicle unparkCar(ParkedVehicle parkedVehicle) {
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

    private boolean isParkingSlotEmpty() {
        if (parkingLots.entrySet().stream()
                .filter(parkingSlot -> parkingSlot.getValue().containsValue(null))
                .count() > 0) {
            this.parkingStatus = false;
            parkingStatusObserver.notifyObservers(parkingStatus);
            return !parkingStatus;
        }
        this.parkingStatus = true;
        parkingStatusObserver.notifyObservers(parkingStatus);
        return !parkingStatus;
    }

    public List<ParkedVehicle> getCarDetails(String... carDetails) {
        List<ParkedVehicle> sortedVehicleByDetails = new ArrayList<>();

        parkingLots.entrySet()
                .stream()
                .forEach(integerTreeMapEntry -> integerTreeMapEntry.getValue().entrySet().stream()
                        .filter(slotNumber -> slotNumber.getValue() != null)
                        .filter(parkedVehicle -> getFilteredByCarDetails(parkedVehicle, carDetails))
                        .forEach(sortByDetails -> sortedVehicleByDetails.add(sortByDetails.getValue())
                        ));
        return sortedVehicleByDetails;
    }

    private boolean getFilteredByCarDetails(Map.Entry<Integer, ParkedVehicle> parkedVehicle, String[] carDetails) {
        for (int i = 0; i < carDetails.length; i++)
            if (!parkedVehicle.getValue().toString().contains(carDetails[i]))
                return false;
        return true;
    }
}
