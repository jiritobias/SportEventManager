package cz.muni.fi.pa165.restapi.hateoas;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import cz.fi.muni.pa165.dto.SportsMenDTO;
import cz.fi.muni.pa165.enums.Gendre;
import cz.fi.muni.pa165.enums.Role;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import java.util.Date;

/**
 * Resource for User entity
 *
 * @author Martin Smid
 */
@Relation(value = "user", collectionRelation = "users")
@JsonPropertyOrder({"id", "firstname", "lastname", "email", "birthdate", "phone", "address", "role", "gender"})
public class UserResource extends ResourceSupport {

    @JsonProperty("id")
    private long dtoId;
    private String firstname;
    private String lastname;
    private String email;
    private Date birthdate;
    private String phone;
    private String address;
    private Role role;
    private Gendre gender;

    public UserResource(SportsMenDTO sportsMenDTO) {
        this.dtoId = sportsMenDTO.getId();
        this.firstname = sportsMenDTO.getFirstname();
        this.lastname = sportsMenDTO.getLastname();
        this.email = sportsMenDTO.getEmail();
        this.birthdate = sportsMenDTO.getBirthdate();
        this.phone = sportsMenDTO.getPhone();
        this.address = sportsMenDTO.getAddress();
        this.role = sportsMenDTO.getRole();
        this.gender = sportsMenDTO.getGendre();
    }

    public long getDtoId() {
        return dtoId;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public Role getRole() {
        return role;
    }

    public Gendre getGender() {
        return gender;
    }
}
