package dk.ecosolutions.oms.service;

import dk.ecosolutions.oms.application.enums.Role;
import dk.ecosolutions.oms.application.enums.Type;
import dk.ecosolutions.oms.domain.Address;
import dk.ecosolutions.oms.domain.Location;
import dk.ecosolutions.oms.domain.User;
import dk.ecosolutions.oms.persistence.database.AddressDao;
import dk.ecosolutions.oms.persistence.database.LocationDao;
import dk.ecosolutions.oms.persistence.database.UserDao;

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

    public static List<Location> allLocations() {
        LocationDao locationDao = new LocationDao();
        return locationDao.all();
    }

    public static Boolean removeLocation(Location location) {
        UserDao userDao = new UserDao();
        for (User user : userDao.all()) {
            if (user.getLocation_id() == location.getId()) {
                if (user.getRole() == Role.owner && location.getType() == Type.CleaningCentral) {
                    return false;
                } else {
                    userDao.delete(user);
                }
            }
        }
        LocationDao locationDao = new LocationDao();
        locationDao.delete(location);
        return true;
    }
}
