package lk.ijse.carhire.dao;

import java.util.List;

public interface CRUD_DAO <T, ID> extends SuperDAO {
    void save(T t) throws Exception;
    void update(T t) throws Exception;
    void delete(T t) throws Exception;
    T search(ID id)throws Exception;
    List<T> getAll()throws Exception;

}
