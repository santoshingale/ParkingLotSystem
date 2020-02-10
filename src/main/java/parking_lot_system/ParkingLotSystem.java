package parking_lot_system;

import java.util.HashMap;
import java.util.Random;

public class ParkingLotSystem {

    public static final int CAPACITY = 2;
    public int parkingLot;

    HashMap<Integer, Car> parkingLots = new HashMap<>(CAPACITY);

    public int parkCar(Car car) {

            int parkingLot = this.getEmptyParkingLot();
            System.out.println(parkingLot);
            parkingLots.put(parkingLot, car);
            System.out.println(parkingLots.values());
            return parkingLot;
    }

    private int getEmptyParkingLot() {
        int lotNumber = 0;

        do {
            Random randomLotNumber = new Random();
            lotNumber = randomLotNumber.nextInt(CAPACITY) + 1;
        } while (parkingLots.containsKey(lotNumber));
        return lotNumber;
    }

    public void unparkCar(int carParkedLot) {
        Car remove = parkingLots.remove(carParkedLot);
        System.out.println(remove);
    }
}
