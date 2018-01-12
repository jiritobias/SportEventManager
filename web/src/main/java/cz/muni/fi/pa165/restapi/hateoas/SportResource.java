package cz.muni.fi.pa165.restapi.hateoas;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import cz.fi.muni.pa165.dto.SportDTO;
import cz.fi.muni.pa165.dto.SportsMenDTO;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;
import java.util.ArrayList;
import java.util.List;

@Relation(value = "sport", collectionRelation = "sports")
@JsonPropertyOrder({"id", "name", "sportsmen"})
public class SportResource extends ResourceSupport {

    @JsonProperty("id")
    private long dtoId;
    private String name;
    @JsonProperty("sportsmen")
    private List<String> sportsmen;

    public SportResource(SportDTO sportDTO) {
        dtoId = sportDTO.getId();
        name = sportDTO.getName();
        sportsmen = new ArrayList<>();

        List<SportsMenDTO> sportsMenDTOList = sportDTO.getSportsMenDTOList();
        if(sportsMenDTOList != null){
            sportsMenDTOList.forEach(s -> sportsmen.add(s.getFirstname() + " " + s.getLastname()));
        }
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
