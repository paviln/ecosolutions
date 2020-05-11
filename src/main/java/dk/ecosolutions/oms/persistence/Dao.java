package dk.ecosolutions.oms.persistence;

import java.util.List;

public interface Dao<T> {
    Object get(int id);

    List<T> getAll();

    void save(T t);

    void update(T t);

    void delete(T t);
}
