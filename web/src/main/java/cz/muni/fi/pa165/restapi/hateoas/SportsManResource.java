package cz.muni.fi.pa165.restapi.hateoas;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import cz.fi.muni.pa165.dto.SportsMenDTO;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

@Relation(value = "sportsman", collectionRelation = "sportsmen")
@JsonPropertyOrder({"id", "firstname", "lastname"})
public class SportsManResource extends ResourceSupport {

    @JsonProperty("id")
    private long dtoId;
    private String firstname;
    private String lastname;

    public SportsManResource(SportsMenDTO sportsMenDTO) {
        this.dtoId = sportsMenDTO.getId();
        this.firstname = sportsMenDTO.getFirstname();
        this.lastname = sportsMenDTO.getLastname();
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
