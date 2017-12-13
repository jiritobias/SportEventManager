package cz.muni.fi.pa165.restapi.hateoas;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import cz.fi.muni.pa165.dto.SportsMenDTO;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import java.text.SimpleDateFormat;

@Relation(value = "user", collectionRelation = "users")
@JsonPropertyOrder({"id", "firstname", "lastname", "email", "birthdate", "phone", "address", "role", "gender"})
public class UserResource extends ResourceSupport {

    private static final String DATE_FORMAT = "yyyy-MM-dd";

    @JsonProperty("id")
    private long dtoId;
    private String firstname;
    private String lastname;
    private String email;
    private String birthdate;
    private String phone;
    private String address;
    private String role;
    private String gender;

    public UserResource(SportsMenDTO sportsMenDTO) {
        this.dtoId = sportsMenDTO.getId();
        this.firstname = sportsMenDTO.getFirstname();
        this.lastname = sportsMenDTO.getLastname();
        this.email = sportsMenDTO.getEmail();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
        this.birthdate = simpleDateFormat.format(sportsMenDTO.getBirthdate());
        this.phone = sportsMenDTO.getPhone();
        this.address = sportsMenDTO.getAddress();
        this.role = sportsMenDTO.getRole().toString();
        this.gender = sportsMenDTO.getGendre().toString();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public long getDtoId() {
        return dtoId;
    }

    public void setDtoId(long dtoId) {
        this.dtoId = dtoId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
