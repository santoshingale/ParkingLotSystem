package parking_lot_system;

import java.util.HashMap;
import java.util.Random;
import java.util.TreeMap;

public class ParkingLotSystem {

    public static final int capacity = 3;

    HashMap<Integer,Car> parkingLots = new HashMap<>(capacity);

    public void parkCar(Car car) {

            Random random = new Random();
       parkingLots.put(random.nextInt(2),car);
    }
}
