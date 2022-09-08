package DAO;

import java.io.FileNotFoundException;
import java.util.List;

public abstract class AbstractDAO<K,T> {

    public abstract T findOne (K id);

    public abstract List<T> findAll ();

    public abstract void create(T obj) throws FileNotFoundException;

    public abstract void deletebyId (K id);
}
