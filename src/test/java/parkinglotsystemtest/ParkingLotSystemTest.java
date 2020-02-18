package parkinglotsystemtest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import parkinglotsystem.*;
import java.time.LocalDateTime;

public class ParkingLotSystemTest {

    ParkedVehicle parkedVehicle1 = null;
    ParkingLotSystem parkingLotSystem = null;

    @Before
    public void setUp() throws Exception {
        parkedVehicle1 = new ParkedVehicle();
        parkingLotSystem = new ParkingLotSystem(2, 1);
    }

    @Test
    public void whenDriverComeToPark_shoulsAbleToPark() throws ParkingLotException {
        Assert.assertTrue(parkingLotSystem.parkCar(parkedVehicle1,Driver.NORMAL_DRIVER));
    }

    @Test
    public void whenDriverComeToUnpark_shoulsAbleToUnpark() throws ParkingLotException {
        parkingLotSystem.parkCar(parkedVehicle1,Driver.NORMAL_DRIVER);
        Assert.assertTrue(parkingLotSystem.isVehicleParked(parkedVehicle1));
        parkingLotSystem.unparkCar(parkedVehicle1);
        try {
            parkingLotSystem.isVehicleParked(parkedVehicle1);
        } catch (ParkingLotException p) {
            Assert.assertEquals("Car is not parked", p.getMessage());
        }
    }

    @Test
    public void whenParkingLotIsEmpty_shouldIntializeStatusToEmpty() throws ParkingLotException {
        parkingLotSystem.parkCar(new ParkedVehicle(),Driver.NORMAL_DRIVER);
        Assert.assertFalse(ParkingLotOwner.isParkingStatus());
    }

    @Test
    public void whenParkingLotIsFull_shouldIntializeStatusToFull() throws ParkingLotException {
        parkingLotSystem.parkCar(new ParkedVehicle(),Driver.NORMAL_DRIVER);
        parkingLotSystem.parkCar(new ParkedVehicle(),Driver.NORMAL_DRIVER);
        Assert.assertTrue(ParkingLotOwner.isParkingStatus());
    }

    @Test
    public void whenParkingLotIsFull_shouldChangeAirportSecurityToFull() throws ParkingLotException {
        parkingLotSystem.parkCar(new ParkedVehicle(),Driver.NORMAL_DRIVER);
        parkingLotSystem.parkCar(new ParkedVehicle(),Driver.NORMAL_DRIVER);
        Assert.assertTrue(AirportSecurity.isParkingStatus());
    }

    @Test
    public void whenCarIsUnparked_shouldChangeTheStatusOfParkingLotToEmpty() {
        parkingLotSystem.parkCar(new ParkedVehicle(),Driver.NORMAL_DRIVER);
        parkingLotSystem.parkCar(parkedVehicle1,Driver.NORMAL_DRIVER);
        Assert.assertTrue(ParkingLotOwner.isParkingStatus());
        parkingLotSystem.unparkCar(parkedVehicle1);
        Assert.assertFalse(ParkingLotOwner.isParkingStatus());
    }

    @Test
    public void whenGivenPerticularLotNo_shouldAbleToPark() throws ParkingLotException {
        Assert.assertTrue(parkingLotSystem.parkCar(parkedVehicle1,Driver.NORMAL_DRIVER, 1,2));
    }

    @Test
    public void whenCarIsPark_shouldAbleToFindVehicle() throws ParkingLotException {
        parkingLotSystem.parkCar(parkedVehicle1,Driver.NORMAL_DRIVER, 1,2);
        int carParkedLotNumber = parkingLotSystem.findCarParkedSlotNumber(parkedVehicle1);
        Assert.assertEquals(1, carParkedLotNumber);
    }

    @Test
    public void whenCarGettingUnpark_shouldOwnerKnowParkingTime() throws ParkingLotException {
        parkingLotSystem.parkCar(parkedVehicle1,Driver.NORMAL_DRIVER);
        int minute = LocalDateTime.now().getMinute();
        parkingLotSystem.unparkCar(parkedVehicle1);
        Assert.assertEquals(minute, ParkingLotOwner.getParkedDuration().getMinute());
    }

    @Test
    public void givenMultipleCars_shouldAbleToParkEvenly() throws ParkingLotException {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(20,5);
        parkingLotSystem.parkCar(new ParkedVehicle(),Driver.NORMAL_DRIVER);
        parkingLotSystem.parkCar(new ParkedVehicle(),Driver.NORMAL_DRIVER);
        parkingLotSystem.parkCar(new ParkedVehicle(),Driver.NORMAL_DRIVER);
        parkingLotSystem.parkCar(new ParkedVehicle(),Driver.NORMAL_DRIVER);
        parkingLotSystem.parkCar(new ParkedVehicle(),Driver.NORMAL_DRIVER);
        parkingLotSystem.parkCar(new ParkedVehicle(),Driver.NORMAL_DRIVER);
        parkingLotSystem.parkCar(parkedVehicle1,Driver.NORMAL_DRIVER);
        Assert.assertEquals(2,parkedVehicle1.spotNo);
    }

    @Test
    public void givenCarToPark_whenDriverIsHandicap_shouldParkedAtNearestSpot() throws ParkingLotException {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(4, 1);
        parkingLotSystem.parkCar(new ParkedVehicle(),Driver.NORMAL_DRIVER);
        parkingLotSystem.parkCar(parkedVehicle1, Driver.HANDICAP);
        parkingLotSystem.parkCar(new ParkedVehicle(),Driver.NORMAL_DRIVER);
        Assert.assertEquals(1,parkedVehicle1.spotNo);
    }
}
