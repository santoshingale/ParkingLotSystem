package parking_lot_system_test;

import org.junit.Test;
import parking_lot_system.Car;
import parking_lot_system.ParkingLotSystem;

import java.util.Date;

public class ParkingLotSystemTest {

    @Test
    public void whenDriverComeToPark_shoulsAbleToPark() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem();
        parkingLotSystem.parkCar(new Car("Elantra","MH05S5455",  new Date() , "red"));
    }

    @Test
    public void whenDriverComeToUnpark_shoulsAbleToUnpark() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem();
        int carParkedLot = parkingLotSystem.parkCar(new Car("Elantra", "MH05S5455", new Date(), "red"));
        parkingLotSystem.unparkCar(1);
    }
}
