package cz.muni.fi.pa165.restapi.hateoas;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import cz.fi.muni.pa165.dto.SportsMenDTO;
import cz.muni.fi.pa165.service.StringService;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

@Relation(value = "userSimple", collectionRelation = "usersSimple")
@JsonPropertyOrder({"id", "firstname", "lastname", "email"})
public class UserSimpleResource extends ResourceSupport {

    @JsonProperty("id")
    private long dtoId;
    private String firstname;
    private String lastname;
    private String email;

    public UserSimpleResource(SportsMenDTO sportsMenDTO) {

        this.dtoId = sportsMenDTO.getId();
        this.firstname = sportsMenDTO.getFirstname();
        this.lastname = sportsMenDTO.getLastname();
        this.email = sportsMenDTO.getEmail();
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
}
