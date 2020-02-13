package parking_lot_system_test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import parking_lot_system.AirportSecurity;
import parking_lot_system.ParkedVehicle;
import parking_lot_system.ParkingLotSystem;

import java.util.Date;

public class ParkingLotSystemTest {

    ParkedVehicle parkedVehicle1 = null;
    ParkingLotSystem parkingLotSystem = null;

    @Before
    public void setUp() throws Exception {
        parkedVehicle1 = new ParkedVehicle("Elantra", "MH05S5455", new Date(), "red");
        parkingLotSystem = new ParkingLotSystem();
    }

    @Test
    public void whenDriverComeToPark_shoulsAbleToPark() {
        parkingLotSystem.parkCar(parkedVehicle1);
        Assert.assertTrue(parkingLotSystem.parkCar(parkedVehicle1));
    }

    @Test
    public void whenDriverComeToUnpark_shoulsAbleToUnpark() {
        parkingLotSystem.parkCar(parkedVehicle1);
        Assert.assertTrue(parkedVehicle1.getParkedStatus());
        Assert.assertTrue(parkingLotSystem.unparkCar(parkedVehicle1));
    }

    @Test
    public void whenParkingLotIsEmpty_shouldIntializeStatusToEmpty() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem();
        parkingLotSystem.parkCar(new ParkedVehicle("City", "MH05S5455", new Date(), "red"));
        Assert.assertEquals(parkingLotSystem.status, ParkingLotSystem.ParkingLotStatus.PARKING_LOT_EMPTY);
    }

    @Test
    public void whenParkingLotIsFull_shouldIntializeStatusToFull() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem();
        parkingLotSystem.parkCar(new ParkedVehicle("City", "MH05S5455", new Date(), "red"));
        parkingLotSystem.parkCar(new ParkedVehicle("xf", "MH05S5455", new Date(), "red"));
        Assert.assertEquals(parkingLotSystem.status, ParkingLotSystem.ParkingLotStatus.PARKING_LOT_FULL);
    }

    @Test
    public void whenParkingLotIsFull_shouldChangeAirportSecurityToFull() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem();
        parkingLotSystem.parkCar(new ParkedVehicle("City", "MH05S5455", new Date(), "red"));
        parkingLotSystem.parkCar(new ParkedVehicle("xf", "MH05S5455", new Date(), "red"));
        Assert.assertEquals(AirportSecurity.securityStatus, ParkingLotSystem.ParkingLotStatus.PARKING_LOT_FULL);
    }

    @Test
    public void whenCarIsUnparked_shouldChangeTheStatusOfParkingLotToEmpty() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem();
        parkingLotSystem.parkCar(new ParkedVehicle("City", "MH05S5455", new Date(), "red"));
        ParkedVehicle parkedVehicle1 = new ParkedVehicle("Elantra", "MH05S5455", new Date(), "red");
        parkingLotSystem.parkCar(parkedVehicle1);
        Assert.assertEquals(AirportSecurity.securityStatus, ParkingLotSystem.ParkingLotStatus.PARKING_LOT_FULL);
        parkingLotSystem.unparkCar(parkedVehicle1);
        Assert.assertEquals(AirportSecurity.securityStatus, ParkingLotSystem.ParkingLotStatus.PARKING_LOT_EMPTY);
    }
}
