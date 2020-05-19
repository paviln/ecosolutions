package dk.ecosolutions.oms.domain;

import dk.ecosolutions.oms.persistence.databse.AddressDao;
import dk.ecosolutions.oms.persistence.databse.LocationDao;

public class LocationService {
    public static void createLocation(Location location) {
        AddressDao addressDao = new AddressDao();
        addressDao.save(location.getAddress());

        for (Address address : addressDao.all()) {
            if (address.getStreet().equals(location.getAddress().getStreet())) {
                location.setAddresses_id(address.getId());
            }
        }

        LocationDao locationDao = new LocationDao();
        locationDao.save(location);
    }
}
