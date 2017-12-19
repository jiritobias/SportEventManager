package cz.muni.fi.pa165.restapi.controllers;

import cz.muni.fi.pa165.restapi.ApiUris;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Martin Smid
 */
@RestController
public class MainController {

    /**
     * Get URIs for REST
     * curl -X GET http://localhost:8080/pa165/rest/
     *
     * @return uris
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public final ResponseEntity<Map<String, String>> getResources() {
        Map<String, String> resourceMap = new HashMap<>();

        resourceMap.put("sports_uri", ApiUris.ROOT_URI_SPORTS);
        resourceMap.put("users_uri", ApiUris.ROOT_URI_USERS);

        return new ResponseEntity<>(resourceMap, HttpStatus.OK);
    }
}
