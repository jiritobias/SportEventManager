package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.entity.User;
import cz.fi.muni.pa165.enums.Gendre;
import java.util.List;

public interface UserDao extends BaseDao<User> {

    /**
     * Find users by gendre.
     */
    List<User> findByGendre(Gendre gendre);

}
