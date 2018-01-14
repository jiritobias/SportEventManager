package cz.muni.fi.pa165.restapi.controllers;

import cz.fi.muni.pa165.dto.ChangePasswordDTO;
import cz.fi.muni.pa165.dto.CreateSportsMenDTO;
import cz.fi.muni.pa165.dto.ResetPasswordDTO;
import cz.fi.muni.pa165.dto.SportsMenDTO;
import cz.fi.muni.pa165.entity.User;
import cz.fi.muni.pa165.enums.Role;
import cz.fi.muni.pa165.facade.SportsMenFacade;
import cz.muni.fi.pa165.restapi.ApiUris;
import cz.muni.fi.pa165.restapi.exceptions.InvalidParameterException;
import cz.muni.fi.pa165.restapi.exceptions.InvalidRequestException;
import cz.muni.fi.pa165.restapi.exceptions.ResourceAlreadyExistingException;
import cz.muni.fi.pa165.restapi.exceptions.ResourceNotFoundException;
import cz.muni.fi.pa165.restapi.hateoas.UserNewPasswordResource;
import cz.muni.fi.pa165.restapi.hateoas.UserNewPasswordResourceAssembler;
import cz.muni.fi.pa165.restapi.hateoas.UserResource;
import cz.muni.fi.pa165.restapi.hateoas.UserResourceAssembler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * User REST Controller
 *
 * @author Martin Smid
 */
@RestController
@ExposesResourceFor(SportsMenDTO.class)
@RequestMapping(ApiUris.ROOT_URI_USERS)
public class UserRestController {

    final static Logger logger = LoggerFactory.getLogger(UserRestController.class);

    @Autowired
    private UserResourceAssembler userResourceAssembler;

    @Autowired
    private UserNewPasswordResourceAssembler userNewPasswordResourceAssembler;

    @Autowired
    private SportsMenFacade sportsMenFacade;

    /**
     * Get a list of Users.
     * curl -i -X http://localhost:8080/pa165/rest/users?role=SPORTSMEN&limit=0&birthdateBegin=0000-00-00&birthdateEnd=9999-99-99&gender=ALL&sortBy=id
     *
     * @param role           role {USER, ADMINISTRATOR, SPORTSMEN, ALL}
     * @param limit          number of results, if limit == 0, all results are displayed
     * @param birthdateBegin begin of birthday range
     * @param birthdateEnd   end of birthday range
     * @param gender         gender {MAN, WOMAN, ALL}
     * @param sortBy         sorting options {id, role, birthdate, gender, firtsname, lastname, name, email}
     * @return http response entity with user resources
     */
    @RequestMapping(method = RequestMethod.GET)
    public final HttpEntity<Resources<UserResource>> getUsers(
            @RequestParam(value = "role", required = false, defaultValue = "ALL") String role,
            @RequestParam(value = "limit", required = false, defaultValue = "0") long limit,
            @RequestParam(value = "birthdateBegin", required = false, defaultValue = "0000-00-00") String birthdateBegin,
            @RequestParam(value = "birthdateEnd", required = false, defaultValue = "9999-99-99") String birthdateEnd,
            @RequestParam(value = "gender", required = false, defaultValue = "ALL") String gender,
            @RequestParam(value = "sortBy", required = false, defaultValue = "id") String sortBy
    ) {
        logger.debug("UserRestController getUsers()");

        List<UserResource> userResources = new ArrayList<>();

        switch (role.toUpperCase()) {
            case "USER":
                userResources = userResourceAssembler.toResources(sportsMenFacade.getAll(Role.USER));
                break;
            case "ADMINISTRATOR":
                userResources = userResourceAssembler.toResources(sportsMenFacade.getAll(Role.ADMINISTRATOR));
                break;
            case "SPORTSMEN":
                userResources = userResourceAssembler.toResources(sportsMenFacade.getAll(Role.SPORTSMEN));
                break;
            case "ALL":
                userResources = userResourceAssembler.toResources(sportsMenFacade.getAll());
                break;
            default:
                throw new InvalidParameterException("Role parameters options: user, admin, sportsmen, all");
        }

        List<UserResource> filteredResources = new ArrayList<>();

        for (UserResource resource : userResources) {
            // filter birthdate: yyyy-mm-dd
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String birthdate = simpleDateFormat.format(resource.getBirthdate());
            boolean dateInRange = birthdate.compareTo(birthdateBegin) >= 0 &&
                    birthdate.compareTo(birthdateEnd) <= 0;
            if (dateInRange) {
                // filter gender: ALL, MAN, WOMAN
                if (!gender.equalsIgnoreCase("ALL")) {
                    if (gender.equalsIgnoreCase(resource.getGender().toString())) {
                        filteredResources.add(resource);
                    }
                } else {
                    filteredResources.add(resource);
                }
            }
        }
        userResources = filteredResources;

        if (limit > 0) {
            filteredResources = new ArrayList<>();
            for (int i = 0; i < Math.min(limit, userResources.size()); i++) {
                filteredResources.add(userResources.get(i));
            }
            userResources = filteredResources;
        }

        // sort results
        switch (sortBy.toLowerCase()) {
            case "role":
                userResources.sort(Comparator.comparing(UserResource::getRole));
                break;
            case "birthdate":
                userResources.sort(Comparator.comparing(UserResource::getBirthdate));
                break;
            case "gender":
                userResources.sort(Comparator.comparing(UserResource::getGender));
                break;
            case "firstname":
                userResources.sort(Comparator.comparing(o -> o.getFirstname().toLowerCase()));
                break;
            case "lastname":
                userResources.sort(Comparator.comparing(o -> o.getLastname().toLowerCase()));
                break;
            case "name":
                userResources.sort((o1, o2) -> {
                    int c = o1.getFirstname().toLowerCase().compareTo(o2.getFirstname().toLowerCase());
                    if (c == 0) {
                        c = o1.getLastname().toLowerCase().compareTo(o2.getLastname().toLowerCase());
                    }
                    return c;
                });
            case "email":
                userResources.sort(Comparator.comparing(UserResource::getEmail));
                break;
            case "id":
                userResources.sort(Comparator.comparing(UserResource::getDtoId));
                break;
            default:
                throw new InvalidParameterException(
                        "SortBy parameter options: id, name, firstname, lastname, role, gender, birthdate, email");
        }

        Resources<UserResource> resources = new Resources<>(
                userResources,
                linkTo(UserRestController.class).withSelfRel(),
                linkTo(UserRestController.class).slash("/create").withRel("create")
        );

        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    /**
     * Get a user with given ID.
     * curl -i -X GET http://localhost:8080/pa165/rest/users/1
     *
     * @param id id of a user
     * @return http response entity with user resource
     * @throws Exception error while retrieving the user
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public final HttpEntity<UserResource> getUser(@PathVariable("id") long id) throws Exception {
        logger.debug("UserManRestController getUser({})", id);

        SportsMenDTO sportsMenDTO = sportsMenFacade.load(id);
        if (sportsMenDTO == null) {
            throw new ResourceNotFoundException("user " + id + " not found");
        }

        UserResource userResource = userResourceAssembler.toResource(sportsMenDTO);
        return new ResponseEntity<UserResource>(userResource, HttpStatus.OK);
    }

    /**
     * Create a new user entity.
     * curl -i -X POST -H "Content-Type: application/json" --data '{"email":"test@test.com", "password":"password", "firstname":"firstname", "lastname":"lastname", "gendre":"MAN", "birthdate":"2000-01-30", "phone":"111222333", "address":"address", "role":"USER"}' http://localhost:8080/pa165/rest/users/create
     *
     * @param sportsMenDTO
     * @param bindingResult
     * @return http response user resource with the given data
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public final HttpEntity<UserResource> createUser(@RequestBody @Valid CreateSportsMenDTO sportsMenDTO, BindingResult bindingResult) {
        logger.debug("UserRestController createUser()");

        if (bindingResult.hasErrors()) {
            logger.error("failed validation {}", bindingResult.toString());
            throw new InvalidRequestException("Failed validation");
        }

        Long id;
        try {
            id = sportsMenFacade.createSportsMen(sportsMenDTO);
        } catch (DataAccessException e) {
            throw new ResourceAlreadyExistingException("User already exists");
        }
        UserResource resource = userResourceAssembler.toResource(sportsMenFacade.load(id));
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    /**
     * Delete a user with given ID.
     * curl -i -X POST --data '{"id":1}' http://localhost:8080/pa165/rest/users/1/delete
     *
     * @param id ID of a user
     * @return http response entity with the user resource
     */
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
    public final HttpEntity<UserResource> deleteUser(@PathVariable("id") long id) {
        SportsMenDTO sportsMenDTO;
        try {
            sportsMenDTO = sportsMenFacade.load(id);
        } catch (Exception e) {
            throw new ResourceNotFoundException("user " + id + " not found");
        }

        UserResource resource = userResourceAssembler.toResource(sportsMenDTO);
        sportsMenFacade.delete(sportsMenDTO);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    /**
     * Change user's password.
     * curl -X POST -H 'Content-Type: application/json' --data '{"id":1, "oldPassword":"sportsmenHeslo", "newPassword":"heslo"}' http://localhost:8080/pa165/rest/users/1/changePassword
     *
     * @param id                ID of the user
     * @param changePasswordDTO object with ID, old password and new password
     * @return http response entity with user resource
     */
    @RequestMapping(value = "/{id}/changePassword", method = RequestMethod.POST)
    public final HttpEntity<UserNewPasswordResource> changePassword(@PathVariable("id") long id, @RequestBody @Valid ChangePasswordDTO changePasswordDTO, BindingResult bindingResult) {
        logger.debug("UserRestController changePassword()");

        if (bindingResult.hasErrors()) {
            logger.error("failed validation {}", bindingResult.toString());
            throw new InvalidRequestException("Failed validation");
        }

        assert id == changePasswordDTO.getId();
        SportsMenDTO sportsMenDTO;
        try {
            sportsMenDTO = sportsMenFacade.load(id);
        } catch (Exception e) {
            throw new ResourceNotFoundException("user " + id + " not found");
        }

        sportsMenFacade.changePassword(changePasswordDTO);

        UserNewPasswordResource resource = userNewPasswordResourceAssembler.toResource(changePasswordDTO);
        return new ResponseEntity<UserNewPasswordResource>(resource, HttpStatus.OK);
    }

    /**
     * Reset user password.
     * curl -X POST -H 'Content-Type: application/json' --data '{"id":1, "email":"prvni@gmail.com"}' http://localhost:8080/pa165/rest/users/1/resetPassword
     *
     * @param id               user ID
     * @param resetPasswordDTO object with ID and email defining the user
     * @return response with ID and the new password
     */
    @RequestMapping(value = "/{id}/resetPassword", method = RequestMethod.POST)
    public final HttpEntity<UserNewPasswordResource> resetPassword(@PathVariable("id") long id, @RequestBody @Valid ResetPasswordDTO resetPasswordDTO, BindingResult bindingResult) {
        logger.debug("UserRestController resetPassword({})", id);

        if (bindingResult.hasErrors()) {
            logger.error("failed validation {}", bindingResult.toString());
            throw new InvalidRequestException("Failed validation");
        }

        SportsMenDTO sportsMenDTO;
        try {
            sportsMenDTO = sportsMenFacade.load(id);
        } catch (Exception e) {
            throw new ResourceNotFoundException("user " + id + " not found");
        }
        assert Objects.equals(sportsMenDTO.getEmail(), resetPasswordDTO.getEmail());

        String password = sportsMenFacade.resetPassword(resetPasswordDTO);
        ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO(id, "", password);

        UserNewPasswordResource resource = userNewPasswordResourceAssembler.toResource(changePasswordDTO);
        return new ResponseEntity<UserNewPasswordResource>(resource, HttpStatus.OK);
    }

    /**
     * Update user.
     *
     * curl -i -X PUT -H "Content-Type: application/json" --data '{"email":"new@email.com","id":1, "firstname":"newFirstname", "lastname":"newLastname", "gendre":"MAN", "birthdate":"2020-01-30", "phone":"111222333", "address":"NEWaddress", "role":"USER", "passwordHash":""}' http://localhost:8080/pa165/rest/users/1
     *
     * @param id
     * @param sportsMenDTO
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public final HttpEntity<UserResource> updateUser(@PathVariable("id") long id, @RequestBody @Valid SportsMenDTO sportsMenDTO, BindingResult bindingResult) {
        logger.debug("UserRestController updateUser({})", id);

        if (bindingResult.hasErrors()) {
            logger.error("failed validation {}", bindingResult.toString());
            throw new InvalidRequestException("Failed validation");
        }

        assert id == sportsMenDTO.getId();

        SportsMenDTO menDTO;
        try {
            menDTO = sportsMenFacade.load(id);
        } catch (Exception e) {
            throw new ResourceNotFoundException("user " + id + " not found");
        }

        if (!(sportsMenDTO.getFirstname() == null || sportsMenDTO.getFirstname().isEmpty())) {
            menDTO.setFirstname(sportsMenDTO.getFirstname());
        }
        if (!(sportsMenDTO.getLastname() == null || sportsMenDTO.getLastname().isEmpty())) {
            menDTO.setLastname(sportsMenDTO.getLastname());
        }
        if (!(sportsMenDTO.getPhone() == null || sportsMenDTO.getPhone().isEmpty())) {
            menDTO.setPhone(sportsMenDTO.getPhone());
        }
        String email = sportsMenDTO.getEmail();
        if (!(email == null || email.isEmpty())) {
            Pattern pattern = Pattern.compile(User.EMAIL_REGEX);
            Matcher matcher = pattern.matcher(email);
            if (!matcher.matches()) {
                throw new IllegalArgumentException("Wrong email format " + email);
            }
            menDTO.setEmail(email);
        }
        if (!(sportsMenDTO.getAddress() == null || sportsMenDTO.getAddress().isEmpty())) {
            menDTO.setAddress(sportsMenDTO.getAddress());
        }
        if (sportsMenDTO.getBirthdate() != null) {
            menDTO.setBirthdate(sportsMenDTO.getBirthdate());
        }
        if (sportsMenDTO.getRole() != null) {
            menDTO.setRole(sportsMenDTO.getRole());
        }
        if (sportsMenDTO.getGendre() != null) {
            menDTO.setGendre(sportsMenDTO.getGendre());
        }

        sportsMenFacade.update(menDTO);

        UserResource resource = userResourceAssembler.toResource(menDTO);
        return new ResponseEntity<UserResource>(resource, HttpStatus.OK);
    }

    @RequestMapping(value = "/loadByEmail/{email}", method = RequestMethod.GET)
    public final HttpEntity<UserResource> getUserByEmail(@PathVariable("email") String email) throws Exception {
        System.out.println(email);
        SportsMenDTO sportsman =  sportsMenFacade.loadByEmail(email);

        if (sportsman == null) {
            throw new ResourceNotFoundException("user " + email + " not found");
        }

        UserResource resource = userResourceAssembler.toResource(sportsman);
        return new ResponseEntity<UserResource>(resource, HttpStatus.OK);
    }
}
