package cz.muni.fi.pa165.service;

import cz.fi.muni.pa165.entity.User;
import cz.fi.muni.pa165.enums.Gendre;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author Martin Smid
 */
@Service
public interface UserService extends BaseService<User> {

    /**
     * Finds user with the given email.
     *
     * @param email email to find
     * @return found user with the email
     */
    User findByEmail(String email);

    /**
     * Finds users with the given gender.
     *
     * @param gender gender to find
     * @return collection of found users with the gender
     */
    List<User> findByGender(Gendre gender);

    /**
     * Find users with the given birth date.
     *
     * @param date birth date
     * @return collection of users with the birth date
     */
    List<User> findByBirthdate(Date date);

    /**
     * Find users with birthdate within the range.
     *
     * @param start start of the range
     * @param end   end of the range
     * @return collection of found users
     */
    List<User> findByBirthdateWithinRange(Date start, Date end);

    /**
     * Checks if the user is administrator.
     *
     * @param user user to check for administrator role
     * @return true iff user is administrator
     */
    boolean isAdmin(User user);

    /**
     * Registers a new user with the given unencrypted password and email address.
     *
     * @param user     user to register
     * @param password unencrypted password
     * @param email    email address
     */
    void registerUser(User user, String password, String email);

    /**
     * Authenticates the user. It checks that the password hash in the records.
     *
     * @param user     user to authenticate
     * @param password password
     * @return true iff the password hash is in the records
     */
    boolean authenticate(User user, String password);

    /**
     * Change user password
     *
     * @param user        user
     * @param oldPassword old password
     * @param newPassword new password
     */
    void changePassword(User user, String oldPassword, String newPassword);

    /**
     * Reset user password
     *
     * @param user  user
     * @param email email
     * @return new password
     */
    String resetPassword(User user, String email);
}
