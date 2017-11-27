package cz.muni.fi.pa165.service;

import org.springframework.stereotype.Service;

/**
 * @author Martin Smid
 */
@Service
public interface StringService {

    /**
     * Create a random string with required length containing given symbols.
     * https://stackoverflow.com/a/41156/8288095
     *
     * @param length  length of the string
     * @return random string
     */
    String getRandomString(int length);
}
