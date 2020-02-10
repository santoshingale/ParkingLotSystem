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
}
