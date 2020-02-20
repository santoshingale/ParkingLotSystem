package parkinglotsystem.enumerate;

import parkinglotsystem.services.ParkedVehicle;
import parkinglotsystem.ParkingLotSystem;

public enum Driver {
    SMALL_VEHICLE_DRIVER{
        @Override
        public boolean getVehicleParked(ParkedVehicle vehicle, ParkingLotSystem parkingLotSystem) {
            return parkingLotSystem.getSpotForNormalDriver(vehicle);
        }
    }, LARGE_VEHICLE_DRIVER{
        @Override
        public boolean getVehicleParked(ParkedVehicle vehicle, ParkingLotSystem parkingLotSystem) {
            return parkingLotSystem.getSpotForLargeVehicle(vehicle);
        }
    }, HANDICAP_DRIVER{
        @Override
        public boolean getVehicleParked(ParkedVehicle vehicle, ParkingLotSystem parkingLotSystem) {
            return parkingLotSystem.getSpotForHandicapDriver(vehicle);
        }
    };

    public abstract boolean getVehicleParked(ParkedVehicle vehicle, ParkingLotSystem parkingLotSystem);
}
