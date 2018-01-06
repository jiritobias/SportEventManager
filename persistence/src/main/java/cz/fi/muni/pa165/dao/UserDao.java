package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.entity.User;
import cz.fi.muni.pa165.enums.Gendre;
import cz.fi.muni.pa165.enums.Role;

import java.util.Date;
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

    /**
     * Find users with birthday in given range.
     *
     * @param start start date of the range
     * @param end   end date of the range
     * @return collection of users with birthday within the range
     */
    List<User> findByBirthdayWithinRange(Date start, Date end);

    /**
     * Find all users with the role.
     *
     * @param role role
     * @return collection of users with the role
     */
    List<User> findAll(Role role);
}
