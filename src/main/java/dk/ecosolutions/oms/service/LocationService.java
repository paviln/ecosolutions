package dk.ecosolutions.oms.service;

import dk.ecosolutions.oms.domain.Address;
import dk.ecosolutions.oms.domain.Location;
import dk.ecosolutions.oms.persistence.database.AddressDao;
import dk.ecosolutions.oms.persistence.database.LocationDao;

import java.util.ArrayList;
import java.util.List;

public class LocationService {
    public static void createLocation(Location location) {
        AddressDao addressDao = new AddressDao();
        addressDao.save(location.getAddress());

        for (Address address : addressDao.all()) {
            if (address.getStreet().equals(location.getAddress().getStreet())) {
                location.setAddress(address);
            }
        }

        LocationDao locationDao = new LocationDao();
        locationDao.save(location);
    }

    public static List<Location> allDeliveryPoints() {
        LocationDao locationDao = new LocationDao();

        List<Location> deliveryPoints = new ArrayList<Location>();
        for (Location location : locationDao.all()) {
            if (location.getTypes_id() == 1) {
                deliveryPoints.add(location);
            }
        }
        return deliveryPoints;
    }
}
