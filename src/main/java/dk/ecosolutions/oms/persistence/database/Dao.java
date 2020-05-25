package dk.ecosolutions.oms.persistence.database;

import java.sql.SQLException;
import java.util.List;

/**
 * The Dao interface defines an abstract API,
 * to performn CRUD opteritions on object of type T.
 *
 * @param <T> defines the type to be used.
 * @author Jens Christensen
 * @version 1.0
 */
public interface Dao<T> {
    T get(int id) throws SQLException;

    List<T> all();

    void save(T t) throws SQLException;

    void update(T t) throws SQLException;

    void delete(T t) throws SQLException;
}
