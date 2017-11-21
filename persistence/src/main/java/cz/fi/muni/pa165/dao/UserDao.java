package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.entity.User;
import cz.fi.muni.pa165.enums.Gendre;
import java.util.List;

/**
 * @author jiritobias
 */
public interface UserDao extends BaseDao<User> {

    /**
     * Find users by gendre.
     */
    List<User> findByGendre(Gendre gendre);

    /**
     * Find user with the given email.
     *
     * @param email email
     * @return found user with the email
     */
    User findByEmail(String email);
}
