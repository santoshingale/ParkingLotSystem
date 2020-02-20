package parkinglotsystem;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import parkinglotsystem.enumerate.Driver;
import parkinglotsystem.enumerate.VehicleDetails;
import parkinglotsystem.exception.ParkingLotException;
import parkinglotsystem.services.AirportSecurity;
import parkinglotsystem.services.ParkedVehicle;
import parkinglotsystem.services.ParkingLotOwner;

import java.time.LocalDateTime;
import java.util.List;

public class ParkingLotSystemTest {

    ParkedVehicle parkedVehicle = null;
    ParkingLotSystem parkingLotSystem = null;

    @Before
    public void setUp() throws Exception {
        parkedVehicle = new ParkedVehicle(Driver.SMALL_VEHICLE_DRIVER);
        parkingLotSystem = new ParkingLotSystem(2, 1);
    }

    @Test
    public void givenVehicle_whenItGetParked_shouldReturnTrue() throws ParkingLotException {
        Assert.assertTrue(parkingLotSystem.parkVehicle(parkedVehicle));
    }

    @Test
    public void givenVehicle_whenDriverUnparkingWrongVehicle_shouldReturnException() throws ParkingLotException {
        parkingLotSystem.parkVehicle(parkedVehicle);
        Assert.assertTrue(parkingLotSystem.isVehicleParked(parkedVehicle));
        parkingLotSystem.unparkCar(parkedVehicle);
        try {
            parkingLotSystem.isVehicleParked(parkedVehicle);
        } catch (ParkingLotException p) {
            Assert.assertEquals("Car is not parked", p.getMessage());
        }
    }

    @Test
    public void givenVehicle_whenParkingLotIsEmpty_shouldInformOwner() throws ParkingLotException {
        parkingLotSystem.parkVehicle(new ParkedVehicle(Driver.SMALL_VEHICLE_DRIVER));
        Assert.assertFalse(ParkingLotOwner.parkingStatus);
    }

    @Test
    public void givenVehicle_whenParkingLotIsFull_shouldInformOwner() throws ParkingLotException {
        parkingLotSystem.parkVehicle(new ParkedVehicle(Driver.SMALL_VEHICLE_DRIVER));
        parkingLotSystem.parkVehicle(new ParkedVehicle(Driver.SMALL_VEHICLE_DRIVER));
        Assert.assertTrue(ParkingLotOwner.parkingStatus);
    }

    @Test
    public void givenVehicle_whenParkingLotIsFull_shouldInformAirportSecurity() throws ParkingLotException {
        parkingLotSystem.parkVehicle(new ParkedVehicle(Driver.SMALL_VEHICLE_DRIVER));
        parkingLotSystem.parkVehicle(new ParkedVehicle(Driver.SMALL_VEHICLE_DRIVER));
        Assert.assertTrue(AirportSecurity.securityStatus);
    }

    @Test
    public void givenVehicle_whenVehicleIsAlreadyParked_shouldThrowException() throws ParkingLotException {
        ParkedVehicle parkedVehicle = new ParkedVehicle(Driver.SMALL_VEHICLE_DRIVER);
        try {
            parkingLotSystem.parkVehicle(parkedVehicle);
            parkingLotSystem.parkVehicle(parkedVehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_PARKED, e.type);
        }
    }

    @Test
    public void givenVehicle_whenLotIsFull_shouldThrowException() throws ParkingLotException {
        parkingLotSystem.parkVehicle(new ParkedVehicle(Driver.SMALL_VEHICLE_DRIVER));
        parkingLotSystem.parkVehicle(new ParkedVehicle(Driver.SMALL_VEHICLE_DRIVER));
        try {
            parkingLotSystem.parkVehicle(new ParkedVehicle(Driver.SMALL_VEHICLE_DRIVER));
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.LOT_FULL, e.type);
        }
    }

    @Test
    public void givenVehicle_whenParkingLotIsEmpty_shouldInformAirportSecurity() throws ParkingLotException {
        parkingLotSystem.parkVehicle(new ParkedVehicle(Driver.SMALL_VEHICLE_DRIVER));
        Assert.assertFalse(AirportSecurity.securityStatus);
    }

    @Test
    public void givenVehicle_whenCarIsUnparked_shouldChangeTheStatusOfParkingLotToEmpty() {
        parkingLotSystem.parkVehicle(new ParkedVehicle(Driver.SMALL_VEHICLE_DRIVER));
        parkingLotSystem.parkVehicle(parkedVehicle);
        Assert.assertTrue(ParkingLotOwner.parkingStatus);
        parkingLotSystem.unparkCar(parkedVehicle);
        Assert.assertFalse(ParkingLotOwner.parkingStatus);
    }

    @Test
    public void givenVehicle_whenDriverWantToUnpark_ShouldReturnParkedDateAndTimeToOwner() {
        parkingLotSystem.parkVehicle(parkedVehicle);
        int minute = LocalDateTime.now().getMinute();
        parkingLotSystem.unparkCar(parkedVehicle);
        Assert.assertEquals(minute, ParkingLotOwner.parkedDuration.getMinute());
    }

    @Test
    public void givenVehicles_whenLotIsEmpty_shouldAbleToParkEvenly() throws ParkingLotException {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(20, 5);
        parkingLotSystem.parkVehicle(new ParkedVehicle(Driver.SMALL_VEHICLE_DRIVER));
        parkingLotSystem.parkVehicle(new ParkedVehicle(Driver.SMALL_VEHICLE_DRIVER));
        parkingLotSystem.parkVehicle(new ParkedVehicle(Driver.SMALL_VEHICLE_DRIVER));
        parkingLotSystem.parkVehicle(new ParkedVehicle(Driver.SMALL_VEHICLE_DRIVER));
        parkingLotSystem.parkVehicle(new ParkedVehicle(Driver.SMALL_VEHICLE_DRIVER));
        parkingLotSystem.parkVehicle(new ParkedVehicle(Driver.SMALL_VEHICLE_DRIVER));
        parkingLotSystem.parkVehicle(parkedVehicle);
        Assert.assertEquals(2, parkedVehicle.spotNo);
    }

    @Test
    public void givenVehicle_whenDriverIsHandicap_shouldParkedAtNearestSpot() throws ParkingLotException {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(4, 2);
        parkingLotSystem.parkVehicle(new ParkedVehicle(Driver.SMALL_VEHICLE_DRIVER));
        parkingLotSystem.parkVehicle(parkedVehicle);
        ParkedVehicle parkedVehicle1 = new ParkedVehicle(Driver.HANDICAP_DRIVER);
        parkingLotSystem.parkVehicle(parkedVehicle1);
        Assert.assertEquals(1, parkedVehicle1.spotNo);
    }

    @Test
    public void givenVehicle_whenItisLarge_shouldAbleToPark() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(10, 1);
        ParkedVehicle parkedVehicle = new ParkedVehicle(Driver.LARGE_VEHICLE_DRIVER);
        parkingLotSystem.parkVehicle(new ParkedVehicle(Driver.SMALL_VEHICLE_DRIVER));
        ParkedVehicle parkedVehicle1 = new ParkedVehicle(Driver.SMALL_VEHICLE_DRIVER);
        parkingLotSystem.parkVehicle(parkedVehicle1);
        parkingLotSystem.parkVehicle(new ParkedVehicle(Driver.SMALL_VEHICLE_DRIVER));
        parkingLotSystem.parkVehicle(new ParkedVehicle(Driver.SMALL_VEHICLE_DRIVER));
        parkingLotSystem.unparkCar(parkedVehicle1);
        Assert.assertTrue(parkingLotSystem.parkVehicle(parkedVehicle));
    }

    @Test
    public void givenVehicle_whenWhiteVehiclesAreParked_shouldReturnThatVehicles() {
        ParkedVehicle parkedVehicle = new ParkedVehicle(Driver.SMALL_VEHICLE_DRIVER, "White");
        ParkedVehicle parkedVehicle1 = new ParkedVehicle(Driver.SMALL_VEHICLE_DRIVER, "Green");
        parkingLotSystem.parkVehicle(parkedVehicle);
        parkingLotSystem.parkVehicle(parkedVehicle1);
        List<ParkedVehicle> vehicleList = parkingLotSystem.getCarDetails(VehicleDetails.WHITE);
        Assert.assertEquals(vehicleList.get(0), parkedVehicle);
    }

    @Test
    public void givenVehicle_whenItIsBlueToyota_shouldReturnThatVehicles() {
        ParkedVehicle parkedVehicle = new ParkedVehicle(Driver.SMALL_VEHICLE_DRIVER, "Blue");
        parkedVehicle.carManufacturer = "Maruti";
        ParkedVehicle parkedVehicle1 = new ParkedVehicle(Driver.SMALL_VEHICLE_DRIVER, "Blue");
        parkedVehicle1.carManufacturer = "Toyoto";
        parkingLotSystem.parkVehicle(parkedVehicle);
        parkingLotSystem.parkVehicle(parkedVehicle1);
        List<ParkedVehicle> vehicleList = parkingLotSystem.getCarDetails(VehicleDetails.TOYOTO, VehicleDetails.BLUE);
        Assert.assertEquals(vehicleList.get(0), parkedVehicle1);
    }

    @Test
    public void givenVehicle_whenItIsBMW_shouldReturnThatVehicles() {
        ParkedVehicle parkedVehicle = new ParkedVehicle(Driver.SMALL_VEHICLE_DRIVER, "Blue");
        parkedVehicle.carManufacturer = "BMW";
        ParkedVehicle parkedVehicle1 = new ParkedVehicle(Driver.SMALL_VEHICLE_DRIVER, "Blue");
        parkedVehicle1.carManufacturer = "Toyota";
        System.out.println(Driver.HANDICAP_DRIVER.name());
        parkingLotSystem.parkVehicle(parkedVehicle);
        parkingLotSystem.parkVehicle(parkedVehicle1);
        List<ParkedVehicle> vehicleList = parkingLotSystem.getCarDetails(VehicleDetails.BMW);
        Assert.assertEquals(vehicleList.get(0), parkedVehicle);
    }

    @Test
    public void givenVehicles_whenItIsNotBMW_shouldThrowException() {
        ParkedVehicle parkedVehicle = new ParkedVehicle(Driver.SMALL_VEHICLE_DRIVER, "Blue");
        parkedVehicle.carManufacturer = "Toyoto";
        try {
            parkingLotSystem.getCarDetails(VehicleDetails.BMW);
        } catch (ParkingLotException p) {
            Assert.assertEquals(ParkingLotException.ExceptionType.NO_SUCH_VEHICLE, p.type);
        }
    }

    @Test
    public void givenVehicles_whenItIsParkedWithin30Min_shouldReturnThatVehicle() {
        parkingLotSystem.parkVehicle(new ParkedVehicle(Driver.SMALL_VEHICLE_DRIVER));
        parkingLotSystem.parkVehicle(new ParkedVehicle(Driver.SMALL_VEHICLE_DRIVER));
        List<ParkedVehicle> vehicleList = parkingLotSystem.getVehicleByTime(30);
        System.out.println(vehicleList.size());
    }
}
