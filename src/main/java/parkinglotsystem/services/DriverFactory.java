package parkinglotsystem.services;

import parkinglotsystem.ParkingLotSystem;
import parkinglotsystem.enumerate.VehicleSize;

public class DriverFactory {
    public static boolean getVehicleParkBySize(ParkedVehicle vehicle, ParkingLotSystem parkingLotSystem) {
        if (vehicle.vehicleSize.equals(VehicleSize.LARGE)) {
            return parkingLotSystem.getSpotForLargeVehicle(vehicle);
        }
        return parkingLotSystem.getSpotForNormalDriver(vehicle);
    }
}
