package parkinglotsystem.enumerate;

import parkinglotsystem.services.DriverFactory;
import parkinglotsystem.model.ParkedVehicle;
import parkinglotsystem.ParkingLotSystem;

public enum DriverType {
    NORMAL_DRIVER {
        @Override
        public boolean getVehicleParked(ParkedVehicle vehicle, ParkingLotSystem parkingLotSystem) {
            return DriverFactory.getVehicleParkBySize(vehicle, parkingLotSystem);
        }
    }, HANDICAP_DRIVER {
        @Override
        public boolean getVehicleParked(ParkedVehicle vehicle, ParkingLotSystem parkingLotSystem) {
            return parkingLotSystem.getSpotForHandicapDriver(vehicle);
        }
    };

    public abstract boolean getVehicleParked(ParkedVehicle vehicle, ParkingLotSystem parkingLotSystem);
}
