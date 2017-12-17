package cz.muni.fi.pa165.restapi.hateoas;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import cz.fi.muni.pa165.dto.CompetitionDTO;
import cz.fi.muni.pa165.dto.SportDTO;
import cz.fi.muni.pa165.dto.SportsMenDTO;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Petra Halova on 13.12.17.
 */

@Relation(value = "competition", collectionRelation = "competitions")
@JsonPropertyOrder({"id", "sport", "sportsmen"})
public class CompetitionResource extends ResourceSupport {

    @JsonProperty
    private Long id;
    private SportDTO sport;
    private List<SportsMenDTO> sportsmen;

    public CompetitionResource(CompetitionDTO competition) {
        id = competition.getId();
        sport = competition.getSport();
        sportsmen = competition.getSportsMen();
    }

    public Long getDtoId(){
        return id;
    }

    public SportDTO getDtoSport(){
        return sport;
    }

    public List<SportsMenDTO> getDtoSportsmen(){
        return sportsmen;
    }

    public void setDtoId(Long id){
        this.id = id;
    }

    public void setDtoSport(SportDTO sport){
        this.sport = sport;
    }

    public void setDtoSportsmen(List<SportsMenDTO> sportsmen){
        this.sportsmen = new ArrayList<>();
        this.sportsmen.addAll(sportsmen);
    }
}
