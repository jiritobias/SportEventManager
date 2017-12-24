package cz.muni.fi.pa165.restapi.hateoas;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import cz.fi.muni.pa165.dto.SportDTO;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

@Relation(value = "sport", collectionRelation = "sports")
@JsonPropertyOrder({"id", "name"})
public class SportResource extends ResourceSupport {

    @JsonProperty("id")
    private long dtoId;
    private String name;

    public SportResource(SportDTO sportDTO) {
        dtoId = sportDTO.getId();
        name = sportDTO.getName();
    }

    public long getDtoId() {
        return dtoId;
    }

    public String getName() {
        return name;
    }

    public void setDtoId(long dtoId) {
        this.dtoId = dtoId;
    }

    public void setName(String name) {
        this.name = name;
    }
}
