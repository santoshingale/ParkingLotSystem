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
        parkingLotSystem.parkCar(new Car("honda","MH05S5455",  new Date() , "red"));
        parkingLotSystem.parkCar(new Car("City","MH05S5455",  new Date() , "red"));
        parkingLotSystem.parkCar(new Car("xf","MH05S5455",  new Date() , "red"));

    }
}
