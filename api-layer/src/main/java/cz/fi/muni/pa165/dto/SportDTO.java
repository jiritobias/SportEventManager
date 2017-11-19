package cz.fi.muni.pa165.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author jiritobias, Martin Smid
 */
@Getter
@Setter
public class SportDTO {

    private Long id;

    private String name;

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SportDTO)) return false;

        SportDTO sportDTO = (SportDTO) o;

        return name != null ? name.equals(sportDTO.name) : sportDTO.name == null;
    }
}
