package parking_lot_system;

import java.util.HashMap;
import java.util.Random;

public class ParkingLotSystem {

    enum ParkingLotStatus{
        PARKING_LOT_FULL,PARKING_LOT_EMPTY
    }

    public static final int CAPACITY = 2;
    public ParkingLotStatus status = ParkingLotStatus.PARKING_LOT_EMPTY;

    HashMap<Integer, Car> parkingLots = new HashMap<>(CAPACITY);

    public void parkCar(Car car) {

        isParkingLotEmpty();
        if (this.status.equals(ParkingLotStatus.PARKING_LOT_EMPTY)) {

            int parkingLot = this.getEmptyParkingLot();
            car.setLotNo(parkingLot);
            System.out.println(parkingLot);
            parkingLots.put(parkingLot, car);
            car.setParkedStatus(true);
            System.out.println(parkingLots.values());

        } else {
            System.out.println("Parking lot is full");
        }

    }

    private int getEmptyParkingLot() {
        int lotNumber = 0;

        do {
            Random randomLotNumber = new Random();
            lotNumber = randomLotNumber.nextInt(CAPACITY) + 1;
        } while (parkingLots.containsKey(lotNumber));
        return lotNumber;
    }

    public void unparkCar(Car car1) {
        Car remove = parkingLots.remove(car1.getLotNo());
        System.out.println(remove);
    }

    private void isParkingLotEmpty() {
        if (parkingLots.size() != CAPACITY)
            this.status=ParkingLotStatus.PARKING_LOT_EMPTY;
        else
            this.status=ParkingLotStatus.PARKING_LOT_FULL;
    }

}
