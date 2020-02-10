package parking_lot_system;

import java.util.HashMap;
import java.util.Random;

public class ParkingLotSystem {

    public static final int capacity = 3;

    HashMap<Integer, Car> parkingLots = new HashMap<>(capacity);

    public void parkCar(Car car) {
        int emptyParkingLot = this.getEmptyParkingLot();
        System.out.println(emptyParkingLot);
        if(emptyParkingLot != capacity +1) {
            parkingLots.put(emptyParkingLot, car);
            System.out.println(parkingLots.values());
        }
    }

    private int getEmptyParkingLot() {
        int lotNumber = 0;
        if (parkingLots.size() == capacity) {
            return capacity + 1;
        }
        do {
            Random randomLotNumber = new Random();
            lotNumber = randomLotNumber.nextInt(3) + 1;
        } while (parkingLots.containsKey(lotNumber));
        return lotNumber;
    }


}
