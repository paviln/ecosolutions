package dk.ecosolutions.oms.persistence.database;

import java.util.List;

/**
 * The Dao interface defines an abstract API, to perform CRUD operations on any object.
 *
 * @param <T> defines the object type.
 * @author Jens Christensen
 * @version 1.0
 */
public interface Dao<T> {
    T get(int id);

    List<T> all();

    void save(T t);

    void update(T t);

    void delete(T t);
}
