package parkinglotsystem.enumerate;

import parkinglotsystem.ParkedVehicle;
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
            return false;
        }
    }, HANDICAP_DRIVER{
        @Override
        public boolean getVehicleParked(ParkedVehicle vehicle, ParkingLotSystem parkingLotSystem) {
            return parkingLotSystem.getSpotForHandicapDriver(vehicle);
        }
    };

    public abstract boolean getVehicleParked(ParkedVehicle vehicle, ParkingLotSystem parkingLotSystem);
}
