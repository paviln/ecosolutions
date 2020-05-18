package dk.ecosolutions.oms.persistence;

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
    Object get(int id);

    List<T> all();

    void save(T t);

    void update(T t);

    void delete(T t);
}
