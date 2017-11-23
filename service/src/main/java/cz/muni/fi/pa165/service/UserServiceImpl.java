package cz.muni.fi.pa165.service;

import cz.fi.muni.pa165.dao.UserDao;
import cz.fi.muni.pa165.entity.User;
import cz.fi.muni.pa165.enums.Gendre;
import cz.fi.muni.pa165.enums.Role;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

/**
 * @author Martin Smid
 */
@Service
public class UserServiceImpl implements UserService {

    @Inject
    @Qualifier("userDaoImpl")
    private UserDao userDao;

    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public List<User> findByGender(Gendre gender) {
        return userDao.findByGendre(gender);
    }

    @Override
    public boolean isAdmin(User user) {
        User foundUser = findById(user.getId());
        return foundUser != null && foundUser.getRole() == Role.ADMINISTRATOR;
    }

    @Override
    public void registerUser(User user, String password, String email) {
        registerUserWithRole(user, password, email, userDao);
    }

    protected static void registerUserWithRole(User user, String rawPassword, String email, UserDao userDao) {
        try {
            user.setPasswordHash(generateStrongPasswordHash(rawPassword));
            user.setEmail(email);
            userDao.create(user);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean authenticate(User user, String password) {
        try {
            return validatePassword(password, user.getPasswordHash());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static String generateStrongPasswordHash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        final int ITERATIONS = 1000;
        char[] chars = password.toCharArray();
        byte[] salt = getSalt();

        PBEKeySpec keySpec = new PBEKeySpec(chars, salt, ITERATIONS, 64 * 8);
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = secretKeyFactory.generateSecret(keySpec).getEncoded();
        return ITERATIONS + ":" + toHex(salt) + ":" + toHex(hash);
    }

    private static byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);
        return salt;
    }

    private static String toHex(byte[] bytes) {
        BigInteger integer = new BigInteger(1, bytes);
        String hex = integer.toString(16);
        int paddingLength = (bytes.length * 2) - hex.length();
        if (paddingLength > 0) {
            return String.format("%0" + paddingLength + "d", 0) + hex;
        } else {
            return hex;
        }
    }

    private static boolean validatePassword(String password, String storedPassword) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String[] parts = storedPassword.split(":");
        int iterations = Integer.parseInt(parts[0]);
        byte[] salt = fromHex(parts[1]);
        byte[] hash = fromHex(parts[2]);

        PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, iterations, hash.length);
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] testHash = secretKeyFactory.generateSecret(keySpec).getEncoded();

        int diff = hash.length ^ testHash.length;
        for (int i = 0; i < hash.length && i < testHash.length; i++) {
            diff |= hash[i] ^ testHash[i];
        }
        return diff == 0;
    }

    private static byte[] fromHex(String hex) {
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }

    @Override
    public void create(User entity) {
        throw new UnsupportedOperationException("User can't be created without their password.");
    }

    @Override
    public User findById(Long id) {
        return userDao.findById(id);
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public void delete(User entity) {
        userDao.delete(entity);
    }
}
