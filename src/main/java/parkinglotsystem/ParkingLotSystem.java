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

    private final int PARKING_LOT_CAPACITY;
    private final int NUMBER_OF_LOTS;
    private final int PARKING_ROW_CAPACITY;
    private final String parkingAttendant = "xyz";


    ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
    AirportSecurity airportSecurity = new AirportSecurity();
    ParkingStatusObserver parkingStatusObserver = new ParkingStatusObserver();

    private Map<Integer, TreeMap<Integer, ParkedVehicle>> parkingLots = new TreeMap<>();
    private boolean parkingStatus;

    public ParkingLotSystem(int capacity, int numberoflots) {
        PARKING_LOT_CAPACITY = capacity;
        NUMBER_OF_LOTS = numberoflots;
        PARKING_ROW_CAPACITY = PARKING_LOT_CAPACITY / NUMBER_OF_LOTS;
        IntStream.range(1, NUMBER_OF_LOTS + 1)
                .forEach(i -> parkingLots.put(i, getNullTreeMap()));

        parkingStatusObserver.registerObserver(parkingLotOwner);
        parkingStatusObserver.registerObserver(airportSecurity);
    }

    private TreeMap<Integer, ParkedVehicle> getNullTreeMap() {
        TreeMap<Integer, ParkedVehicle> parkingSpots = new TreeMap<>();
        IntStream.range(1, PARKING_ROW_CAPACITY + 1).forEach(i -> parkingSpots.put(i, null));
        return parkingSpots;
    }

    public boolean parkVehicle(ParkedVehicle parkedVehicle) {
        if (isVehicleParked(parkedVehicle)) throw new ParkingLotException("Vehicle already parked"
                , ParkingLotException.ExceptionType.VEHICLE_NOT_PARKED);
        if (isParkingSlotEmpty()) {
            return parkedVehicle.driver.getVehicleParked(parkedVehicle, this);
        }
        throw new ParkingLotException("Parking lot is full", ParkingLotException.ExceptionType.LOT_FULL);
    }

    public boolean getSpotForNormalDriver(ParkedVehicle parkedVehicle) {
        if (parkedVehicle.lotNo == 0 || parkedVehicle.spotNo == 0) {
            int parkingLot = this.getParkingLot();
            int spotNumber = this.getEmptySpot(parkingLot);
            parkedVehicle.lotNo = parkingLot;
            parkedVehicle.spotNo = spotNumber;
            parkedVehicle.setParkedTime(LocalDateTime.now());
        }
        parkedVehicle.attendantName = parkingAttendant;
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
        parkedVehicle.parkedTime = LocalDateTime.now();
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
        if (spotNumber != PARKING_LOT_CAPACITY)
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

    public List<ParkedVehicle> getAllParkedVehicle(int... lotNumbers) {
        List<ParkedVehicle> allParkedVehicle = new ArrayList<>();
        parkingLots.entrySet()
                .stream().filter(parkingLot -> {
            if (lotNumbers.length == 0) {
                return true;
            } else {
                if (parkingLot.getKey() % lotNumbers[0] == 0)
                    return true;
            }
            return false;
        })
                .forEach(integerTreeMapEntry -> integerTreeMapEntry.getValue().entrySet().stream()
                        .filter(slotNumber -> slotNumber.getValue() != null)
                        .forEach(sortByDetails -> allParkedVehicle.add(sortByDetails.getValue())));
        return checkParkedVehicleList(allParkedVehicle);
    }

    public List<ParkedVehicle> getCarByDetails(VehicleDetails... vehicleDetails) {
        List<ParkedVehicle> sortedVehicleByDetails = new ArrayList<>();
        List<ParkedVehicle> allParkedVehicle = getAllParkedVehicle();
        allParkedVehicle.stream()
                .filter(parkingSlot -> getFilteredByCarDetails(parkingSlot, vehicleDetails)).
                forEach(sortByDetails -> sortedVehicleByDetails.add(sortByDetails));
        return checkParkedVehicleList(sortedVehicleByDetails);
    }

    public List<ParkedVehicle> getHandicapCarInLot(int... rowNo) {
        List<ParkedVehicle> sortedVehicleByDetails = new ArrayList<>();
        List<ParkedVehicle> allParkedVehicle = getAllParkedVehicle(rowNo[0]);
        allParkedVehicle.stream()
                .filter(parkingSlot -> parkingSlot.driver.toString().contains("HANDICAP_DRIVER")).
                forEach(sortByDetails -> sortedVehicleByDetails.add(sortByDetails));
        return sortedVehicleByDetails;
    }

    private boolean getFilteredByCarDetails(ParkedVehicle parkedVehicle, VehicleDetails[] carDetails) {
        for (int i = 0; i < carDetails.length; i++)
            if (!parkedVehicle.toString().toLowerCase().contains(carDetails[i].toString().toLowerCase()))
                return false;
        return true;
    }

    public List<ParkedVehicle> getVehicleByTime(int time) {
        List<ParkedVehicle> sortedVehicleByTime = new ArrayList<>();
        List<ParkedVehicle> allParkedVehicle = getAllParkedVehicle();
        allParkedVehicle.stream()
                .filter(parkedVehicle -> parkedVehicle.parkedTime.isAfter(LocalDateTime.now().minusMinutes(time)))
                .forEach(sortByDetails -> sortedVehicleByTime.add(sortByDetails));
        return checkParkedVehicleList(sortedVehicleByTime);
    }

    private List<ParkedVehicle> checkParkedVehicleList(List<ParkedVehicle> parkedVehicleList) {
        if (parkedVehicleList.size() > 0)
            return parkedVehicleList;
        throw new ParkingLotException("No such vehicle in lot", ParkingLotException.ExceptionType.NO_SUCH_VEHICLE);
    }
}
