package dk.ecosolutions.oms.service;

import dk.ecosolutions.oms.application.controllers.WelcomeController;
import dk.ecosolutions.oms.domain.Address;
import dk.ecosolutions.oms.domain.Location;
import dk.ecosolutions.oms.domain.Order;
import dk.ecosolutions.oms.persistence.database.AddressDao;
import dk.ecosolutions.oms.persistence.database.LocationDao;
import dk.ecosolutions.oms.persistence.database.OrderDoa;

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

    public static List<Location> allLocations() {
        LocationDao locationDao = new LocationDao();
        return locationDao.all();
    }

    public static List<Location> allLocationsWithOrders(int status) {
        List<Location> locations = new ArrayList<>();
        LocationDao locationDao = new LocationDao();
        OrderDoa orderDoa = new OrderDoa();
        List<Order> orders = orderDoa.all();
        for (Location location : locationDao.all()) {
            for (Order order : orders) {
                if (location.getId() == order.getLocation().getId() && order.getStatus() == status) {
                    locations.add(location);
                    break;
                }
            }
        }
        return locations;
    }

    public static void updateLocation(Location location) {
        LocationDao locationDao = new LocationDao();
        locationDao.update(location);
    }

    public static Boolean removeLocation(Location location) {
        if (location != null && WelcomeController.getAuthenticatedUser().getLocation().getId() != location.getId()) {
            LocationDao locationDao = new LocationDao();
            locationDao.delete(location);
            return true;
        }
        return false;
    }
}
