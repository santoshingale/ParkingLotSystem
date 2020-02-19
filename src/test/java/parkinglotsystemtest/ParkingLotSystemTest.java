package parkinglotsystemtest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import parkinglotsystem.*;

import java.time.LocalDateTime;
import java.util.List;

public class ParkingLotSystemTest {

    ParkedVehicle parkedVehicle = null;
    ParkingLotSystem parkingLotSystem = null;

    @Before
    public void setUp() throws Exception {
        parkedVehicle = new ParkedVehicle(Driver.NORMAL_DRIVER, VehicleSize.SMALL);
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
        parkingLotSystem.parkVehicle(new ParkedVehicle(Driver.NORMAL_DRIVER, VehicleSize.SMALL));
        Assert.assertFalse(ParkingLotOwner.isParkingStatus());
    }

    @Test
    public void givenVehicle_whenParkingLotIsFull_shouldInformOwner() throws ParkingLotException {
        parkingLotSystem.parkVehicle(new ParkedVehicle(Driver.NORMAL_DRIVER, VehicleSize.SMALL));
        parkingLotSystem.parkVehicle(new ParkedVehicle(Driver.NORMAL_DRIVER, VehicleSize.SMALL));
        Assert.assertTrue(ParkingLotOwner.isParkingStatus());
    }

    @Test
    public void givenVehicle_whenParkingLotIsFull_shouldInformAirportSecurity() throws ParkingLotException {
        parkingLotSystem.parkVehicle(new ParkedVehicle(Driver.NORMAL_DRIVER, VehicleSize.SMALL));
        parkingLotSystem.parkVehicle(new ParkedVehicle(Driver.NORMAL_DRIVER, VehicleSize.SMALL));
        Assert.assertTrue(AirportSecurity.securityStatus);
    }

    @Test
    public void givenVehicle_whenParkingLotIsEmpty_shouldInformAirportSecurity() throws ParkingLotException {
        parkingLotSystem.parkVehicle(new ParkedVehicle(Driver.NORMAL_DRIVER, VehicleSize.SMALL));
        Assert.assertFalse(AirportSecurity.securityStatus);
    }

    @Test
    public void givenVehicle_whenCarIsUnparked_shouldChangeTheStatusOfParkingLotToEmpty() {
        parkingLotSystem.parkVehicle(new ParkedVehicle(Driver.NORMAL_DRIVER, VehicleSize.SMALL));
        parkingLotSystem.parkVehicle(parkedVehicle);
        Assert.assertTrue(ParkingLotOwner.isParkingStatus());
        parkingLotSystem.unparkCar(parkedVehicle);
        Assert.assertFalse(ParkingLotOwner.isParkingStatus());
    }

    @Test
    public void givenVehicle_whenGivenPerticularLotNo_shouldAbleToPark() throws ParkingLotException {
        Assert.assertTrue(parkingLotSystem.parkVehicle(parkedVehicle, 1, 2));
    }

    @Test
    public void givenVehicle_whenCarIsPark_shouldAbleToFindVehicle() throws ParkingLotException {
        parkingLotSystem.parkVehicle(parkedVehicle, 1, 2);
        int carParkedLotNumber = parkingLotSystem.findCarParkedBySlotNumber(parkedVehicle);
        Assert.assertEquals(1, carParkedLotNumber);
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
        parkingLotSystem.parkVehicle(new ParkedVehicle(Driver.NORMAL_DRIVER, VehicleSize.SMALL));
        parkingLotSystem.parkVehicle(new ParkedVehicle(Driver.NORMAL_DRIVER, VehicleSize.SMALL));
        parkingLotSystem.parkVehicle(new ParkedVehicle(Driver.NORMAL_DRIVER, VehicleSize.SMALL));
        parkingLotSystem.parkVehicle(new ParkedVehicle(Driver.NORMAL_DRIVER, VehicleSize.SMALL));
        parkingLotSystem.parkVehicle(new ParkedVehicle(Driver.NORMAL_DRIVER, VehicleSize.SMALL));
        parkingLotSystem.parkVehicle(new ParkedVehicle(Driver.NORMAL_DRIVER, VehicleSize.SMALL));
        parkingLotSystem.parkVehicle(parkedVehicle);
        Assert.assertEquals(2, parkedVehicle.spotNo);
    }

    @Test
    public void givenVehicle_whenDriverIsHandicap_shouldParkedAtNearestSpot() throws ParkingLotException {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(4, 2);
        parkingLotSystem.parkVehicle(new ParkedVehicle(Driver.NORMAL_DRIVER, VehicleSize.SMALL));
        parkingLotSystem.parkVehicle(parkedVehicle);
        ParkedVehicle parkedVehicle1 = new ParkedVehicle(Driver.HANDICAP, VehicleSize.SMALL);
        parkingLotSystem.parkVehicle(parkedVehicle1);
        Assert.assertEquals(1, parkedVehicle1.spotNo);
    }

    @Test
    public void givenLargeVehicleToPark_shouldAbleToPark() {
        ParkedVehicle parkedVehicle = new ParkedVehicle(Driver.HANDICAP, VehicleSize.LARGE);
        Assert.assertTrue(parkingLotSystem.parkVehicle(parkedVehicle));
    }

    @Test
    public void givenVehicle_whenWhiteVehiclesAreParked_shouldReturnThatVehicles() {
        ParkedVehicle parkedVehicle = new ParkedVehicle("White");
        ParkedVehicle parkedVehicle1 = new ParkedVehicle("Green");
        parkingLotSystem.parkVehicle(parkedVehicle);
        parkingLotSystem.parkVehicle(parkedVehicle1);
        List<ParkedVehicle> white = parkingLotSystem.getCarDetails("White");
        Assert.assertEquals(white.get(0),parkedVehicle);
    }

    @Test
    public void givenVehicle_whenItIsBlueToyota_shouldReturnThatVehicles() {
        ParkedVehicle parkedVehicle = new ParkedVehicle("Blue","BMW");
        ParkedVehicle parkedVehicle1 = new ParkedVehicle("Blue","Toyota");
        parkingLotSystem.parkVehicle(parkedVehicle);
        parkingLotSystem.parkVehicle(parkedVehicle1);
        List<ParkedVehicle> white = parkingLotSystem.getCarDetails("Blue","Toyota");
        Assert.assertEquals(white.get(0),parkedVehicle1);
    }
}
