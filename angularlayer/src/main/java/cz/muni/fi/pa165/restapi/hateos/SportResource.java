package cz.muni.fi.pa165.restapi.hateos;

import cz.fi.muni.pa165.dto.SportDTO;
import org.springframework.hateoas.ResourceSupport;

public class SportResource extends ResourceSupport {
    public SportResource(SportDTO sportDTO) {
    }
}
