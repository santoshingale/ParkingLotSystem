package parking_lot_system_test;

import org.junit.Assert;
import org.junit.Test;
import parking_lot_system.Car;
import parking_lot_system.ParkingLotSystem;

import java.util.Date;

public class ParkingLotSystemTest {

    @Test
    public void whenDriverComeToPark_shoulsAbleToPark() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem();
        Car car1 = new Car("Elantra","MH05S5455",  new Date() , "red");
        parkingLotSystem.parkCar(car1);
        Assert.assertTrue(car1.getParkedStatus());
    }

    @Test
    public void whenDriverComeToUnpark_shoulsAbleToUnpark() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem();
        Car car1 = new Car("Elantra","MH05S5455",  new Date() , "red");
        parkingLotSystem.parkCar(car1);
        Assert.assertTrue(car1.getParkedStatus());
        parkingLotSystem.unparkCar(car1);
    }

    @Test
    public void whenParkingLotIsEmpty_shouldIntializeStatusToEmpty() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem();
        parkingLotSystem.parkCar(new Car("City","MH05S5455",  new Date() , "red"));
        Assert.assertEquals(parkingLotSystem.status, ParkingLotSystem.ParkingLotStatus.PARKING_LOT_EMPTY);
    }

    @Test
    public void whenParkingLotIsFull_shouldIntializeStatusToFull() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem();
        parkingLotSystem.parkCar(new Car("City","MH05S5455",  new Date() , "red"));
        parkingLotSystem.parkCar(new Car("xf","MH05S5455",  new Date() , "red"));
        Assert.assertEquals(parkingLotSystem.status, ParkingLotSystem.ParkingLotStatus.PARKING_LOT_FULL);
    }

    @Test
    public void whenParkingLotIsFull_shouldChangeAirportSecurityToFull() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem();
        parkingLotSystem.parkCar(new Car("City","MH05S5455",  new Date() , "red"));
        parkingLotSystem.parkCar(new Car("xf","MH05S5455",  new Date() , "red"));
        Assert.assertEquals(parkingLotSystem.airportSecurity.securityStatus, ParkingLotSystem.ParkingLotStatus.PARKING_LOT_FULL);
    }
}
