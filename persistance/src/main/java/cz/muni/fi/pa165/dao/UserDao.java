package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.enums.Gendre;
import java.util.List;

public interface UserDao extends BaseDao<User> {

    User findById(Long id);

    List<User> findByGendre(Gendre gendre);

    List<User> findAll();
}
