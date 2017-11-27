package cz.muni.fi.pa165.service;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Locale;
import java.util.Random;

/**
 * @author Martin Smid
 */
@Service
public class StringServiceImpl implements StringService {

    private static final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static final String lower = upper.toLowerCase(Locale.ROOT);

    private static final String digits = "0123456789";

    private static final String alphanum = upper + lower + digits;

    private final Random random = new SecureRandom();

    private char[] symbols = alphanum.toCharArray();

    @Override
    public String getRandomString(int length) {
        if (length < 1) {
            throw new IllegalArgumentException("Length must be positive");
        }
        char[] buf = new char[length];

        for (int i = 0; i < buf.length; i++) {
            buf[i] = symbols[random.nextInt(symbols.length)];
        }
        return new String(buf);
    }
}
