package parkinglotsystem;

import parkinglotsystem.enumerate.VehicleDetails;
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

    private int parkingLotCapacity;
    private int numbOfLots;
    private int parkingRowCapacity;
    private String parkingAttendant = "xyz";


    ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
    AirportSecurity airportSecurity = new AirportSecurity();
    ParkingStatusObserver parkingStatusObserver = new ParkingStatusObserver();

    private Map<Integer, TreeMap<Integer, ParkedVehicle>> parkingLots = new TreeMap<>();
    private boolean parkingStatus;

    public ParkingLotSystem(int capacity, int numberoflots) {
        parkingLotCapacity = capacity;
        numbOfLots = numberoflots;
        parkingRowCapacity = parkingLotCapacity / numbOfLots;
        IntStream.range(1, numbOfLots + 1)
                .forEach(i -> parkingLots.put(i, getNullTreeMap()));

        parkingStatusObserver.registerObserver(parkingLotOwner);
        parkingStatusObserver.registerObserver(airportSecurity);
    }

    private TreeMap<Integer, ParkedVehicle> getNullTreeMap() {
        TreeMap<Integer, ParkedVehicle> parkingSpots = new TreeMap<>();
        IntStream.range(1, parkingRowCapacity + 1).forEach(i -> parkingSpots.put(i, null));
        return parkingSpots;
    }

    public boolean parkVehicle(ParkedVehicle parkedVehicle) {
        if(isVehicleParked(parkedVehicle)) throw new ParkingLotException("Vehicle already parked"
                , ParkingLotException.ExceptionType.VEHICLE_NOT_PARKED);
        if (isParkingSlotEmpty()) {
            return parkedVehicle.driver.getVehicleParked(parkedVehicle, this);
        }
        throw new ParkingLotException("Parking lot is full", ParkingLotException.ExceptionType.VEHICLE_NOT_PARKED);
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

    public boolean getSpotForLargeVehicle(ParkedVehicle parkedVehicle) {
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
        if (spotNumber != parkingLotCapacity)
            spotNumber += 1;
        parkedVehicle.spotNo = spotNumber;
        this.getSpotForNormalDriver(parkedVehicle);
        return true;
    }

    private boolean getFilterLargeEmptySpace(int parkingLot, Map.Entry<Integer, ParkedVehicle> integerParkedVehicleEntry) {
        Integer emptySpot = integerParkedVehicleEntry.getKey();
        if (parkingLots.get(parkingLot).get(emptySpot + 1) != null)
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
        throw new ParkingLotException("Car is not parked", ParkingLotException.ExceptionType.VEHICLE_NOT_PARKED);
    }

    private boolean isParkingSlotEmpty() {
        System.out.println(parkingLots.toString());
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

    public boolean isVehicleParked(ParkedVehicle parkedVehicle) {
        if (parkingLots.entrySet()
                .stream()
                .filter(parkingLot -> parkingLot.getValue().containsValue(parkedVehicle))
                .count() > 0) {
            return true;
        }
        return false;
    }

    public List<ParkedVehicle> getCarDetails(VehicleDetails... vehicleDetails) {
        List<ParkedVehicle> sortedVehicleByDetails = new ArrayList<>();

        parkingLots.entrySet()
                .stream()
                .forEach(integerTreeMapEntry -> integerTreeMapEntry.getValue().entrySet().stream()
                        .filter(slotNumber -> slotNumber.getValue() != null)
                        .filter(parkedVehicle -> getFilteredByCarDetails(parkedVehicle, vehicleDetails))
                        .forEach(sortByDetails -> sortedVehicleByDetails.add(sortByDetails.getValue())
                        ));
        return sortedVehicleByDetails;
    }

    private boolean getFilteredByCarDetails(Map.Entry<Integer, ParkedVehicle> parkedVehicle, VehicleDetails[] carDetails) {
        for (int i = 0; i < carDetails.length; i++)
            if (!parkedVehicle.getValue().toString().toLowerCase().contains(carDetails[i].toString().toLowerCase()))
                return false;
        return true;
    }
}
